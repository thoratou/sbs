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
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.fields.FieldFile;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.utils.Logger;

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
	private boolean isClean;
	
	/**
	 * Default constructor for ActionConfigure.
	 */
	public ActionConfigure() {
		isGlobal = false;
		isClean = false;
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
	 * Chooses between local or global configuration.
	 * 
	 * @param b
	 */
	public void setClean(boolean clean) {
		isClean = clean;
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
	 * @throws FieldException 
	 */
	public void perform() throws FieldException {
		//select the file to write
		if(!isGlobal && projects.isEmpty()){
			ErrorList.instance.addError("\"configure\" action without target to configure");
			ErrorList.instance.needUsage();
			return;
		}
		
		if(isClean){
			if(configs.size()>0)
				Logger.warning("clean option incompatible with configurations, -e options ignored");
			if(isGlobal){
				FieldFile fieldFile = new FieldFile();
				fieldFile.set("${SBS_HOME}/.sbsconfig");
				clean(fieldFile);
			}
			for(int i=0; i<projects.size(); i++){
				FieldFile fieldFile = new FieldFile();
				fieldFile.set(projects.get(i)+"/.sbsconfig");
				clean(fieldFile);
			}
		}
		else{
			if(isGlobal){
				FieldFile fieldFile = new FieldFile();
				fieldFile.set("${SBS_HOME}/.sbsconfig");
				write(fieldFile);
			}
			for(int i=0; i<projects.size(); i++){
				FieldFile fieldFile = new FieldFile();
				fieldFile.set(projects.get(i)+"/.sbsconfig");
				write(fieldFile);
			}			
		}
	}
	
	/**
	 * Suppress .sbsconfig file
	 * 
	 * @param file
	 * @throws FieldException 
	 */
	private void clean(FieldFile fieldFile) throws FieldException {
		if(new File(fieldFile.get()).delete())
			Logger.info("configuration cleaned : "+fieldFile.get());
		else
			ErrorList.instance.addWarning("no configuration file : "+fieldFile.get());
	}

	/**
	 * Writes .sbsconfig file
	 * 
	 * @param file
	 * @throws FieldException 
	 */
	private void write(FieldFile file) throws FieldException{
		File outFile = new File(file.get());
		try {
			FileWriter outWriter = new FileWriter(outFile,false);
			for(int i = 0; i<configs.size(); i++){
				outWriter.write(configs.get(i)+"\n");
			}
			outWriter.close();
		} catch (FileNotFoundException e) {
			ErrorList.instance.addError("Can't create file .sbsconfig");
			return;
		} catch (IOException e) {
			ErrorList.instance.addError("Can't write file .sbsconfig");
			return;
		}
	}

	public void setContext(ContextHandler contextHandler) {	}
}
