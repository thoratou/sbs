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

package screen.tools.sbs.cmake;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import screen.tools.sbs.utils.FieldBuildType;


/**
 * @author Ratouit Thomas
 *
 */
public class CMakePackWriter {
	private CMakePack cmakePack;
	private Writer cmakeListsWriter;
	private List<CMakeSegmentWriter> writers;
	
	/**
	 * Convert and SBS build type into a CMake build type string
	 * 
	 * @param type Input SBS build type
	 * @return Output CMake build type
	 */
	public static String getCmakeBuildType(FieldBuildType.Type type){
		switch(type){
		case EXECUTABLE:
			return "EXECUTABLE";
		case STATIC_LIBRARY:
			return "STATIC";
		case SHARED_LIBRARY:
			return "SHARED";
		}
		return null;
	}
	
	/**
	 * Writer constructor
	 * 
	 * @param pack Input CMake pack
	 * @param writer Writer for output CMakelists.txt
	 */
	public CMakePackWriter(CMakePack pack, Writer writer) {
		cmakePack = pack;
		cmakeListsWriter = writer;
		writers = new ArrayList<CMakeSegmentWriter>();
	}
	
	/**
	 * Registers a CMake segment writer
	 * 
	 * @param writer
	 */
	public void addSegmentWriter(CMakeSegmentWriter writer){
		writers.add(writer);
	}
	
	/**
	 * Performs the writing
	 */
	public void write(){
		try {
			for(int i=0; i<writers.size(); i++){
				writers.get(i).write(cmakePack,cmakeListsWriter);
				cmakeListsWriter.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
