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

import screen.tools.sbs.cmake.CMakeSegmentWriter;
import screen.tools.sbs.cmake.SBSCMakePack;

/**
 * write filter for source files
 * <pre>
 * FILE(
 *     GLOB_RECURSE
 *     SRC_FILES
 *     src/*.cpp
 *     src/*.c
 *     src/*.hpp
 *     src/*.h
 *     src/*.inl
 *     src/*.tpp
 *     src/*.i
 * )
 * </pre>
 * 
 * @author Ratouit Thomas
 *
 */
public class CMakeSourceFilterWriter implements CMakeSegmentWriter{
	/**
	 * @see screen.tools.sbs.cmake.CMakeSegmentWriter#write(screen.tools.sbs.cmake.SBSCMakePack, java.io.Writer)
	 */
	public void write(SBSCMakePack cmakePack, Writer cmakeListsWriter)
			throws IOException {
		cmakeListsWriter.write("FILE(\n");
		cmakeListsWriter.write("    GLOB_RECURSE\n");
		cmakeListsWriter.write("    SRC_FILES\n");
		cmakeListsWriter.write("    src/*.cpp\n");
		cmakeListsWriter.write("    src/*.c\n");
		cmakeListsWriter.write("    src/*.hpp\n");
		cmakeListsWriter.write("    src/*.h\n");
		cmakeListsWriter.write("    src/*.inl\n");
		cmakeListsWriter.write("    src/*.tpp\n");
		cmakeListsWriter.write("    src/*.i\n");
		cmakeListsWriter.write(")\n");	}
}
