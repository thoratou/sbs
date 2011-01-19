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

package screen.tools.sbs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import screen.tools.sbs.objects.EnvironmentVariables;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.GlobalSettings;
import screen.tools.sbs.objects.Pack;
import screen.tools.sbs.utils.Logger;
import screen.tools.sbs.utils.ProcessLauncher;
import screen.tools.sbs.utils.Utilities;

/**
 * Class to copy component headers into the repository.
 * Deprecated due to repository component files that indicate include paths 
 * 
 * @deprecated
 * @author Ratouit Thomas
 *
 */
@Deprecated
public class SBSHeaderDeployer {

	public void deploy(String sbsXmlPath, Pack pack) {
		try {
			ErrorList err = GlobalSettings.getGlobalSettings().getErrorList();
			EnvironmentVariables variables = GlobalSettings.getGlobalSettings().getEnvironmentVariables();
			
	    	String[] command = null;
	    	
	    	if(!variables.contains("REPOSITORY_ROOT")){
				err.addError("undefined variable : REPOSITORY_ROOT");
			}
			String repoRoot = variables.getValue("REPOSITORY_ROOT");
	    	String packPath = pack.getProperties().getName().getString();
	    	String packVersion = pack.getProperties().getVersion().getString();
	    	String root = System.getProperty("SBS_ROOT");
	    	
	    	if(Utilities.isWindows())
	    		command = new String[]{"cmd.exe", "/C", "/Q", "/E:off", root+"/deploy-header.bat", new File(sbsXmlPath).getAbsolutePath(), repoRoot +"/"+packPath+"/"+packVersion};
	    	else if(Utilities.isLinux())
	    		command = new String[]{"/bin/sh", root+"/deploy-header.sh", new File(sbsXmlPath).getAbsolutePath(), repoRoot +"/"+packPath+"/"+packVersion};
	    	
	    	if(command!=null){
		    	Logger.info("command : "+ProcessLauncher.getCommand(command));
		    	ProcessLauncher p = new ProcessLauncher();
		
				p.execute(command,null,new File(sbsXmlPath));
				
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		
		        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		
		        String s;
		        while ((s = stdInput.readLine()) != null) {
		        	Logger.info(s);
		        }
		        while ((s = stdError.readLine()) != null) {
		            err.addError(s);
		        }
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
