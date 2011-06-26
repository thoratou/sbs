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

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import screen.tools.sbs.objects.Dependency;
import screen.tools.sbs.objects.Description;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.Flag;
import screen.tools.sbs.objects.Library;
import screen.tools.sbs.objects.Pack;
import screen.tools.sbs.objects.ProjectProperties;
import screen.tools.sbs.utils.FieldBuildType;
import screen.tools.sbs.utils.FieldException;
import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.utils.FieldString;

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
		convertFromDependencies(pack.getDependencyList(), pack.getDescriptionMap());
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
	protected void convertFromProperties(ProjectProperties properties) throws FieldException {
		FieldString name = pack.getProperties().getName();
		cmakePack.setProjectName(name.getString().replaceAll("/", ""));
		cmakePack.setProjectVersion(pack.getProperties().getVersion());

		FieldString buildType = pack.getProperties().getBuildType();
		FieldBuildType type = new FieldBuildType();
		type.set(buildType.getString());
		cmakePack.setBuildType(type);
	}
	
	/**
	 * Convert dependency list
	 * Content :
	 *  - include path list
	 *  - library path list
	 *  - library list
	 * 
	 * @param dependencyList 
	 * @param descriptionMap
	 * @throws FieldException 
	 */

	protected void convertFromDependencies(List<Dependency> dependencyList,
			Hashtable<String, Description> descriptionMap) throws FieldException {
		Iterator<Dependency> iterator = dependencyList.iterator();
		while (iterator.hasNext()) {
			Dependency dependency = iterator.next();
			convertFromIncludePathList(dependency.getIncludePathList());
			convertFromLibraryPathList(dependency.getLibraryPathList());
			convertFromLibraryList(dependency.getLibraryList(), descriptionMap);
		}
	}

	/**
	 * Convert include path list
	 * 
	 * @param includePathList
	 * @throws FieldException 
	 */
	protected void convertFromIncludePathList(List<FieldPath> includePathList) throws FieldException {
		Iterator<FieldPath> iterator = includePathList.iterator();
		while (iterator.hasNext()) {
			FieldPath fieldPath = iterator.next();
			cmakePack.addIncludeDirectory(fieldPath.getCMakeString());
		}
	}

	/**
	 * Convert library path list
	 * 
	 * @param libraryPathList
	 * @throws FieldException 
	 */
	protected void convertFromLibraryPathList(List<FieldPath> libraryPathList) throws FieldException {
		Iterator<FieldPath> iterator = libraryPathList.iterator();
		while (iterator.hasNext()) {
			FieldPath fieldPath = iterator.next();
			cmakePack.addLinkDirectory(fieldPath.getCMakeString());
		}
	}

	/**
	 * Convert library list
	 * 
	 * @param libraryList
	 * @param descriptionMap
	 * @throws FieldException 
	 */
	protected void convertFromLibraryList(List<Library> libraryList,
			Hashtable<String, Description> descriptionMap) throws FieldException {
		Iterator<Library> iterator = libraryList.iterator();
		while (iterator.hasNext()) {
			Library library = iterator.next();
			FieldString fieldName = library.getName();
			String name = fieldName.getString();
			Description description = descriptionMap.get(name);
			if(description != null){
				cmakePack.addLinkLibraries(description.getCompileName().getString());
			}
			else
				ErrorList.instance.addError("no description for library "+name+" into the pack");
		}
	}

	/**
	 * Convert flag list
	 * 
	 * @param flagList
	 */
	protected void convertFromFlags(List<Flag> flagList) {
		Iterator<Flag> iterator = flagList.iterator();
		while (iterator.hasNext()) {
			Flag flag = iterator.next();
			cmakePack.addCompileFlag(flag.getFlag(), flag.getValue());
		}
	}	
}
