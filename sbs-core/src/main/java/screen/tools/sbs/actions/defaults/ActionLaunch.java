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

package screen.tools.sbs.actions.defaults;

import screen.tools.sbs.actions.Action;
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.PackContext;
import screen.tools.sbs.context.defaults.RuntimePathListContext;
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.fields.FieldList;
import screen.tools.sbs.fields.FieldPath;
import screen.tools.sbs.pack.Pack;
import screen.tools.sbs.utils.ExecLauncher;

/**
 * Action to launch an executable compiled with SBS.
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionLaunch implements Action {
	private ContextHandler contextHandler;

	/**
	 * Launches the executable.
	 * @throws ContextException 
	 * @throws FieldException 
	 */
	public void perform() throws ContextException, FieldException {
		//Pack pack = GlobalSettings.getGlobalSettings().getPack();
		Pack pack = contextHandler.<PackContext>get(ContextKeys.PACK).getPack();
		FieldList<FieldPath> paths = contextHandler.<RuntimePathListContext>get(ContextKeys.RUNTIME_PATHS).getPaths();
		ExecLauncher launcher = new ExecLauncher(contextHandler, pack, paths);
		launcher.launch();
	}
	
	public void setContext(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

}
