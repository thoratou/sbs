package screen.tools.sbs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import screen.tools.sbs.objects.ErrorList;

public abstract class ProcessHandler {
	
	private ProcessLauncher p;
	
	public ProcessHandler(List<String> command) {
		p = new ProcessLauncher(command);
	}

	public ProcessHandler(String[] command) {
		p = new ProcessLauncher(command);
	}

	public ProcessLauncher getProcessLauncher(){
		return p;
	}
	
	public ProcessBuilder getProcessBuilder(){
		return p.getProcessBuilder();
	}
	
	public void exec(){
	    p.execute();
		
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
	
	public abstract void processOutLine(String line);
	public abstract void processErrLine(String line);
}
