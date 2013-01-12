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

package screen.tools.sbs.profile;

import screen.tools.sbs.objects.Entry;

public class Profile implements Entry<Profile>{
	public ProfileHierarchie buildTypeHierarchie;
	public ProfileHierarchie buildModeHierarchie;
	public ProfileHierarchie toolChainHierarchie;
	
	public Profile() {
		buildTypeHierarchie = new ProfileHierarchie();
		buildModeHierarchie = new ProfileHierarchie();
		toolChainHierarchie = new ProfileHierarchie();
	}

	public Profile(Profile profile) {
		buildTypeHierarchie = profile.buildTypeHierarchie.copy();
		buildModeHierarchie = profile.buildModeHierarchie.copy();
		toolChainHierarchie = profile.toolChainHierarchie.copy();
	}
	
	public ProfileHierarchie getBuildModeHierarchie() {
		return buildModeHierarchie;
	}
	
	public ProfileHierarchie getBuildTypeHierarchie() {
		return buildTypeHierarchie;
	}
	
	public ProfileHierarchie getToolChainHierarchie() {
		return toolChainHierarchie;
	}

	@Override
	public void merge(Profile profile) {
		buildTypeHierarchie.merge(profile.buildTypeHierarchie);
		buildModeHierarchie.merge(profile.buildModeHierarchie);
		toolChainHierarchie.merge(profile.toolChainHierarchie);
	}
	@Override
	public Profile copy() {
		return new Profile(this);
	}
}
