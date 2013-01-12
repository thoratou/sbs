/*****************************************************************************
 * This source file is part of SBS (Screen Build System),                    *
 * which is a component of Screen Framework                                  *
 *                                                                           *
 * Copyright (c) 2008-2013 Ratouit Thomas                                    *
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
			new ProcessHandler(new String[]{"cmake","-version"}) {
				
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
			}.exec();
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
