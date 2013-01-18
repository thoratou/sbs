/*****************************************************************************
 * This source file is part of SBS (Screen Build System),                    *
 * which is a component of Screen Framework                                  *
 *                                                                           *
 * Copyright (c) 2008-2013 Ratouit Thomas                                    *
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

package screen.tools.sbs.pack;

import screen.tools.sbs.fields.FieldFactory;
import screen.tools.sbs.fields.FieldObject;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.fields.interfaces.FieldKeyInterface;
import screen.tools.sbs.fields.interfaces.FieldValueInterface;
import screen.tools.sbs.objects.Entry;

public class PackFlag implements Entry<PackFlag>,
								 FieldKeyInterface,
								 FieldValueInterface{
	private FieldString key;
	private FieldObject value;
	
	public PackFlag() {
		key = FieldFactory.createMandatoryFieldString();
		value = new FieldObject();
	}
	
	public PackFlag(PackFlag flag) {
		key = flag.key.copy();
		value = flag.value.copy();
	}
	
	@Override
	public FieldString getKey() {
		return key;
	}
	
	@Override
	public FieldObject getValue() {
		return value;
	}
	
	@Override
	public void merge(PackFlag flag) {
		key.merge(flag.key);
		value.merge(flag.value);
	}

	@Override
	public PackFlag copy() {
		return new PackFlag(this);
	}
}
