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
		if(name!=null)
			this.name = name;
		else
			ErrorList.instance.addWarning("Null FieldString for description name");
	}
	
	public void setName(String name) {
		setName(new FieldString(name));
	}
	
	public FieldString getName() {
		return name;
	}
	
	public void setFullName(FieldString name) {
		if(name!=null)
			this.fullName = name;
		else
			ErrorList.instance.addWarning("Null FieldString for description full name");
	}
	
	public void setFullName(String name) {
		setFullName(new FieldString(name));
	}

	public FieldString getFullName() {
		return fullName;
	}
	
	public void setCompileName(FieldString name) {
		if(name!=null)
			this.compileName = name;
		else
			ErrorList.instance.addWarning("Null FieldString for description compile name");
	}
	
	public void setCompileName(String name) {
		setCompileName(new FieldString(name));
	}

	public FieldString getCompileName() {
		return compileName;
	}

	public void setBuildType(FieldBuildType buildType) {
		if(buildType!=null)
			this.buildType = buildType;
		else
			ErrorList.instance.addWarning("Null FieldBuildType for description build type");
	}
	
	public void setBuildType(String type) {
		setBuildType(new FieldBuildType(type));
	}
	
	public void setBuildType(FieldBuildType.Type type) {
		setBuildType(new FieldBuildType(type));
	}

	public FieldBuildType getBuildType() {
		return buildType;
	}
	
	public void setBuildMode(FieldBuildMode buildMode) {
		if(buildMode!=null)
			this.buildMode = buildMode;
		else
			ErrorList.instance.addWarning("Null FieldBuildMode for description build mode");
	}
	
	public void setBuildMode(String mode) {
		setBuildMode(new FieldBuildMode(mode));
	}
	
	public void setBuildMode(FieldBuildMode.Type type) {
		setBuildMode(new FieldBuildMode(type));
	}

	public FieldBuildMode getBuildMode() {
		return buildMode;
	}
}
