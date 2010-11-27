/*****************************************************************************
 * This source file is part of SBS (Screen Build System),                    *
 * which is a component of Screen Framework                                  *
 *                                                                           *
 * Copyright (c) 2008-2010 Ratouit Thomas                                    *
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

package screen.tools.sbs.cmake;

import java.io.File;
import java.io.IOException;

import screen.tools.sbs.objects.Pack;
import screen.tools.sbs.utils.Logger;
import screen.tools.sbs.utils.ProcessLauncher;
import screen.tools.sbs.utils.Utilities;

/**
 * Cleans files associated to and generated by CMake for a given component
 * 
 * @author Ratouit Thomas
 *
 */
public class SBSCMakeCleaner {
	public SBSCMakeCleaner(){}
	
	/**
	 * Performs the cleaning
	 * 
	 * @param pack
	 * @param sbsXmlPath
	 */
	public void clean(Pack pack, String sbsXmlPath) {
		try {
	    	String[] command = null;
	    	String root = System.getProperty("SBS_ROOT");
	    	
	    	if(Utilities.isWindows())
	    		command = new String[]{"cmd.exe", "/C", "/Q", "/E:off", root+"/clean.bat"};
	    	else if(Utilities.isLinux())
	    		command = new String[]{"/bin/sh", root+"/clean.sh"};
	    	
	    	if(command!=null){
		    	Logger.info("command : "+ProcessLauncher.getCommand(command));
		    	ProcessLauncher p = new ProcessLauncher();
				p.execute(command,null,new File(sbsXmlPath));
				p.processOutputs();
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}