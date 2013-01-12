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

package screen.tools.sbs.component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.fields.FieldBool;
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.fields.FieldFile;
import screen.tools.sbs.fields.FieldList;
import screen.tools.sbs.fields.FieldPath;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.fields.interfaces.FieldBuildModeInterface;
import screen.tools.sbs.fields.interfaces.FieldBuildTypeInterface;
import screen.tools.sbs.fields.interfaces.FieldCompileNameInterface;
import screen.tools.sbs.fields.interfaces.FieldExportInterface;
import screen.tools.sbs.fields.interfaces.FieldFileInterface;
import screen.tools.sbs.fields.interfaces.FieldFullNameInterface;
import screen.tools.sbs.fields.interfaces.FieldInterface;
import screen.tools.sbs.fields.interfaces.FieldKeyInterface;
import screen.tools.sbs.fields.interfaces.FieldLibraryNameInterface;
import screen.tools.sbs.fields.interfaces.FieldNameInterface;
import screen.tools.sbs.fields.interfaces.FieldPathInterface;
import screen.tools.sbs.fields.interfaces.FieldToolChainInterface;
import screen.tools.sbs.fields.interfaces.FieldVersionInterface;
import screen.tools.sbs.pack.PackManifest;

public class ComponentDomWriter {

	//private final ContextHandler contextHandler;

	public ComponentDomWriter(ContextHandler contextHandler) {
		//this.contextHandler = contextHandler;
	}

