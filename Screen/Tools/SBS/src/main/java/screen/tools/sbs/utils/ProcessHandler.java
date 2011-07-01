package screen.tools.sbs.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import screen.tools.sbs.objects.ErrorList;

public abstract class ProcessHandler {
	
	public void exec(String[] command, File path){
	    try {
	    	final ProcessLauncher p = new ProcessLauncher(); 	
	    	p.execute(command,null,path);
			
	    	Thread outThread = new Thread() {
	    		public void run() {
	    			try {
	    				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    				String line = "";
	    				try {
	    					while((line = reader.readLine()) != null) {
	    			        	processOutLine(line);
	    					}
	    				} finally {
	    					reader.close();
	    				}
	    			} catch(IOException e) {
	    	        	ErrorList.instance.addError(e.getMessage());
	    			}
	    		}
	    	};
	
	    	Thread errThread = new Thread() {
	    		public void run() {
	    			try {
	    				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	    				String line = "";
	    				try {
	    					while((line = reader.readLine()) != null) {
	    						processErrLine(line);
	    					}
	    				} finally {
	    					reader.close();
	    				}
	    			} catch(IOException e) {
	    	        	ErrorList.instance.addError(e.getMessage());
	    			}
	    		}
	    	};
	    	
	    	outThread.start();
	    	errThread.start();
	    	
	    	while(outThread.isAlive() || errThread.isAlive());
	    }
	    catch (IOException e) {
	    	ErrorList.instance.addError(e.getMessage());
	    }
	}
	
	public abstract void processOutLine(String line);
	public abstract void processErrLine(String line);
}
