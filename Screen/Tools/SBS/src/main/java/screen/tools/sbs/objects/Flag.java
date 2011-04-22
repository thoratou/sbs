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

import screen.tools.sbs.utils.FieldBuildMode;
import screen.tools.sbs.utils.FieldString;

public class Flag {
	private FieldString flag;
	private FieldString value;
	private FieldBuildMode buildMode;
	
	public Flag() {
		flag = new FieldString();
		value = new FieldString();
		buildMode = new FieldBuildMode();
	}
	
	public void setFlag(FieldString flag) {
		if(flag!=null)
			this.flag = flag;
		else
			ErrorList.instance.addWarning("Null FieldString for flag key");
	}
	
	public void setFlag(String flag) {
		setFlag(new FieldString(flag));
	}
	
	public FieldString getFlag() {
		return flag;
	}
	
	public void setValue(FieldString value) {
		if(value!=null)
			this.value = value;
		else
			ErrorList.instance.addWarning("Null FieldString for flag value");
	}

	public void setValue(String value) {
		setValue(new FieldString(value));
	}
		
	public FieldString getValue() {
		return value;
	}
	
	public void setBuildMode(FieldBuildMode mode){
		if(value!=null)
			this.buildMode = mode;
		else
			ErrorList.instance.addWarning("Null FieldString for flag build mode");
	}

	public void setBuildMode(String mode){
		setBuildMode(new FieldBuildMode(mode));
	}
	
	public FieldBuildMode getBuildMode(){
		return buildMode;
	}
}
