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

import screen.tools.sbs.actions.Action;
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.PackContext;
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.fields.FieldList;
import screen.tools.sbs.fields.FieldPath;
import screen.tools.sbs.pack.Pack;
import screen.tools.sbs.pack.PackFlag;
import screen.tools.sbs.pack.PackLibrary;
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
		Logger.info("properties : {");
		Logger.info("    pack = " + pack.getProperties().getName().get());
		Logger.info("    version = " + pack.getProperties().getVersion().get());
		Logger.info("    build = " + pack.getProperties().getBuildType().get());
		Logger.info("} ,");
		
		FieldList<PackLibrary> deps = pack.getLibraryList();
		Logger.info("library : {");
		for (int i=0; i<deps.size(); i++){
			Logger.info("[");
			PackLibrary dep = deps.get(i);
			if(!dep.getName().isEmpty())
				Logger.info("    name = " + dep.getName().get());
			if(!dep.getVersion().isEmpty())
				Logger.info("    version = " + dep.getVersion().get());
			Logger.info("] ,");
		}
		Logger.info("} ,");

		FieldList<FieldPath> incs = pack.getIncludePathList();
		Logger.info("includes : {");
		for(int j=0; j<incs.size(); j++){
			Logger.info("[");
			Logger.info("    path = " + incs.get(j).get());
			Logger.info("] ,");
		}
		Logger.info("} ,");
		
		FieldList<FieldPath> libPaths = pack.getLibraryPathList();
		Logger.info("library : {");
		for(int j=0; j<libPaths.size(); j++){
			Logger.info("[");
			Logger.info("    path = " + libPaths.get(j).get());
			Logger.info("] ,");
		}
		
		FieldList<PackLibrary> libs = pack.getLibraryList();
		for(int j=0; j<libs.size(); j++){
			Logger.info("[");
			if(!libs.get(j).getName().isEmpty())
				Logger.info("    library name = " + libs.get(j).getName().get());
			if(!libs.get(j).getVersion().isEmpty())
				Logger.info("    library version = " + libs.get(j).getVersion().get());
			Logger.info("] ,");
		}
		Logger.info("} ,");
		
		Logger.info("flags : {");
		FieldList<PackFlag> flags = pack.getFlagList();
		for (int i=0; i<flags.size(); i++){
			Logger.info("[");
			Logger.info("    flag = "+flags.get(i).getKey().get());
			Logger.info("    value = "+flags.get(i).getValue().getObject());
			Logger.info("] ,");
		}
		Logger.info("}");		
	}
}
