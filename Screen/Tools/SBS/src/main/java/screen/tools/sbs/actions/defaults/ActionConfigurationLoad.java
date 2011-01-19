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

package screen.tools.sbs.actions.defaults;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import screen.tools.sbs.actions.Action;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.GlobalSettings;
import screen.tools.sbs.repositories.RepositoryDataTable;
import screen.tools.sbs.repositories.RepositoryFilterTable;
import screen.tools.sbs.repositories.RepositoryParser;

/**
 * Action to load local or global configuration.
 * Configuration files to load are registered into .sbsconfig files.
 * If local configuration exists, load it. However, load global one.
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionConfigurationLoad implements Action {

	/**
	 * Performs configuration load
	 */
	public void perform() {
		ErrorList err = GlobalSettings.getGlobalSettings().getErrorList();
		String root = GlobalSettings.getGlobalSettings().getEnvironmentVariables().getValue("SBS_ROOT");
		String sbsXmlPath = GlobalSettings.getGlobalSettings().getSbsXmlPath();
		
		//searches local .sbsconfig
		File file = new File(sbsXmlPath+"/.sbsconfig");
		
		//searches global .sbsconfig
		if(!file.exists()){
			file = new File(root+"/.sbsconfig");
			if(!file.exists()){
				err.addError("Can't find configuration, please use \"configure\" target");
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
				GlobalSettings.getGlobalSettings().getEnvironmentVariables().putFromFile(root+"/"+configs[i]+".config");				
			}
			reader.close();
		} catch (FileNotFoundException e) {
			err.addError("Can't read configuration");
			return;
		} catch (IOException e) {
			err.addError("Can't read configuration");
			return;
		}
		
		//load repositories
		RepositoryDataTable repositoryDataTable = GlobalSettings.getGlobalSettings().getRepositoryDataTable();
		RepositoryFilterTable repositoryFilterTable = GlobalSettings.getGlobalSettings().getRepositoryFilterTable();
		
		String sbsRoot = GlobalSettings.getGlobalSettings().getEnvironmentVariables().getValue("SBS_ROOT");
		
		RepositoryParser parser = new RepositoryParser(
									new File(sbsRoot+"/repositories/repositories.xml"),
									repositoryDataTable,
									repositoryFilterTable);
		parser.fill();
	}

}
