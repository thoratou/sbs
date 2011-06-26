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

package screen.tools.sbs.objects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import screen.tools.sbs.utils.FieldException;

public class Pack {
	private ProjectProperties properties;
	private List<Dependency> dependencyList;
	private List<Flag> flagList;
	private Hashtable<String, Description> descriptionList;
	
	public Pack(){
		properties = new ProjectProperties();
		dependencyList = new ArrayList<Dependency>();
		flagList = new ArrayList<Flag>();
		descriptionList =  new Hashtable<String, Description>();
	}
	
	public void setProperties(ProjectProperties properties) {
		if(properties!=null)
			this.properties = properties;
		else
			ErrorList.instance.addWarning("Null FieldString for pack properties");
	}
	public ProjectProperties getProperties() {
		return properties;
	}
	
	public void addDependency(Dependency dependency) {
		if(dependency!=null)
			dependencyList.add(dependency);
		else
			ErrorList.instance.addWarning("Null FieldString for pack dependency");
	}

	public void setDependencyList(List<Dependency> dependencyList) {
		if(dependencyList!=null)
			this.dependencyList = dependencyList;
		else
			ErrorList.instance.addWarning("Null FieldString for pack dependency list");
	}

	public List<Dependency> getDependencyList() {
		return dependencyList;
	}
	
	public void addFlag(Flag flag) {
		if(flag!=null)
			flagList.add(flag);
		else
			ErrorList.instance.addWarning("Null FieldString for pack flag");
	}

	public void setFlagList(List<Flag> flagList) {
		if(flagList!=null)
			this.flagList = flagList;
		else
			ErrorList.instance.addWarning("Null FieldString for pack flag list");
	}

	public List<Flag> getFlagList() {
		return flagList;
	}
	
	public void addDescription(Description desc) {
		try {
		if(desc!=null)
				descriptionList.put(desc.getName().getString(),desc);
		else
			ErrorList.instance.addWarning("Null FieldString for pack description");
		} catch (FieldException e) {
			ErrorList.instance.addWarning("Can't add invalid description");
		}
	}

	public void setDescriptionMap(Hashtable<String, Description> descriptionList) {
		if(descriptionList!=null)
			this.descriptionList = descriptionList;
		else
			ErrorList.instance.addWarning("Null FieldString for pack description list");
	}

	public List<Description> getDescriptionList() {
		List<Description> descs = new ArrayList<Description>();
		Set<String> set = descriptionList.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String s = it.next();
			descs.add(descriptionList.get(s));
		}
		return descs;
	}
	
	public Hashtable<String,Description> getDescriptionMap(){
		return descriptionList;
	}
	
	public Description getDescription(String string){
		if(string!=null)
			if(descriptionList.containsKey(string))
				return descriptionList.get(string);
		return null;
	}
}
