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

package screen.tools.sbs.profile;

import screen.tools.sbs.fields.FieldFactory;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.objects.Entry;
import screen.tools.sbs.utils.Logger.LogLevel;

public class ProfileHierarchieItem implements Entry<ProfileHierarchieItem>{
	private FieldString field;
	private LogLevel logLevel;
	
	public ProfileHierarchieItem() {
		field = FieldFactory.createMandatoryFieldString();
		logLevel = LogLevel.NO_LOG;
	}

	public ProfileHierarchieItem(ProfileHierarchieItem profileHierarchieItem) {
		field = profileHierarchieItem.field.copy();
		logLevel = profileHierarchieItem.logLevel;
	}
	
	public FieldString getField() {
		return field;
	}
	
	public LogLevel getLogLevel() {
		return logLevel;
	}
	
	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	@Override
	public void merge(ProfileHierarchieItem profileHierarchieItem) {
		field.merge(profileHierarchieItem.field);
		logLevel = profileHierarchieItem.logLevel;
	}

	@Override
	public ProfileHierarchieItem copy() {
		return new ProfileHierarchieItem(this);
	}

}
