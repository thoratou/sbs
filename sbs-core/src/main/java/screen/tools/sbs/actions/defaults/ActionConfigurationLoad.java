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

package screen.tools.sbs.actions.defaults;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import screen.tools.sbs.actions.Action;
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.EnvironmentVariablesContext;
import screen.tools.sbs.context.defaults.SbsFileAndPathContext;
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.objects.EnvironmentVariables;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.utils.Logger;

/**
 * Action to load local or global configuration.
 * Configuration files to load are registered into .sbsconfig files.
 * If local configuration exists, load it. However, load global one.
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionConfigurationLoad implements Action {

	private ContextHandler contextHandler;

	/**
	 * Performs configuration load
	 * @throws ContextException 
	 * @throws FieldException 
	 */
	public void perform() throws ContextException, FieldException {
		EnvironmentVariables environmentVariables = contextHandler.<EnvironmentVariablesContext>get(ContextKeys.ENV_VARIABLES).getEnvironmentVariables();
		FieldString fieldRoot = environmentVariables.getFieldString("SBS_HOME");
		String root = fieldRoot.get();
		
		String sbsXmlPath = contextHandler.<SbsFileAndPathContext>get(ContextKeys.SBS_FILE_AND_PATH).getSbsXmlPath().get();
		
		//searches local .sbsconfig
		File file = new File(sbsXmlPath+"/.sbsconfig");
		
		//searches global .sbsconfig
		if(!file.exists()){
			file = new File(root+"/.sbsconfig");
			if(!file.exists()){
				ErrorList.instance.addError("Can't find configuration, please use \"configure\" target");
				return;
			}
		}
		
		//loads selected configuration
		try {
			FileReader reader = new FileReader(file);
			char cbuf[] = new char[1024];
			String config = "";
			while(reader.read(cbuf) > 0){
				config+=new String(cbuf);
			}
			config =  config.replaceAll("\t", "");
			config =  config.replaceAll(" ", "");
			
			String[] configs = config.split("\n");
			for(int i=0; i<configs.length-1; i++){
				String configFile = root+"/"+configs[i]+".config";
				contextHandler.<EnvironmentVariablesContext>get(ContextKeys.ENV_VARIABLES).getEnvironmentVariables().putFromFile(configFile);
				Logger.info("Use configuration : "+configFile);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			ErrorList.instance.addError("Can't read configuration");
			return;
		} catch (IOException e) {
			ErrorList.instance.addError("Can't read configuration");
			return;
		}
		
		//load repositories
		//TODO
	}

	public void setContext(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

}
