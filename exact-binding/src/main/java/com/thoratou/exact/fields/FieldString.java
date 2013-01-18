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

package com.thoratou.exact.fields;

public class FieldString extends FieldBase<String> implements Entry<FieldString> {
	private String originalString;
	private String defaultValue;

	public FieldString() {
		super(Type.MANDATORY);
		originalString = null;
		defaultValue = null;
	}
	
	public FieldString(FieldString fieldString) {
		super(fieldString.getType());
		
		if(originalString != null){
			originalString = new String(fieldString.originalString);
		}
		else{
			originalString = null;
		}
		
		if(defaultValue != null){
			defaultValue = new String(fieldString.defaultValue);
		}
		else{
			defaultValue = null;
		}
	}

	public FieldString(Type type, String defaultValue) {
		super(type);
		originalString = null;
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean isEmpty(){
			return originalString == null;
	}
	
	@Override
	public void set(String originalString) {
		if(originalString == null)
			this.originalString = null;
		else
			this.originalString = new String(originalString);
	}
	
	@Override
	public String getDefault() {
		return defaultValue;
	}

	@Override
	public String getOriginal() {
		return originalString;
	}
	
	@Override
	public String get(EnvironmentVariables additionalVars) throws FieldException {
		String ret = null;
		if(!isEmpty()){
			ret = convertFromOriginalToFinal(originalString,additionalVars);
		}
		
		if(ret == null){
			if(defaultValue != null){
				ret = convertFromOriginalToFinal(defaultValue,additionalVars);	
				if(ret == null){
					throw new FieldException(defaultValue);
				}
			}
		}

		if(ret == null){
			throw new FieldException(originalString);
		}

		return ret;
	}

	@Override
	public String get() throws FieldException{
		return get(null);
	}
	
	@Override
	public boolean equals(Object arg0) {
		FieldString fieldString = (FieldString) arg0;
		if(arg0==null)
			return false;
		try {
			return get().equals(fieldString.get());
		} catch (FieldException e) {
			return false;
		}
	}
		
	@Override
	public int hashCode() {
		return getOriginal().hashCode();
	}
	
	@Override
	public void merge(FieldString fieldString) {
		if(fieldString.originalString != null)
			originalString = new String(fieldString.originalString);
		else
			originalString = null;
		
		//do not merge type and default value
	}

	@Override
	public FieldString copy() {
		return new FieldString(this);
	}
	
	public static String convertFromOriginalToFinal(String originalString, EnvironmentVariables additionalVars){
		if(additionalVars == null)
			additionalVars = new EnvironmentVariables();
		
		String finalString = "";
		//Logger.debug("originalString : "+originalString);
		//Logger.debug("finalString : "+finalString);
		boolean isValid = true;
		int currentIndex = 0;
		int returnedIndex = 0;
		EnvironmentVariables env = getCurrentEnvironmentVariables();
		
		while((returnedIndex = originalString.indexOf("${", currentIndex)) != -1){
			//Logger.debug("originalString : "+originalString);
			//Logger.debug("finalString : "+finalString);
			finalString = finalString.concat(originalString.substring(currentIndex, returnedIndex));
			int endVarIndex = 0;
			if((endVarIndex = originalString.indexOf("}", returnedIndex)) == -1){
				//ErrorList.instance.addError("variable never ended by } caracter : "
				//			  + originalString.substring(returnedIndex, endVarIndex));
				isValid = false;
			}
			else{
				String var = originalString.substring(returnedIndex+2, endVarIndex);
				//Logger.debug("var : "+var);
				boolean stringOK = false;
				if(!stringOK)
				{
					FieldString fieldString = additionalVars.getFieldString(var);
					if(fieldString.isValid(additionalVars)){
						//String value = fieldString.getString(additionalVars);
						String value = convertFromOriginalToFinal(
								fieldString.getOriginal(),
								additionalVars);
						//Logger.debug("var value : "+value);
						finalString = finalString.concat(value);
						stringOK = true;
					}
				}
				if(!stringOK)
				{
					FieldString fieldString = env.getFieldString(var);
					if(fieldString.isValid(additionalVars)){
						//String value = fieldString.getString(additionalVars);
						String value = convertFromOriginalToFinal(
								fieldString.getOriginal(),
								additionalVars);
						//Logger.debug("var value : "+value);
						finalString = finalString.concat(value);
						stringOK = true;
					}
				}
				if(!stringOK){
					//ErrorList.instance.addError("undefined variable : "+var);
					isValid = false;
				}
				currentIndex = endVarIndex + 1;
			}
		}
		finalString = finalString.concat(originalString.substring(currentIndex));
		//Logger.debug("originalString : "+originalString);
		//Logger.debug("finalString : "+finalString);
		if(isValid)
			return finalString;
		else
			return null;
	}
	
	private boolean isValid(EnvironmentVariables additionalVars){
		return !isEmpty() && (convertFromOriginalToFinal(originalString,additionalVars)!=null);
	}
}
