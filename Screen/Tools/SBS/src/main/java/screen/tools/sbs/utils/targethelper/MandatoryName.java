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

package screen.tools.sbs.utils.targethelper;

import java.util.List;

import screen.tools.sbs.targets.Parameters;

/**
 * @author Ratouit Thomas
 *
 */
public class MandatoryName implements Mandatory{
	private String name;
	
	/**
	 * 
	 */
	public MandatoryName() {
		name = null;
	}

	/**
	 * @see screen.tools.sbs.utils.targethelper.Mandatory#getDescription()
	 */
	public String getDescription() {
		return "component-name";
	}

	/**
	 * @see screen.tools.sbs.utils.targethelper.Mandatory#perform(screen.tools.sbs.targets.Parameters, int)
	 */
	public int perform(Parameters pars, int it) {
		name = pars.getParameterAt(it);
		return it;
	}
	
	/**
	 * @return The component name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @see screen.tools.sbs.utils.targethelper.Mandatory#usage(java.util.List)
	 */
	public void usage(List<String> manUsage) {
		manUsage.add("<component-name> : the component name");
	}

}