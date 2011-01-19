/*****************************************************************************
 * This source file is part of SBS (Screen Build System),                    *
 * which is a component of Screen Framework                                  *
 *                                                                           *
 * Copyright (c) 2008-2011 Ratouit Thomas                                    *
 *                                                                           *
 * This program is free software; you can redistribute it and/or modify it   *
 * under the terms of the GNU Lesser General Public License as published by  *
 * the Free Software Foundation; either version 3 of the License, or (at     *
 * your option) any later version.                                           *
 *                                                                           *
 * This program is distributed in the hope that it will be useful, but       *
 * WITHOUT ANY WARRANTY; without even the implied warranty of                *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser   *
 * General Public License for more details.                                  *
 *                                                                           *
 * You should have received a copy of the GNU Lesser General Public License  *
 * along with this program; if not, write to the Free Software Foundation,   *
 * Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA, or go to   *
 * http://www.gnu.org/copyleft/lesser.txt.                                   *
 *****************************************************************************/

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
