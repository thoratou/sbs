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
import java.util.List;

/**
 * 
 * @author Ratouit Thomas
 * 
 * TinyPack is the end-user view of a component
 * It will take in account only data into sbs.xml file
 * and will avoid auto imports
 *
 */
public class TinyPack extends Pack{
	private List<Dependency> runtimeList;
	private List<Import> importList;
	
	public TinyPack() {
		super();
		runtimeList = new ArrayList<Dependency>();
		importList = new ArrayList<Import>();
	}
	
	public void addRuntime(Dependency dependency) {
		if(dependency!=null)
			runtimeList.add(dependency);
		else
			ErrorList.instance.addWarning("Null FieldString for pack dependency");
	}

	public void setRuntimeList(List<Dependency> runtimeList) {
		if(runtimeList!=null)
			this.runtimeList = runtimeList;
		else
			ErrorList.instance.addWarning("Null FieldString for pack dependency list");
	}

	public List<Dependency> getRuntimeList() {
		return runtimeList;
	}
	
	public void addImport(Import import_) {
		if(import_!=null)
			importList.add(import_);
		else
			ErrorList.instance.addWarning("Null field for pack import");
	}

	public void setImportList(List<Import> importList) {
		if(importList!=null)
			this.importList = importList;
		else
			ErrorList.instance.addWarning("Null field for pack import list");
	}

	public List<Import> getImportList() {
		return importList;
	}

}
