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

import org.jdom.Document;

import screen.tools.sbs.actions.Action;
import screen.tools.sbs.component.ComponentPack;
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ComponentPackContext;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.SbsFileAndPathContext;
import screen.tools.sbs.context.defaults.XmlDocumentContext;
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
		Document doc = contextHandler.<XmlDocumentContext>get(ContextKeys.SBS_XML_DOCUMENT).getDocument();
		String path = contextHandler.<SbsFileAndPathContext>get(ContextKeys.SBS_FILE_AND_PATH).getSbsXmlPath();
		ComponentPack pack = new ComponentPack();
		ComponentPack testPack = new ComponentPack();
		FieldPath fieldPath = new FieldPath();
		fieldPath.set(path);
		SBSDomDataFiller dataFiller = new SBSDomDataFiller(contextHandler, pack,testPack,fieldPath);
		dataFiller.fill(doc);
		contextHandler.<ComponentPackContext>get(ContextKeys.COMPONENT_PACK).setPack(pack);
		contextHandler.<ComponentPackContext>get(ContextKeys.COMPONENT_TEST_PACK).setPack(testPack);
	}

	public void setContext(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}
}
