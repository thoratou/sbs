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

import org.w3c.dom.Document;

import screen.tools.sbs.actions.Action;
import screen.tools.sbs.objects.GlobalSettings;
import screen.tools.sbs.objects.Pack;
import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.xml.SBSDomDataFiller;

/**
 * Action to load test pack from an XML Dom.
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionTestPackLoad implements Action {
	/**
	 * Loads the test pack from the global XML Dom.
	 * The test pack is set in global settings.
	 */
	public void perform() {
		Document doc = GlobalSettings.getGlobalSettings().getXmlDocument();
		String path = GlobalSettings.getGlobalSettings().getSbsXmlPath();
		Pack pack = new Pack();
		SBSDomDataFiller dataFiller = new SBSDomDataFiller(null,pack,new FieldPath(path));
		dataFiller.fill(doc,true);
		GlobalSettings.getGlobalSettings().setTestPack(pack);
	}

}
