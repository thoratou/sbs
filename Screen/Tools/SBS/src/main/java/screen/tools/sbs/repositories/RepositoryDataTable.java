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
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import screen.tools.sbs.utils.FieldException;
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
	
	public List<RepositoryData> getSorterByIDList() throws FieldException{
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
