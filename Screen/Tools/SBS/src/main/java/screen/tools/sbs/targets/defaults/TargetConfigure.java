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

package screen.tools.sbs.targets.defaults;

import screen.tools.sbs.actions.ActionManager;
import screen.tools.sbs.actions.defaults.ActionConfigure;
import screen.tools.sbs.targets.Parameters;
import screen.tools.sbs.targets.Target;
import screen.tools.sbs.targets.TargetCall;
import screen.tools.sbs.utils.Logger;
import screen.tools.sbs.utils.TargetHelper;
import screen.tools.sbs.utils.targethelper.OptionEnvFiles;
import screen.tools.sbs.utils.targethelper.OptionGlobal;
import screen.tools.sbs.utils.targethelper.OptionProjects;
import screen.tools.sbs.utils.targethelper.OptionVerbose;

public class TargetConfigure implements Target {
	
	private TargetHelper helper;
	private OptionGlobal optionGlobal;
	private OptionProjects optionProjects;
	private OptionEnvFiles optionEnvFiles;
	private OptionVerbose optionVerbose;

	public TargetConfigure() {
		helper = new TargetHelper(getTargetCall());
		optionGlobal = new OptionGlobal();
		optionProjects = new OptionProjects();
		optionEnvFiles = new OptionEnvFiles();
		optionVerbose = new OptionVerbose();
		
		helper.addOption(optionGlobal);
		helper.addOption(optionProjects);
		helper.addOption(optionEnvFiles);
		helper.addOption(optionVerbose);
	}
	
	public void registerActions(ActionManager actionManager,
			Parameters parameters) {
		helper.perform(parameters);
		
		ActionConfigure action = new ActionConfigure();
		action.setGlobal(optionGlobal.isGlobal());
		
		for(int i = 0; i<optionProjects.getPaths().size(); i++){
			action.pushProject(optionProjects.getPaths().get(i));
		}
		
		for(int i = 0; i<optionEnvFiles.getFiles().size(); i++){
			action.pushConfig(optionEnvFiles.getFiles().get(i));
		}
		actionManager.pushAction(action);		
	}

	public TargetCall getTargetCall() {
		TargetCall call = new TargetCall();
		call.setTarget("configure");
		return call;
	}

	public void usage() {
		Logger.info("configure environment files to load for other commands");
		helper.usage();
	}

}
