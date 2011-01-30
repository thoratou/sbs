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

import java.io.File;

import org.w3c.dom.Document;

import screen.tools.sbs.repositories.RepositoryDataTable;
import screen.tools.sbs.repositories.RepositoryFilterTable;
import screen.tools.sbs.targets.TargetCall;

public class GlobalSettings {
	private static GlobalSettings globalSettings = new GlobalSettings();
	private EnvironmentVariables envVar;
	private ErrorList errorList;
	private String sbsXmlPath;
	private String sbsXmlFile;
	private Pack pack;
	private Pack testPack;
	private boolean debug;
	private boolean isUsage;
	private TargetCall targetUsage;
	private Document xmlDocument;
	private RepositoryFilterTable repositoryFilterTable;
	private RepositoryDataTable repositoryDataTable;
	
	private GlobalSettings() {
		envVar = new EnvironmentVariables();
		errorList = new ErrorList();
		sbsXmlFile = null;
		sbsXmlPath = null;
		pack = null;
		testPack = null;
		debug = false;
		isUsage = false;
		targetUsage = null;
		xmlDocument = null;
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
	
	public void setSbsXmlPath(String path) {
		sbsXmlPath = new File(path).getAbsolutePath()+"/";
	}
	
	public String getSbsXmlPath(){
		return sbsXmlPath;
	}
	
	public void setSbsXmlFile(String file){
		sbsXmlFile = file;
	}
	
	public String getSbsXmlFile(){
		return sbsXmlFile;
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

	@Deprecated
	public Pack getPack() {
		return pack;
	}
	
	@Deprecated
	public Pack getTestPack() {
		return testPack;
	}
	
	@Deprecated
	public void setPack(Pack pack) {
		this.pack = pack;
	}
	
	@Deprecated
	public void setTestPack(Pack testPack) {
		this.testPack = testPack;
	}

	@Deprecated
	public void setXmlDocument(Document doc) {
		xmlDocument = doc;
	}
	
	@Deprecated
	public Document getXmlDocument(){
		return xmlDocument;
	}

	public RepositoryFilterTable getRepositoryFilterTable() {
		return repositoryFilterTable;
	}

	public RepositoryDataTable getRepositoryDataTable() {
		return repositoryDataTable;
	}
}
