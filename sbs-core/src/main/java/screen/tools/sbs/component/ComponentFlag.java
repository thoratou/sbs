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

package screen.tools.sbs.component;

import screen.tools.sbs.fields.FieldFactory;
import screen.tools.sbs.fields.FieldObject;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.fields.interfaces.FieldBuildModeInterface;
import screen.tools.sbs.fields.interfaces.FieldKeyInterface;
import screen.tools.sbs.fields.interfaces.FieldToolChainInterface;
import screen.tools.sbs.fields.interfaces.FieldValueInterface;
import screen.tools.sbs.objects.Entry;

public class ComponentFlag implements 	Entry<ComponentFlag>,
										FieldKeyInterface,
										FieldValueInterface,
										FieldToolChainInterface,
										FieldBuildModeInterface {
	private FieldString key;
	private FieldObject value;
	private FieldString toolChain;
	private FieldString buildMode;
	
	public ComponentFlag() {
		key = FieldFactory.createMandatoryFieldString();
		value = new FieldObject();
		toolChain = FieldFactory.createOptionalFieldString("all");
		buildMode = FieldFactory.createOptionalFieldString("all");
	}
	
	public ComponentFlag(ComponentFlag flag) {
		key = flag.key.copy();
		value = flag.value.copy();
		toolChain = flag.buildMode.copy();
		buildMode = flag.buildMode.copy();
	}

	public FieldString getKey() {
		return key;
	}
	
	public FieldObject getValue() {
		return value;
	}
	
	@Override
	public FieldString getToolChain() {
		return toolChain;
	}
	
	@Override
	public FieldString getBuildMode(){
		return buildMode;
	}

	@Override
	public void merge(ComponentFlag flag) {
		key.merge(flag.key);
		value.merge(flag.value);
		toolChain.merge(flag.toolChain);
		buildMode.merge(flag.buildMode);
	}

	@Override
	public ComponentFlag copy() {
		return new ComponentFlag(this);
	}
}
