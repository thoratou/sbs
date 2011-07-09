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

package screen.tools.sbs.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.objects.Dependency;
import screen.tools.sbs.objects.Description;
import screen.tools.sbs.objects.Flag;
import screen.tools.sbs.objects.Import;
import screen.tools.sbs.objects.Library;
import screen.tools.sbs.objects.ProjectProperties;
import screen.tools.sbs.objects.TinyPack;
import screen.tools.sbs.utils.FieldBuildMode;
import screen.tools.sbs.utils.FieldBuildMode.Type;
import screen.tools.sbs.utils.FieldBuildType;
import screen.tools.sbs.utils.FieldException;
import screen.tools.sbs.utils.FieldFile;
import screen.tools.sbs.utils.FieldObject;
import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.utils.FieldPathType;
import screen.tools.sbs.utils.FieldString;

public class PackDomWriter {

	//private final ContextHandler contextHandler;

	public PackDomWriter(ContextHandler contextHandler) {
		//this.contextHandler = contextHandler;
	}

	public void write(TinyPack pack, TinyPack testPack, String path, String file) throws FieldException {
		Element root = new Element("pack");
		Document document = new Document(root);
		
		ProjectProperties properties = pack.getProperties();
		writeProperties(properties,root);
		
		//main
		{
			List<Dependency> dependencyList = pack.getDependencyList();
			List<Dependency> runtimeList = pack.getRuntimeList();
			List<Description> descriptionList = pack.getDescriptionList();
			List<Flag> flagList = pack.getFlagList();
			List<Import> importList = pack.getImportList();
			if(!dependencyList.isEmpty() ||
					!descriptionList.isEmpty() ||
					!flagList.isEmpty() ||
					!importList.isEmpty() ||
					!runtimeList.isEmpty()){
				Element main = new Element("main");
				
				writeDependencies(dependencyList,main,"dependencies");
				writeDependencies(runtimeList, main, "runtime");
				writeDescriptions(descriptionList,main);
				writeFlags(flagList,main);
				writeImports(importList,main);
				
				root.addContent(main);
			}
		}
		
		//test
		{
			List<Dependency> dependencyList = testPack.getDependencyList();
			List<Dependency> runtimeList = testPack.getRuntimeList();
			List<Description> descriptionList = testPack.getDescriptionList();
			List<Flag> flagList = testPack.getFlagList();
			List<Import> importList = testPack.getImportList();
			if(!dependencyList.isEmpty() ||
					!descriptionList.isEmpty() ||
					!flagList.isEmpty() ||
					!importList.isEmpty() ||
					!runtimeList.isEmpty()){
				Element test = new Element("test");
				
				writeDependencies(dependencyList,test,"dependencies");
				writeDependencies(runtimeList,test,"runtime");
				writeDescriptions(descriptionList,test);
				writeFlags(flagList,test);
				writeImports(importList,test);
				
				root.addContent(test);
			}
		}
				
		writeDom(document,path,file);
	}

	private void writeProperties(ProjectProperties properties, Element root) throws FieldException {
		FieldString name = properties.getName();
		FieldString version = properties.getVersion();
		FieldString buildType = properties.getBuildType();
		
		if(!name.isEmpty() || !version.isEmpty() || !buildType.isEmpty()){
			Element propertiesElt = new Element("properties");
	
			if(!name.isEmpty()){
				Element nameElt = new Element("name");
				nameElt.setText(name.getString());
				propertiesElt.addContent(nameElt);
			}
			if(!version.isEmpty()){
				Element versionElt = new Element("version");
				versionElt.setText(version.getString());
				propertiesElt.addContent(versionElt);
			}
			if(!buildType.isEmpty()){
				Element buildTypeElt = new Element("buildtype");
				buildTypeElt.setText(buildType.getString());
				propertiesElt.addContent(buildTypeElt);
			}
			
			root.addContent(propertiesElt);
		}
	}

