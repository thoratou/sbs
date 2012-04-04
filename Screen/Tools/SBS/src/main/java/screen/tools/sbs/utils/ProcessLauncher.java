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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

public class ProcessLauncher {

	private Process process;
	private ProcessBuilder processBuilder;
	
	public static String getCommand(String[] command){
		StringBuffer buffer = new StringBuffer();
    	for(int i = 0; i<command.length; i++){
    		buffer.append(command[i]);
    		if(i+1<command.length)
    			buffer.append(" ");
    	}
		return buffer.toString();
	}
	
	public static String getCommand(List<String> command){
		StringBuffer buffer = new StringBuffer();
		Iterator<String> iterator = command.iterator();
		while(iterator.hasNext()){
			buffer.append(iterator.next());
			if(iterator.hasNext())
				buffer.append(" ");
		}
		return buffer.toString();
	}
	
	public ProcessLauncher(List<String> command) {
		process = null;
		processBuilder = new ProcessBuilder(command);
	}
	
	public ProcessLauncher(String[] command) {
		process = null;
		processBuilder = new ProcessBuilder(command);
	}

	public ProcessBuilder getProcessBuilder(){
		return processBuilder;
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
	
	public void execute() {
		try {
			process = processBuilder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread() {
		    public void run() {
		        try {
			            process.waitFor();
		        } catch(InterruptedException e) {
		            e.printStackTrace();
		        }
		    }
		}.start();
	}
}