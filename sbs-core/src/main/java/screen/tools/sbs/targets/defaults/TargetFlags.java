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

package screen.tools.sbs.targets.defaults;

import screen.tools.sbs.actions.ActionManager;
import screen.tools.sbs.actions.defaults.ActionAddFlagsTinyPack;
import screen.tools.sbs.actions.defaults.ActionAddFlagsTinyTestPack;
import screen.tools.sbs.actions.defaults.ActionConfigurationLoad;
import screen.tools.sbs.actions.defaults.ActionTinyPackLoad;
import screen.tools.sbs.actions.defaults.ActionWriteTinyPack;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ComponentPackContext;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.RepositoryContext;
import screen.tools.sbs.context.defaults.SbsFileAndPathContext;
import screen.tools.sbs.targets.Parameters;
import screen.tools.sbs.targets.Target;
import screen.tools.sbs.targets.TargetCall;
import screen.tools.sbs.utils.Logger;
import screen.tools.sbs.utils.TargetHelper;
import screen.tools.sbs.utils.targethelper.MandatoryField;
import screen.tools.sbs.utils.targethelper.MandatoryPath;
import screen.tools.sbs.utils.targethelper.MandatorySubTarget;
import screen.tools.sbs.utils.targethelper.OptionFile;
import screen.tools.sbs.utils.targethelper.OptionIsTest;
import screen.tools.sbs.utils.targethelper.OptionVerbose;
import screen.tools.sbs.utils.targethelper.OptionVoid;

public class TargetFlags implements Target {
	private TargetHelper helper;
	private TargetHelper addHelper;
	
	private MandatorySubTarget mandatorySubTarget;
	private MandatoryPath mandatoryPath;
	private OptionVoid optionVoid;
	
	private MandatoryField mandatoryField;
	private OptionFile optionChooseSbsFile;
	private OptionIsTest optionIsTest;
	private OptionVerbose optionVerbose;
	
	public TargetFlags() {
		helper = new TargetHelper(getTargetCall());
		addHelper = new TargetHelper(getTargetCall());
		
		mandatorySubTarget = new MandatorySubTarget();
		mandatoryPath = new MandatoryPath();
		optionVoid = new OptionVoid();
		
		mandatoryField = new MandatoryField();
		optionChooseSbsFile = new OptionFile("sbs.xml");
		optionIsTest = new OptionIsTest();
		optionVerbose = new OptionVerbose();
		
		helper.addMandatory(mandatorySubTarget);
		helper.addOption(optionVoid);

		addHelper.addMandatory(mandatorySubTarget);
		addHelper.addMandatory(mandatoryPath);
		addHelper.addMandatory(mandatoryField);
		addHelper.addOption(optionChooseSbsFile);
		addHelper.addOption(optionIsTest);
		addHelper.addOption(optionVerbose);
	}
	
	public void registerActions(ActionManager actionManager,
			Parameters parameters) {		
		helper.perform(parameters);
		
		SbsFileAndPathContext context = new SbsFileAndPathContext();
		
		ContextHandler contextHandler = new ContextHandler();
		contextHandler.addContext(ContextKeys.COMPONENT_PACK, new ComponentPackContext());
		contextHandler.addContext(ContextKeys.COMPONENT_TEST_PACK, new ComponentPackContext());
		contextHandler.addContext(ContextKeys.SBS_FILE_AND_PATH, context);
		contextHandler.addContext(ContextKeys.REPOSITORIES, new RepositoryContext());
		actionManager.setContext(contextHandler);
		
		if("add".equals(mandatorySubTarget.getSubTarget())){
			addHelper.perform(parameters);
			
			context.getSbsXmlFile().set(optionChooseSbsFile.getFile());
			context.getSbsXmlPath().set(mandatoryPath.getPath());
			
			actionManager.pushAction(new ActionConfigurationLoad());
			actionManager.pushAction(new ActionTinyPackLoad());

			if(optionIsTest.isMain()){
				ActionAddFlagsTinyPack actionAddFlagsTinyPack = new ActionAddFlagsTinyPack();
				actionAddFlagsTinyPack.setField(mandatoryField.getField());
				actionManager.pushAction(actionAddFlagsTinyPack);
				
			}
			if(optionIsTest.isTest()){
				ActionAddFlagsTinyTestPack actionAddFlagsTinyTestPack = new ActionAddFlagsTinyTestPack();
				actionAddFlagsTinyTestPack.setField(mandatoryField.getField());
				actionManager.pushAction(actionAddFlagsTinyTestPack);				
			}		
			
			actionManager.pushAction(new ActionWriteTinyPack());
		}
	}

	public TargetCall getTargetCall() {
		TargetCall call = new TargetCall();
		call.setTarget("flags");
		return call;
	}

	public void usage() {
		Logger.info("perform actions on pack or test pack flags");
		helper.usage();
	}
}
