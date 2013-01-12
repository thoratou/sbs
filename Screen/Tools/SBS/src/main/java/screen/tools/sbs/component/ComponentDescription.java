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
import screen.tools.sbs.fields.FieldPath;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.fields.interfaces.FieldBuildModeInterface;
import screen.tools.sbs.fields.interfaces.FieldBuildTypeInterface;
import screen.tools.sbs.fields.interfaces.FieldCompileNameInterface;
import screen.tools.sbs.fields.interfaces.FieldFullNameInterface;
import screen.tools.sbs.fields.interfaces.FieldLibraryNameInterface;
import screen.tools.sbs.fields.interfaces.FieldPathInterface;
import screen.tools.sbs.fields.interfaces.FieldToolChainInterface;
import screen.tools.sbs.objects.Entry;

public class ComponentDescription implements 	Entry<ComponentDescription>,
												FieldLibraryNameInterface,
												FieldFullNameInterface,
												FieldCompileNameInterface,
												FieldPathInterface,
												FieldToolChainInterface,
												FieldBuildModeInterface,
												FieldBuildTypeInterface {
	private FieldString libraryName;
	private FieldString fullName;
	private FieldString compileName;
	private FieldPath path;
	private FieldString toolChain;
	private FieldString buildType;
	private FieldString buildMode;
	
	public ComponentDescription() {
		libraryName = FieldFactory.createMandatoryFieldString();
		fullName = FieldFactory.createMandatoryFieldString();
		compileName = FieldFactory.createMandatoryFieldString();
		path = FieldFactory.createOptionalFieldPath(".");
		toolChain = FieldFactory.createOptionalFieldString("all");
		buildType = FieldFactory.createOptionalFieldString("all");
		buildMode = FieldFactory.createOptionalFieldString("all");
	}
	
	public ComponentDescription(ComponentDescription description) {
		libraryName = description.libraryName.copy();
		fullName = description.fullName.copy();
		compileName = description.compileName.copy();
		path = description.path.copy();
		toolChain = description.toolChain.copy();
		buildType = description.buildType.copy();
		buildMode = description.buildMode.copy();
	}

	@Override
	public FieldString getLibraryName() {
		return libraryName;
	}
	
	public FieldString getFullName() {
		return fullName;
	}
	
	public FieldString getCompileName() {
		return compileName;
	}
	
	@Override
	public FieldPath getPath() {
		return path;
	}
	
	@Override
	public FieldString getToolChain() {
		return toolChain;
	}

	@Override
	public FieldString getBuildType() {
		return buildType;
	}
	
	@Override
	public FieldString getBuildMode() {
		return buildMode;
	}

	@Override
	public void merge(ComponentDescription description) {
		libraryName.merge(description.libraryName);
		fullName.merge(description.fullName);
		compileName.merge(description.compileName);
		path.merge(description.path);
		toolChain.merge(description.toolChain);
		buildType.merge(description.buildType);
		buildMode.merge(description.buildMode);
	}

	@Override
	public ComponentDescription copy() {
		return new ComponentDescription(this);
	}
}
