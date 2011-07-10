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

import java.io.File;

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
	
	public void launch() throws ContextException, FieldException{
		EnvironmentVariables variables = contextHandler.<EnvironmentVariablesContext>get(ContextKeys.ENV_VARIABLES).getEnvironmentVariables();
		String path = null;
		if(isTest)
			path = contextHandler.<SbsFileAndPathContext>get(ContextKeys.SBS_FILE_AND_PATH).getSbsXmlPath()+"test/";
		else
			path = contextHandler.<SbsFileAndPathContext>get(ContextKeys.SBS_FILE_AND_PATH).getSbsXmlPath();
		
		FieldString fieldCompileCommand = variables.getFieldString("COMPILE_COMMAND");
		String compileCommand = fieldCompileCommand.getString();

        String[] cmd = compileCommand.split(" ");
        Logger.info(ProcessLauncher.getCommand(cmd));
		
		ProcessHandler processHandler = new ProcessHandler(cmd) {
			
			@Override
			public void processOutLine(String line) {
				Logger.info(line);					
			}
			
			@Override
			public void processErrLine(String line) {
		    	if(line.contains("Creating library file"))
		    		//Wascana and MSYS log error just for .a files associated to a .dll
		    		Logger.info(line);
		    	else{
					Logger.error(line);
		        	ErrorList.instance.addError(line);
		    	}
			}
		};
		processHandler.getProcessBuilder().directory(new File(path));
		processHandler.exec();
	}
}
