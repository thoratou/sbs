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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import screen.tools.sbs.cmake.writers.CMakeAddProjectWriter;
import screen.tools.sbs.cmake.writers.CMakeBuildFolderWriter;
import screen.tools.sbs.cmake.writers.CMakeBuildModeWriter;
import screen.tools.sbs.cmake.writers.CMakeBuildTypeWriter;
import screen.tools.sbs.cmake.writers.CMakeDefinitionListWriter;
import screen.tools.sbs.cmake.writers.CMakeFolderCleanWriter;
import screen.tools.sbs.cmake.writers.CMakeHeaderFilterWriter;
import screen.tools.sbs.cmake.writers.CMakeHeaderFolderListWriter;
import screen.tools.sbs.cmake.writers.CMakeLibraryListWriter;
import screen.tools.sbs.cmake.writers.CMakeLinkFolderListWriter;
import screen.tools.sbs.cmake.writers.CMakeProjectNameWriter;
import screen.tools.sbs.cmake.writers.CMakeProjectVersionWriter;
import screen.tools.sbs.cmake.writers.CMakeSourceFilterWriter;
import screen.tools.sbs.cmake.writers.CMakeVersionWriter;
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.EnvironmentVariablesContext;
import screen.tools.sbs.objects.Dependency;
import screen.tools.sbs.objects.EnvironmentVariables;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.Library;
import screen.tools.sbs.objects.Pack;
import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.utils.FieldString;
import screen.tools.sbs.utils.Utilities;

/**
 * Generates CMakeLists.txt and SBS files for global repository from a pack
 * 
 * @author Ratouit Thomas
 * 
 */
public class SBSCMakeFileGenerator {
	private Pack pack;
	private String sbsXmlPath;
	private boolean isTest;
	
	//context variables
	private String repoRoot;
	private String compileMode;
	private String envName;
	private String[] flags;
	private String defaultIncludePath;
	private String defaultLibPath;
	private String packName;
	private String packPath;
	private String packVersion;
	private String packBuildType;
	private boolean hasLibBuild;
	private boolean hasSharedLibBuild;
	private String typedFolder;
	private String outputPath;

	private String componentPath;

	private String compileName;

	private String fullName;

	private ContextHandler contextHandler;
	
	/**
	 * Constructor for SBSCMakeFileGenerator class
	 * 
	 * @param pack
	 * @param sbsXmlPath
	 * @param isTest
	 */
	public SBSCMakeFileGenerator(ContextHandler contextHandler, Pack pack, String sbsXmlPath, boolean isTest) {
		this.contextHandler = contextHandler;
		this.pack = pack;
		this.sbsXmlPath = sbsXmlPath;
		this.isTest = isTest;
	}
	
