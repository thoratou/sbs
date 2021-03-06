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
import screen.tools.sbs.fields.FieldString;

/**
 * write libraries to link
 * <pre>
 * TARGET_LINK_LIBRARIES(
 *     ${PROJECT_NAME}
 *     ScreenUtils
 *     boost_thread-mgw34-mt-1_42
 *     ScreenMath
 *     DevIL
 * )
 * </pre>
 * 
 * @author Ratouit Thomas
 *
 */
public class CMakeLibraryListWriter implements CMakeSegmentWriter{
	/**
	 * @throws FieldException 
	 * @see screen.tools.sbs.cmake.CMakeSegmentWriter#write(screen.tools.sbs.cmake.CMakePack, java.io.Writer)
	 */
	public void write(CMakePack cmakePack, Writer cmakeListsWriter)
			throws IOException, FieldException {
		FieldList<FieldString> linkLibraries = cmakePack.getLinkLibraries();
		Iterator<FieldString> iterator = linkLibraries.iterator();
		cmakeListsWriter.write("TARGET_LINK_LIBRARIES(\n");
		cmakeListsWriter.write("    ${PROJECT_NAME}\n");
		while(iterator.hasNext()){
			FieldString next = iterator.next();
			cmakeListsWriter.write("    "+next.get()+"\n");
		}		
		cmakeListsWriter.write(")\n");
	}
}
