/*****************************************************************************
 * This source file is part of SBS (Screen Build System),                    *
 * which is a component of Screen Framework                                  *
 *                                                                           *
 * Copyright (c) 2008-2010 Ratouit Thomas                                    *
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

package screen.tools.sbs.targets;

import java.util.Enumeration;
import java.util.Hashtable;

import screen.tools.sbs.actions.ActionManager;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.GlobalSettings;
import screen.tools.sbs.targets.defaults.TargetBuild;
import screen.tools.sbs.targets.defaults.TargetCheck;
import screen.tools.sbs.targets.defaults.TargetClean;
import screen.tools.sbs.targets.defaults.TargetConfigure;
import screen.tools.sbs.targets.defaults.TargetGenerate;
import screen.tools.sbs.targets.defaults.TargetHelp;
import screen.tools.sbs.targets.defaults.TargetRepositories;
import screen.tools.sbs.targets.defaults.TargetRun;
import screen.tools.sbs.targets.defaults.TargetTest;
import screen.tools.sbs.utils.Logger;

public class TargetManager {
	private Hashtable<TargetCall, Target> targets;
	private ActionManager actionManager;
	
	public TargetManager(ActionManager actionManager) {
		this.actionManager = actionManager; 
		targets = new Hashtable<TargetCall, Target>();
		registerDefaultTarget();
	}

	private void registerDefaultTarget() {
		//here add default SBS targets
		registerTarget(new TargetConfigure());
		registerTarget(new TargetClean());
		registerTarget(new TargetCheck());
		registerTarget(new TargetGenerate());
		registerTarget(new TargetBuild());
		registerTarget(new TargetTest());
		registerTarget(new TargetRun());
		registerTarget(new TargetRepositories());
		registerTarget(new TargetHelp());
	}
	
	public void call(TargetCall targetCall, Parameters parameters){
		ErrorList err = GlobalSettings.getGlobalSettings().getErrorList();
		Target target = targets.get(targetCall);
		if(target!=null)
			target.registerActions(actionManager,parameters);
		else{
			err.addError("Unknown target \""+targetCall.getTarget()+"\"");
			GlobalSettings.getGlobalSettings().needUsage();
		}
	}
	
	public void callUsage(TargetCall targetCall){
		if(targetCall!=null){
			Target target = targets.get(targetCall);
			if(target!=null){
				target.usage();
			}
			else{
				defaultUsage();
			}
		}
		else{
			defaultUsage();
		}
	}
	
	private void defaultUsage() {
		Enumeration<Target> tars = targets.elements();
		Logger.info("usage : sbs <target> [parameters]");
		Logger.info("target list :");
		while(tars.hasMoreElements()){
			Target target = tars.nextElement();
			Logger.info("    "+target.getTargetCall().getTarget());
		}
	}

	private void registerTarget(Target target){
		Logger.debug("register target : "+target.getTargetCall().getTarget());
		targets.put(target.getTargetCall(), target);
	}	
}
