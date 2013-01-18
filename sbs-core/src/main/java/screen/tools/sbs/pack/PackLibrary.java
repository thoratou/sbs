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

import screen.tools.sbs.fields.FieldBool;
import screen.tools.sbs.fields.FieldFactory;
import screen.tools.sbs.fields.FieldPath;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.fields.interfaces.FieldBuildModeInterface;
import screen.tools.sbs.fields.interfaces.FieldBuildTypeInterface;
import screen.tools.sbs.fields.interfaces.FieldCompileNameInterface;
import screen.tools.sbs.fields.interfaces.FieldExportInterface;
import screen.tools.sbs.fields.interfaces.FieldFullNameInterface;
import screen.tools.sbs.fields.interfaces.FieldLibraryNameInterface;
import screen.tools.sbs.fields.interfaces.FieldNameInterface;
import screen.tools.sbs.fields.interfaces.FieldPathInterface;
import screen.tools.sbs.fields.interfaces.FieldToolChainInterface;
import screen.tools.sbs.fields.interfaces.FieldVersionInterface;
import screen.tools.sbs.objects.Entry;

public class PackLibrary implements Entry<PackLibrary>,
									FieldNameInterface,
									FieldVersionInterface,
									FieldExportInterface,
									FieldLibraryNameInterface,
									FieldFullNameInterface,
									FieldCompileNameInterface,
									FieldPathInterface,
									FieldToolChainInterface,
									FieldBuildTypeInterface,
									FieldBuildModeInterface{
	private FieldString name;
	private FieldString version;
	private FieldBool export;
	private FieldString libraryName;
	private FieldString fullName;
	private FieldString compileName;
	private FieldPath path;
	private FieldString toolChain;
	private FieldString buildType;
	private FieldString buildMode;
	
	public PackLibrary(){
		name = FieldFactory.createMandatoryFieldString();
		version = FieldFactory.createMandatoryFieldString();
		export = FieldFactory.createOptionalFieldBool("false");
		libraryName = FieldFactory.createMandatoryFieldString();
		fullName = FieldFactory.createMandatoryFieldString();
		compileName = FieldFactory.createMandatoryFieldString();
		path = FieldFactory.createMandatoryFieldPath();
		toolChain = FieldFactory.createMandatoryFieldString();
		buildType = FieldFactory.createMandatoryFieldString();
		buildMode = FieldFactory.createMandatoryFieldString();
	}

	public PackLibrary(PackLibrary library) {
		name = library.name.copy();
		version = library.version.copy();
		export = library.export.copy();
		libraryName = library.libraryName.copy();
		fullName = library.fullName.copy();
		compileName = library.compileName.copy();
		path = library.path.copy();
		toolChain = library.toolChain.copy();
		buildType = library.buildType.copy();
		buildMode = library.buildMode.copy();
	}

	@Override
	public FieldString getName() {
		return name;
	}

	@Override
	public FieldString getVersion() {
		return version;
	}
	
	@Override
	public FieldBool getExport() {
		return export;
	}
	
	@Override
	public FieldString getLibraryName() {
		return libraryName;
	}
	
	@Override
	public FieldString getFullName() {
		return fullName;
	}
	
	@Override
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
	public void merge(PackLibrary library) {
		name.merge(library.name);
		version.merge(library.version);
		export.merge(library.export);
		fullName.merge(library.fullName);
		compileName.merge(library.compileName);
		path.merge(library.path);
		buildType.merge(library.buildType);
		buildMode.merge(library.buildMode);
	}

	@Override
	public PackLibrary copy() {
		return new PackLibrary(this);
	}
}