	private void writeDependencies(List<Dependency> dependencyList, Element root, String elementName) throws FieldException {
		if(!dependencyList.isEmpty()){
			Element dependencyElt = new Element(elementName);

			Iterator<Dependency> iterator = dependencyList.iterator();
			while(iterator.hasNext()){
				Dependency next = iterator.next();
				writeDependency(next, dependencyElt);
			}
			
			root.addContent(dependencyElt);
		}
	}
	
	private void writeDependency(Dependency dependency, Element dependencyRoot) throws FieldException {
		Element dependencyElt = new Element("dependency");

		FieldString name = dependency.getName();
		FieldString version = dependency.getVersion();
		
		if(!name.isEmpty())
			dependencyElt.setAttribute("name", dependency.getName().getString());
		if(!version.isEmpty())
			dependencyElt.setAttribute("version", dependency.getVersion().getString());
		if(dependency.getExport().getBool())
			dependencyElt.setAttribute("export", "true");
		
		List<FieldPath> includePathList = dependency.getIncludePathList();
		writeIncludePathList(includePathList,dependencyElt);
		
		List<Library> libraryList = dependency.getLibraryList();
		List<FieldPath> libraryPathList = dependency.getLibraryPathList();
		
		if(!libraryList.isEmpty() || !libraryPathList.isEmpty()){
			Element libraryRoot = new Element("libraries");
			writeLibraryList(libraryList,libraryRoot);
			writeLibraryPathList(libraryPathList,libraryRoot);
			dependencyElt.addContent(libraryRoot);
		}
		
		dependencyRoot.addContent(dependencyElt);
	}

	private void writeIncludePathList(List<FieldPath> includePathList,
			Element dependencyElt) throws FieldException {
		if(!includePathList.isEmpty()){
			Element includePathListEtl = new Element("includes");
			
			Iterator<FieldPath> iterator = includePathList.iterator();
			while(iterator.hasNext()){
				FieldPath next = iterator.next();
				writePath(next,includePathListEtl);
			}
			
			dependencyElt.addContent(includePathListEtl);
		}
	}

	private void writeLibraryPathList(List<FieldPath> libraryPathList,
			Element libraryRoot) throws FieldException {
		Iterator<FieldPath> iterator = libraryPathList.iterator();
		while(iterator.hasNext()){
			FieldPath next = iterator.next();
			writePath(next, libraryRoot);
		}
	}

	private void writeLibraryList(List<Library> libraryList, Element libraryRoot) throws FieldException {
		Iterator<Library> iterator = libraryList.iterator();
		while(iterator.hasNext()){
			Library next = iterator.next();
			writeLibrary(next,libraryRoot);
		}
	}

	private void writeLibrary(Library library, Element libraryRoot) throws FieldException {
		Element libraryElt = new Element("lib");
		libraryElt.setText(library.getName().getString());
		libraryRoot.addContent(libraryElt);
	}

	private void writePath(FieldPath fieldPath, Element pathRoot) throws FieldException {
		Element pathElt = new Element("path");
		
		FieldBuildMode buildMode = fieldPath.getBuildMode();
		if(buildMode.get() != FieldBuildMode.Type.ALL)
			pathElt.setAttribute("build", buildMode.getAsString());
		if(fieldPath.getPathType() == FieldPathType.Type.ABSOLUTE)
			pathElt.setAttribute("type","absolute");
		pathElt.setText(fieldPath.getString());
		
		pathRoot.addContent(pathElt);
	}
	
	private void writeFlags(List<Flag> flagList, Element root) throws FieldException {
		if(!flagList.isEmpty()){
			Element flagRoot = new Element("flags");
			
			Iterator<Flag> iterator = flagList.iterator();
			while(iterator.hasNext()){
				Flag next = iterator.next();
				writeFlag(next,flagRoot);
			}
			root.addContent(flagRoot);
		}
	}

