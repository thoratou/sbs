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

import screen.tools.sbs.fields.FieldFactory;
import screen.tools.sbs.fields.FieldPath;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.fields.interfaces.FieldBuildModeInterface;
import screen.tools.sbs.fields.interfaces.FieldNameInterface;
import screen.tools.sbs.fields.interfaces.FieldPathInterface;
import screen.tools.sbs.fields.interfaces.FieldRepositoryIdInterface;
import screen.tools.sbs.fields.interfaces.FieldToolChainInterface;
import screen.tools.sbs.fields.interfaces.FieldVersionInterface;
import screen.tools.sbs.objects.Entry;

public class ComponentData implements Entry<ComponentData>,
											FieldRepositoryIdInterface,
											FieldNameInterface,
											FieldVersionInterface,
											FieldToolChainInterface,
											FieldBuildModeInterface,
											FieldPathInterface{
	private FieldString repositoryId;	
	private FieldString name;
	private FieldString version;
	private FieldString toolChain;
	private FieldString buildMode;
	private FieldPath path;
	
	public ComponentData() {
		repositoryId = FieldFactory.createMandatoryFieldString();
		name = FieldFactory.createMandatoryFieldString();
		version = FieldFactory.createMandatoryFieldString();
		toolChain = FieldFactory.createMandatoryFieldString();
		buildMode = FieldFactory.createMandatoryFieldString();
		path = FieldFactory.createOptionalFieldPath();
	}
	
	public ComponentData(ComponentData component) {
		repositoryId = component.repositoryId.copy();
		name = component.name.copy();
		version = component.version.copy();
		toolChain = component.toolChain.copy();
		buildMode = component.buildMode.copy();
		path = component.path.copy();
	}
	
	@Override
	public FieldString getRepositoryId() {
		return repositoryId;
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
	public FieldPath getPath() {
		return path;
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
	public void merge(ComponentData component) {
		repositoryId.merge(component.repositoryId);
		name.merge(component.name);
		version.merge(component.version);
		toolChain.merge(component.toolChain);
		buildMode.merge(component.buildMode);
		path.merge(component.path);
	}

	@Override
	public ComponentData copy() {
		return new ComponentData(this);
	}
}
