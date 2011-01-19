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
import screen.tools.sbs.utils.FieldBuildType;
import screen.tools.sbs.utils.FieldString;

public class Description {
	private FieldString name;
	private FieldString fullName;
	private FieldString compileName;
	private FieldBuildType buildType;
	private FieldBuildMode buildMode;
	
	public Description() {
		name = new FieldString();
		fullName = new FieldString();
		compileName = new FieldString();
		buildType = new FieldBuildType();
		buildMode = new FieldBuildMode();
	}
	
	public void setName(FieldString name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name.setString(name);
	}

	public FieldString getName() {
		return name;
	}
	
	public void setFullName(FieldString name) {
		fullName = name;
	}
	
	public void setFullName(String name) {
		fullName.setString(name);
	}

	public FieldString getFullName() {
		return fullName;
	}
	
	public void setCompileName(FieldString name) {
		compileName = name;
	}
	
	public void setCompileName(String name) {
		compileName.setString(name);
	}

	public FieldString getCompileName() {
		return compileName;
	}

	public void setBuildType(FieldBuildType buildType) {
		this.buildType = buildType;
	}
	
	public void setBuildType(String type) {
		buildType.set(type);
	}
	
	public void setBuildType(FieldBuildType.Type type) {
		buildType.set(type);
	}

	public FieldBuildType.Type getBuildType() {
		return buildType.get();
	}
	
	public void setBuildMode(FieldBuildMode buildMode) {
		this.buildMode = buildMode;
	}
	
	public void setBuildMode(String mode) {
		buildMode.set(mode);
	}
	
	public void setBuildMode(FieldBuildMode.Type type) {
		buildMode.set(type);
	}

	public FieldBuildMode getBuildMode() {
		return buildMode;
	}
}
