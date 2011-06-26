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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.utils.FieldBuildType;
import screen.tools.sbs.utils.FieldObject;
import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.utils.FieldString;

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
	private FieldBuildType buildType;
	private Hashtable<FieldString, FieldObject> compileFlags;
	//private List<FieldString> projectSourceFiles;
	//private List<FieldString> projectIncludeFiles;
	private List<FieldPath> includeDirectories;
	private List<FieldPath> linkDirectories;
	private List<FieldString> linkLibraries;
	private FieldPath outputPath;
	private boolean isTest;
	
	public CMakePack() {
		projectName = new FieldString();
		projectVersion = new FieldString();
		buildMode = new FieldString();
		buildType = new FieldBuildType();
		compileFlags = new Hashtable<FieldString, FieldObject>();
		//projectSourceFiles = new ArrayList<FieldString>();
		//projectIncludeFiles = new ArrayList<FieldString>();
		includeDirectories = new ArrayList<FieldPath>();
		linkDirectories = new ArrayList<FieldPath>();
		linkLibraries = new ArrayList<FieldString>();
		outputPath = new FieldPath();
		isTest = false;
	}
	
	public void setVersion(FieldString cmakeVersion) {
		if(cmakeVersion!=null)
			this.cmakeVersion = cmakeVersion;
		else
			ErrorList.instance.addWarning("Null FieldString for cmakeVersion");
	}

	public FieldString getVersion() {
		return cmakeVersion;
	}
	
	public void setVersion(String cmakeversion) {
		setVersion(new FieldString(cmakeversion));
	}

	public void setProjectName(FieldString projectName) {
		if(projectName!=null)
			this.projectName = projectName;
		else
			ErrorList.instance.addWarning("Null FieldString for projectName");
	}
	
	public void setProjectName(String projectName) {
		setProjectName(new FieldString(projectName));
	}
	
	public FieldString getProjectName() {
		return projectName;
	}
	
	public void setProjectVersion(FieldString projectVersion) {
		if(projectVersion!=null)
			this.projectVersion = projectVersion;
		else
			ErrorList.instance.addWarning("Null FieldString for projectVersion");
	}
	
	public void setProjectVersion(String projectVersion) {
		setProjectVersion(new FieldString(projectVersion));
	}
	
	public FieldString getProjectVersion() {
		return projectVersion;
	}

	public void setBuildMode(FieldString buildMode) {
		if(buildMode!=null)
			this.buildMode = buildMode;
		else
			ErrorList.instance.addWarning("Null FieldString for buildMode");
	}
	
	public void setBuildMode(String buildMode) {
		setBuildMode(new FieldString(buildMode));
	}

	public FieldString getBuildMode() {
		return buildMode;
	}

	public void setBuildType(FieldBuildType buildType) {
		if(buildType!=null)
			this.buildType = buildType;
		else
			ErrorList.instance.addWarning("Null FieldString for buildType");
	}

	public void setBuildType(FieldBuildType.Type buildType) {
		this.buildType = new FieldBuildType(buildType);
	}

	public FieldBuildType getBuildType() {
		return buildType;
	}

	public void setCompileFlags(Hashtable<FieldString, FieldObject> compileFlags) {
		this.compileFlags = compileFlags;
	}
	
	public void addCompileFlag(FieldString key, FieldObject object) {
		if(key!=null){
			if(object == null)
				object =  new FieldObject();
			compileFlags.put(key, object);
		}
		else
			ErrorList.instance.addWarning("Null FieldString for compileFlag");
	}

	public void addCompileFlag(FieldString flag, Object value) {
		addCompileFlag(flag, new FieldObject(value));
	}

	public void addCompileFlag(String flag, FieldObject value) {
		addCompileFlag(new FieldString(flag), value);
	}

	
	public void addCompileFlag(String flag, Object value) {
		addCompileFlag(new FieldString(flag), new FieldObject(value));
	}

	public Hashtable<FieldString, FieldObject> getCompileFlags() {
		return compileFlags;
	}

	/*
	public void setProjectSourceFiles(List<FieldString> projectSourceFiles) {
		this.projectSourceFiles = projectSourceFiles;
	}
	
	public void addProjectSourceFile(FieldString file) {
		if(projectSourceFile!=null)
			this.projectSourceFile.add(file);
		else
			err.addWarning("Null FieldString for projectSourceFile");	}
	
	public void addProjectSourceFile(String file) {
		addProjectSourceFile(new FieldString(file));
	}

	public List<FieldString> getProjectSourceFiles() {
		return projectSourceFiles;
	}
	

	public void setProjectIncludeFiles(List<FieldString> projectIncludeFiles) {
		this.projectIncludeFiles = projectIncludeFiles;
	}
	
	public void addProjectIncludeFile(FieldString file) {
		if(file!=null)
			projectIncludeFiles.add(file);
		else
			err.addWarning("Null FieldString for projectIncludeFile");	}
	}
	
	public void addProjectIncludeFile(String file) {
		addProjectIncludeFile(new FieldString(file));
	}

	public List<FieldString> getProjectIncludeFiles() {
		return projectIncludeFiles;
	}
	*/

	public void setIncludeDirectories(List<FieldPath> includeDirectories) {
		this.includeDirectories = includeDirectories;
	}
	
	public void addIncludeDirectory(FieldPath file) {
		if(file!=null)
			includeDirectories.add(file);
		else
			ErrorList.instance.addWarning("Null FieldString for includeDirectory");
	}
	
	public void addIncludeDirectory(String file) {
		addIncludeDirectory(new FieldPath(file));
	}

	public List<FieldPath> getIncludeDirectories() {
		return includeDirectories;
	}

	public void setLinkDirectories(List<FieldPath> linkDirectories) {
		this.linkDirectories = linkDirectories;
	}
	
	public void addLinkDirectory(FieldPath file) {
		if(file!=null)
			linkDirectories.add(file);
		else
			ErrorList.instance.addWarning("Null FieldString for linkDirectory");
	}
	
	public void addLinkDirectory(String file) {
		addLinkDirectory(new FieldPath(file));
	}

	public List<FieldPath> getLinkDirectories() {
		return linkDirectories;
	}

	public void setLinkLibraries(List<FieldString> linkLibraries) {
		this.linkLibraries = linkLibraries;
	}
	
	public void addLinkLibraries(FieldString file) {
		if(file!=null)
			linkLibraries.add(file);
		else
			ErrorList.instance.addWarning("Null FieldString for linkLibrary");
	}
	
	public void addLinkLibraries(String file) {
		addLinkLibraries(new FieldString(file));
	}

	public List<FieldString> getLinkLibraries() {
		return linkLibraries;
	}

	public void setOutputPath(FieldPath outputPath) {
		if(outputPath!=null)
			this.outputPath = outputPath;
		else
			ErrorList.instance.addWarning("Null FieldString for outputPath");
	}
	
	public void setOutputPath(String outputPath) {
		setOutputPath(new FieldPath(outputPath));
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
