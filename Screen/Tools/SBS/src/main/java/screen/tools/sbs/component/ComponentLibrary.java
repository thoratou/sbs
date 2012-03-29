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

package screen.tools.sbs.component;

import screen.tools.sbs.fields.FieldFactory;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.fields.interfaces.FieldBuildModeInterface;
import screen.tools.sbs.fields.interfaces.FieldLibraryNameInterface;
import screen.tools.sbs.fields.interfaces.FieldToolChainInterface;
import screen.tools.sbs.objects.Entry;

public class ComponentLibrary implements 	Entry<ComponentLibrary>,
											FieldLibraryNameInterface,
											FieldToolChainInterface,
											FieldBuildModeInterface {
	private FieldString libraryName;
	private FieldString toolChain;
	private FieldString buildMode;
	
	public ComponentLibrary(){
		libraryName = FieldFactory.createMandatoryFieldString();
		toolChain = FieldFactory.createOptionalFieldString("all");
		buildMode = FieldFactory.createOptionalFieldString("all");
	}

	public ComponentLibrary(ComponentLibrary library) {
		libraryName = library.libraryName.copy();
		toolChain = library.toolChain.copy();
		buildMode = library.buildMode.copy();
	}

	@Override
	public FieldString getLibraryName() {
		return libraryName;
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
	public void merge(ComponentLibrary library) {
		libraryName.merge(library.libraryName);
		toolChain.merge(library.toolChain);
		buildMode.merge(library.buildMode);
	}

	@Override
	public ComponentLibrary copy() {
		return new ComponentLibrary(this);
	}
}