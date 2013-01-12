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

package screen.tools.sbs.utils.targethelper;

import java.util.List;

import screen.tools.sbs.targets.Parameters;
import screen.tools.sbs.utils.Logger;

public class OptionVerbose implements Option{
	
	private String option;

	public OptionVerbose() {
		option = "-v";
	}
	
	public OptionVerbose(String defaultOption) {
		option = defaultOption;
	}

	public int perform(Parameters pars, int it) {
		String par = pars.getParameterAt(it);
		if(option.equals(par)){
			Logger.setDebug(true);
			return it;
		}
		return -1;
	}

	public void usage(List<String> manUsage) {
		manUsage.add(option+" : verbose mode");		
	}

}
