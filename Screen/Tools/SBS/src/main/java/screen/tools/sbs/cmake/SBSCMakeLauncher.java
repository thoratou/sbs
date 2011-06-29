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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;

import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.EnvironmentVariablesContext;
import screen.tools.sbs.objects.EnvironmentVariables;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.utils.FieldException;
import screen.tools.sbs.utils.FieldJSONObject;
import screen.tools.sbs.utils.FieldString;
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
	
	public void launch(String sbsXmlPath) throws ContextException, FieldException{
		EnvironmentVariables variables = contextHandler.<EnvironmentVariablesContext>get(ContextKeys.ENV_VARIABLES).getEnvironmentVariables();
		
		FieldString fieldTargetEnv = variables.getFieldString("TARGET_ENV");
		FieldString fieldMakeProg = variables.getFieldString("MAKE_PROGRAM");
		FieldString fieldCCompiler = variables.getFieldString("C_COMPILER");
		FieldString fieldCppCompiler = variables.getFieldString("CPP_COMPILER");
		FieldString fieldRcCompiler = variables.getFieldString("RC_COMPILER");
		
		FieldString fieldAddVarSetString = variables.getFieldString("CMAKE_ADD_VAR_SET");
		FieldJSONObject fieldAddVarSet = new FieldJSONObject(fieldAddVarSetString);
				
		String targetEnv = fieldTargetEnv.getString();
		
		if("/".equals(sbsXmlPath))
			sbsXmlPath=".";
		
        try {
			List<String> command = new ArrayList<String>();
			command.add("cmake");
			command.add(".");
			command.add("-G");
			command.add(targetEnv);
        	
        	if(!fieldMakeProg.isEmpty()){
        		String makeProg = fieldMakeProg.getString();
				if(!makeProg.equals(""))
	        		command.add("-DCMAKE_MAKE_PROGRAM=\""+makeProg+"\"");
			}
        	
        	if(!fieldCCompiler.isEmpty()){
        		String cCompiler = fieldCCompiler.getString();
	        	if(!cCompiler.equals(""))
	        		command.add("-DCMAKE_C_COMPILER=\""+cCompiler+"\"");
        	}
        	
        	if(!fieldCppCompiler.isEmpty()){
        		String cppCompiler = fieldCppCompiler.getString();
		    	if(!cppCompiler.equals(""))
		    		command.add("-DCMAKE_CXX_COMPILER=\""+cppCompiler+"\"");
		    }
        	
        	if(!fieldRcCompiler.isEmpty()){
        		String rcCompiler = fieldRcCompiler.getString();
            	if(!rcCompiler.equals(""))
            		command.add("-DCMAKE_RC_COMPILER=\""+rcCompiler+"\"");        		
        	}
        	
        	if(!fieldAddVarSet.isEmpty()){
        		JSONObject jsonObject = fieldAddVarSet.getJSONObject();
        		Set<?> keySet = jsonObject.keySet();			
        		Iterator<?> iterator = keySet.iterator();			
        		while(iterator.hasNext()){
					Object next = iterator.next();
					if(next instanceof String){
						String key = (String) next;
						Object object = jsonObject.get(key);
						if(object == null){
							command.add("-D"+key);
						}
						if(object instanceof String){
							String value = (String)object;
							command.add("-D"+key+"=\""+value+"\"");							
						}
						else if(object instanceof Number){
							Number value = (Number)object;
							command.add("-D"+key+"="+value);							
						}
					}
        		}
			}
        	
        	StringBuffer buffer = new StringBuffer();
        	Iterator<String> iterator = command.iterator();
        	while(iterator.hasNext()){
        		buffer.append(iterator.next());
        		if(iterator.hasNext())
        			buffer.append(" ");
        	}
        	Logger.info("Cmake command : ");
        	Logger.info(buffer.toString());

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
            	ErrorList.instance.addError(s);
            }
            
        }
        catch (IOException e) {
        	ErrorList.instance.addError(e.getMessage());
        }

	}
}
