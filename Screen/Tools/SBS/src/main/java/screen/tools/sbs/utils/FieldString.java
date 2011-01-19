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

package screen.tools.sbs.utils;

import screen.tools.sbs.objects.EnvironmentVariables;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.GlobalSettings;

public class FieldString {
	private String originalString;

	public FieldString() {
		originalString = null;
	}
	
	public FieldString(String value){
		// copy value
		if(value == null)
			originalString = null;
		else
			originalString = new String(value);
	}
	
	public boolean isEmpty(){
		if(originalString == null)
			return true;
		return "".equals(originalString);
	}
	
	public boolean isValid(EnvironmentVariables additionalVars){
		return !isEmpty() && (convertFromOriginalToFinal(originalString,additionalVars)!=null);
	}

	public boolean isValid(){
		return isValid(null);
	}

	public void setString(String originalString) {
		if(originalString == null)
			this.originalString = null;
		else
			this.originalString = new String(originalString);
	}

	public String getOriginalString() {
		return originalString;
	}
	
	public String getString(EnvironmentVariables additionalVars) {
		if(!isEmpty())
			return convertFromOriginalToFinal(originalString,additionalVars);
		return null;
	}

	public String getString(){
		return getString(null);
	}
	
	@Override
	public boolean equals(Object arg0) {
		FieldString fieldString = (FieldString) arg0;
		if(arg0==null)
			return false;
		return getString().equals(fieldString.getString());
	}
		
	@Override
	public int hashCode() {
		return getString().hashCode();
	}
	
	private String convertFromOriginalToFinal(String originalString, EnvironmentVariables additionalVars){
		if(additionalVars == null)
			additionalVars = new EnvironmentVariables();
		
		String finalString = "";
		Logger.debug("originalString : "+originalString);
		Logger.debug("finalString : "+finalString);
		boolean isValid = true;
		int currentIndex = 0;
		int returnedIndex = 0;
		EnvironmentVariables env = GlobalSettings.getGlobalSettings().getEnvironmentVariables();
		ErrorList err = GlobalSettings.getGlobalSettings().getErrorList();
		
		while((returnedIndex = originalString.indexOf("${", currentIndex)) != -1){
			Logger.debug("originalString : "+originalString);
			Logger.debug("finalString : "+finalString);
			finalString = finalString.concat(originalString.substring(currentIndex, returnedIndex));
			int endVarIndex = 0;
			if((endVarIndex = originalString.indexOf("}", returnedIndex)) == -1){
				err.addError("variable never ended by } caracter : "
							  + originalString.substring(returnedIndex, endVarIndex));
				isValid = false;
			}
			else{
				String var = originalString.substring(returnedIndex+2, endVarIndex);
				Logger.debug("var : "+var);
				if(additionalVars.contains(var)){
					String value = additionalVars.getValue(var,additionalVars);
					Logger.debug("var value : "+value);
					finalString = finalString.concat(value);
				}
				else if(env.contains(var)){
					String value = env.getValue(var,additionalVars);
					Logger.debug("var value : "+value);
					finalString = finalString.concat(value);
				}
				else {
					err.addError("undefined variable : "+var);
					isValid = false;
				}
				currentIndex = endVarIndex + 1;
			}
		}
		finalString = finalString.concat(originalString.substring(currentIndex));
		Logger.debug("originalString : "+originalString);
		Logger.debug("finalString : "+finalString);
		if(isValid)
			return finalString;
		else
			return null;
	}
}
