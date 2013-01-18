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
import screen.tools.sbs.fields.FieldFile;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.fields.interfaces.FieldBuildModeInterface;
import screen.tools.sbs.fields.interfaces.FieldFileInterface;
import screen.tools.sbs.fields.interfaces.FieldToolChainInterface;
import screen.tools.sbs.objects.Entry;

public class ComponentImport implements Entry<ComponentImport>,
										FieldFileInterface,
										FieldToolChainInterface,
										FieldBuildModeInterface {
	private FieldFile file;
	private FieldString toolChain;
	private FieldString buildMode;
	
	public ComponentImport() {
		file = FieldFactory.createMandatoryFieldFile();
		toolChain = FieldFactory.createOptionalFieldString("all");
		buildMode = FieldFactory.createOptionalFieldString("all");
	}

	public ComponentImport(ComponentImport import_) {
		file = import_.file.copy();
		toolChain = import_.toolChain.copy();
		buildMode = import_.buildMode.copy();
	}

	@Override
	public FieldFile getFile() {
		return file;
	}
	
	@Override
	public FieldString getToolChain() {
		return toolChain;
	}

	@Override
	public FieldString getBuildMode() {
		return buildMode;
	}

	@Override
	public void merge(ComponentImport import_) {
		file.merge(import_.file);
		toolChain.merge(import_.toolChain);
		buildMode.merge(import_.buildMode);
	}

	@Override
	public ComponentImport copy() {
		return new ComponentImport(this);
	}
}
