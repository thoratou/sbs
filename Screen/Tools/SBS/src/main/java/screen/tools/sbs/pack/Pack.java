/*****************************************************************************
 * This source file is part of SBS (Screen Build System),                    *
 * which is a component of Screen Framework                                  *
 *                                                                           *
 * Copyright (c) 2008-2013 Ratouit Thomas                                    *
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

package screen.tools.sbs.pack;

import screen.tools.sbs.fields.FieldList;
import screen.tools.sbs.fields.FieldPath;
import screen.tools.sbs.objects.Entry;

public class Pack implements Entry<Pack>{
	private PackManifest properties;
	private FieldList<FieldPath> includePathList;
	private FieldList<FieldPath> libraryPathList;
	private FieldList<PackLibrary> libraryList;
	private FieldList<PackLibrary> runtimeList;	
	private FieldList<PackFlag> flagList;
	
	public Pack(){
		properties = new PackManifest();
		includePathList = new FieldList<FieldPath>(new FieldPath());
		libraryPathList = new FieldList<FieldPath>(new FieldPath());
		libraryList = new FieldList<PackLibrary>(new PackLibrary());
		runtimeList = new FieldList<PackLibrary>(new PackLibrary());
		flagList = new FieldList<PackFlag>(new PackFlag());
	}
	
	public Pack(Pack pack) {
		properties = pack.properties.copy();
		includePathList = pack.includePathList.copy();
		libraryPathList = pack.libraryPathList.copy();
		libraryList = pack.libraryList.copy();
		runtimeList = pack.runtimeList.copy();
		flagList = pack.flagList.copy();
	}

	public PackManifest getProperties() {
		return properties;
	}
	
	public FieldList<FieldPath> getIncludePathList() {
		return includePathList;
	}
	
	public FieldList<FieldPath> getLibraryPathList() {
		return libraryPathList;
	}
	
	public FieldList<PackLibrary> getLibraryList() {
		return libraryList;
	}
	
	public FieldList<PackLibrary> getRuntimeList() {
		return runtimeList;
	}
	
	public FieldList<PackFlag> getFlagList() {
		return flagList;
	}

	@Override
	public void merge(Pack pack) {
		properties.merge(pack.properties);
		includePathList.merge(pack.includePathList);
		libraryPathList.merge(pack.libraryPathList);
		libraryList.merge(pack.libraryList);
		runtimeList.merge(pack.runtimeList);
		flagList.merge(pack.flagList);
	}

	@Override
	public Pack copy() {
		return new Pack(this);
	}
}
