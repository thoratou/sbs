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

public class RepositoryType {
	// no type
	public static final int NO_TYPE_FLAG = 0x01;	  
	// is remote or local repository
	public static final int REMOTE_LOCAL_FLAG = 0x02;
	// is release or snapshot repository
	public static final int RELEASE_SNAPSHOT_FLAG = 0x04;
	// is release or snapshot external or internal repository
	public static final int EXTERNAL_INTERNAL_FLAG = 0x08;
	
	private int type;
	
	public RepositoryType() {
		setType(NO_TYPE_FLAG);
	}
	
	public RepositoryType(int type) {
		setType(type);
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}	
	
	@Override
	public String toString() {
		if((type & NO_TYPE_FLAG) != 0)
			return "E";
		
		String ret = "";
		
		if((type & REMOTE_LOCAL_FLAG) != 0)
			ret += "R";
		else
			ret += "L";
		
		if((type & RELEASE_SNAPSHOT_FLAG) != 0)
			ret += "R";
		else
			ret += "S";

		if((type & EXTERNAL_INTERNAL_FLAG) != 0)
			ret += "E";
		else
			ret += "I";

		return ret;
	}
}

