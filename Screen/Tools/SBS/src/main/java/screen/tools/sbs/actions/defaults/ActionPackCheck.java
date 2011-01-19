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

import java.util.List;

import screen.tools.sbs.actions.Action;
import screen.tools.sbs.objects.Dependency;
import screen.tools.sbs.objects.Description;
import screen.tools.sbs.objects.Flag;
import screen.tools.sbs.objects.GlobalSettings;
import screen.tools.sbs.objects.Library;
import screen.tools.sbs.objects.Pack;
import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.utils.Logger;

/**
 * Action to verify pack content.
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionPackCheck implements Action {

	/**
	 * Verifies pack content. 
	 */
	public void perform() {
		Pack pack = GlobalSettings.getGlobalSettings().getPack();
		checkFields(pack);
	}
	
	/**
	 * Static method to verify a main pack or test pack.
	 * Also used by ActionTestPackCheck class.
	 * 
	 * @param pack
	 */
	public static void checkFields(Pack pack){
		Logger.debug("Properties :");
		Logger.debug("pack = " + pack.getProperties().getName().getString());
		Logger.debug("version = " + pack.getProperties().getVersion().getString());
		Logger.debug("build = " + pack.getProperties().getBuildType().getString());
		
		Logger.debug("Dependencies :");
		List<Dependency> deps = pack.getDependencyList();
		for (int i=0; i<deps.size(); i++){
			Logger.debug("Dependency{");
			
			Dependency dep = deps.get(i);
			if(!dep.getName().isEmpty())
				Logger.debug("    name = " + dep.getName().getString());
			if(!dep.getVersion().isEmpty())
				Logger.debug("    version = " + dep.getVersion().getString());
			
			List<FieldPath> incs = dep.getIncludePathList();
			for(int j=0; j<incs.size(); j++){
				Logger.debug("    include path = " + incs.get(j).getString());
			}
			
			List<FieldPath> libPaths = dep.getLibraryPathList();
			for(int j=0; j<libPaths.size(); j++){
				Logger.debug("    library path = " + libPaths.get(j).getString());
			}
			
			List<Library> libs = dep.getLibraryList();
			for(int j=0; j<libs.size(); j++){
				if(!libs.get(j).getName().isEmpty())
					Logger.debug("    library name = " + libs.get(j).getName().getString());
				if(!libs.get(j).getVersion().isEmpty())
					Logger.debug("    library version = " + libs.get(j).getVersion().getString());
			}
			
			Logger.debug("}");
		}
		
		Logger.debug("Flags :");
		List<Flag> flags = pack.getFlagList();
		for (int i=0; i<flags.size(); i++){
			Logger.debug("Flag{");
			Logger.debug("    flag = "+flags.get(i).getFlag().getString());
			Logger.debug("    value = "+flags.get(i).getValue().getString());
			Logger.debug("}");
		}
		
		Logger.debug("Descriptions :");
		List<Description> descs = pack.getDescriptionList();
		for (int i=0; i<descs.size(); i++){
			Logger.debug("Description{");
			Logger.debug("    name = "+descs.get(i).getName().getString());
			Logger.debug("    compileName = "+descs.get(i).getCompileName().getString());
			Logger.debug("    fullName = "+descs.get(i).getFullName().getString());
			Logger.debug("    buildType = "+descs.get(i).getBuildType());
			Logger.debug("}");
		}
	}
}
