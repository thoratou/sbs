package screen.tools.sbs.cmake;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import screen.tools.sbs.objects.Dependency;
import screen.tools.sbs.objects.Description;
import screen.tools.sbs.objects.ErrorList;
import screen.tools.sbs.objects.Flag;
import screen.tools.sbs.objects.GlobalSettings;
import screen.tools.sbs.objects.Library;
import screen.tools.sbs.objects.Pack;
import screen.tools.sbs.objects.ProjectProperties;
import screen.tools.sbs.utils.FieldBuildType;
import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.utils.FieldString;

/**
 * @author Ratouit Thomas
 *
 */
public class CMakePackGenerator {
	private static ErrorList err = GlobalSettings.getGlobalSettings().getErrorList();

	Pack pack;
	CMakePack cmakePack;
	
	public CMakePackGenerator(Pack pack, CMakePack cmakePack) {
		this.pack = pack;
		this.cmakePack = cmakePack;
	}
	
	public void generate(){
		convertFromProperties(pack.getProperties());
		convertFromDependencies(pack.getDependencyList(), pack.getDescriptionMap());
		convertFromFlags(pack.getFlagList());
	}

	protected void convertFromProperties(ProjectProperties properties) {
		FieldString name = pack.getProperties().getName();
		if(name.isValid())
			cmakePack.setProjectName(name.getString().replaceAll("/", ""));
		else
			err.addError("invalid name into the pack");
		cmakePack.setProjectVersion(pack.getProperties().getVersion());

		FieldString buildType = pack.getProperties().getBuildType();
		FieldBuildType type = new FieldBuildType();
		if(buildType.isValid()){
			type.set(buildType.getString());
			if(type.isValid()){
				cmakePack.setBuildType(type);
			}
			else{
				err.addError("Internal error : invalid build type into the pack");
			}
		}
		else
			err.addError("invalid build type into the pack");
	}

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

	protected void convertFromIncludePathList(List<FieldPath> includePathList) {
		Iterator<FieldPath> iterator = includePathList.iterator();
		while (iterator.hasNext()) {
			FieldPath fieldPath = iterator.next();
			if(fieldPath.isValid())
				cmakePack.addIncludeDirectory(fieldPath.getString());
			else
				err.addError("invalid include path into the pack");
		}
	}

	protected void convertFromLibraryPathList(List<FieldPath> libraryPathList) {
		Iterator<FieldPath> iterator = libraryPathList.iterator();
		while (iterator.hasNext()) {
			FieldPath fieldPath = iterator.next();
			if(fieldPath.isValid())
				cmakePack.addLinkDirectory(fieldPath.getString());
			else
				err.addError("invalid link path into the pack");
		}
	}

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
					cmakePack.addLinkLibraries(description.getCompileName());
				}
				else
					err.addError("no description for library "+name+" into the pack");
			}
			else
				err.addError("invalid library into the pack");
		}
	}

	protected void convertFromFlags(List<Flag> flagList) {
		Iterator<Flag> iterator = flagList.iterator();
		while (iterator.hasNext()) {
			Flag flag = iterator.next();
			cmakePack.addCompileFlag(flag.getFlag(), flag.getValue());
		}
	}	
}
