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

package screen.tools.sbs.context.defaults;

import screen.tools.sbs.context.Context;
import screen.tools.sbs.fields.FieldList;
import screen.tools.sbs.fields.FieldPath;

public class RuntimePathListContext implements Context {
	private FieldList<FieldPath> runtimePaths;
	private boolean isAvailable; 

	public RuntimePathListContext() {
		runtimePaths = new FieldList<FieldPath>(new FieldPath());
		isAvailable = false;
	}

	public FieldList<FieldPath> getPaths() {
		return runtimePaths;
	}
	
	@Override
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	@Override
	public boolean isAvailable() {
		return isAvailable;
	}
}
