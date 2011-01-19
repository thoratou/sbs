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

package screen.tools.sbs.objects;

import java.util.ArrayList;
import java.util.List;

import screen.tools.sbs.utils.Logger;

public class ErrorList {
	private List<String> errorList;
	private List<String> warningList;
	private boolean logError;
	
	public ErrorList() {
		errorList = new ArrayList<String>();
		warningList = new ArrayList<String>();
		logError = true;
	}
	
	public void addError(String error){
		if(logError){
			errorList.add(error);
		}
	}
	
	public void addWarning(String warning){
		if(logError){
			warningList.add(warning);
		}
	}
	
	public void displayErrors(){
		for(int i=0; i<errorList.size(); i++){
			Logger.error(errorList.get(i));
		}
	}
	
	public void displayWarnings(){
		for(int i=0; i<warningList.size(); i++){
			Logger.warning(warningList.get(i));
		}
	}
	
	public boolean hasErrors(){
		return !errorList.isEmpty();
	}
	
	public boolean hasWarnings(){
		return !warningList.isEmpty();
	}
	
	public List<String> getErrors(){
		return errorList;
	}
	
	public List<String> getWarnings(){
		return warningList;
	}

	public void setLogError(boolean b) {
		logError = true;
	}
}
