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
import screen.tools.sbs.actions.defaults.ActionCreateFolders;
import screen.tools.sbs.actions.defaults.ActionCreateTinyPack;
import screen.tools.sbs.actions.defaults.ActionWriteTinyPack;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ComponentPackContext;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.SbsFileAndPathContext;
import screen.tools.sbs.targets.Parameters;
import screen.tools.sbs.targets.Target;
import screen.tools.sbs.targets.TargetCall;
import screen.tools.sbs.utils.Logger;
import screen.tools.sbs.utils.TargetHelper;
import screen.tools.sbs.utils.targethelper.MandatoryBuildType;
import screen.tools.sbs.utils.targethelper.MandatoryName;
import screen.tools.sbs.utils.targethelper.MandatoryPath;
import screen.tools.sbs.utils.targethelper.MandatoryVersion;
import screen.tools.sbs.utils.targethelper.OptionFile;
import screen.tools.sbs.utils.targethelper.OptionVerbose;

public class TargetCreateComponent implements Target {
	private TargetHelper helper;
	private MandatoryPath mandatoryPath;
	private MandatoryName mandatoryName;
	private MandatoryVersion mandatoryVersion;
	private MandatoryBuildType mandatoryBuildType;
	private OptionFile optionChooseSbsFile;
	private OptionVerbose optionVerbose;

	
	public TargetCreateComponent() {
		helper = new TargetHelper(getTargetCall());
		mandatoryPath = new MandatoryPath();
		mandatoryName = new MandatoryName();
		mandatoryVersion = new MandatoryVersion();
		mandatoryBuildType = new MandatoryBuildType();
		optionChooseSbsFile = new OptionFile("sbs.xml","-o");
		optionVerbose = new OptionVerbose();
		
		helper.addMandatory(mandatoryPath);
		helper.addMandatory(mandatoryName);
		helper.addMandatory(mandatoryVersion);
		helper.addMandatory(mandatoryBuildType);
		helper.addOption(optionChooseSbsFile);
		helper.addOption(optionVerbose);
	}

	public void registerActions(ActionManager actionManager,
			Parameters parameters) {
		helper.perform(parameters);
		
		SbsFileAndPathContext context = new SbsFileAndPathContext();
		context.getSbsXmlFile().set(optionChooseSbsFile.getFile());
		context.getSbsXmlPath().set(mandatoryPath.getPath());
		
		ContextHandler contextHandler = new ContextHandler();
		contextHandler.addContext(ContextKeys.COMPONENT_PACK, new ComponentPackContext());
		contextHandler.addContext(ContextKeys.COMPONENT_TEST_PACK, new ComponentPackContext());
		contextHandler.addContext(ContextKeys.SBS_FILE_AND_PATH, context);
		actionManager.setContext(contextHandler);
		
		ActionCreateTinyPack actionCreatePack = new ActionCreateTinyPack();
		actionCreatePack.setName(mandatoryName.getName());
		actionCreatePack.setVersion(mandatoryVersion.getVersion());
		actionCreatePack.setBuildType(mandatoryBuildType.getBuildType());
		
		ActionWriteTinyPack actionWriteTinyPack = new ActionWriteTinyPack();
		
		ActionCreateFolders actionCreateSource =  new ActionCreateFolders();
		actionCreateSource.setFolderPath("src");
		
		ActionCreateFolders actionCreateInclude =  new ActionCreateFolders();
		actionCreateInclude.setFolderPath("include");
		
		actionManager.pushAction(actionCreatePack);
		actionManager.pushAction(actionWriteTinyPack);
		actionManager.pushAction(actionCreateSource);
		actionManager.pushAction(actionCreateInclude);
	}

	public TargetCall getTargetCall() {
		TargetCall call = new TargetCall();
		call.setTarget("create-component");
		return call;
	}

	public void usage() {
		Logger.info("generate a basic skeleton for a new component");
		helper.usage();
	}

}
