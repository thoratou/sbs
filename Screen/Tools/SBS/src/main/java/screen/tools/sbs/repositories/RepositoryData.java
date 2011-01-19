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

package screen.tools.sbs.repositories;

import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.utils.FieldString;

public class RepositoryData {
	private FieldString id;
	private RepositoryType type;
	private FieldPath path;
	
	public RepositoryData() {
		id = new FieldString();
		type = new RepositoryType();
		path = new FieldPath();
	}
	
	public void setId(FieldString id) {
		this.id = id;
	}

	public FieldString getId() {
		return id;
	}

	public void setType(RepositoryType type) {
		this.type = type;
	}

	public RepositoryType getType() {
		return type;
	}

	public void setPath(FieldPath path) {
		this.path = path;
	}

	public FieldPath getPath() {
		return path;
	}
}
