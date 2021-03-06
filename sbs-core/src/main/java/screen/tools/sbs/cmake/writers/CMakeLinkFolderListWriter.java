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

package screen.tools.sbs.cmake.writers;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import screen.tools.sbs.cmake.CMakePack;
import screen.tools.sbs.cmake.CMakeSegmentWriter;
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.fields.FieldList;
import screen.tools.sbs.fields.FieldPath;

/**
 * write folder list for all libraries
 * <pre>
 * LINK_DIRECTORIES(
 *     C:/Users/Thanh/Documents/Developments/SbsRepository/boost/1.42/lib/MingW/Release/./
 *     C:/Users/Thanh/Documents/Developments/SbsRepository/Screen/Utils/0.1.0/lib/MingW/Release/./
 *     C:/Users/Thanh/Documents/Developments/SbsRepository/Screen/Math/0.1.0/lib/MingW/Release/./
 *     C:/Users/Thanh/Documents/Developments/SbsRepository/DevIL/1.7.8/lib/MingW/Release/./
 * )
 * </pre>
 * 
 * @author Ratouit Thomas
 *
 */
public class CMakeLinkFolderListWriter implements CMakeSegmentWriter{
	/**
	 * @throws FieldException 
	 * @see screen.tools.sbs.cmake.CMakeSegmentWriter#write(screen.tools.sbs.cmake.CMakePack, java.io.Writer)
	 */
	public void write(CMakePack cmakePack, Writer cmakeListsWriter)
			throws IOException, FieldException {
		FieldList<FieldPath> linkDirectories = cmakePack.getLinkDirectories();
		Iterator<FieldPath> iterator = linkDirectories.iterator();
		cmakeListsWriter.write("LINK_DIRECTORIES(\n");
		while(iterator.hasNext()){
			FieldPath next = iterator.next();
			cmakeListsWriter.write("    "+next.getCMakeString()+"\n");
		}		
		cmakeListsWriter.write(")\n");
	}
}