	private void retrieveContext() throws ContextException{
		EnvironmentVariables variables = contextHandler.<EnvironmentVariablesContext>get(ContextKeys.ENV_VARIABLES).getEnvironmentVariables();
		
		//repository root
		FieldString fieldRepoRoot = variables.getFieldString("REPOSITORY_ROOT");
		if(!fieldRepoRoot.isValid()) return;
		repoRoot = fieldRepoRoot.getString();
		
		//env name
		FieldString fieldEnvName = variables.getFieldString("ENV_NAME");
		if(!fieldEnvName.isValid()) return;
		envName = fieldEnvName.getString();

		//compile mode
		FieldString fieldCompileMode = variables.getFieldString("_COMPILE_MODE");
		if(!fieldCompileMode.isValid()) return;
		compileMode = fieldCompileMode.getString();
		
		//compile flags
		String flagVar = compileMode.toUpperCase()+"_FLAGS";
		FieldString fieldFlag = variables.getFieldString(flagVar);
		if(!fieldFlag.isValid()) return;
		String flagString = fieldFlag.getString();
		flags = flagString.split(" ");
		
		//default paths
		if(Utilities.isLinux()){
			FieldString fieldIncludePath = variables.getFieldString("DEFAULT_INCLUDE_PATH");
			if(!fieldIncludePath.isValid()) return;
			defaultIncludePath = fieldIncludePath.getString();
			
			FieldString fieldLibPath = variables.getFieldString("DEFAULT_LIB_PATH");
			if(!fieldLibPath.isValid()) return;
			defaultLibPath = fieldLibPath.getString();
		}
		
		//pack properties		
		packName = pack.getProperties().getName().getString().replaceAll("/", "");
		packPath = pack.getProperties().getName().getString();
		packVersion = pack.getProperties().getVersion().getString();

		packBuildType = pack.getProperties().getBuildType().getString();
		hasLibBuild = "static".equals(packBuildType) || "shared".equals(packBuildType);
		typedFolder = hasLibBuild ? "lib" : "exe";
		hasSharedLibBuild = "shared".equals(packBuildType); 
		
		outputPath = repoRoot+"/"+packPath+"/"+packVersion+"/"+typedFolder+"/"+envName+"/"+ compileMode;
		componentPath = repoRoot +"/"+packPath+"/"+packVersion;
		
		EnvironmentVariables additionalVars = new EnvironmentVariables();
		additionalVars.put("LIB_NAME", packName);
		additionalVars.put("EXE_NAME", packName);
		compileName = new FieldString(
				hasLibBuild ? (
					hasSharedLibBuild ?
						"${DEFAULT_SHARED_LIB_COMPILE_NAME}" :
						"${DEFAULT_STATIC_LIB_COMPILE_NAME}"
						) :
					""
				).getString(additionalVars);
		fullName = new FieldString(
				hasLibBuild ? (
					hasSharedLibBuild ?
						"${DEFAULT_SHARED_LIB_FULL_NAME}" :
						"${DEFAULT_STATIC_LIB_FULL_NAME}"
						) :
					"${DEFAULT_EXE_FULL_NAME}"
				).getString(additionalVars);
	}
	
	public void generate() throws ContextException {
		retrieveContext();
		generateCMakeLists();
		generateComponentFiles();
		generateSymbolicLinks();
	}
	
	private void generateCMakeLists(){
		try {
			//handler to write CMakeLists.txt file
			File cmakeListFile = new File(sbsXmlPath + "CMakeLists.txt");
			FileWriter cmakeListWriter = null;
			try {
				cmakeListWriter = new FileWriter(cmakeListFile,false);
			} catch (FileNotFoundException e) {
				ErrorList.instance.addError("Can't create file CMakeLists.txt : "+e.getMessage());
				return;
			}
			
			CMakePack cmakePack = new CMakePack();
			CMakePackGenerator generator = new CMakePackGenerator(pack, cmakePack);
			generator.generate();
						
			cmakePack.setVersion("2.6");
			cmakePack.setBuildMode(compileMode);
			cmakePack.setTest(isTest);
			
			for(int i=0; i<flags.length; i++){
				cmakePack.addCompileFlag(flags[i], (String)null);
			}
			
			if(!isTest){
				cmakePack.addIncludeDirectory("src");
				cmakePack.addIncludeDirectory("include");
			}
			else{
				cmakePack.addIncludeDirectory(".");
			}
			
			if(Utilities.isLinux()){
				cmakePack.addIncludeDirectory(defaultIncludePath);
				cmakePack.addLinkDirectory(defaultLibPath);				
			}
			cmakePack.setOutputPath(outputPath);

			CMakePackWriter writer = new CMakePackWriter(cmakePack, cmakeListWriter);
			writer.addSegmentWriter(new CMakeVersionWriter());
			writer.addSegmentWriter(new CMakeProjectNameWriter());
			writer.addSegmentWriter(new CMakeProjectVersionWriter());
			writer.addSegmentWriter(new CMakeBuildModeWriter());
			writer.addSegmentWriter(new CMakeBuildTypeWriter());
			writer.addSegmentWriter(new CMakeDefinitionListWriter());
			writer.addSegmentWriter(new CMakeFolderCleanWriter());
			writer.addSegmentWriter(new CMakeSourceFilterWriter());
			writer.addSegmentWriter(new CMakeHeaderFilterWriter());
			writer.addSegmentWriter(new CMakeHeaderFolderListWriter());
			writer.addSegmentWriter(new CMakeLinkFolderListWriter());
			writer.addSegmentWriter(new CMakeBuildFolderWriter());
			writer.addSegmentWriter(new CMakeAddProjectWriter());
			writer.addSegmentWriter(new CMakeLibraryListWriter());
			writer.write();
			
			cmakeListWriter.close();
		} catch (IOException e) {
			ErrorList.instance.addError(e.getMessage());
		}
	}
		
