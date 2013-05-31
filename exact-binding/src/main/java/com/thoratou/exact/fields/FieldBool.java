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

public class FieldBool extends FieldBase<String> implements Entry<FieldBool>{
	FieldString fieldString;
	
	public FieldBool() {
		super(Type.MANDATORY);
		fieldString = new FieldString();
	}
	
	public FieldBool(FieldBool fieldBool) {
		super(fieldBool.getType());
		fieldString = fieldBool.fieldString.copy();
	}

	public FieldBool(Type type, String defaultValue) {
		super(type);
		fieldString = new FieldString(type, defaultValue);
	}

	@Override
	public boolean isEmpty(){
		return fieldString.isEmpty();
	}
	
	@Override
	public void set(String bool) {
		fieldString.set(bool);
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
	public String get() throws FieldException {
		String ret = fieldString.get();
		if(!isValid(ret)){
			throw new FieldException(fieldString.getOriginal());
		}
		return ret;
	}

	public boolean getBool() throws FieldException {
		return get().equals("true");
	}

	@Override
	public void merge(FieldBool fieldBool) {
		fieldString.merge(fieldBool.fieldString);
	}

	@Override
	public FieldBool copy() {
		return new FieldBool(this);
	}
	
	private boolean isValid(String value){
		return ("true".equals(value) || "false".equals(value));
	}

	
}
