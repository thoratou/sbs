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

package screen.tools.sbs.targets;

import java.util.ArrayList;
import java.util.List;

import screen.tools.sbs.objects.ErrorList;

public class Parameters {
	public TargetCall targetCall;
	public List<String> parameters;
	
	public Parameters(String[] args) {
		if(args.length < 1){
			ErrorList.instance.addError("No parameters");
		}
		else{
			retrievePluginAndTarget(args[0]);
			retrieveParameters(args);
		}
	}

	private void retrievePluginAndTarget(String string) {
		//for now, no plugin support
		targetCall = new TargetCall();
		targetCall.setTarget(string);
	}

	private void retrieveParameters(String[] args) {
		parameters = new ArrayList<String>();
		for(int i = 1; i<args.length; i++){
			parameters.add(new String(args[i]));
		}
	}
	
	public TargetCall getTargetCall() {
		return targetCall;
	}
		
	public String getParameterAt(int i) {
		return parameters.get(i);
	}

	public List<String> getParameters() {
		return parameters;
	}
	
	public int size(){
		if(parameters==null)
			return -1;
		else
			return parameters.size();
	}
}
