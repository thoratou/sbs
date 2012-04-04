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
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;

/**
 * Action to generate simple empty folder.
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionCreateFolders implements Action {
	private String path;
	//private ContextHandler contextHandler;
	
	/**
	 * 
	 */
	public ActionCreateFolders() {
		path = null;
	}

	/**
	 * Performs create action.
	 * @throws ContextException 
	 */
	public void perform() throws ContextException {
		if(path != null)
		{
			File file = new File(path);		
			file.mkdirs();
		}
	}
	
	public void setContext(ContextHandler contextHandler) {
		//this.contextHandler = contextHandler;
	}


	/**
	 * @param path
	 */
	public void setFolderPath(String path) {
		this.path = path;
	}
}
