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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import screen.tools.sbs.actions.Action;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.GlobalSettings;
import screen.tools.sbs.utils.FieldFile;

/**
 * Action to set configuration files to load for other commands.
 * This unique action is to save all configuration files set.
 * Could save local or global configuration.
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionConfigure implements Action {
	private boolean isGlobal;
	private List<String> configs;
	private List<String> projects;
	
	/**
	 * Default constructor for ActionConfigure.
	 */
	public ActionConfigure() {
		isGlobal = false;
		configs = new ArrayList<String>();
		projects = new ArrayList<String>();
	}

	/**
	 * Chooses between local or global configuration.
	 * 
	 * @param b
	 */
	public void setGlobal(boolean b) {
		isGlobal = b;
	}

	/**
	 * Adds a configuration file to save.
	 * 
	 * @param config
	 */
	public void pushConfig(String config) {
		configs.add(config);
	}
	
	/**
	 * Adds a project that will load configuration files.
	 * 
	 * @param project
	 */
	public void pushProject(String project) {
		projects.add(project);
	}

	/**
	 * Saves configuration files to load.
	 */
	public void perform() {
		ErrorList err = GlobalSettings.getGlobalSettings().getErrorList();
		//select the file to write
		if(!isGlobal && projects.isEmpty()){
			err.addError("\"configure\" action without target to configure");
			GlobalSettings.getGlobalSettings().needUsage();
			return;
		}
		
		if(isGlobal){
			write(new FieldFile("${SBS_ROOT}/.sbsconfig"));
		}
		for(int i=0; i<projects.size(); i++){
			write(new FieldFile(projects.get(i)+"/.sbsconfig"));
		}
	}
	
	/**
	 * Writes .sbsconfig file
	 * 
	 * @param file
	 */
	private void write(FieldFile file){
		ErrorList err = GlobalSettings.getGlobalSettings().getErrorList();
		File outFile = new File(file.getString());
		try {
			FileWriter outWriter = new FileWriter(outFile,false);
			for(int i = 0; i<configs.size(); i++){
				outWriter.write(configs.get(i)+"\n");
			}
			outWriter.close();
		} catch (FileNotFoundException e) {
			err.addError("Can't create file CMakeLists.txt");
			return;
		} catch (IOException e) {
			err.addError("Can't write file CMakeLists.txt");
			return;
		}
	}

	public void setContext(ContextHandler contextHandler) {}

}
