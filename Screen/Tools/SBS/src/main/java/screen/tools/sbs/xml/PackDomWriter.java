package screen.tools.sbs.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.objects.ProjectProperties;
import screen.tools.sbs.objects.TinyPack;
import screen.tools.sbs.utils.FieldString;

public class PackDomWriter {

	private final ContextHandler contextHandler;

	public PackDomWriter(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

	public void write(TinyPack pack, String path, String file) {
		Element root = new Element("pack");
		Document document = new Document(root);
		
		ProjectProperties properties = pack.getProperties();
		writeProperties(properties,root);
		
		writeDom(document,path,file);
	}

	private void writeProperties(ProjectProperties properties, Element root) {
		FieldString name = properties.getName();
		FieldString version = properties.getVersion();
		FieldString buildType = properties.getBuildType();
		
		boolean nameValid = name.isValid();
		boolean versionValid = version.isValid();
		boolean buildTypeValid = buildType.isValid();
		
		if(nameValid || versionValid || buildTypeValid){
			Element propertiesElt = new Element("properties");
			
			if(nameValid){
				Element nameElt = new Element("name");
				nameElt.setText(name.getString());
				propertiesElt.addContent(nameElt);
			}
			
			if(versionValid){
				Element versionElt = new Element("version");
				versionElt.setText(version.getString());
				propertiesElt.addContent(versionElt);
			}
			
			if(buildTypeValid){
				Element buildTypeElt = new Element("buildtype");
				buildTypeElt.setText(buildType.getString());
				propertiesElt.addContent(buildTypeElt);
			}
			
			root.addContent(propertiesElt);
		}
	}

	private void writeDom(Document document, String path, String file) {
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		try {
			outputter.output(document, new FileOutputStream(path+"/"+file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
