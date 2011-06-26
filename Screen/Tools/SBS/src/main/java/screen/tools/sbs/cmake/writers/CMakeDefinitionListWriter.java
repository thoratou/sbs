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

package screen.tools.sbs.cmake.writers;

import java.io.IOException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import screen.tools.sbs.cmake.CMakeSegmentWriter;
import screen.tools.sbs.cmake.CMakePack;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.utils.FieldObject;
import screen.tools.sbs.utils.FieldString;

/**
 * fill all ADD_DEFINITIONS instructions
 * <pre>
 * ADD_DEFINITIONS("-DSCREEN_ASSERT")
 * ADD_DEFINITIONS("-DSCREEN_AUTHORIZE_LOG_INFO")
 * ADD_DEFINITIONS("-DSCREEN_MAIN_BUILD_SHARED_LIBRARY")
 * </pre>
 * 
 * @author Ratouit Thomas
 *
 */
public class CMakeDefinitionListWriter implements CMakeSegmentWriter{
	/**
	 * @see screen.tools.sbs.cmake.CMakeSegmentWriter#write(screen.tools.sbs.cmake.CMakePack, java.io.Writer)
	 */
	public void write(CMakePack cmakePack, Writer cmakeListsWriter)
			throws IOException {
		Hashtable<FieldString, FieldObject> compileFlags = cmakePack.getCompileFlags();
		Set<Entry<FieldString, FieldObject>> entrySet = compileFlags.entrySet();
		Iterator<Entry<FieldString, FieldObject>> iterator = entrySet.iterator();
		while(iterator.hasNext()){
			Entry<FieldString, FieldObject> next = iterator.next();
			FieldString flag = next.getKey();
			FieldObject value = next.getValue();
			if(flag.isValid()){
				cmakeListsWriter.write("ADD_DEFINITIONS(-D"+flag.getString());
				if(value.isValid()){
					Object object = value.getObject();
					if(object instanceof String)
						//http://www.cmake.org/pipermail/cmake/2007-June/014611.html
						//for string value, we need 2 \\, so here 4 for those 2 and 1 for quote :$
						cmakeListsWriter.write("=\\\\\""+(String)object+"\\\\\"");
					else if(object instanceof Number)
						cmakeListsWriter.write("="+(Number)object);
				}
				cmakeListsWriter.write(")\n");
			}
			else{
				ErrorList.instance.addError("invalid definition");
			}
		}		
	}
}
