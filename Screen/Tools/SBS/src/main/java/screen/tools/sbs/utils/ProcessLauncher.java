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

package screen.tools.sbs.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import screen.tools.sbs.objects.ErrorList;

public class ProcessLauncher {

	private Process process;
	
	public static String getCommand(String[] command){
		String comStr = "";
    	for(int i = 0; i<command.length; i++){
    		comStr+=command[i];
    		if(i+1<command.length)
    			comStr+=" ";
    	}
		return comStr;
	}
	
	public ProcessLauncher() {
		process = null;
	}
	
	public InputStream getInputStream() {
		return process.getInputStream();
	}
	
	public OutputStream getOutputStream() {
		return process.getOutputStream();
	}
	
	public InputStream getErrorStream() {
		return process.getErrorStream();
	}

	public void execute(String command) throws IOException {
		process = Runtime.getRuntime().exec(command);
		execute();
	}
	
	public void execute(String[] cmdarray) throws IOException {
		process = Runtime.getRuntime().exec(cmdarray);
		execute();
	}
	
	public void execute(String[] cmdarray, String[] envp) throws IOException {
		process = Runtime.getRuntime().exec(cmdarray, envp);
		execute();
	}
	
	public void execute(String[] cmdarray, String[] envp, File dir) throws IOException {
		process = Runtime.getRuntime().exec(cmdarray, envp, dir);
		execute();
	}
	
	public void execute(String command, String[] envp) throws IOException {
		process = Runtime.getRuntime().exec(command, envp);
		execute();
	}
	
	public void execute(String command, String[] envp, File dir) throws IOException  {
		process = Runtime.getRuntime().exec(command, envp, dir);
		execute();
	}
	
	private void execute() {
		new Thread() {
		    public void run() {
		        try {
		            process.waitFor();
		        } catch(InterruptedException ie) {
		            ie.printStackTrace();
		        }
		    }
		}.start();
	}

	public void processOutputs() throws IOException {
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(getErrorStream()));

        String s;
		while ((s = stdInput.readLine()) != null) {
			Logger.info(s);
		}
        while ((s = stdError.readLine()) != null) {
        	ErrorList.instance.addError(s);
        }		
	}
	
	
}