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

package screen.tools.sbs.fields;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import screen.tools.sbs.objects.Entry;
import screen.tools.sbs.objects.EnvironmentVariables;
import screen.tools.sbs.objects.ErrorList;

public class FieldJSONObject implements Entry<FieldJSONObject>{
	FieldString jsonString;

	public FieldJSONObject() {
		jsonString = new FieldString();
	}
	
	public FieldJSONObject(FieldJSONObject fieldJSONObject) {
		jsonString = fieldJSONObject.jsonString;
	}

	public boolean isEmpty(){
		return jsonString.isEmpty();
	}

	public void set(String originalString) {
		jsonString.set(originalString);
	}

	public String getOriginal() {
		return jsonString.getOriginal();
	}
	
	public String get(EnvironmentVariables additionalVars) throws FieldException {
		return jsonString.get(additionalVars);
	}

	public String getString() throws FieldException{
		return get(null);
	}
	
	public JSONObject getJSONObject(EnvironmentVariables additionalVars) throws FieldException {
		JSONObject jsonObject = convertFromOriginalToFinal(
									jsonString.getOriginal(),
									additionalVars);
		if(jsonObject == null)
			throw new FieldException(jsonString.getOriginal());
		return jsonObject;
	}

	public JSONObject getJSONObject() throws FieldException{
		return getJSONObject(null);
	}
		
	private JSONObject convertFromOriginalToFinal(String originalString, EnvironmentVariables additionalVars){
		String basicString = FieldString.convertFromOriginalToFinal(
								jsonString.getOriginal(),
								additionalVars);
		JSONObject object = null;
		try {
			object = (JSONObject)new JSONParser().parse(basicString);
		} catch (ParseException e) {
			ErrorList.instance.addError(
					"can't parse "+
					jsonString.getOriginal()+
					" as JSON object : "+
					e.getMessage());
		}
		if(object == null)
			ErrorList.instance.addError(
					"can't parse "+
					jsonString.getOriginal()+
					" as JSON object : ");			
		return object;
	}

	@Override
	public void merge(FieldJSONObject fieldJSONObject) {
		jsonString.merge(fieldJSONObject.jsonString);
	}

	@Override
	public FieldJSONObject copy() {
		return new FieldJSONObject(this);
	}
}
