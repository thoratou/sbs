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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.EnvironmentVariablesContext;
import screen.tools.sbs.objects.EnvironmentVariables;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.Pack;

public class ExecLauncher {
	private Pack pack;
	private ContextHandler contextHandler;
	
	public ExecLauncher(ContextHandler contextHandler, Pack pack) {
		this.contextHandler = contextHandler;
		this.pack = pack;
	}
	
	public void launch() throws ContextException{
		EnvironmentVariables variables = contextHandler.<EnvironmentVariablesContext>get(ContextKeys.ENV_VARIABLES).getEnvironmentVariables();
		
		FieldString fieldRepoRoot = variables.getFieldString("REPOSITORY_ROOT");
		if(!fieldRepoRoot.isValid()) return;
		String repoRoot = fieldRepoRoot.getString();
		
		FieldString fieldEnvName = variables.getFieldString("ENV_NAME");
		if(!fieldEnvName.isValid()) return;
		String envName = fieldEnvName.getString();

		FieldString fieldCompileMode = variables.getFieldString("_COMPILE_MODE");
		if(!fieldCompileMode.isValid()) return;
		String compileMode = fieldCompileMode.getString();

		String path = repoRoot+"/"+envName+"/"+compileMode;

		EnvironmentVariables addVars = new EnvironmentVariables();
		addVars.put("EXE_NAME", pack.getProperties().getName().getString().replaceAll("/", ""));
		addVars.put("EXE_VERSION", pack.getProperties().getVersion().getString());
		FieldString launchCommand = variables.getFieldString("LAUNCH_COMMAND");
		if(launchCommand.isValid(addVars)){		
		    try {
				List<String> command = new ArrayList<String>();
				command.add(path+"/"+launchCommand.getString(addVars));
		    	ProcessLauncher p = new ProcessLauncher();
		    	
		    	Logger.info(path);
		    	Iterator<String> iterator = command.iterator();
		    	while(iterator.hasNext()){
		    		String next = iterator.next();
		    		Logger.info(next);
		    	}
		    	
		    	p.execute(command.toArray(new String[command.size()]),null,new File(path));
				
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		
		        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		
		        String s;
		        while ((s = stdInput.readLine()) != null) {
		        	Logger.info(s);
		        }
		        while ((s = stdError.readLine()) != null) {
		        	ErrorList.instance.addError(s);
		        }
		        
		    }
		    catch (IOException e) {
		    	ErrorList.instance.addError(e.getMessage());
		    }
		}
	}
}
