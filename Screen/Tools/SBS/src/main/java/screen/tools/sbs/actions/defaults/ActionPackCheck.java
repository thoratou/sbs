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
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.PackContext;
import screen.tools.sbs.objects.Dependency;
import screen.tools.sbs.objects.Description;
import screen.tools.sbs.objects.Flag;
import screen.tools.sbs.objects.Library;
import screen.tools.sbs.objects.Pack;
import screen.tools.sbs.utils.FieldException;
import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.utils.Logger;

/**
 * Action to verify pack content.
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionPackCheck implements Action {
	private ContextHandler contextHandler;

	/**
	 * Verifies pack content. 
	 * @throws ContextException 
	 * @throws FieldException 
	 */
	public void perform() throws ContextException, FieldException {
		//Pack pack = GlobalSettings.getGlobalSettings().getPack();
		Pack pack = contextHandler.<PackContext>get(ContextKeys.PACK).getPack();
		checkFields(pack);
	}
	
	public void setContext(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

	
	/**
	 * Static method to verify a main pack or test pack.
	 * Also used by ActionTestPackCheck class.
	 * 
	 * @param pack
	 * @throws FieldException 
	 */
	public static void checkFields(Pack pack) throws FieldException{
		Logger.info("Properties :");
		Logger.info("pack = " + pack.getProperties().getName().getString());
		Logger.info("version = " + pack.getProperties().getVersion().getString());
		Logger.info("build = " + pack.getProperties().getBuildType().getString());
		
		Logger.info("Dependencies :");
		List<Dependency> deps = pack.getDependencyList();
		for (int i=0; i<deps.size(); i++){
			Logger.info("Dependency{");
			
			Dependency dep = deps.get(i);
			if(!dep.getName().isEmpty())
				Logger.info("    name = " + dep.getName().getString());
			if(!dep.getVersion().isEmpty())
				Logger.info("    version = " + dep.getVersion().getString());
			
			List<FieldPath> incs = dep.getIncludePathList();
			for(int j=0; j<incs.size(); j++){
				Logger.info("    include path = " + incs.get(j).getString());
			}
			
			List<FieldPath> libPaths = dep.getLibraryPathList();
			for(int j=0; j<libPaths.size(); j++){
				Logger.info("    library path = " + libPaths.get(j).getString());
			}
			
			List<Library> libs = dep.getLibraryList();
			for(int j=0; j<libs.size(); j++){
				if(!libs.get(j).getName().isEmpty())
					Logger.info("    library name = " + libs.get(j).getName().getString());
				if(!libs.get(j).getVersion().isEmpty())
					Logger.info("    library version = " + libs.get(j).getVersion().getString());
			}
			
			Logger.info("}");
		}
		
		Logger.info("Flags :");
		List<Flag> flags = pack.getFlagList();
		for (int i=0; i<flags.size(); i++){
			Logger.info("Flag{");
			Logger.info("    flag = "+flags.get(i).getFlag().getString());
			Logger.info("    value = "+flags.get(i).getValue().getObject());
			Logger.info("}");
		}
		
		Logger.info("Descriptions :");
		List<Description> descs = pack.getDescriptionList();
		for (int i=0; i<descs.size(); i++){
			Logger.info("Description{");
			Logger.info("    name = "+descs.get(i).getName().getString());
			Logger.info("    compileName = "+descs.get(i).getCompileName().getString());
			if(!descs.get(i).getFullName().isEmpty())
			Logger.info("    fullName = "+descs.get(i).getFullName().getString());
			Logger.info("    buildType = "+descs.get(i).getBuildType().getString());
			Logger.info("}");
		}
	}
}
