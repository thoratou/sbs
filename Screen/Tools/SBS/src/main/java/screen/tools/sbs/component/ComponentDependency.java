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

import screen.tools.sbs.fields.FieldBool;
import screen.tools.sbs.fields.FieldFactory;
import screen.tools.sbs.fields.FieldList;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.fields.interfaces.FieldBuildModeInterface;
import screen.tools.sbs.fields.interfaces.FieldExportInterface;
import screen.tools.sbs.fields.interfaces.FieldNameInterface;
import screen.tools.sbs.fields.interfaces.FieldToolChainInterface;
import screen.tools.sbs.fields.interfaces.FieldVersionInterface;
import screen.tools.sbs.objects.Entry;

public class ComponentDependency implements Entry<ComponentDependency>,
											FieldNameInterface,
											FieldVersionInterface,
											FieldExportInterface,
											FieldToolChainInterface,
											FieldBuildModeInterface {
	private FieldString name;
	private FieldString version;
	private FieldBool export;
	private FieldList<ComponentPath> includePathList;
	private FieldList<ComponentPath> libraryPathList;
	private FieldList<ComponentLibrary> libraryList;
	private FieldString toolChain;
	private FieldString buildMode;
	
	public ComponentDependency() {
		name = FieldFactory.createOptionalFieldString();
		version = FieldFactory.createOptionalFieldString();
		export = FieldFactory.createOptionalFieldBool("false");
		includePathList = new FieldList<ComponentPath>(new ComponentPath());
		libraryPathList = new FieldList<ComponentPath>(new ComponentPath());
		libraryList = new FieldList<ComponentLibrary>(new ComponentLibrary());
		toolChain = FieldFactory.createOptionalFieldString("all");
		buildMode = FieldFactory.createOptionalFieldString("all");
	}
	
	public ComponentDependency(ComponentDependency dependency) {
		name = dependency.name.copy();
		version = dependency.version.copy();
		export = dependency.export.copy();
		includePathList = dependency.includePathList.copy();
		libraryPathList = dependency.libraryPathList.copy();
		libraryList = dependency.libraryList.copy();
		toolChain = dependency.toolChain.copy();
		buildMode = dependency.buildMode.copy();
	}

	@Override
	public FieldString getName() {
		return name;
	}

	@Override
	public FieldString getVersion() {
		return version;
	}
	
	public FieldBool getExport() {
		return export;
	}

	public FieldList<ComponentPath> getIncludePathList() {
		return includePathList;
	}
	
	public FieldList<ComponentPath> getLibraryPathList() {
		return libraryPathList;
	}
	
	public FieldList<ComponentLibrary> getLibraryList() {
		return libraryList;
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
	public void merge(ComponentDependency dependency) {
		name.merge(dependency.name);
		version.merge(dependency.version);
		export.merge(dependency.export);
		includePathList.merge(dependency.includePathList);
		libraryPathList.merge(dependency.libraryPathList);
		libraryList.merge(dependency.libraryList);
		toolChain.merge(dependency.toolChain);
		buildMode.merge(dependency.toolChain);
	}

	@Override
	public ComponentDependency copy() {
		return new ComponentDependency(this);
	}
}
