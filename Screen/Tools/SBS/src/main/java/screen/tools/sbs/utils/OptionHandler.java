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

package screen.tools.sbs.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.GlobalSettings;

public class OptionHandler {
	public enum Phase{
		LOAD_CONF,
		LOAD_XML,
		CHECK,
		DEPLOY_HEADER,
		GENERATE,
		COMPILE,
		CLEAN,
		LOAD_XML_TEST,
		CHECK_TEST,
		GENERATE_TEST,
		COMPILE_TEST,
		CLEAN_TEST,
		TEST
	}
	private String sbsXmlPath;
	private List<Phase> phaseList;
	private String sbsXmlFile;
	
	public OptionHandler(String[] args) {
		ErrorList err = GlobalSettings.getGlobalSettings().getErrorList();
		phaseList = new ArrayList<Phase>();
		sbsXmlFile = "sbs.xml";
		
		if(args.length == 0){
			err.addError("No parameters");
		}
		else if(args.length == 1){
			if("-h".equals(args[0])){
				usage();
			}
			else {
				err.addError("Bad parameter : " + args[0]);
			}
		}
		else{
			sbsXmlPath = args[0];
			//get the full path for sbs root component
			sbsXmlPath = new File(sbsXmlPath).getAbsolutePath()+"/";
			
			String phase = args[1];
			
            GlobalSettings.getGlobalSettings().getEnvironmentVariables().put("_COMPILE_MODE", "Release");
            
			boolean hasMainBuild = true;
			boolean hasTestBuild = false;
            
            for(int i=2; i<args.length; i++){
				String option = args[i];
				if("-v".equals(option)){
					GlobalSettings.getGlobalSettings().setDebug(true);
				} else if("-d".equals(option)){
					GlobalSettings.getGlobalSettings().getEnvironmentVariables().put("_COMPILE_MODE", "Debug");
				} else if("-e".equals(option)){
					if(i+1>=args.length){
						err.addError("Bad parameter / no env config : ");
					}
					else{
						String root = System.getProperty("SBS_ROOT");
						GlobalSettings.getGlobalSettings().getEnvironmentVariables().putFromFile(root+"/"+args[i+1]+".config");
						i++;
					}
				} else if("-i".equals(option)){
					if(i+1>=args.length){
						err.addError("Bad parameter / no sbs.xml file : ");
					}
					else{
						sbsXmlFile = args[i+1];
						i++;
					}
				} else if("-t".equals(option)){
					hasMainBuild = false;
					hasTestBuild = true;
				} else if("-b".equals(option)){
					hasTestBuild = true;
				}
			}
			
			if("check".equals(phase)){
            	phaseList.add(Phase.LOAD_CONF);
    			phaseList.add(Phase.LOAD_XML);
				phaseList.add(Phase.CHECK);
				if(hasTestBuild){
					phaseList.add(Phase.LOAD_XML_TEST);
					phaseList.add(Phase.CHECK_TEST);
				}
			}
			else if("deploy-header".equals(phase)){
				phaseList.add(Phase.LOAD_CONF);
    			phaseList.add(Phase.LOAD_XML);
				phaseList.add(Phase.CHECK);
				phaseList.add(Phase.DEPLOY_HEADER);
			}
            else if("generate".equals(phase)){
            	phaseList.add(Phase.LOAD_CONF);
    			phaseList.add(Phase.LOAD_XML);
				if(hasMainBuild){
					phaseList.add(Phase.CHECK);
					phaseList.add(Phase.GENERATE);
				}
				if(hasTestBuild){
					phaseList.add(Phase.LOAD_XML_TEST);
					phaseList.add(Phase.CHECK_TEST);
					phaseList.add(Phase.GENERATE_TEST);
				}
			}
			else if("compile".equals(phase)){
				if(hasMainBuild){
					phaseList.add(Phase.COMPILE);
				}
				if(hasTestBuild){
					phaseList.add(Phase.COMPILE_TEST);
				}
			}
			else if("test".equals(phase)){
				phaseList.add(Phase.TEST);
			}
			else if("build".equals(phase)){
				phaseList.add(Phase.LOAD_CONF);
				phaseList.add(Phase.LOAD_XML);
				if(hasMainBuild){
					phaseList.add(Phase.CHECK);
					phaseList.add(Phase.GENERATE);
					phaseList.add(Phase.COMPILE);
				}
				if(hasTestBuild){
					phaseList.add(Phase.LOAD_XML_TEST);
					phaseList.add(Phase.CHECK_TEST);
					phaseList.add(Phase.GENERATE_TEST);
					phaseList.add(Phase.COMPILE_TEST);
				}
			}
			else if("clean".equals(phase)){
				phaseList.add(Phase.LOAD_CONF);
				phaseList.add(Phase.LOAD_XML);
				if(hasMainBuild){
					phaseList.add(Phase.CLEAN);
				}
				if(hasTestBuild){
					phaseList.add(Phase.LOAD_XML_TEST);
					phaseList.add(Phase.CLEAN_TEST);
				}
			}
			else{
				err.addError("Bad parameter / unknown phase : " + phase);
			}
		}
	}
	
	public String getSbsXmlPath(){
		return sbsXmlPath;
	}
	
	public String getSbsXmlFile(){
		return sbsXmlFile;
	}
	
	public void usage(){
		Logger.info("Usage :");
		Logger.info("    parameters : <path-to-sbs.xml> <phase> -[options]");
		Logger.info("    phase :");
		Logger.info("        check : verify configuration");
		Logger.info("        deploy-header : deploy include files on SBS repository");
		Logger.info("        generate : generate makefiles");
		Logger.info("        compile  : compile pack sources");
		Logger.info("        build  : generate+compile");
		Logger.info("        test  : launch tests");
		Logger.info("    options :");
		Logger.info("        -e <your config file> : set specific environment configurations");
		Logger.info("        -i <your sbs.xml> : set specific sbs component file");
		Logger.info("        -v : verbose (debug mode)");
		Logger.info("        -d : debug compile");
		Logger.info("        -t : tests");
	}
	
	public List<Phase> getPhaseList(){
		return phaseList;
	}
}
