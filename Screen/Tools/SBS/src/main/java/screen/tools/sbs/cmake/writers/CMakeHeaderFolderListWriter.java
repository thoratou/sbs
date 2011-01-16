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

package screen.tools.sbs.cmake.writers;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import screen.tools.sbs.cmake.CMakeSegmentWriter;
import screen.tools.sbs.cmake.CMakePack;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.GlobalSettings;
import screen.tools.sbs.utils.FieldPath;

/**
 * write folder list for all header files
 * <pre>
 * INCLUDE_DIRECTORIES(
 *     src
 *     include
 *     C:/Users/Thanh/Documents/Developments/ScreenFramework/Screen/Utils/include/
 *     C:/Users/Thanh/Documents/Developments/SbsRepository/boost/1.42/include/
 *     C:/Users/Thanh/Documents/Developments/ScreenFramework/Screen/Math/include/
 *     C:/Users/Thanh/Documents/Developments/SbsRepository/DevIL/1.7.8/include/
 * )
 * </pre>
 * 
 * @author Ratouit Thomas
 *
 */
public class CMakeHeaderFolderListWriter implements CMakeSegmentWriter{
	/**
	 * @see screen.tools.sbs.cmake.CMakeSegmentWriter#write(screen.tools.sbs.cmake.CMakePack, java.io.Writer)
	 */
	public void write(CMakePack cmakePack, Writer cmakeListsWriter)
			throws IOException {
		ErrorList err = GlobalSettings.getGlobalSettings().getErrorList();

		List<FieldPath> includeDirectories = cmakePack.getIncludeDirectories();
		Iterator<FieldPath> iterator = includeDirectories.iterator();
		cmakeListsWriter.write("INCLUDE_DIRECTORIES(\n");
		while(iterator.hasNext()){
			FieldPath next = iterator.next();
			if(next.isValid()){
				cmakeListsWriter.write("    "+next.getCMakeString()+"\n");
			}
			else{
				err.addError("invalid include directory");
			}
		}		
		cmakeListsWriter.write(")\n");
	}
}
