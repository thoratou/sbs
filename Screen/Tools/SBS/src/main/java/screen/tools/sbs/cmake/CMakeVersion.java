package screen.tools.sbs.cmake;

import java.util.regex.Pattern;

import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.utils.Logger;
import screen.tools.sbs.utils.ProcessHandler;

public class CMakeVersion {
	private static String[] cmakeVersion = null;
	
	private CMakeVersion() {}
	
	static String[] getVersion() throws ContextException{
		if(cmakeVersion == null){
			new ProcessHandler() {
				
				@Override
				public void processOutLine(String line) {
					Logger.debug(line);
			        Pattern p = Pattern.compile(" ");
			        String[] words = p.split(line);
			        if(words.length == 3){
			        	if(words[0].equals("cmake") && words[1].equals("version")){
					        Pattern p2 = Pattern.compile("\\.");
					        String[] numbers = p2.split(words[2]);
					        if(numbers.length>0){
						        cmakeVersion = numbers;
					        }
			        	}
			        }
				}
				
				@Override
				public void processErrLine(String line) {
					Logger.error(line);
		        	ErrorList.instance.addError(line);
				}
			}.exec(new String[]{"cmake","-version"}, null);
		}
		if(cmakeVersion == null){
			throw new ContextException("can't retrieve cmake version");
		}
		
		return cmakeVersion;
	}
	
	static boolean isUpperThan(String[] expectedVersion, String[] cmakeVersion) throws ContextException{
		int size = Math.min(expectedVersion.length, cmakeVersion.length);
		for(int i=0; i<size; i++){
			Integer expDigit = new Integer(expectedVersion[i]);
			Integer cmakeDigit = new Integer(cmakeVersion[i]);
			
			int result = expDigit - cmakeDigit;
			if(result > 0)
				return true;
			else if (result < 0)
				return false;
		}
		
		return (expectedVersion.length >= cmakeVersion.length);
	}
}
