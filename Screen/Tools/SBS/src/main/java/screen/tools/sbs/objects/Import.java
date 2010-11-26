/*****************************************************************************
 * This source file is part of SBS (Screen Build System),                    *
 * which is a component of Screen Framework                                  *
 *                                                                           *
 * Copyright (c) 2008-2010 Ratouit Thomas                                    *
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
import screen.tools.sbs.utils.FieldFile;

public class Import {
	private FieldBuildMode buildMode;
	private FieldFile file;
	
	public Import() {
		buildMode = new FieldBuildMode();
		file = new FieldFile();
	}

	public void setBuildMode(FieldBuildMode buildMode) {
		this.buildMode = buildMode;
	}
	
	public void setBuildMode(String mode) {
		buildMode.set(mode);
	}
	
	public void setBuildMode(FieldBuildMode.Type mode) {
		buildMode.set(mode);
	}

	public FieldBuildMode getBuildMode() {
		return buildMode;
	}

	public void setFile(FieldFile file) {
		this.file = file;
	}
	
	public void setFile(String file) {
		this.file.setString(file);
	}

	public FieldFile getFile() {
		return file;
	}
}
