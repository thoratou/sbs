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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.EnvironmentVariablesContext;
import screen.tools.sbs.objects.EnvironmentVariables;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.GlobalSettings;
import screen.tools.sbs.utils.Logger;
import screen.tools.sbs.utils.ProcessLauncher;

/**
 * Class to launch CMake to generate makefiles and environment files
 * 
 * @author Ratouit Thomas
 *
 */
public class SBSCMakeLauncher {
	private ContextHandler contextHandler;

	public SBSCMakeLauncher(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}
	
	public void launch(String sbsXmlPath) throws ContextException{
		EnvironmentVariables variables = contextHandler.<EnvironmentVariablesContext>get(ContextKeys.ENV_VARIABLES).getEnvironmentVariables();
		ErrorList err = GlobalSettings.getGlobalSettings().getErrorList();
		
		if(!variables.contains("TARGET_ENV")){
			err.addError("undefined variable : TARGET_ENV");
		}
		if(!variables.contains("MAKE_PROGRAM")){
			err.addError("undefined variable : MAKE_PROGRAM");
		}
		if(!variables.contains("C_COMPILER")){
			err.addError("undefined variable : C_COMPILER");
		}
		if(!variables.contains("CPP_COMPILER")){
			err.addError("undefined variable : CPP_COMPILER");
		}
		
		if(err.hasErrors())
			return;
		
		String targetEnv = variables.getValue("TARGET_ENV");
		String makeProg = variables.getValue("MAKE_PROGRAM");
		makeProg = (makeProg == null) ? "" : makeProg;
		String cCompiler = variables.getValue("C_COMPILER");
		cCompiler = (cCompiler == null) ? "" : cCompiler;
		String cppCompiler = variables.getValue("CPP_COMPILER");
		cppCompiler = (cppCompiler == null) ? "" : cppCompiler;
		
		if("/".equals(sbsXmlPath))
			sbsXmlPath=".";
		
        try {

			List<String> command = new ArrayList<String>();
			command.add("cmake");
			command.add(".");
			command.add("-G");
			command.add(targetEnv);
        	if(!makeProg.equals(""))
        		command.add("-DCMAKE_MAKE_PROGRAM=\""+makeProg+"\"");
        	if(!cCompiler.equals(""))
        		command.add("-DCMAKE_C_COMPILER=\""+cCompiler+"\"");
        	if(!cppCompiler.equals(""))
        		command.add("-DCMAKE_CXX_COMPILER=\""+cppCompiler+"\"");

			String [] cmd = new String[command.size()];
        	//Logger.info("command : "+command);
        	ProcessLauncher p = new ProcessLauncher();
			p.execute(command.toArray(cmd),null,new File(sbsXmlPath));
			
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

	        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String s;
	        while ((s = stdInput.readLine()) != null) {
            	Logger.info(s);
            }
            while ((s = stdError.readLine()) != null) {
                err.addError(s);
            }
            
        }
        catch (IOException e) {
        	err.addError(e.getMessage());
        }

	}
}
