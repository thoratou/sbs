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
import screen.tools.sbs.fields.FieldPath;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.fields.interfaces.FieldBuildModeInterface;
import screen.tools.sbs.fields.interfaces.FieldBuildTypeInterface;
import screen.tools.sbs.fields.interfaces.FieldNameInterface;
import screen.tools.sbs.fields.interfaces.FieldPathInterface;
import screen.tools.sbs.fields.interfaces.FieldToolChainInterface;
import screen.tools.sbs.fields.interfaces.FieldVersionInterface;
import screen.tools.sbs.objects.Entry;

public class PackManifest implements Entry<PackManifest>,
									 FieldNameInterface,
									 FieldVersionInterface,
									 FieldBuildTypeInterface,
									 FieldBuildModeInterface,
									 FieldToolChainInterface,
									 FieldPathInterface{
	private FieldString name;
	private FieldString version;
	private FieldString buildType;
	private FieldString buildMode;
	private FieldString toolChain;
	private FieldPath path;
	
	public PackManifest() {
		name = FieldFactory.createMandatoryFieldString();
		version = FieldFactory.createMandatoryFieldString();
		buildType = FieldFactory.createMandatoryFieldString();
		buildMode = FieldFactory.createMandatoryFieldString();
		toolChain = FieldFactory.createMandatoryFieldString();
		path = FieldFactory.createMandatoryFieldPath();
	}

	public PackManifest(PackManifest packManifest) {
		name = packManifest.name.copy();
		version = packManifest.version.copy();
		buildType = packManifest.buildType.copy();
		buildMode = packManifest.buildMode.copy();
		toolChain = packManifest.toolChain.copy();
		path = packManifest.path.copy();
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
	public FieldString getBuildType() {
		return buildType;
	}
	
	@Override
	public FieldString getBuildMode() {
		return buildMode;
	}
	
	@Override
	public FieldString getToolChain() {
		return toolChain;
	}
	
	@Override
	public FieldPath getPath() {
		return path;
	}

	@Override
	public void merge(PackManifest packManifest) {
		name.merge(packManifest.name);
		version.merge(packManifest.version);
		buildType.merge(packManifest.buildType);
		buildMode.merge(packManifest.buildMode);
		toolChain.merge(packManifest.toolChain);
		path.merge(packManifest.path);
	}

	@Override
	public PackManifest copy() {
		return new PackManifest(this);
	}
}
