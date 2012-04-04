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

package screen.tools.sbs.repositories.query;

import java.sql.ResultSet;
import java.sql.SQLException;

import screen.tools.sbs.fields.FieldList;
import screen.tools.sbs.repositories.RepositoryData;
import screen.tools.sbs.utils.DbQueryHandler;

public class RetrieveRepositoriesQuery {
	
	public void process(final FieldList<RepositoryData> list){
		new DbQueryHandler("select * from repository") {
			
			@Override
			protected boolean processResult(ResultSet result) throws SQLException {
				RepositoryData data = list.allocate();
				data.getRepositoryId().set(result.getString("repo-id"));
				data.getLocationType().set(result.getString("location-type"));
				data.getDeliveryType().set(result.getString("delivery-type"));
				data.getRemotePath().set(result.getString("remote-path"));
				data.getLocalPath().set(result.getString("local-path"));
				return true;
			}
		}.process();
	}
}
