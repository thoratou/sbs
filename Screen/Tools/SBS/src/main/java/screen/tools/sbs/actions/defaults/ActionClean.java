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
import screen.tools.sbs.cmake.SBSCMakeCleaner;
import screen.tools.sbs.context.Context;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.PackContext;
import screen.tools.sbs.objects.GlobalSettings;
import screen.tools.sbs.objects.Pack;

/**
 * Action to clean a component environment.
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionClean implements Action {
	PackContext packContext;
	
	/**
	 * Performs clean action.
	 */
	public void perform() {
		SBSCMakeCleaner cleaner = new SBSCMakeCleaner();
		//Pack pack = GlobalSettings.getGlobalSettings().getPack();
		Pack pack = packContext.getPack();
		String sbsXmlPath = GlobalSettings.getGlobalSettings().getSbsXmlPath();
		cleaner.clean(pack, sbsXmlPath);		
	}

	public void setContext(ContextHandler contextHandler) {
		Context context = contextHandler.getContext(ContextKeys.PACK);
		packContext = (PackContext) context;
	}

}
