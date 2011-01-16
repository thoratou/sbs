package screen.tools.sbs.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import screen.tools.sbs.utils.FieldString;

public class RepositoryDataTable {
	private Hashtable<FieldString, RepositoryData> dataTable;
	
	public RepositoryDataTable() {
		dataTable = new Hashtable<FieldString, RepositoryData>();
	}
	
	public void add(RepositoryData data){
		dataTable.put(data.getId(), data);
	}
	
	public void removeFromID(FieldString id){
		dataTable.remove(id);
	}
	
	public RepositoryData getFromId(FieldString id){
		return dataTable.get(id);
	}

	public List<RepositoryData> getList(){
		List<RepositoryData> list = new ArrayList<RepositoryData>(dataTable.values());
		return list;
	}
	
	public List<FieldString> getIDList(){
		List<FieldString> idList = new ArrayList<FieldString>();
		Enumeration<FieldString> elements = dataTable.keys();
		while(elements.hasMoreElements()){
			FieldString nextElement = elements.nextElement();
			idList.add(nextElement);
		}
		return idList;
	}
	
	public List<RepositoryData> getSorterByIDList(){
		List<FieldString> idList = getIDList();
		List<String> strList = new ArrayList<String>();
		
		Iterator<FieldString> iterator = idList.iterator();
		while (iterator.hasNext()) {
			FieldString fieldString = (FieldString) iterator.next();
			String string = fieldString.getString();
			strList.add(string);
		}
		
		Collections.sort(strList);
		
		List<RepositoryData> dataList = new ArrayList<RepositoryData>();
		
		Iterator<String> iterator2 = strList.iterator();
		while (iterator2.hasNext()) {
			String string = (String) iterator2.next();
			RepositoryData fromId = getFromId(new FieldString(string));
			dataList.add(fromId);
		}
		return dataList;
	}
}
