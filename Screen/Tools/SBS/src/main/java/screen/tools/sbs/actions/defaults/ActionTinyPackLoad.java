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

import java.io.File;

import screen.tools.sbs.actions.Action;
import screen.tools.sbs.component.ComponentDomReader;
import screen.tools.sbs.component.ComponentPack;
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ComponentPackContext;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.SbsFileAndPathContext;
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.fields.FieldPath;

/**
 * Action to load pack from an XML Dom.
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionTinyPackLoad implements Action {

	private ContextHandler contextHandler;

	/**
	 * Loads the pack from the global XML Dom.
	 * The pack is set in global settings.
	 * @throws ContextException 
	 * @throws FieldException 
	 */
	public void perform() throws ContextException, FieldException {
		String path = contextHandler.<SbsFileAndPathContext>get(ContextKeys.SBS_FILE_AND_PATH).getSbsXmlPath().get();
		String file = contextHandler.<SbsFileAndPathContext>get(ContextKeys.SBS_FILE_AND_PATH).getSbsXmlFile().get();
		ComponentPack pack = new ComponentPack();
		ComponentPack testPack = new ComponentPack();
		FieldPath fieldPath = new FieldPath();
		fieldPath.set(path);
		ComponentDomReader domReader = new ComponentDomReader();
		domReader.read(pack,testPack, new File(path+"/"+file ));
		contextHandler.<ComponentPackContext>get(ContextKeys.COMPONENT_PACK).getPack().merge(pack);
		contextHandler.<ComponentPackContext>get(ContextKeys.COMPONENT_TEST_PACK).getPack().merge(testPack);
	}

	public void setContext(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}
}
