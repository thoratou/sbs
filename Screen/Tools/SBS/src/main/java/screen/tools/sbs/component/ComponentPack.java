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

import screen.tools.sbs.fields.FieldList;
import screen.tools.sbs.fields.FieldPath;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.fields.interfaces.FieldBuildModeInterface;
import screen.tools.sbs.fields.interfaces.FieldBuildTypeInterface;
import screen.tools.sbs.fields.interfaces.FieldNameInterface;
import screen.tools.sbs.fields.interfaces.FieldPathInterface;
import screen.tools.sbs.fields.interfaces.FieldToolChainInterface;
import screen.tools.sbs.fields.interfaces.FieldVersionInterface;
import screen.tools.sbs.objects.Entry;
import screen.tools.sbs.pack.PackManifest;

public class ComponentPack implements 	Entry<ComponentPack>,
										FieldNameInterface,
										FieldVersionInterface,
										FieldPathInterface,
										FieldToolChainInterface,
										FieldBuildModeInterface,
										FieldBuildTypeInterface {
	private PackManifest properties;
	private FieldList<ComponentDependency> dependencyList;
	private FieldList<ComponentDependency> runtimeList;
	private FieldList<ComponentDescription> descriptionList;
	private FieldList<ComponentFlag> flagList;
	private FieldList<ComponentImport> importList;
	
	public ComponentPack(){
		properties = new PackManifest();
		dependencyList = new FieldList<ComponentDependency>(new ComponentDependency());
		runtimeList = new FieldList<ComponentDependency>(new ComponentDependency());
		descriptionList =  new FieldList<ComponentDescription>(new ComponentDescription());
		flagList = new FieldList<ComponentFlag>(new ComponentFlag());
		importList = new FieldList<ComponentImport>(new ComponentImport());
	}
	
	public ComponentPack(ComponentPack componentPack) {
		properties = componentPack.properties.copy();
		dependencyList = componentPack.dependencyList.copy();
		runtimeList = componentPack.runtimeList.copy();
		dependencyList = componentPack.dependencyList.copy();
		flagList = componentPack.flagList.copy();
		importList = componentPack.importList.copy();
	}

	public PackManifest getProperties() {
		return properties;
	}
	
	public FieldList<ComponentDependency> getDependencyList() {
		return dependencyList;
	}

	public FieldList<ComponentDependency> getRuntimeList() {
		return runtimeList;
	}
	
	public FieldList<ComponentDescription> getDescriptionList() {
		return descriptionList;
	}

	public FieldList<ComponentFlag> getFlagList() {
		return flagList;
	}
	
	public FieldList<ComponentImport> getImportList() {
		return importList;
	}
	
	@Override
	public FieldString getVersion() {
		return properties.getVersion();
	}

	@Override
	public FieldString getName() {
		return properties.getName();
	}
	
	@Override
	public FieldPath getPath() {
		return properties.getPath();
	}
	
	@Override
	public FieldString getBuildMode() {
		return properties.getBuildMode();
	}

	@Override
	public FieldString getToolChain() {
		return properties.getBuildMode();
	}
	
	@Override
	public FieldString getBuildType() {
		return properties.getBuildType();
	}

	@Override
	public void merge(ComponentPack componentPack) {
		properties.merge(componentPack.properties);
		dependencyList.merge(componentPack.dependencyList);
		runtimeList.merge(componentPack.runtimeList);
		dependencyList.merge(componentPack.dependencyList);
		flagList.merge(componentPack.flagList);
		importList.merge(componentPack.importList);
	}

	@Override
	public ComponentPack copy() {
		return new ComponentPack(this);
	}
}