	private void generateComponentFiles(){
		try{
			//creation component root folder
			new File(outputPath).mkdirs();
			
			//self component description file writing
			if(hasLibBuild){
				File sbsComponentFile = new File(componentPath+"/component.xml");
				FileWriter sbsComponentWriter = null;
				try {
					sbsComponentWriter = new FileWriter(sbsComponentFile,false);
				} catch (FileNotFoundException e) {
					ErrorList.instance.addError("Can't create file component.xml");
					return;
				}
				sbsComponentWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				sbsComponentWriter.write("<pack>\n");
				sbsComponentWriter.write("\t<properties>\n");
				sbsComponentWriter.write("\t\t<name>"+packPath+"</name>\n");
				sbsComponentWriter.write("\t\t<version>"+packVersion+"</version>\n");
				sbsComponentWriter.write("\t\t<buildtype>"+packBuildType+"</buildtype>\n");
				sbsComponentWriter.write("\t</properties>\n");
				sbsComponentWriter.write("\t<main>\n");
				sbsComponentWriter.write("\t\t<dependencies>\n");
				sbsComponentWriter.write("\t\t\t<dependency>\n");
				sbsComponentWriter.write("\t\t\t\t<includes>\n");
				sbsComponentWriter.write("\t\t\t\t\t<path type=\"absolute\">"+new File(sbsXmlPath).getAbsolutePath()+"/include</path>\n");
				sbsComponentWriter.write("\t\t\t\t</includes>\n");
				sbsComponentWriter.write("\t\t\t\t<libraries>\n");
				sbsComponentWriter.write("\t\t\t\t\t<lib>"+packPath+"</lib>\n");
				sbsComponentWriter.write("\t\t\t\t</libraries>\n");
				sbsComponentWriter.write("\t\t\t</dependency>\n");
				
				//add exported dependencies
				List<Dependency> depList = pack.getDependencyList();
				for(int i=0; i<depList.size(); i++){
					Dependency dep = depList.get(i);
					if(dep.getExport().getBool()){
						if(dep.getName().isValid()){
							if(dep.getVersion().isValid()){
								sbsComponentWriter.write("\t\t\t<dependency name=\""+dep.getName().getString()+"\" version=\""+dep.getVersion().getString()+"\">\n");
							}
							else{
								sbsComponentWriter.write("\t\t\t<dependency name=\""+dep.getName().getString()+"\">\n");
							}
						}
						else{
							if(dep.getVersion().isValid()){
								sbsComponentWriter.write("\t\t\t<dependency version=\""+dep.getVersion().getString()+"\">\n");
							}
							else{
								sbsComponentWriter.write("\t\t\t<dependency>\n");
							}
						}
						if(dep.getIncludePathList().size() > 0){
							sbsComponentWriter.write("\t\t\t\t<includes>\n");
							List<FieldPath> incs = dep.getIncludePathList();
							for(int j=0; j<incs.size(); j++){
								sbsComponentWriter.write("\t\t\t\t\t<path type=\"absolute\" build=\""+incs.get(j).getBuildMode().getAsString()+"\">"+incs.get(j).getString()+"</path>\n");
							}
							sbsComponentWriter.write("\t\t\t\t</includes>\n");
						}
						if(dep.getLibraryPathList().size() > 0 || dep.getLibraryList().size() > 0){
							sbsComponentWriter.write("\t\t\t\t<libraries>\n");
							List<FieldPath> libPaths = dep.getLibraryPathList();
							for(int j=0; j<libPaths.size(); j++){
								sbsComponentWriter.write("\t\t\t\t\t<path type=\"absolute\" build=\""+libPaths.get(j).getBuildMode().getAsString()+"\">"+libPaths.get(j).getString()+"</path>\n");
							}
							List<Library> libs = dep.getLibraryList();
							for(int j=0; j<libs.size(); j++){
								if(libs.get(j).getVersion().isValid())
									sbsComponentWriter.write("\t\t\t\t\t<lib version=\""+libs.get(j).getVersion().getString()+"\">"+libs.get(j).getName().getString()+"</lib>\n");
								else	
									sbsComponentWriter.write("\t\t\t\t\t<lib>"+libs.get(j).getName().getString()+"</lib>\n");
							}
							sbsComponentWriter.write("\t\t\t\t</libraries>\n");
						}
						sbsComponentWriter.write("\t\t\t</dependency>\n");
					}
				}
				
				sbsComponentWriter.write("\t\t</dependencies>\n");
				sbsComponentWriter.write("\t</main>\n");
				sbsComponentWriter.write("\t<imports>\n");
				sbsComponentWriter.write("\t\t<import build=\"release\" file=\"lib/${ENV_NAME}/Release/library-description.xml\"/>\n");
				sbsComponentWriter.write("\t\t<import build=\"debug\" file=\"lib/${ENV_NAME}/Debug/library-description.xml\"/>\n");
				sbsComponentWriter.write("\t</imports>\n");
				sbsComponentWriter.write("</pack>\n");
				sbsComponentWriter.close();
				
				File sbsLibraryFile = new File(outputPath+"/library-description.xml");
				FileWriter sbsLibraryWriter = null;
				try {
					sbsLibraryWriter = new FileWriter(sbsLibraryFile,false);
				} catch (FileNotFoundException e) {
					ErrorList.instance.addError("Can't create file component.xml");
					return;
				}
				
				sbsLibraryWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				sbsLibraryWriter.write("<pack>\n");
				sbsLibraryWriter.write("\t<main>\n");
				sbsLibraryWriter.write("\t\t<dependencies>\n");
				sbsLibraryWriter.write("\t\t\t<dependency>\n");
				sbsLibraryWriter.write("\t\t\t\t<libraries>\n");
				sbsLibraryWriter.write("\t\t\t\t\t<path>.</path>\n");
				sbsLibraryWriter.write("\t\t\t\t</libraries>\n");
				sbsLibraryWriter.write("\t\t\t</dependency>\n");
				sbsLibraryWriter.write("\t\t</dependencies>\n");
				sbsLibraryWriter.write("\t</main>\n");
				sbsLibraryWriter.write("\t<descriptions>\n");
				sbsLibraryWriter.write("\t\t<library name=\""+packPath+"\" path=\".\" full-name=\""+fullName+"\" compile-name=\""+compileName+"\" type=\""+packBuildType+"\" />\n");
				sbsLibraryWriter.write("\t</descriptions>\n");
				sbsLibraryWriter.write("</pack>\n");
				sbsLibraryWriter.close();
			}
		} catch (IOException e) {
			ErrorList.instance.addError(e.getMessage());
		}
	}
	
