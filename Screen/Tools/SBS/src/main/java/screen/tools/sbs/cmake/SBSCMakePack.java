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

import screen.tools.sbs.utils.FieldBuildType;
import screen.tools.sbs.utils.FieldString;
import screen.tools.sbs.utils.FieldBuildType.Type;

/**
 * Class to handle information for a CMakeLists.txt file
 * 
 * @author Ratouit Thomas
 *
 */
public class SBSCMakePack {
	private FieldString projectName;
	private FieldString buildMode;
	private FieldBuildType.Type buildType;
	private Hashtable<FieldString, FieldString> compileFlags;
	private List<FieldString> projectSourceFiles;
	private List<FieldString> projectIncludeFiles;
	private List<FieldString> includeDirectories;
	private List<FieldString> linkDirectories;
	private List<FieldString> linkLibraries;
	private FieldString outputPath;
	
	public SBSCMakePack() {
		projectName = new FieldString();
		buildMode = new FieldString();
		buildType = Type.STATIC_LIBRARY;
		compileFlags = new Hashtable<FieldString, FieldString>();
		projectSourceFiles = new ArrayList<FieldString>();
		projectIncludeFiles = new ArrayList<FieldString>();
		includeDirectories = new ArrayList<FieldString>();
		linkDirectories = new ArrayList<FieldString>();
		linkLibraries = new ArrayList<FieldString>();
		outputPath = new FieldString();
	}
	
	public void setProjectName(FieldString projectName) {
		this.projectName = projectName;
	}
	
	public void setProjectName(String projectName) {
		setProjectName(new FieldString(projectName));
	}
	
	public FieldString getProjectName() {
		return projectName;
	}

	public void setBuildMode(FieldString buildMode) {
		this.buildMode = buildMode;
	}
	
	public void setBuildMode(String buildMode) {
		setBuildMode(new FieldString(buildMode));
	}

	public FieldString getBuildMode() {
		return buildMode;
	}

	public void setBuildType(FieldBuildType.Type buildType) {
		this.buildType = buildType;
	}

	public FieldBuildType.Type getBuildType() {
		return buildType;
	}

	public void setCompileFlags(Hashtable<FieldString, FieldString> compileFlags) {
		this.compileFlags = compileFlags;
	}
	
	public void addCompileFlag(FieldString flag, FieldString value) {
		if(flag!=null){
			if(flag.isValid()){
				if(value==null)
					value = new FieldString();
				compileFlags.put(flag, value);
			}
		}
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

	public void setProjectSourceFiles(List<FieldString> projectSourceFiles) {
		this.projectSourceFiles = projectSourceFiles;
	}
	
	public void addProjectSourceFile(FieldString file) {
		if(file!=null){
			if(file.isValid()){
				projectSourceFiles.add(file);
			}
		}
	}
	
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
		if(file!=null){
			if(file.isValid()){
				projectIncludeFiles.add(file);
			}
		}
	}
	
	public void addProjectIncludeFile(String file) {
		addProjectIncludeFile(new FieldString(file));
	}

	public List<FieldString> getProjectIncludeFiles() {
		return projectIncludeFiles;
	}

	public void setIncludeDirectories(List<FieldString> includeDirectories) {
		this.includeDirectories = includeDirectories;
	}
	
	public void addIncludeDirectory(FieldString file) {
		if(file!=null){
			if(file.isValid()){
				includeDirectories.add(file);
			}
		}
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
		if(file!=null){
			if(file.isValid()){
				linkDirectories.add(file);
			}
		}
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
		if(file!=null){
			if(file.isValid()){
				linkLibraries.add(file);
			}
		}
	}
	
	public void addLinkLibraries(String file) {
		addLinkLibraries(new FieldString(file));
	}

	public List<FieldString> getLinkLibraries() {
		return linkLibraries;
	}

	public void setOutputPath(FieldString outputPath) {
		this.outputPath = outputPath;
	}
	
	public void setOutputPath(String outputPath) {
		setOutputPath(new FieldString(outputPath));
	}

	public FieldString getOutputPath() {
		return outputPath;
	}
}
