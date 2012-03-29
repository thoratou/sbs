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

package screen.tools.sbs.cmake;

import screen.tools.sbs.fields.FieldList;
import screen.tools.sbs.fields.FieldMap;
import screen.tools.sbs.fields.FieldObject;
import screen.tools.sbs.fields.FieldPath;
import screen.tools.sbs.fields.FieldString;

/**
 * Class to handle information for a CMakeLists.txt file
 * 
 * @author Ratouit Thomas
 *
 */
public class CMakePack {
	private FieldString cmakeVersion;
	private FieldString projectName;
	private FieldString projectVersion;
	private FieldString buildMode;
	private FieldString buildType;
	private FieldMap<FieldString, FieldObject> compileFlags;
	//private List<FieldString> projectSourceFiles;
	//private List<FieldString> projectIncludeFiles;
	private FieldList<FieldPath> includeDirectories;
	private FieldList<FieldPath> linkDirectories;
	private FieldList<FieldString> linkLibraries;
	private FieldPath outputPath;
	private boolean isTest;
	
	public CMakePack() {
		projectName = new FieldString();
		projectVersion = new FieldString();
		buildMode = new FieldString();
		buildType = new FieldString();
		compileFlags = new FieldMap<FieldString, FieldObject>(new FieldObject());
		//projectSourceFiles = new ArrayList<FieldString>();
		//projectIncludeFiles = new ArrayList<FieldString>();
		includeDirectories = new FieldList<FieldPath>(new FieldPath());
		linkDirectories = new FieldList<FieldPath>(new FieldPath());
		linkLibraries = new FieldList<FieldString>(new FieldString());
		outputPath = new FieldPath();
		isTest = false;
	}
	
	public FieldString getCmakeVersion() {
		return cmakeVersion;
	}
	
	public FieldString getProjectName() {
		return projectName;
	}
	
	public FieldString getProjectVersion() {
		return projectVersion;
	}

	public FieldString getBuildMode() {
		return buildMode;
	}

	public FieldString getBuildType() {
		return buildType;
	}
	
	public FieldMap<FieldString, FieldObject> getCompileFlags() {
		return compileFlags;
	}
	

	public FieldList<FieldPath> getIncludeDirectories() {
		return includeDirectories;
	}

	public FieldList<FieldPath> getLinkDirectories() {
		return linkDirectories;
	}
	
	public FieldList<FieldString> getLinkLibraries() {
		return linkLibraries;
	}

	public FieldPath getOutputPath() {
		return outputPath;
	}
	
	public void setTest(boolean isTest){
		this.isTest = isTest;
	}

	public boolean isTest() {
		return isTest;
	}
}
