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
	 */
	public void generate(){
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
	 */
	protected void convertFromProperties(ProjectProperties properties) {
		FieldString name = pack.getProperties().getName();
		if(name.isValid())
			cmakePack.setProjectName(name.getString().replaceAll("/", ""));
		else
			ErrorList.instance.addError("invalid name into the pack");
		cmakePack.setProjectVersion(pack.getProperties().getVersion());

		FieldString buildType = pack.getProperties().getBuildType();
		FieldBuildType type = new FieldBuildType();
		if(buildType.isValid()){
			type.set(buildType.getString());
			if(type.isValid()){
				cmakePack.setBuildType(type);
			}
			else{
				ErrorList.instance.addError("Internal error : invalid build type into the pack");
			}
		}
		else
			ErrorList.instance.addError("invalid build type into the pack");
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
	 */

	protected void convertFromDependencies(List<Dependency> dependencyList,
			Hashtable<String, Description> descriptionMap) {
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
	 */
	protected void convertFromIncludePathList(List<FieldPath> includePathList) {
		Iterator<FieldPath> iterator = includePathList.iterator();
		while (iterator.hasNext()) {
			FieldPath fieldPath = iterator.next();
			if(fieldPath.isValid())
				cmakePack.addIncludeDirectory(fieldPath.getCMakeString());
			else
				ErrorList.instance.addError("invalid include path into the pack");
		}
	}

	/**
	 * Convert library path list
	 * 
	 * @param libraryPathList
	 */
	protected void convertFromLibraryPathList(List<FieldPath> libraryPathList) {
		Iterator<FieldPath> iterator = libraryPathList.iterator();
		while (iterator.hasNext()) {
			FieldPath fieldPath = iterator.next();
			if(fieldPath.isValid())
				cmakePack.addLinkDirectory(fieldPath.getCMakeString());
			else
				ErrorList.instance.addError("invalid link path into the pack");
		}
	}

	/**
	 * Convert library list
	 * 
	 * @param libraryList
	 * @param descriptionMap
	 */
	protected void convertFromLibraryList(List<Library> libraryList,
			Hashtable<String, Description> descriptionMap) {
		Iterator<Library> iterator = libraryList.iterator();
		while (iterator.hasNext()) {
			Library library = iterator.next();
			FieldString fieldName = library.getName();
			if(fieldName.isValid()){
				String name = fieldName.getString();
				Description description = descriptionMap.get(name);
				if(description != null){
					cmakePack.addLinkLibraries(description.getCompileName().getString());
				}
				else
					ErrorList.instance.addError("no description for library "+name+" into the pack");
			}
			else
				ErrorList.instance.addError("invalid library into the pack");
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