	private void generateSymbolicLinks(){
		if(hasSharedLibBuild || !hasLibBuild){
			String deployPath = repoRoot+"/"+envName+"/"+compileMode;
			new File(deployPath).mkdirs();
			/*File target = new File(outputPath+"/"+fullName);
			File link = new File(deployPath+"/"+fullName+"."+packVersion);
			File finalLink = new File(deployPath+"/"+fullName);*/
			//Path targetPath = target.toPath();
			//Path linkPath = link.toPath();
			//Path finalLinkPath = finalLink.toPath();
			
			/*if(Utilities.isWindows()){
				try {
					//linkPath.createLink(targetPath);
					Files.createLink(linkPath, targetPath);
				} catch (IOException e) {
					err.addWarning("Unable to create hard link :\n"+e.getMessage());
				}
		
				try {
					//finalLinkPath.createLink(linkPath);
					Files.createLink(finalLinkPath, linkPath);
				} catch (IOException e) {
					err.addWarning("Unable to create hard link :\n"+e.getMessage());
				}
			}
			
			if(Utilities.isLinux()){
				try {
					//linkPath.createSymbolicLink(targetPath);
					Files.createSymbolicLink(targetPath, linkPath);
				} catch (IOException e) {
					err.addWarning("Unable to create symbolic link :\n"+e.getMessage());
				}
		
				try {
					//finalLinkPath.createSymbolicLink(linkPath);
					Files.createSymbolicLink(linkPath, finalLinkPath);
				} catch (IOException e) {
					err.addWarning("Unable to create symbolic link :\n"+e.getMessage());
				}
			}*/
			
		}
	}
}