	public void write(ComponentPack pack, ComponentPack testPack, String path, String file) throws FieldException {
		Element root = new Element("pack");
		Document document = new Document(root);
		
		PackManifest properties = pack.getProperties();
		writeProperties(properties,root);
		
		//main
		{
			FieldList<ComponentDependency> dependencyList = pack.getDependencyList();
			FieldList<ComponentDependency> runtimeList = pack.getRuntimeList();
			FieldList<ComponentDescription> descriptionList = pack.getDescriptionList();
			FieldList<ComponentFlag> flagList = pack.getFlagList();
			FieldList<ComponentImport> importList = pack.getImportList();
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
			FieldList<ComponentDependency> dependencyList = testPack.getDependencyList();
			FieldList<ComponentDependency> runtimeList = testPack.getRuntimeList();
			FieldList<ComponentDescription> descriptionList = testPack.getDescriptionList();
			FieldList<ComponentFlag> flagList = testPack.getFlagList();
			FieldList<ComponentImport> importList = testPack.getImportList();
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
	
	////////////////
	// Properties //
	////////////////

	private void writeProperties(PackManifest properties, Element root) throws FieldException {
		FieldString name = properties.getName();
		FieldString version = properties.getVersion();
		FieldString buildType = properties.getBuildType();
		
		Element propertiesElt = new Element("properties");

		Element nameElt = new Element("name");
		nameElt.setText(name.get());
		propertiesElt.addContent(nameElt);

		Element versionElt = new Element("version");
		versionElt.setText(version.get());
		propertiesElt.addContent(versionElt);

		Element buildTypeElt = new Element("type");
		buildTypeElt.setText(buildType.get());
		propertiesElt.addContent(buildTypeElt);
			
		root.addContent(propertiesElt);
	}
	
	//////////////////
	// Dependencies //
	//////////////////

	private void writeDependencies(FieldList<ComponentDependency> dependencyList, Element root, String elementName) throws FieldException {
		if(!dependencyList.isEmpty()){
			Element dependencyElt = new Element(elementName);

			Iterator<ComponentDependency> iterator = dependencyList.iterator();
			while(iterator.hasNext()){
				ComponentDependency next = iterator.next();
				writeDependency(next, dependencyElt);
			}
			
			root.addContent(dependencyElt);
		}
	}
	
	private void writeDependency(ComponentDependency dependency, Element dependencyRoot) throws FieldException {
		Element dependencyElt = new Element("dependency");

		//name, version, toolchain, build mode, export
		writeCommonAttributes(dependency, dependencyElt);
		
		FieldList<ComponentPath> includePathList = dependency.getIncludePathList();
		writeIncludePathList(includePathList,dependencyElt);
		
		FieldList<ComponentLibrary> libraryList = dependency.getLibraryList();
		FieldList<ComponentPath> libraryPathList = dependency.getLibraryPathList();
		
		if(!libraryList.isEmpty() || !libraryPathList.isEmpty()){
			Element libraryRoot = new Element("libraries");
			writeLibraryList(libraryList,libraryRoot);
			writeLibraryPathList(libraryPathList,libraryRoot);
			dependencyElt.addContent(libraryRoot);
		}
		
		dependencyRoot.addContent(dependencyElt);
	}
	
	///////////////////
	// Include paths //
	///////////////////

	private void writeIncludePathList(FieldList<ComponentPath> includePathList,
			Element dependencyElt) throws FieldException {
		if(!includePathList.isEmpty()){
			Element includePathListEtl = new Element("includes");
			
			Iterator<ComponentPath> iterator = includePathList.iterator();
			while(iterator.hasNext()){
				ComponentPath next = iterator.next();
				writeIncludePath(next,includePathListEtl);
			}
			
			dependencyElt.addContent(includePathListEtl);
		}
	}
	
	private void writeIncludePath(ComponentPath componentPath,
			Element includeRoot) throws FieldException {
		Element includeElt = new Element("path");
		//path, toolchain, build mode
		writeCommonAttributes(componentPath, includeElt);
		includeRoot.addContent(includeElt);
	}
	
	///////////////////
	// Library paths //
	///////////////////

	private void writeLibraryPathList(FieldList<ComponentPath> libraryPathList,
			Element libraryRoot) throws FieldException {
		Iterator<ComponentPath> iterator = libraryPathList.iterator();
		while(iterator.hasNext()){
			ComponentPath next = iterator.next();
			writeLibraryPath(next, libraryRoot);
		}
	}
	
	private void writeLibraryPath(ComponentPath componentPath,
			Element libraryRoot) throws FieldException {
		Element libraryElt = new Element("path");
		//path, toolchain, build mode
		writeCommonAttributes(componentPath, libraryElt);
		libraryRoot.addContent(libraryElt);
	}
	
	///////////////
	// Libraries //
	///////////////

	private void writeLibraryList(FieldList<ComponentLibrary> libraryList, Element libraryRoot) throws FieldException {
		Iterator<ComponentLibrary> iterator = libraryList.iterator();
		while(iterator.hasNext()){
			ComponentLibrary next = iterator.next();
			writeLibrary(next,libraryRoot);
		}
	}

	private void writeLibrary(ComponentLibrary library, Element libraryRoot) throws FieldException {
		Element libraryElt = new Element("library");
		//name, version, toolchain, build mode
		writeCommonAttributes(library, libraryElt);
		libraryRoot.addContent(libraryElt);
	}
	
	///////////
	// Flags //
	///////////
	
	private void writeFlags(FieldList<ComponentFlag> flagList, Element root) throws FieldException {
		if(!flagList.isEmpty()){
			Element flagRoot = new Element("flags");
			
			Iterator<ComponentFlag> iterator = flagList.iterator();
			while(iterator.hasNext()){
				ComponentFlag next = iterator.next();
				writeFlag(next,flagRoot);
			}
			root.addContent(flagRoot);
		}
	}

	private void writeFlag(ComponentFlag next, Element flagRoot) throws FieldException {
		Element flagElt = new Element("flag");
				
		//key, value, toolchain, build mode
		writeCommonAttributes(next, flagElt);
		
		flagRoot.addContent(flagElt);
	}
	
	//////////////////
	// Descriptions //
	//////////////////

	private void writeDescriptions(FieldList<ComponentDescription> descriptionList,
			Element root) throws FieldException {
		if(!descriptionList.isEmpty()){
			Element descriptionRoot = new Element("descriptions");
			
			Iterator<ComponentDescription> iterator = descriptionList.iterator();
			while(iterator.hasNext()){
				ComponentDescription next = iterator.next();
				writeDescription(next,descriptionRoot);
			}
			
			root.addContent(descriptionRoot);
		}
	}

	private void writeDescription(ComponentDescription description, Element descriptionRoot) throws FieldException {
		Element descriptionElt = new Element("library");
		
		//name, compile-name, full-name, path, toolchain, build mode, build type
		writeCommonAttributes(description, descriptionElt);
		
		descriptionRoot.addContent(descriptionElt);
	}
	
	/////////////
	// Imports //
	/////////////
	
	private void writeImports(FieldList<ComponentImport> importList, Element root) throws FieldException {
		if(!importList.isEmpty()){
			Element importRoot = new Element("imports");
			
			Iterator<ComponentImport> iterator = importList.iterator();
			while(iterator.hasNext()){
				ComponentImport next = iterator.next();
				writeImport(next,importRoot);
			}
			
			root.addContent(importRoot);
		}
	}

	private void writeImport(ComponentImport import_, Element importRoot) throws FieldException {
		Element importElt = new Element("import");
		//file, toolchain, build mode
		writeCommonAttributes(import_, importElt);
		importRoot.addContent(importElt);
	}
	
	///////////////////////
	// Common attributes //
	///////////////////////
	
	private <T> void writeCommonAttributes(FieldInterface interface_, Element element) throws FieldException {	
		if(interface_ instanceof FieldBuildModeInterface){
			writeString(((FieldBuildModeInterface) interface_).getBuildMode(), element, "mode");
		}

		if(interface_ instanceof FieldBuildTypeInterface){
			writeString(((FieldBuildTypeInterface) interface_).getBuildType(), element, "type");
		}
		
		if(interface_ instanceof FieldCompileNameInterface){
			writeString(((FieldCompileNameInterface) interface_).getCompileName(), element, "compile-name");
		}
		
		if(interface_ instanceof FieldExportInterface){
			writeBool(((FieldExportInterface) interface_).getExport(), element, "export");
		}

		if(interface_ instanceof FieldFileInterface){
			writeFile(((FieldFileInterface) interface_).getFile(), element, "file");
		}
		
		if(interface_ instanceof FieldFullNameInterface){
			writeString(((FieldFullNameInterface) interface_).getFullName(), element, "full-name");
		}
		
		if(interface_ instanceof FieldKeyInterface){
			writeString(((FieldKeyInterface) interface_).getKey(), element, "key");
		}
		
		if(interface_ instanceof FieldLibraryNameInterface){
			writeString(((FieldLibraryNameInterface) interface_).getLibraryName(), element, "library-name");
		}
		
		if(interface_ instanceof FieldNameInterface){
			writeString(((FieldNameInterface) interface_).getName(), element, "name");
		}
		
		if(interface_ instanceof FieldPathInterface){
			writePath(((FieldPathInterface)interface_).getPath(), element, "path");
		}
		
		if(interface_ instanceof FieldToolChainInterface){
			writeString(((FieldToolChainInterface) interface_).getToolChain(), element, "toolchain");
		}
		

//		TODO
//		if(interface_ instanceof ComponentValueInterface){
//			writeValue(((ComponentValueInterface) interface_).getValue(), element, "value");
//		}

		if(interface_ instanceof FieldVersionInterface){
			writeString(((FieldVersionInterface) interface_).getVersion(), element, "version");
		}
	}
	
	private void writeString(FieldString fieldString, Element element, String key) throws FieldException {
		if(fieldString.isMandatory()){
			element.setAttribute(key, fieldString.get());
		}
		else if(fieldString.isOptional()){
			if(!fieldString.isEmpty()){
				element.setAttribute(key, fieldString.get());
			}
		}
	}

	private void writeBool(FieldBool fieldBool, Element element, String key) throws FieldException {
		if(fieldBool.isMandatory()){
			element.setAttribute(key, fieldBool.get());
		}
		else if(fieldBool.isOptional()){
			if(!fieldBool.isEmpty()){
				element.setAttribute(key, fieldBool.get());
			}
		}
	}
	
	private void writePath(FieldPath fieldPath, Element element, String key) throws FieldException {
		if(fieldPath.isMandatory()){
			element.setAttribute(key, fieldPath.get());
		}
		else if(fieldPath.isOptional()){
			if(!fieldPath.isEmpty()){
				element.setAttribute(key, fieldPath.get());
			}
		}
	}

	private void writeFile(FieldFile fieldFile, Element element, String key) throws FieldException {
		if(fieldFile.isMandatory()){
			element.setAttribute(key, fieldFile.get());
		}
		else if(fieldFile.isOptional()){
			if(!fieldFile.isEmpty()){
				element.setAttribute(key, fieldFile.get());
			}
		}
	}
	
	/////////
	// DOM //
	/////////
	
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
