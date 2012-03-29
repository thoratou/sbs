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
import screen.tools.sbs.objects.Entry;

public class RepositoryLocalData implements Entry<RepositoryLocalData> {
	private FieldString repositoryId;
	private FieldString locationType;
	private FieldString deliveryType;	
	private FieldPath remotePath;
	private FieldPath localPath;
	
	public RepositoryLocalData() {
		repositoryId = FieldFactory.createMandatoryFieldString();
		locationType = FieldFactory.createMandatoryFieldString();
		deliveryType = FieldFactory.createMandatoryFieldString();
		remotePath = FieldFactory.createOptionalFieldPath();
		localPath = FieldFactory.createOptionalFieldPath();
	}
	
	public RepositoryLocalData(RepositoryLocalData data) {
		repositoryId = data.repositoryId.copy();
		locationType = data.locationType.copy();
		deliveryType = data.deliveryType.copy();
		remotePath = data.remotePath.copy();
		localPath = data.localPath.copy();
	}

	@Override
	public void merge(RepositoryLocalData data) {
		repositoryId.merge(data.repositoryId);
		locationType.merge(data.locationType);
		deliveryType.merge(data.deliveryType);
		remotePath.merge(data.remotePath);
		localPath.merge(data.localPath);
	}

	@Override
	public RepositoryLocalData copy() {
		return new RepositoryLocalData(this);
	}
}
