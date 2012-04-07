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
import screen.tools.sbs.context.defaults.EnvironmentVariablesContext;
import screen.tools.sbs.context.defaults.ProfileContext;
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.fields.FieldFile;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.objects.EnvironmentVariables;
import screen.tools.sbs.profile.Profile;
import screen.tools.sbs.profile.ProfileReader;
import screen.tools.sbs.utils.Logger;

/**
 * Action to load local or global configuration.
 * Configuration files to load are registered into .sbsconfig files.
 * If local configuration exists, load it. However, load global one.
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionProfileLoad implements Action {

	private ContextHandler contextHandler;

	/**
	 * Performs configuration load
	 * @throws ContextException 
	 * @throws FieldException 
	 */
	public void perform() throws ContextException, FieldException {
		Profile profile = contextHandler.<ProfileContext>get(ContextKeys.PROFILE).getProfile();
		EnvironmentVariables environmentVariables = contextHandler.<EnvironmentVariablesContext>get(ContextKeys.ENV_VARIABLES).getEnvironmentVariables();

		FieldString mode = environmentVariables.getFieldString("_COMPILE_MODE");
		FieldFile profileFile = new FieldFile();
		if(mode.get().equals("release")){
			FieldString var = environmentVariables.getFieldString("RELEASE_PROFILE");
			Logger.info("Use release profile : "+var.get());
			profileFile.set(var.get());
		}
		else if(mode.get().equals("debug")){
			FieldString var = environmentVariables.getFieldString("DEBUG_PROFILE");
			Logger.info("Use debug profile : "+var.get());
			profileFile.set(var.get());
		}
		else{
			//TODO other profiles
			Logger.info("Use custom profile");
		}
		
		ProfileReader reader = new ProfileReader(profile);
		reader.read(profileFile.get());
	}

	public void setContext(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

}
