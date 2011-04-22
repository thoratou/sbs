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

import java.util.ArrayList;
import java.util.List;

import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.targets.Parameters;

public class OptionEnvFiles implements Option {

	private String option;
	private List<String> envFiles;
	
	public OptionEnvFiles() {
		option = "-e";
		envFiles = new ArrayList<String>();
	}

	public OptionEnvFiles(String defaultOption) {
		option = defaultOption;
		envFiles = new ArrayList<String>();
	}
	
	public int perform(Parameters pars, int it) {
		String par = pars.getParameterAt(it);
		if(option.equals(par)){
			if(it+1<pars.size()){
				envFiles.add(pars.getParameterAt(it+1));
				it++;
			}
			else{
				ErrorList.instance.addError("Missing parameter / \""+option+"\" option without environment file name");
				ErrorList.instance.needUsage();
			}
			return it;
		}
		return -1;
	}

	public List<String> getFiles(){
		return envFiles;
	}

	public void usage(List<String> manUsage) {
		manUsage.add(option+" <environment> : use specific environment file (without .config extension)");
	}
}
