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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import screen.tools.sbs.objects.Dependency;
import screen.tools.sbs.objects.Description;
import screen.tools.sbs.objects.EnvironmentVariables;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.Flag;
import screen.tools.sbs.objects.GlobalSettings;
import screen.tools.sbs.objects.Library;
import screen.tools.sbs.objects.Pack;
import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.utils.FieldString;
import screen.tools.sbs.utils.Logger;
import screen.tools.sbs.utils.ProcessLauncher;
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
	
	/**
	 * Constructor for SBSCMakeFileGenerator class
	 * 
	 * @param pack
	 * @param sbsXmlPath
	 * @param isTest
	 */
	public SBSCMakeFileGenerator(Pack pack, String sbsXmlPath, boolean isTest) {
		this.pack = pack;
		this.sbsXmlPath = sbsXmlPath;
		this.isTest = isTest;
	}
	
	public void generate() {
		ErrorList err = GlobalSettings.getGlobalSettings().getErrorList();
		
		try {
			//handler to write CMakeLists.txt file
			File cmakeListFile = new File(sbsXmlPath + "CMakeLists.txt");
			FileWriter cmakeListWriter = null;
			try {
				cmakeListWriter = new FileWriter(cmakeListFile,false);
			} catch (FileNotFoundException e) {
				err.addError("Can't create file CMakeLists.txt");
				return;
			}
			
			//retrieves pack properties
			String packName = pack.getProperties().getName().getString().replaceAll("/", "");
			String packPath = pack.getProperties().getName().getString();
			String packVersion = pack.getProperties().getVersion().getString();
			
			EnvironmentVariables variables = GlobalSettings.getGlobalSettings().getEnvironmentVariables();
			
			//compile mode
			String compileMode = variables.getValue("_COMPILE_MODE");
			boolean isDebugMode = "Debug".equals(compileMode);
			
			//compile flags
			String flagVar = compileMode.toUpperCase()+"_FLAGS";
			if(!variables.contains(flagVar)){
				err.addError("undefined variable : "+flagVar);
			}
			String flagString = variables.getValue(flagVar);
			String[] flags = flagString.split(" ");
			
			//build type
			String packBuildType = pack.getProperties().getBuildType().getString();
			boolean hasLibBuild = "static".equals(packBuildType) || "shared".equals(packBuildType);
			String sharedLibBuild = "shared".equals(packBuildType) ? "ON" : "OFF";
			boolean hasSharedLibBuild = "shared".equals(packBuildType); 
			
			List<Dependency> deps = pack.getDependencyList();
			
			if(!variables.contains("REPOSITORY_ROOT")){
				err.addError("undefined variable : REPOSITORY_ROOT");
			}
			String repoRoot = variables.getValue("REPOSITORY_ROOT");
			
			if(!variables.contains("ENV_NAME")){
				err.addError("undefined variable : ENV_NAME");
			}
			String envName = variables.getValue("ENV_NAME");
			
			if(err.hasErrors())
				return;
			
			//CMake headers
			cmakeListWriter.write("CMAKE_MINIMUM_REQUIRED(VERSION 2.6)\n");
			cmakeListWriter.write("\n");
			cmakeListWriter.write("PROJECT("+ packName +")\n");
			cmakeListWriter.write("\n");
			cmakeListWriter.write("SET(${PROJECT_NAME}_VERSION \""+ pack.getProperties().getVersion().getString() +"\")\n");
			cmakeListWriter.write("\n");
			cmakeListWriter.write("SET(CMAKE_BUILD_TYPE \""+ compileMode +"\")\n");
			cmakeListWriter.write("\n");
			
			//library build type 
			if(hasLibBuild){
				cmakeListWriter.write("OPTION(BUILD_SHARED_LIBS "+ sharedLibBuild +")\n");
				cmakeListWriter.write("\n");
			}
			
			//sbs.xml compilation flags
			for(int i=0; i<flags.length; i++){
				if(!"".equals(flags[i])){
					cmakeListWriter.write("ADD_DEFINITIONS(\"-D"+ flags[i] +"\")\n");
				}
			}
			
			//*.config compilation flags
			List<Flag> flagList = pack.getFlagList();
			for(int i=0; i<flagList.size(); i++){
				if(flagList.get(i).getBuildMode().isSameMode(!isDebugMode)){
					String flag = flagList.get(i).getFlag().getString();
					String value = flagList.get(i).getValue().getString();
					if(value == null || "".equals(value)){
						cmakeListWriter.write("ADD_DEFINITIONS(\"-D"+ flag +"\")\n");
					}
					else{
						cmakeListWriter.write("ADD_DEFINITIONS(\"-D"+ flag + "=" + value +"\")\n");
					}
				}
			}
			
			//remove generated files by CMake, in order to not compile them
			cmakeListWriter.write("FILE(REMOVE_RECURSE CMakeFiles/CompilerIdCXX/)\n");
			cmakeListWriter.write("FILE(REMOVE_RECURSE CMakeFiles/CompilerIdC/)\n");
			
			//component include and source files
			if(!isTest){
				cmakeListWriter.write("FILE(\n");
				cmakeListWriter.write("    GLOB_RECURSE\n");
				cmakeListWriter.write("    SRC_FILES\n");
				cmakeListWriter.write("    src/*.cpp\n");
				cmakeListWriter.write("    src/*.c\n");
				cmakeListWriter.write("    src/*.hpp\n");
				cmakeListWriter.write("    src/*.h\n");
				cmakeListWriter.write("    src/*.inl\n");
				cmakeListWriter.write("    src/*.tpp\n");
				cmakeListWriter.write("    src/*.i\n");
				cmakeListWriter.write(")\n");
				cmakeListWriter.write("\n");
				cmakeListWriter.write("FILE(\n");
				cmakeListWriter.write("    GLOB_RECURSE\n");
				cmakeListWriter.write("    INC_FILES\n");
				cmakeListWriter.write("    include/*.hpp\n");	
				cmakeListWriter.write("    include/*.h\n");
				cmakeListWriter.write("    include/*.inl\n");
				cmakeListWriter.write("    include/*.tpp\n");
				cmakeListWriter.write("    include/*.i\n");
				cmakeListWriter.write(")\n");
			} else {
				cmakeListWriter.write("FILE(\n");
				cmakeListWriter.write("    GLOB_RECURSE\n");
				cmakeListWriter.write("    SRC_FILES\n");
				cmakeListWriter.write("    *.cpp\n");
				cmakeListWriter.write("    *.c\n");
				cmakeListWriter.write("    *.hpp\n");
				cmakeListWriter.write("    *.h\n");
				cmakeListWriter.write("    *.inl\n");
				cmakeListWriter.write("    *.tpp\n");
				cmakeListWriter.write("    *.i\n");
				cmakeListWriter.write(")\n");
			}
			cmakeListWriter.write("\n");
			cmakeListWriter.write("INCLUDE_DIRECTORIES(\n");
			if(!isTest){
				cmakeListWriter.write("    src\n");
				cmakeListWriter.write("    include\n");
			}
			else{
				cmakeListWriter.write("    .\n");
			}
			
			//external include paths
			for(int i=0; i<deps.size(); i++){
				Dependency dep = deps.get(i);
				List<FieldPath> paths = dep.getIncludePathList();
				for(int j=0; j<paths.size(); j++){
					cmakeListWriter.write("    "+ paths.get(j).getString() +"\n");
				}
			}
			if(Utilities.isLinux()){
				if(!variables.contains("DEFAULT_INCLUDE_PATH")){
					err.addError("undefined variable : DEFAULT_INCLUDE_PATH");
				}
				String defaultInclude = variables.getValue("DEFAULT_INCLUDE_PATH");
				cmakeListWriter.write("    "+defaultInclude+"\n");
			}
			cmakeListWriter.write(")\n");
			cmakeListWriter.write("\n");
			
			//external library paths
			cmakeListWriter.write("LINK_DIRECTORIES(\n");
			for(int i=0; i<deps.size(); i++){
				Dependency dep = deps.get(i);
				List<FieldPath> paths = dep.getLibraryPathList();
				for(int j=0; j<paths.size(); j++){
					cmakeListWriter.write("    "+ paths.get(j).getString() +"\n");
				}
			}
			if(Utilities.isLinux()){
				if(!variables.contains("DEFAULT_LIB_PATH")){
					err.addError("undefined variable : DEFAULT_LIB_PATH");
				}
				String defaultInclude = variables.getValue("DEFAULT_LIB_PATH");
				cmakeListWriter.write("    "+defaultInclude+"\n");
			}
			cmakeListWriter.write(")\n");
			cmakeListWriter.write("\n");
			
			//output build path
			if(hasLibBuild)
				cmakeListWriter.write("SET(LIBRARY_OUTPUT_PATH "+ repoRoot +"/"+packPath+"/"+packVersion+"/lib/"+envName+"/"+ compileMode +")\n");
			else
				cmakeListWriter.write("SET(EXECUTABLE_OUTPUT_PATH "+ repoRoot +"/"+packPath+"/"+packVersion+"/exe/"+envName+"/"+ compileMode +")\n");
			cmakeListWriter.write("\n");
			
			//CMake project name
			if(hasLibBuild)
				cmakeListWriter.write("ADD_LIBRARY(\n");
			else
				cmakeListWriter.write("ADD_EXECUTABLE(\n");
			cmakeListWriter.write("    ${PROJECT_NAME}\n");
			if(hasLibBuild)
				cmakeListWriter.write("    "+packBuildType.toUpperCase()+"\n");
			cmakeListWriter.write("    ${SRC_FILES}\n");
			cmakeListWriter.write("    ${INC_FILES}\n");
			cmakeListWriter.write(")\n");
			
			//Dependency library list
			cmakeListWriter.write("TARGET_LINK_LIBRARIES(\n");
			cmakeListWriter.write("    ${PROJECT_NAME}\n");
			for(int i=0; i<deps.size(); i++){
				Dependency dep = deps.get(i);
				for(int j=0; j<dep.getLibraryList().size(); j++){
					String libName = dep.getLibraryList().get(j).getName().getString();
					Description desc = pack.getDescription(libName);
					if(desc != null){
						cmakeListWriter.write("    "+ desc.getCompileName().getString() +"\n");
					}
				}
			}
			cmakeListWriter.write(")\n");
			cmakeListWriter.write("\n");
			
			cmakeListWriter.close();
			
			//creation component root folder
			new File(repoRoot +"/"+packPath+"/"+packVersion).mkdirs();
			
			//self component description file writing
			if(hasLibBuild){
				File sbsComponentFile = new File(repoRoot +"/"+packPath+"/"+packVersion+"/component.xml");
				FileWriter sbsComponentWriter = null;
				try {
					sbsComponentWriter = new FileWriter(sbsComponentFile,false);
				} catch (FileNotFoundException e) {
					err.addError("Can't create file component.xml");
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
				
				new File(repoRoot +"/"+packPath+"/"+packVersion+"/lib/"+envName+"/"+compileMode+"/").mkdirs();
				
				File sbsLibraryFile = new File(repoRoot +"/"+packPath+"/"+packVersion+"/lib/"+envName+"/"+compileMode+"/library-description.xml");
				FileWriter sbsLibraryWriter = null;
				try {
					sbsLibraryWriter = new FileWriter(sbsLibraryFile,false);
				} catch (FileNotFoundException e) {
					err.addError("Can't create file component.xml");
					return;
				}
				
				EnvironmentVariables additionalVars = new EnvironmentVariables();
				additionalVars.put("LIB_NAME", packName);
				FieldString compileName = new FieldString(hasSharedLibBuild ? "${DEFAULT_SHARED_LIB_COMPILE_NAME}" : "${DEFAULT_STATIC_LIB_COMPILE_NAME}");
				FieldString fullName = new FieldString(hasSharedLibBuild ? "${DEFAULT_SHARED_LIB_FULL_NAME}" : "${DEFAULT_STATIC_LIB_FULL_NAME}");
				
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
				sbsLibraryWriter.write("\t\t<library name=\""+packPath+"\" path=\".\" full-name=\""+fullName.getString(additionalVars)+"\" compile-name=\""+compileName.getString(additionalVars)+"\" type=\""+packBuildType+"\" />\n");
				sbsLibraryWriter.write("\t</descriptions>\n");
				sbsLibraryWriter.write("</pack>\n");
				sbsLibraryWriter.close();
			}
			
			
			//Symbolic link generation
			if(Utilities.isLinux()){
				//On Linux, generate symbolic links to dynamic libraries and executables
				String root = System.getProperty("SBS_ROOT");
				String[] cmd = null;
				if(hasSharedLibBuild){
				   //shared library
			       cmd = new String[]{"/bin/sh",
							root+"/"+"generate-lib-sym-links.sh",
							packName,
							packVersion,
							repoRoot +"/"+packPath+"/"+packVersion+"/lib/"+envName+"/"+compileMode+"/",
							repoRoot+"/"+envName+"/"+compileMode+"/" };
				}
				else if(!hasLibBuild){
					//executable
					cmd = new String[]{"/bin/sh",
							root+"/"+"generate-exe-sym-links.sh",
							packName,
							packVersion,
							repoRoot +"/"+packPath+"/"+packVersion+"/exe/"+envName+"/"+compileMode+"/",
							repoRoot+"/"+envName+"/"+compileMode+"/" };
				}
				if(cmd!=null){
					Logger.info("command : "+ProcessLauncher.getCommand(cmd));
					
					ProcessLauncher p = new ProcessLauncher();
					p.execute(cmd,null,new File(sbsXmlPath));
					
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		
		            String s;
			        while ((s = stdInput.readLine()) != null) {
		            	Logger.info(s);
		            }
		            while ((s = stdError.readLine()) != null) {
		            	if(s.contains("ln: creating symbolic link") && s.contains("File exists"))
		            		Logger.debug(s);
		            	else
		            		err.addError(s);
		            }
				}
			}
		} catch (IOException e) {
			err.addError(e.getMessage());
		}
	}
}
