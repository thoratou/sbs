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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import screen.tools.sbs.objects.EnvironmentVariables;
import screen.tools.sbs.objects.ErrorList;

public class FieldJSONObject{
	FieldString jsonString;

	public FieldJSONObject(FieldString jsonString) {
		if(jsonString == null)
			this.jsonString = new FieldString();
		else
			this.jsonString = jsonString;
	}

	public FieldJSONObject(String jsonString) {
		this.jsonString = new FieldString(jsonString);
	}

	public FieldJSONObject() {
		jsonString = new FieldString();
	}
	
	public boolean isEmpty(){
		return jsonString.isEmpty();
	}
	
	public boolean isValid(EnvironmentVariables additionalVars){
		return !isEmpty() &&
				(convertFromOriginalToFinal(jsonString.getOriginalString(),additionalVars)!=null);
	}

	public boolean isValid(){
		return isValid(null);
	}

	public void setString(String originalString) {
		jsonString.setString(originalString);
	}

	public String getOriginalString() {
		return jsonString.getOriginalString();
	}
	
	public String getString(EnvironmentVariables additionalVars) throws FieldException {
		return jsonString.getString(additionalVars);
	}

	public String getString() throws FieldException{
		return getString(null);
	}
	
	public JSONObject getJSONObject(EnvironmentVariables additionalVars) throws FieldException {
		JSONObject jsonObject = convertFromOriginalToFinal(
									jsonString.getOriginalString(),
									additionalVars);
		if(jsonObject == null)
			throw new FieldException(jsonString.getOriginalString());
		return jsonObject;
	}

	public JSONObject getJSONObject() throws FieldException{
		return getJSONObject(null);
	}
		
	private JSONObject convertFromOriginalToFinal(String originalString, EnvironmentVariables additionalVars){
		String basicString = FieldString.convertFromOriginalToFinal(
								jsonString.getOriginalString(),
								additionalVars);
		JSONObject object = null;
		try {
			object = (JSONObject)new JSONParser().parse(basicString);
		} catch (ParseException e) {
			ErrorList.instance.addError(
					"can't parse "+
					jsonString.getOriginalString()+
					" as JSON object : "+
					e.getMessage());
		}
		if(object == null)
			ErrorList.instance.addError(
					"can't parse "+
					jsonString.getOriginalString()+
					" as JSON object : ");			
		return object;
	}
}
