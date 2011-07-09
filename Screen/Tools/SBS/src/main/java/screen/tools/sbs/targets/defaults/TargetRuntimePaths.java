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
import screen.tools.sbs.actions.defaults.ActionConfigurationLoad;
import screen.tools.sbs.actions.defaults.ActionPackLoad;
import screen.tools.sbs.actions.defaults.ActionRuntimePathDisplay;
import screen.tools.sbs.actions.defaults.ActionTestPackLoad;
import screen.tools.sbs.actions.defaults.ActionTestRuntimePathDisplay;
import screen.tools.sbs.actions.defaults.ActionXmlLoad;
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.EnvironmentVariablesContext;
import screen.tools.sbs.context.defaults.PackContext;
import screen.tools.sbs.context.defaults.RepositoryContext;
import screen.tools.sbs.context.defaults.SbsFileAndPathContext;
import screen.tools.sbs.context.defaults.XmlDocumentContext;
import screen.tools.sbs.targets.Parameters;
import screen.tools.sbs.targets.Target;
import screen.tools.sbs.targets.TargetCall;
import screen.tools.sbs.utils.Logger;
import screen.tools.sbs.utils.TargetHelper;
import screen.tools.sbs.utils.targethelper.MandatoryPath;
import screen.tools.sbs.utils.targethelper.OptionFile;
import screen.tools.sbs.utils.targethelper.OptionIsDebug;
import screen.tools.sbs.utils.targethelper.OptionIsTest;
import screen.tools.sbs.utils.targethelper.OptionVerbose;

public class TargetRuntimePaths implements Target {
	private TargetHelper helper;
	private MandatoryPath mandatoryPath;
	private OptionFile optionChooseSbsFile;
	private OptionIsTest optionIsTest;
	private OptionIsDebug optionIsDebug;
	private OptionVerbose optionVerbose;

	
	public TargetRuntimePaths() {
		helper = new TargetHelper(getTargetCall());
		mandatoryPath = new MandatoryPath();
		optionChooseSbsFile = new OptionFile("sbs.xml");
		optionIsTest = new OptionIsTest();
		optionIsDebug = new OptionIsDebug();
		optionVerbose = new OptionVerbose();
		
		helper.addMandatory(mandatoryPath);
		helper.addOption(optionChooseSbsFile);
		helper.addOption(optionIsTest);
		helper.addOption(optionIsDebug);
		helper.addOption(optionVerbose);
	}
	
	public void registerActions(ActionManager actionManager,
			Parameters parameters) throws ContextException {		
		helper.perform(parameters);
		
		SbsFileAndPathContext context = new SbsFileAndPathContext();
		context.setSbsXmlFile(optionChooseSbsFile.getFile());
		context.setSbsXmlPath(mandatoryPath.getPath());
		
		ContextHandler contextHandler = new ContextHandler();
		contextHandler.addContext(ContextKeys.PACK, new PackContext());
		contextHandler.addContext(ContextKeys.TEST_PACK, new PackContext());
		contextHandler.addContext(ContextKeys.SBS_XML_DOCUMENT, new XmlDocumentContext());
		contextHandler.addContext(ContextKeys.SBS_FILE_AND_PATH, context);
		contextHandler.addContext(ContextKeys.REPOSITORIES, new RepositoryContext());
		actionManager.setContext(contextHandler);
		
		contextHandler.<EnvironmentVariablesContext>get(ContextKeys.ENV_VARIABLES).getEnvironmentVariables()
			.put("_COMPILE_MODE", optionIsDebug.getString());

		actionManager.pushAction(new ActionConfigurationLoad());
		actionManager.pushAction(new ActionXmlLoad());
		if(optionIsTest.isMain()){
			ActionPackLoad actionPackLoad = new ActionPackLoad();
			actionPackLoad.processRuntime(true);
			actionManager.pushAction(actionPackLoad);
			actionManager.pushAction(new ActionRuntimePathDisplay());
		}
		if(optionIsTest.isTest()){
			ActionTestPackLoad actionPackLoad = new ActionTestPackLoad();
			actionPackLoad.processRuntime(true);
			actionManager.pushAction(actionPackLoad);
			actionManager.pushAction(new ActionTestRuntimePathDisplay());
		}
	}

	public TargetCall getTargetCall() {
		TargetCall call = new TargetCall();
		call.setTarget("runtime-display");
		return call;
	}

	public void usage() {
		Logger.info("display library paths needed for rumtime");
		helper.usage();
	}
	
	

}
