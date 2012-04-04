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
import screen.tools.sbs.component.ComponentPack;
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ComponentPackContext;
import screen.tools.sbs.context.defaults.ContextKeys;

/**
 * Action to generate a basic component.
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionCreateTinyPack implements Action {
	private String name;
	private String version;
	private String buildType;
	private ContextHandler contextHandler;
	
	/**
	 * 
	 */
	public ActionCreateTinyPack() {
		name = null;
		version = null;
		buildType = null;
	}

	/**
	 * Performs create action.
	 * @throws ContextException 
	 */
	public void perform() throws ContextException {
		ComponentPack pack = new ComponentPack();
		pack.getProperties().getName().set(name);
		pack.getProperties().getVersion().set(version);
		pack.getProperties().getBuildType().set(buildType);
		contextHandler.<ComponentPackContext>get(ContextKeys.COMPONENT_PACK).getPack().merge(pack);
	}
	
	public void setContext(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}


	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @param buildType
	 */
	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}

}
