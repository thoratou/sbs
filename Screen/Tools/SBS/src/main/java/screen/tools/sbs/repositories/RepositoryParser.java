package screen.tools.sbs.repositories;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.GlobalSettings;
import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.utils.FieldString;

public class RepositoryParser {
	private static String repositories = "//repositories/data/repository";
	private static String filters = "//repositories/filters/filter";

	private File inputFile;
	private RepositoryDataTable dataTable;
	private RepositoryFilterTable filterTable;
	
	public RepositoryParser(File inputFile,
							RepositoryDataTable dataTable,
							RepositoryFilterTable filterTable) {
		this.inputFile = inputFile;
		this.dataTable = dataTable;
		this.filterTable = filterTable;
	}
	
	public void fill(){
		ErrorList errList = GlobalSettings.getGlobalSettings().getErrorList();
		
		Document doc = parseFile(inputFile);
		
		XPathFactory xFactory = XPathFactory.newInstance();
		XPath query = xFactory.newXPath();

		try {
			//repositories
			{
				Object result = query.compile(repositories).evaluate(doc, XPathConstants.NODESET);
	
				NodeList nodes = (NodeList) result;
				for (int i = 0; i < nodes.getLength(); i++) {
					Element element = (Element) nodes.item(i);
					RepositoryData data = new RepositoryData();
					
				    data.setId(new FieldString(element.getAttribute("id")));
				    data.setPath(new FieldPath(element.getAttribute("path")));
				    
				    FieldString fieldString = new FieldString(element.getAttribute("type"));
				    if(fieldString.isValid()){
				    	String string = fieldString.getString();
				    	String[] split = string.split(",");
				    					    	
				    	int flags = 0;
				    	for(int j=0; j<split.length; j++){
				    		String string2 = split[j];
				    		if("empty".equals(string2))
				    			flags |= RepositoryType.NO_TYPE_FLAG;
				    		else if("remote".equals(string2))
				    			flags |= RepositoryType.REMOTE_LOCAL_FLAG;
				    		else if("release".equals(string2))
				    			flags |= RepositoryType.RELEASE_SNAPSHOT_FLAG;
				    		else if("external".equals(string2))
				    			flags |= RepositoryType.EXTERNAL_INTERNAL_FLAG;
				    		else if(!"local".equals(string2) &&
				    				!"snapshot".equals(string2) &&
				    				!"internal".equals(string2))
				    			errList.addWarning("unknown repository type : "+string2);
				    	}
				    	data.setType(new RepositoryType(flags));
				    }
				    //TODO comment
				    dataTable.add(data);
				}
			}			
			
			//filters
			{
				Object result = query.compile(filters).evaluate(doc, XPathConstants.NODESET);
	
				NodeList nodes = (NodeList) result;
				for (int i = 0; i < nodes.getLength(); i++) {
					Element element = (Element) nodes.item(i);
					RepositoryFilter filter = new RepositoryFilter();
					
				    filter.setId(new FieldString(element.getAttribute("id")));
				    filter.setComponentName(new FieldString(element.getAttribute("component-name")));
				    filter.setComponentVersion(new FieldString(element.getAttribute("component-version")));
				    filter.setCompiler(new FieldString(element.getAttribute("compiler")));
				    
				    String repId = element.getAttribute("repository-id");
				    if(repId != null){
				    	if(!"".equals(repId)){
				    		RepositoryData data = dataTable.getFromId(new FieldString(repId));
				    		filter.setData(data);
				    		filterTable.addFilter(filter);
				    	}
				    }
				}
			}
		} catch (XPathExpressionException e) {
			errList.addError(e.getMessage());
		}
	}
	
	private static Document parseFile(File file){
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(file);
		}catch(ParserConfigurationException e){
			e.printStackTrace();
		}catch(SAXException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return doc;		
	}
}
