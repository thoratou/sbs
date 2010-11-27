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
 * write filter for header files
 * <pre>
 * FILE(
 *     GLOB_RECURSE
 *     INC_FILES
 *     include/*.hpp
 *     include/*.h
 *     include/*.inl
 *     include/*.tpp
 *     include/*.i
 * )
 * </pre>
 * 
 * @author Ratouit Thomas
 *
 */
public class CMakeHeaderFilterWriter implements CMakeSegmentWriter{
	/**
	 * @see screen.tools.sbs.cmake.CMakeSegmentWriter#write(screen.tools.sbs.cmake.SBSCMakePack, java.io.Writer)
	 */
	public void write(SBSCMakePack cmakePack, Writer cmakeListsWriter)
			throws IOException {
		cmakeListsWriter.write("FILE(\n");
		cmakeListsWriter.write("    GLOB_RECURSE\n");
		cmakeListsWriter.write("    INC_FILES\n");
		cmakeListsWriter.write("    include/*.hpp\n");
		cmakeListsWriter.write("    include/*.h\n");
		cmakeListsWriter.write("    include/*.inl\n");
		cmakeListsWriter.write("    include/*.tpp\n");
		cmakeListsWriter.write("    include/*.i\n");
		cmakeListsWriter.write(")\n");
	}
}
