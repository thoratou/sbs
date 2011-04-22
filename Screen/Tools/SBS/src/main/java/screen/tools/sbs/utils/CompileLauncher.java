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

package screen.tools.sbs.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.EnvironmentVariablesContext;
import screen.tools.sbs.context.defaults.SbsFileAndPathContext;
import screen.tools.sbs.objects.EnvironmentVariables;
import screen.tools.sbs.objects.ErrorList;

public class CompileLauncher {
	private boolean isTest;
	private ContextHandler contextHandler;

	public CompileLauncher(ContextHandler contextHandler, boolean test){
		this.contextHandler = contextHandler;
		isTest = test;
	}
	
	public void launch() throws ContextException{
		EnvironmentVariables variables = contextHandler.<EnvironmentVariablesContext>get(ContextKeys.ENV_VARIABLES).getEnvironmentVariables();
		String path = null;
		if(isTest)
			path = contextHandler.<SbsFileAndPathContext>get(ContextKeys.SBS_FILE_AND_PATH).getSbsXmlPath()+"test/";
		else
			path = contextHandler.<SbsFileAndPathContext>get(ContextKeys.SBS_FILE_AND_PATH).getSbsXmlPath();
		
		if(!variables.contains("COMPILE_COMMAND")){
			ErrorList.instance.addError("undefined variable : COMPILE_COMMAND");
		}
		String compileCommand = variables.getValue("COMPILE_COMMAND");
		
        try {
        	String[] cmd = compileCommand.split(" ");
        	//Logger.info("command : "+command);
        	ProcessLauncher p = new ProcessLauncher();
			p.execute(cmd,null,new File(path));
			
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

	        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String s;
	        while ((s = stdInput.readLine()) != null) {
            	Logger.info(s);
            }
            while ((s = stdError.readLine()) != null) {
            	if(s.contains("Creating library file"))
            		//Wascana and MSYS log error just for .a files associated to a .dll
            		Logger.info(s);
            	else
            		ErrorList.instance.addError(s);
            }
            
        }
        catch (IOException e) {
        	ErrorList.instance.addError(e.getMessage());
        }
	}
}
