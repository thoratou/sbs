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

package screen.tools.sbs.cmake;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;

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
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.fields.FieldJSONObject;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.objects.EnvironmentVariables;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.pack.Pack;
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
	JSONObject flagsObject;
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
	
	private void retrieveContext() throws ContextException, FieldException{
		EnvironmentVariables variables = contextHandler.<EnvironmentVariablesContext>get(ContextKeys.ENV_VARIABLES).getEnvironmentVariables();
		
		//repository root
		FieldString fieldRepoRoot = variables.getFieldString("REPOSITORY_ROOT");
		repoRoot = fieldRepoRoot.get();
		
		//env name
		FieldString fieldEnvName = variables.getFieldString("ENV_NAME");
		envName = fieldEnvName.get();

		//compile mode
		FieldString fieldCompileMode = variables.getFieldString("_COMPILE_MODE");
		compileMode = fieldCompileMode.get();
		
		//compile flags
		
		String flagVar = compileMode.toUpperCase()+"_FLAGS";
		FieldString fieldFlag = variables.getFieldString(flagVar);
		if(!fieldFlag.isEmpty()){
			FieldJSONObject jsonFlags = new FieldJSONObject();
			jsonFlags.set(fieldFlag.getOriginal());
			flagsObject = jsonFlags.getJSONObject();
		}
		else{
			flagsObject = null;
		}
		
		//default paths
		if(Utilities.isLinux()){
			FieldString fieldIncludePath = variables.getFieldString("DEFAULT_INCLUDE_PATH");
			defaultIncludePath = fieldIncludePath.get();
			
			FieldString fieldLibPath = variables.getFieldString("DEFAULT_LIB_PATH");
			defaultLibPath = fieldLibPath.get();
		}
		
		//pack properties		
		packName = pack.getProperties().getName().get().replaceAll("/", "");
		packPath = pack.getProperties().getName().get();
		packVersion = pack.getProperties().getVersion().get();

		packBuildType = pack.getProperties().getBuildType().get();
		hasLibBuild = "static".equals(packBuildType) || "shared".equals(packBuildType);
		typedFolder = hasLibBuild ? "lib" : "exe";
		hasSharedLibBuild = "shared".equals(packBuildType); 
		
		outputPath = repoRoot+"/"+packPath+"/"+packVersion+"/"+typedFolder+"/"+envName+"/"+ compileMode;
		EnvironmentVariables additionalVars = new EnvironmentVariables();
		additionalVars.put("LIB_NAME", packName);
		additionalVars.put("EXE_NAME", packName);
	}
	
	public void generate() throws ContextException, FieldException {
		retrieveContext();
		generateCMakeLists();
		generateComponentFiles();
	}
	
	private void generateCMakeLists() throws FieldException{
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
						
			cmakePack.getCmakeVersion().set("2.6");
			cmakePack.getBuildMode().set(compileMode);
			cmakePack.setTest(isTest);
			
			if(flagsObject!=null){
				Set<?> keySet = flagsObject.keySet();			
				Iterator<?> iterator = keySet.iterator();			
				while(iterator.hasNext()){
					Object next = iterator.next();
					String key = (String) next;
					FieldString keyField = new FieldString();
					keyField.set(key);
					cmakePack.getCompileFlags().allocate(keyField).setObject(flagsObject.get(key));
				}
			}
			
			if(!isTest){
				cmakePack.getIncludeDirectories().allocate().set("src");
				cmakePack.getIncludeDirectories().allocate().set("include");
			}
			else{
				cmakePack.getIncludeDirectories().allocate().set(".");
			}
			
			if(Utilities.isLinux()){
				cmakePack.getIncludeDirectories().allocate().set(defaultIncludePath);
				cmakePack.getIncludeDirectories().allocate().set(defaultLibPath);				
			}
			cmakePack.getOutputPath().set(outputPath);

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
		
	private void generateComponentFiles() throws FieldException{
		//creation component root folder
		new File(outputPath).mkdirs();
		
		//TODO
	}
}
