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

import screen.tools.sbs.utils.FieldString;

public class Library {
	private static ErrorList err = GlobalSettings.getGlobalSettings().getErrorList();	

	private FieldString name;
	private FieldString version;
	
	public Library(){
		name = new 	FieldString();
		version = new 	FieldString();
	}

	public void setName(FieldString name) {
		if(name!=null)
			this.name = name;
		else
			err.addWarning("Null FieldString for library name");
	}
	
	public void setName(String name) {
		setName(new FieldString(name));
	}

	public FieldString getName() {
		return name;
	}

	public void setVersion(FieldString version) {
		if(version!=null)
			this.version = version;
		else
			err.addWarning("Null FieldString for library version");
	}

	public void setVersion(String version) {
		setVersion(new FieldString(version));
	}

	public FieldString getVersion() {
		return version;
	}
}