package screen.tools.sbs.repositories;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import screen.tools.sbs.utils.FieldString;

public class RepositoryFilterTable {
	private Hashtable<FieldString, RepositoryFilter> filterTable;
	private List<FieldString> sortedIdList;
	
	public RepositoryFilterTable() {
		filterTable = new Hashtable<FieldString, RepositoryFilter>();
		sortedIdList = new ArrayList<FieldString>();
	}
	
	public void addFilter(RepositoryFilter filter){
		filterTable.put(filter.getId(), filter);
		sortedIdList.add(filter.getId());
	}
	
	public void removeFromID(FieldString id){
		filterTable.remove(id);
		sortedIdList.remove(id);
	}
	
	public RepositoryData getDataFromFilterID(FieldString id){
		return filterTable.get(id).getData();
	}
	
	public RepositoryFilter getFromId(FieldString id){
		return filterTable.get(id);
	}
	
	public List<RepositoryData> filterDataItems(RepositoryFilter inputFilter){
		List<RepositoryData> data = new ArrayList<RepositoryData>();
		Iterator<FieldString> iterator = sortedIdList.iterator();
		while(iterator.hasNext()){
			FieldString next = iterator.next();
			RepositoryFilter repositoryFilter = filterTable.get(next);
			if(!repositoryFilter.match(inputFilter))
				data.add(repositoryFilter.getData());
		}
		return data;
	}
	
	public List<RepositoryFilter> filter(RepositoryFilter inputFilter){
		List<RepositoryFilter> data = new ArrayList<RepositoryFilter>();
		Iterator<FieldString> iterator = sortedIdList.iterator();
		while(iterator.hasNext()){
			FieldString next = iterator.next();
			RepositoryFilter repositoryFilter = getFromId(next);
			if(repositoryFilter.match(inputFilter))
				data.add(repositoryFilter);
		}
		return data;
	}
	
	public List<RepositoryData> filterUnsortedDataItems(RepositoryFilter inputFilter){
		List<RepositoryData> data = new ArrayList<RepositoryData>();
		Iterator<RepositoryFilter> iterator = filterTable.values().iterator();
		while(iterator.hasNext()){
			RepositoryFilter next = iterator.next();
			if(!next.match(inputFilter))
				data.add(next.getData());
		}
		return data;
	}
	
	public List<RepositoryFilter> filterUnsorted(RepositoryFilter inputFilter){
		List<RepositoryFilter> data = new ArrayList<RepositoryFilter>();
		Iterator<RepositoryFilter> iterator = filterTable.values().iterator();
		while(iterator.hasNext()){
			RepositoryFilter next = iterator.next();
			if(!next.match(inputFilter))
				data.add(next);
		}
		return data;
	}
	
	public List<RepositoryFilter> getList(){
		return new ArrayList<RepositoryFilter>(filterTable.values());
	}
	
	public List<RepositoryFilter> getSortedList(){
		List<RepositoryFilter> list = new ArrayList<RepositoryFilter>();
		
		Iterator<FieldString> iterator = sortedIdList.iterator();
		while (iterator.hasNext()) {
			FieldString fieldString = (FieldString) iterator.next();
			list.add(getFromId(fieldString));
		}
		return list;
	}
}
