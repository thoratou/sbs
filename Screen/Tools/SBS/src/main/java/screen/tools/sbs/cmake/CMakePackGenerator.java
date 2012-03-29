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

import java.util.Iterator;

import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.fields.FieldList;
import screen.tools.sbs.fields.FieldObject;
import screen.tools.sbs.fields.FieldPath;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.pack.Pack;
import screen.tools.sbs.pack.PackFlag;
import screen.tools.sbs.pack.PackLibrary;
import screen.tools.sbs.pack.PackManifest;

/**
 * Generate a CMake pack from and SBS pack
 * 
 * @author Ratouit Thomas
 *
 */
public class CMakePackGenerator {
	private Pack pack;
	private CMakePack cmakePack;
	
	/**
	 * Unique generator constructor
	 * 
	 * @param pack Input SBS pack
	 * @param cmakePack Output CMake pack
	 */
	public CMakePackGenerator(Pack pack, CMakePack cmakePack) {
		this.pack = pack;
		this.cmakePack = cmakePack;
	}
	
	/**
	 * performs the generation
	 * @throws FieldException 
	 */
	public void generate() throws FieldException{
		convertFromProperties(pack.getProperties());
		convertFromIncludePathList(pack.getIncludePathList());
		convertFromLibraryPathList(pack.getLibraryPathList());
		convertFromLibraries(pack.getLibraryList());
		convertFromFlags(pack.getFlagList());
	}

	/**
	 * Convert SBS property part of an SBS Pack
	 * Content :
	 *  - component name
	 *  - component version
	 *  - component build type
	 *  
	 *  @param properties Component properties
	 * @throws FieldException 
	 */
	protected void convertFromProperties(PackManifest properties) throws FieldException {
		FieldString name = pack.getProperties().getName();
		cmakePack.getProjectName().set(name.get().replaceAll("/", ""));
		cmakePack.getProjectVersion().set(pack.getProperties().getVersion().get());

		FieldString buildType = pack.getProperties().getBuildType();
		cmakePack.getBuildType().set(buildType.get());
	}

	/**
	 * Convert include path list
	 * 
	 * @param includePathList
	 * @throws FieldException 
	 */
	protected void convertFromIncludePathList(FieldList<FieldPath> includePathList) throws FieldException {
		Iterator<FieldPath> iterator = includePathList.iterator();
		while (iterator.hasNext()) {
			FieldPath fieldPath = iterator.next();
			cmakePack.getIncludeDirectories().allocate().set(fieldPath.getCMakeString());
		}
	}

	/**
	 * Convert library path list
	 * 
	 * @param libraryPathList
	 * @throws FieldException 
	 */
	protected void convertFromLibraryPathList(FieldList<FieldPath> libraryPathList) throws FieldException {
		Iterator<FieldPath> iterator = libraryPathList.iterator();
		while (iterator.hasNext()) {
			FieldPath fieldPath = iterator.next();
			cmakePack.getLinkDirectories().allocate().set(fieldPath.getCMakeString());
		}
	}
	
	/**
	 * Convert dependency list
	 * Content :
	 *  - library list
	 * 
	 * @param libraryList 
	 * @throws FieldException 
	 */

	protected void convertFromLibraries(FieldList<PackLibrary> libraryList) throws FieldException {
		Iterator<PackLibrary> iterator = libraryList.iterator();
		while (iterator.hasNext()) {
			PackLibrary library = iterator.next();
			cmakePack.getLinkLibraries().allocate().set(library.getCompileName().get());
		}
	}

	/**
	 * Convert flag list
	 * 
	 * @param flagList
	 */
	protected void convertFromFlags(FieldList<PackFlag> flagList) {
		Iterator<PackFlag> iterator = flagList.iterator();
		while (iterator.hasNext()) {
			PackFlag flag = iterator.next();
			FieldObject value = cmakePack.getCompileFlags().allocate(flag.getKey());
			value.merge(flag.getValue());
		}
	}	
}
