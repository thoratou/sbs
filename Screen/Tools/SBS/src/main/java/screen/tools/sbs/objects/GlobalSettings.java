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

package screen.tools.sbs.objects;

import screen.tools.sbs.repositories.RepositoryDataTable;
import screen.tools.sbs.repositories.RepositoryFilterTable;
import screen.tools.sbs.targets.TargetCall;

public class GlobalSettings {
	private static GlobalSettings globalSettings = new GlobalSettings();
	private EnvironmentVariables envVar;
	private ErrorList errorList;
	private boolean debug;
	private boolean isUsage;
	private TargetCall targetUsage;
	private RepositoryFilterTable repositoryFilterTable;
	private RepositoryDataTable repositoryDataTable;
	
	private GlobalSettings() {
		envVar = new EnvironmentVariables();
		errorList = new ErrorList();
		debug = false;
		isUsage = false;
		targetUsage = null;
		repositoryFilterTable = new RepositoryFilterTable();
		repositoryDataTable = new RepositoryDataTable();
	}
	
	public static GlobalSettings getGlobalSettings() {
		return globalSettings;
	}

	public EnvironmentVariables getEnvironmentVariables() {
		return envVar;
	}
	
	public ErrorList getErrorList(){
		return errorList;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setError(boolean b) {
		errorList.setLogError(b);
	}

	public void needUsage() {
		isUsage = true;
		targetUsage = null;
	}
	
	public void needUsage(TargetCall call) {
		isUsage = true;
		targetUsage = call;
	}

	
	public boolean isPrintUsage(){
		return isUsage;
	}
	
	public TargetCall getTargetUsage(){
		return targetUsage;
	}
	
	public RepositoryFilterTable getRepositoryFilterTable() {
		return repositoryFilterTable;
	}

	public RepositoryDataTable getRepositoryDataTable() {
		return repositoryDataTable;
	}
}
