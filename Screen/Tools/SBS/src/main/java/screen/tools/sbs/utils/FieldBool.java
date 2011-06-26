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

public class FieldBool{
	String value;
	
	public FieldBool() {
		value = null;
	}
	
	public FieldBool(String value) {
		this.value = value;
	}
	
	public FieldBool(boolean value) {
		setBool(value);
	}
	
	public boolean isEmpty(){
		return value == null;
	}
	
	public boolean isValid(){
		return !isEmpty() && ("true".equals(value) || "false".equals(value));
	}
	
	public boolean getBool() {
		return "true".equals(value);
	}
	
	public void setString(String value){
		this.value = value;
	}
	
	public void setBool(boolean value){
		this.value = value ? "true" : "false";
	}
	
	public String getString(){
		return getBool() ? "true" : "false";
	}
	
	public String getOriginalString(){
		return value;
	}
	
}
