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

package screen.tools.sbs;

import screen.tools.sbs.actions.ActionManager;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.GlobalSettings;
import screen.tools.sbs.targets.Parameters;
import screen.tools.sbs.targets.TargetManager;
import screen.tools.sbs.utils.Logger;

/**
 * Main class, entry point for sbs commands
 * 
 * @author Ratouit Thomas
 * 
 */
public class Main {
	/**
	 * Logs registered errors and warnings.
	 * 
	 * @return has error during the process
	 * 
	 */
	private static boolean checkErrors(){
		ErrorList err = GlobalSettings.getGlobalSettings().getErrorList();
		if(err.hasErrors()){
			Logger.info("errors detected");
			Logger.info("Logged errors (" + err.getErrors().size() + ") :");
			err.displayErrors();
			if(err.hasWarnings()){
				Logger.info("Logged warnings (" + err.getWarnings().size() + ")");
				err.displayWarnings();
			}
			Logger.info("============== STOP ===============");
			return false;
		}else if(err.hasWarnings()){
			Logger.info("warnings detected");
			Logger.info("Logged warnings (" + err.getWarnings().size() + ")");
			err.displayWarnings();
		}
		return true;
	}
	
	/**
	 * Software entry / Main method.
	 * 
	 * @param args
	 * @throws Exception
	 * 
	 */
	public static void main(String[] args) throws Exception {
		Logger.info("------------ begin SBS ------------");
		
		String root = System.getProperty("SBS_ROOT");
		GlobalSettings.getGlobalSettings().getEnvironmentVariables().put("SBS_ROOT", root);
		
		ActionManager actionManager = new ActionManager();
		TargetManager targetManager = new TargetManager(actionManager);
		Parameters parameters = new Parameters(args);
		
		//register actions for a given target
		targetManager.call(parameters.getTargetCall(), parameters);
		//verify that there is no error to resume the process
		checkErrors();
		if(GlobalSettings.getGlobalSettings().isPrintUsage()){
			//print help
			targetManager.callUsage(GlobalSettings.getGlobalSettings().getTargetUsage());
		}
		else{
			//process registered actions
			actionManager.processActions();
			//verify that there is no error to resume the process
			checkErrors();
			if(GlobalSettings.getGlobalSettings().isPrintUsage())
				//print help
				targetManager.callUsage(GlobalSettings.getGlobalSettings().getTargetUsage());
		}
		/*
		Logger.info("----- begin parse parameters ------");
		OptionHandler optHandler = new OptionHandler(args);
		if(!checkErrors()){
			optHandler.usage();
			return;
		}
		Logger.info("------ end parse parameters -------");
		
		List<Phase> phaseList = optHandler.getPhaseList();
		Pack pack = new Pack();
		Pack testPack = new Pack();
		SBSDomDataFiller dataFiller = null;
		Document doc = null;
		
		for(int i=0; i<phaseList.size(); i++){
			Phase phase = phaseList.get(i);
			
			if(phase == Phase.LOAD_CONF){
				Logger.info("---- begin load configuration -----");
				String root = System.getProperty("SBS_ROOT");
				GlobalSettings.getGlobalSettings().getEnvironmentVariables().putFromFile(root+"/sbs.config");
				if(!checkErrors()) return;
				Logger.info("----- end load configuration ------");
			}
			else if(phase == Phase.LOAD_XML){
				Logger.info("-------- begin XML parsing --------");
				doc = SBSDomParser.parserFile(new File(optHandler.getSbsXmlPath()+optHandler.getSbsXmlFile()));
				if(!checkErrors()) return;
				Logger.info("--------- end XML parsing ---------");
				
				Logger.info("--------- begin data fill ---------");
				dataFiller = new SBSDomDataFiller(pack,testPack,new FieldPath(optHandler.getSbsXmlPath()));
				dataFiller.fill(doc,false);
				if(!checkErrors()) return;
				Logger.info("---------- end data fill ----------");
			}
			else if(phase == Phase.CHECK){
				Logger.info("------- begin check fields --------");
				checkFields(pack);
				if(!checkErrors()) return;
				Logger.info("-------- end check fields ---------");
			}
			else if(phase == Phase.DEPLOY_HEADER){
				Logger.info("---- begin deploy header files ----");
				SBSHeaderDeployer deployer = new SBSHeaderDeployer();
				deployer.deploy(optHandler.getSbsXmlPath(), pack);
				Logger.info("----- end deploy header files -----");
			}
			else if(phase == Phase.GENERATE){
				Logger.info("----- begin generate makefile -----");
				SBSCMakeFileGenerator generator = new SBSCMakeFileGenerator(pack, optHandler.getSbsXmlPath(), false);
				generator.generate();
				if(!checkErrors()) return;
				SBSCMakeLauncher launcher = new SBSCMakeLauncher();
				launcher.launch(optHandler.getSbsXmlPath());
				if(!checkErrors()) return;
				Logger.info("------ end generate makefile ------");
			}
			else if(phase == Phase.COMPILE){
				Logger.info("-------- begin compilation --------");
				Logger.warning("TODO");
				Logger.info("--------- end compilation ---------");
			}
			else if(phase == Phase.CLEAN){
				Logger.info("----------- begin clean -----------");
				SBSCMakeCleaner cleaner = new SBSCMakeCleaner();
				cleaner.clean(pack, optHandler.getSbsXmlPath());
				Logger.info("------------ end clean ------------");
			}
			else if(phase == Phase.LOAD_XML_TEST){
				Logger.info("--------- begin data fill ---------");
				dataFiller.fill(doc,true);
				if(!checkErrors()) return;
				Logger.info("---------- end data fill ----------");
			}
			else if(phase == Phase.CHECK_TEST){
				Logger.info("----- begin check test fields -----");
				checkFields(testPack);
				if(!checkErrors()) return;
				Logger.info("------ end check test fields ------");
			}
			else if(phase == Phase.GENERATE_TEST){
				Logger.info("----- begin generate test makefile -----");
				SBSCMakeFileGenerator generator = new SBSCMakeFileGenerator(testPack, optHandler.getSbsXmlPath()+"test/", true);
				generator.generate();
				if(!checkErrors()) return;
				SBSCMakeLauncher launcher = new SBSCMakeLauncher();
				launcher.launch(optHandler.getSbsXmlPath()+"test/");
				if(!checkErrors()) return;
				Logger.info("------ end generate test makefile ------");
			}
			else if(phase == Phase.COMPILE_TEST){
				Logger.info("-------- begin compilation --------");
				Logger.warning("TODO");
				Logger.info("--------- end compilation ---------");
			}
			else if(phase == Phase.CLEAN_TEST){
				Logger.info("-------- begin clean test ---------");
				SBSCMakeCleaner cleaner = new SBSCMakeCleaner();
				cleaner.clean(testPack, optHandler.getSbsXmlPath()+"test/");
				Logger.info("--------- end clean test ----------");
			}
			else if(phase == Phase.TEST){
				Logger.info("----------- begin test ------------");
				Logger.warning("TODO");
				Logger.info("------------ end test -------------");
			}
		}
		*/
		
		Logger.info("------------- end SBS -------------");
		Logger.info("");
		Logger.info("-----------------------------------");
		Logger.info("        COMMAND SUCCESSFUL         ");
		Logger.info("-----------------------------------");
	}
}
