/*****************************************************************************
 * This source file is part of SBS (Screen Build System),                    *
 * which is a component of Screen Framework                                  *
 *                                                                           *
 * Copyright (c) 2008-2010 Ratouit Thomas                                    *
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
import screen.tools.sbs.objects.GlobalSettings;
import screen.tools.sbs.utils.FieldBuildType;
import screen.tools.sbs.utils.FieldString;

/**
 * Class to handle information for a CMakeLists.txt file
 * 
 * @author Ratouit Thomas
 *
 */
public class CMakePack {
	private static ErrorList err = GlobalSettings.getGlobalSettings().getErrorList();
	
	private FieldString cmakeVersion;
	private FieldString projectName;
	private FieldString projectVersion;
	private FieldString buildMode;
	private FieldBuildType buildType;
	private Hashtable<FieldString, FieldString> compileFlags;
	//private List<FieldString> projectSourceFiles;
	//private List<FieldString> projectIncludeFiles;
	private List<FieldString> includeDirectories;
	private List<FieldString> linkDirectories;
	private List<FieldString> linkLibraries;
	private FieldString outputPath;
	private boolean isTest;
	
	public CMakePack() {
		projectName = new FieldString();
		projectVersion = new FieldString();
		buildMode = new FieldString();
		buildType = new FieldBuildType();
		compileFlags = new Hashtable<FieldString, FieldString>();
		//projectSourceFiles = new ArrayList<FieldString>();
		//projectIncludeFiles = new ArrayList<FieldString>();
		includeDirectories = new ArrayList<FieldString>();
		linkDirectories = new ArrayList<FieldString>();
		linkLibraries = new ArrayList<FieldString>();
		outputPath = new FieldString();
		isTest = false;
	}
	
	public void setVersion(FieldString cmakeVersion) {
		if(cmakeVersion!=null)
			this.cmakeVersion = cmakeVersion;
		else
			err.addWarning("Null FieldString for cmakeVersion");
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
			err.addWarning("Null FieldString for projectName");
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
			err.addWarning("Null FieldString for projectVersion");
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
			err.addWarning("Null FieldString for buildMode");
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
			err.addWarning("Null FieldString for buildType");
	}

	public void setBuildType(FieldBuildType.Type buildType) {
		this.buildType = new FieldBuildType(buildType);
	}

	public FieldBuildType getBuildType() {
		return buildType;
	}

	public void setCompileFlags(Hashtable<FieldString, FieldString> compileFlags) {
		this.compileFlags = compileFlags;
	}
	
	public void addCompileFlag(FieldString flag, FieldString value) {
		if(flag!=null){
			if(value==null)
				value = new FieldString();
			compileFlags.put(flag, value);
		}
		else
			err.addWarning("Null FieldString for compileFlag");
	}
	
	public void addCompileFlag(FieldString flag, String value) {
		addCompileFlag(flag, new FieldString(value));
	}
	
	public void addCompileFlag(String flag, FieldString value) {
		addCompileFlag(new FieldString(flag), value);
	}
	
	public void addCompileFlag(String flag, String value) {
		addCompileFlag(new FieldString(flag), new FieldString(value));
	}


	public Hashtable<FieldString, FieldString> getCompileFlags() {
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

	public void setIncludeDirectories(List<FieldString> includeDirectories) {
		this.includeDirectories = includeDirectories;
	}
	
	public void addIncludeDirectory(FieldString file) {
		if(file!=null)
			includeDirectories.add(file);
		else
			err.addWarning("Null FieldString for includeDirectory");
	}
	
	public void addIncludeDirectory(String file) {
		addIncludeDirectory(new FieldString(file));
	}

	public List<FieldString> getIncludeDirectories() {
		return includeDirectories;
	}

	public void setLinkDirectories(List<FieldString> linkDirectories) {
		this.linkDirectories = linkDirectories;
	}
	
	public void addLinkDirectory(FieldString file) {
		if(file!=null)
			linkDirectories.add(file);
		else
			err.addWarning("Null FieldString for linkDirectory");
	}
	
	public void addLinkDirectory(String file) {
		addLinkDirectory(new FieldString(file));
	}

	public List<FieldString> getLinkDirectories() {
		return linkDirectories;
	}

	public void setLinkLibraries(List<FieldString> linkLibraries) {
		this.linkLibraries = linkLibraries;
	}
	
	public void addLinkLibraries(FieldString file) {
		if(file!=null)
			linkLibraries.add(file);
		else
			err.addWarning("Null FieldString for linkLibrary");
	}
	
	public void addLinkLibraries(String file) {
		addLinkLibraries(new FieldString(file));
	}

	public List<FieldString> getLinkLibraries() {
		return linkLibraries;
	}

	public void setOutputPath(FieldString outputPath) {
		if(outputPath!=null)
			this.outputPath = outputPath;
		else
			err.addWarning("Null FieldString for outputPath");
	}
	
	public void setOutputPath(String outputPath) {
		setOutputPath(new FieldString(outputPath));
	}

	public FieldString getOutputPath() {
		return outputPath;
	}
	
	public void setTest(boolean isTest){
		this.isTest = isTest;
	}

	public boolean isTest() {
		return isTest;
	}
}