	private void writeFlag(Flag flag, Element flagRoot) throws FieldException {
		Element flagElt = new Element("flag");
		
		FieldString fieldFlag = flag.getFlag();
		FieldObject fieldValue = flag.getValue();
		FieldBuildMode fieldBuildMode = flag.getBuildMode();
		
		flagElt.setAttribute("flag",fieldFlag.getString());
		
		if(!fieldValue.isEmpty()){
			Object value = fieldValue.getObject();			
			if(value instanceof Number){
				Number number = (Number) value;
				flagElt.setAttribute("value",""+number);
			}
			else if(value instanceof String){
				String string = (String) value;
				flagElt.setAttribute("value",string);
				flagElt.setAttribute("type","string");
			}
		}
		
		Type type = fieldBuildMode.get();
		if(type != Type.ALL){
			flagElt.setAttribute("config",fieldBuildMode.getAsString());
		}
		
		flagRoot.addContent(flagElt);
	}

	private void writeDescriptions(List<Description> descriptionList,
			Element root) throws FieldException {
		if(!descriptionList.isEmpty()){
			Element descriptionRoot = new Element("descriptions");
			
			Iterator<Description> iterator = descriptionList.iterator();
			while(iterator.hasNext()){
				Description next = iterator.next();
				writeDescription(next,descriptionRoot);
			}
			
			root.addContent(descriptionRoot);
		}
	}

	private void writeDescription(Description description, Element descriptionRoot) throws FieldException {
		Element descriptionElt = new Element("library");
		
		FieldString nameField = description.getName();
		FieldString compileNameField = description.getCompileName();
		FieldString fullNameField = description.getFullName();
		FieldBuildType buildTypeField = description.getBuildType();
		FieldBuildMode buildModeField = description.getBuildMode();
		
		String name = nameField.getString();
		descriptionElt.setAttribute("name",name);
		
		if(!compileNameField.isEmpty()){
			String compileName = compileNameField.getString();
			descriptionElt.setAttribute("compile-name",compileName);
		}

		if(!fullNameField.isEmpty()){
			String fullName = fullNameField.getString();
			descriptionElt.setAttribute("full-name",fullName);
		}
		
		String type = buildTypeField.getString();
		descriptionElt.setAttribute("type",type);
		
		FieldBuildMode.Type mode = buildModeField.get();
		if(mode!=FieldBuildMode.Type.ALL){
			String modeString = buildModeField.getAsString();
			descriptionElt.setAttribute("build",modeString);
		}
		
		descriptionRoot.addContent(descriptionElt);
	}
	
	private void writeImports(List<Import> importList, Element root) throws FieldException {
		if(!importList.isEmpty()){
			Element importRoot = new Element("imports");
			
			Iterator<Import> iterator = importList.iterator();
			while(iterator.hasNext()){
				Import next = iterator.next();
				writeImport(next,importRoot);
			}
			
			root.addContent(importRoot);
		}
	}

	private void writeImport(Import import_, Element importRoot) throws FieldException {
		Element importElt = new Element("import");
		
		FieldFile file = import_.getFile();
		FieldBuildMode buildMode = import_.getBuildMode();
		
		if(!file.isEmpty()){
			String originalString = file.getOriginalString();
			importElt.setAttribute("file",originalString);
		}

		FieldBuildMode.Type mode = buildMode.get();
		if(mode!=FieldBuildMode.Type.ALL){
			String modeString = buildMode.getAsString();
			importElt.setAttribute("build",modeString);
		}
		
		importRoot.addContent(importElt);
	}


	private void writeDom(Document document, String path, String file) {
		Format prettyFormat = Format.getPrettyFormat();
		//change indent to 4 lines
		prettyFormat.setIndent("    ");
		XMLOutputter outputter = new XMLOutputter(prettyFormat);
		try {
			outputter.output(document, new FileOutputStream(path+"/"+file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
