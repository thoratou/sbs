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

package screen.tools.sbs.fields;

import screen.tools.sbs.objects.Entry;
import screen.tools.sbs.objects.EnvironmentVariables;

public class FieldPath extends FieldBase<String> implements Entry<FieldPath> {
	FieldString fieldString;
	
	public FieldPath() {
		super(Type.MANDATORY);
		fieldString = new FieldString();
	}
	
	public FieldPath(FieldPath fieldFile) {
		super(fieldFile.getType());
		fieldString = fieldFile.fieldString.copy();
	}
	
	public FieldPath(Type type, String defaultValue) {
		super(type);
		fieldString = new FieldString(type, defaultValue);
	}

	@Override
	public boolean isEmpty(){
		return fieldString.isEmpty();
	}

	@Override
	public void set(String path) {
		fieldString.set(path);
	}

	@Override
	public String getOriginal() {
		return fieldString.getOriginal();
	}
	
	@Override
	public String getDefault() throws FieldException {
		return fieldString.getDefault();
	}
	
	@Override
	public String get(EnvironmentVariables additionalVars) throws FieldException {
		String ret = fieldString.get(additionalVars);
		if(ret == null)
			return null;
		ret = ret.replaceAll("\\\\ ", " ");
		ret = ret.replaceAll("\\\\", "/");
		if(!ret.endsWith("/"))
			ret += "/";
		return ret;
	}

	@Override
	public String get() throws FieldException{
		return get(null);
	}
	
	public String getCMakeString(EnvironmentVariables additionalVars) throws FieldException{
		return get(additionalVars).replaceAll(" ", "\\\\ ");
	}
	
	public String getCMakeString() throws FieldException{
		return getCMakeString(null);
	}

	@Override
	public void merge(FieldPath fieldPath) {
		fieldString.merge(fieldPath.fieldString);
	}

	@Override
	public FieldPath copy() {
		return new FieldPath(this);
	}
}
