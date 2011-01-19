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

import screen.tools.sbs.utils.FieldString;

public class FieldBuildType {
	
	public enum Type{
		EXECUTABLE,
		STATIC_LIBRARY,
		SHARED_LIBRARY
	}
	
	private Type type;
	private boolean valid;
	
	public FieldBuildType() {
		type = Type.EXECUTABLE;
		valid = false;
	}
	
	public FieldBuildType(Type buildType) {
		type = buildType;
		valid = true;
	}

	public void set(FieldBuildType.Type type){
		this.type = type;
		valid = true;
	}
	
	public void set(String value){
		FieldString field = new FieldString(value);
		String string = field.getString();
		if("static".equals(string)){
			type = Type.STATIC_LIBRARY;
			valid = true;
		}
		else if ("shared".equals(string)){
			type = Type.SHARED_LIBRARY;
			valid = true;
		}
		else if ("executable".equals(string)){
			type = Type.EXECUTABLE;
			valid = true;
		}
		else{
			type = Type.EXECUTABLE;
			valid = false;
		}
	}
	
	public Type get() {
		return type;
	}
	
	public boolean isValid() {
		return valid;
	}
}
