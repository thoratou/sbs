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

package screen.tools.sbs.repositories;

import screen.tools.sbs.fields.FieldFactory;
import screen.tools.sbs.fields.FieldPath;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.fields.interfaces.FieldDeliveryTypeInterface;
import screen.tools.sbs.fields.interfaces.FieldLocalPathInterface;
import screen.tools.sbs.fields.interfaces.FieldLocationTypeInterface;
import screen.tools.sbs.fields.interfaces.FieldRemotePathInterface;
import screen.tools.sbs.fields.interfaces.FieldRepositoryIdInterface;
import screen.tools.sbs.objects.Entry;

public class RepositoryData implements Entry<RepositoryData>,
									   FieldRepositoryIdInterface,
									   FieldLocationTypeInterface,
									   FieldDeliveryTypeInterface,
									   FieldRemotePathInterface,
									   FieldLocalPathInterface{
	private FieldString repositoryId;
	private FieldString locationType;
	private FieldString deliveryType;	
	private FieldPath remotePath;
	private FieldPath localPath;
	
	public RepositoryData() {
		repositoryId = FieldFactory.createMandatoryFieldString();
		locationType = FieldFactory.createMandatoryFieldString();
		deliveryType = FieldFactory.createMandatoryFieldString();
		remotePath = FieldFactory.createOptionalFieldPath();
		localPath = FieldFactory.createOptionalFieldPath();
	}
	
	public RepositoryData(RepositoryData data) {
		repositoryId = data.repositoryId.copy();
		locationType = data.locationType.copy();
		deliveryType = data.deliveryType.copy();
		remotePath = data.remotePath.copy();
		localPath = data.localPath.copy();
	}

	@Override
	public FieldString getRepositoryId() {
		return repositoryId;
	}

	@Override
	public FieldString getLocationType() {
		return locationType;
	}

	@Override
	public FieldString getDeliveryType() {
		return deliveryType;
	}

	@Override
	public FieldPath getRemotePath() {
		return remotePath;
	}

	@Override
	public FieldPath getLocalPath() {
		return localPath;
	}
	
	@Override
	public void merge(RepositoryData data) {
		repositoryId.merge(data.repositoryId);
		locationType.merge(data.locationType);
		deliveryType.merge(data.deliveryType);
		remotePath.merge(data.remotePath);
		localPath.merge(data.localPath);
	}

	@Override
	public RepositoryData copy() {
		return new RepositoryData(this);
	}

}
