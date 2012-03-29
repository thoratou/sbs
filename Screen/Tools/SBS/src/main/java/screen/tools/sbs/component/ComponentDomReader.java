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

package screen.tools.sbs.component;

import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

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
import screen.tools.sbs.utils.DomChildHandler;
import screen.tools.sbs.utils.Logger;

public class ComponentDomReader {
	private static String propertyNameQuery = "//properties/name/text()";
	private static String propertyVersionQuery = "//properties/version/text()";
	private static String propertyBuildTypeQuery = "//properties/type/text()";

	public void read(ComponentPack pack, ComponentPack testPack, Document doc) throws FieldException{
		try {
			Element root = doc.getRootElement();
			
			//Main manifest
			PackManifest packManifest = new PackManifest();
			readProperties(packManifest, root);
			pack.getProperties().merge(packManifest);
			
			//Test manifest
			PackManifest testManifest = packManifest.copy();
			String testName = testManifest.getName().getOriginal()+"/Test";
			testManifest.getName().set(testName);
			testManifest.getBuildType().set("executable");
			testPack.getProperties().merge(testManifest);
			
			final ComponentPack finalPack = pack;
			new DomChildHandler(root,"main") {
				
				@Override
				protected void processChild(Element child) {
					readComponentPack(finalPack, child);
				}
			}.process();

			final ComponentPack finalTestPack = testPack;
			new DomChildHandler(root,"test") {
				
				@Override
				protected void processChild(Element child) {
					readComponentPack(finalTestPack, child);
				}
			}.process();
			
		} catch (JDOMException e) {
			e.printStackTrace();
		}

	}

	////////////////
	// Properties //
	////////////////
	
	private void readProperties(PackManifest properties, Element root) throws FieldException, JDOMException {
		//name
		{
			XPath xpa = XPath.newInstance(propertyNameQuery);
			String propertyName = xpa.valueOf(root);
			Logger.debug("propertyName : "+propertyName);
			properties.getName().set(propertyName);
		}
		
		//version
		{
			XPath xpa = XPath.newInstance(propertyVersionQuery);
			String propertyVersion = xpa.valueOf(root);
			Logger.debug("propertyVersion : "+propertyVersion);
			properties.getVersion().set(propertyVersion);
		}
		
		//build type
		{
			XPath xpa = XPath.newInstance(propertyBuildTypeQuery);
			String propertyBuildType = xpa.valueOf(root);
			Logger.debug("propertyBuildType : "+propertyBuildType);
			properties.getBuildType().set(propertyBuildType);
		}
	}
	
	//////////////////
	// Main or test //
	//////////////////
	
	
	private void readComponentPack(ComponentPack pack, Element main) {
		//dependencies
		FieldList<ComponentDependency> dependencyList = pack.getDependencyList();
		readDependencies(dependencyList, main);
		
		//runtime
		FieldList<ComponentDependency> runtimeList = pack.getRuntimeList();
		readRuntimes(runtimeList, main);
		
		//descriptions
		FieldList<ComponentDescription> descriptionList = pack.getDescriptionList();
		readDescriptions(descriptionList, main);
		
		//flags
		FieldList<ComponentFlag> flagList = pack.getFlagList();
		readFlags(flagList, main);
		
		//imports
		FieldList<ComponentImport> importList = pack.getImportList();
		readImports(importList, main);
	}
	
	//////////////////
	// Dependencies //
	//////////////////
	
	private void readDependencies(FieldList<ComponentDependency> dependencyList, Element main) {
		final FieldList<ComponentDependency> finalList = dependencyList;
		
		new DomChildHandler(main, "dependencies") {
			
			@Override
			protected void processChild(Element child) {
				ComponentDependency dependency = finalList.allocate();
				readDependency(dependency, child);				
			}
		}.process();
	}

	private void readDependency(ComponentDependency dependency, Element dependencyElt) {
		readCommonAttributes(dependency, dependencyElt);
		
		final ComponentDependency finalDep = dependency;
		
		//include path
		new DomChildHandler(dependencyElt,"includes") {
			
			@Override
			public void processChild(Element child) {
				new DomChildHandler(child, "path") {
			
					@Override
					public void processChild(Element child) {
						ComponentPath path = finalDep.getIncludePathList().allocate();
						readCommonAttributes(path, child);
					}
				}.process();
			}
		}.process();
		
		//libraries
		new DomChildHandler(dependencyElt, "libraries") {
			
			@Override
			public void processChild(Element child) {
				new DomChildHandler(child, "path") {
					
					@Override
					public void processChild(Element child) {
						ComponentPath path = finalDep.getLibraryPathList().allocate();
						readCommonAttributes(path, child);
					}
				}.process();
				
				new DomChildHandler(child, "library") {
					
					@Override
					protected void processChild(Element child) {
						ComponentLibrary library = finalDep.getLibraryList().allocate();
						readCommonAttributes(library, child);
					}
				}.process();
			}
		}.process();
	}
	
	/////////////
	// Runtime //
	/////////////

	private void readRuntimes(FieldList<ComponentDependency> runtimeList, Element main) {
		final FieldList<ComponentDependency> finalList = runtimeList;
		
		new DomChildHandler(main, "runtime") {
			
			@Override
			protected void processChild(Element child) {
				ComponentDependency dependency = finalList.allocate();
				readDependency(dependency, child);				
			}
		}.process();		
	}
	
	//////////////////
	// Descriptions //
	//////////////////

	private void readDescriptions(FieldList<ComponentDescription> descriptionList, Element main) {
		final FieldList<ComponentDescription> finalList = descriptionList;
		
		new DomChildHandler(main, "descriptions") {
			
			@Override
			protected void processChild(Element child) {
				new DomChildHandler(child, "library") {
					
					@Override
					protected void processChild(Element child) {
						ComponentDescription description = finalList.allocate();
						readCommonAttributes(description, child);
					}
				}.process();
			}
		}.process();
	}
	
	///////////
	// Flags //
	///////////
	
	private void readFlags(FieldList<ComponentFlag> flagList, Element main) {
		final FieldList<ComponentFlag> finalList = flagList;
		
		new DomChildHandler(main, "flags") {
			
			@Override
			protected void processChild(Element child) {
				new DomChildHandler(child, "flag") {
					
					@Override
					protected void processChild(Element child) {
						ComponentFlag flag = finalList.allocate();
						readCommonAttributes(flag, child);
					}
				}.process();
			}
		}.process();
	}

	/////////////
	// Imports //
	/////////////

	private void readImports(FieldList<ComponentImport> importList, Element main) {
		final FieldList<ComponentImport> finalList = importList;
		
		new DomChildHandler(main, "imports") {
			
			@Override
			protected void processChild(Element child) {
				new DomChildHandler(child, "import") {
					
					@Override
					protected void processChild(Element child) {
						ComponentImport import_ = finalList.allocate();
						readCommonAttributes(import_, child);
					}
				};
			}
		};
	}

	///////////////////////
	// Common attributes //
	///////////////////////
	
	private void readCommonAttributes(FieldInterface interface_, Element element) {
		if(interface_ instanceof FieldBuildModeInterface){
			readString(((FieldBuildModeInterface) interface_).getBuildMode(), element, "mode");
		}

		if(interface_ instanceof FieldBuildTypeInterface){
			readString(((FieldBuildTypeInterface) interface_).getBuildType(), element, "type");
		}
		
		if(interface_ instanceof FieldCompileNameInterface){
			readString(((FieldCompileNameInterface) interface_).getCompileName(), element, "compile-name");
		}
		
		if(interface_ instanceof FieldExportInterface){
			readBool(((FieldExportInterface) interface_).getExport(), element, "export");
		}
		
		if(interface_ instanceof FieldFileInterface){
			readFile(((FieldFileInterface) interface_).getFile(), element, "file");
		}
		
		if(interface_ instanceof FieldFullNameInterface){
			readString(((FieldFullNameInterface) interface_).getFullName(), element, "full-name");
		}
		
		if(interface_ instanceof FieldKeyInterface){
			readString(((FieldKeyInterface) interface_).getKey(), element, "key");
		}
		
		if(interface_ instanceof FieldLibraryNameInterface){
			readString(((FieldLibraryNameInterface) interface_).getLibraryName(), element, "library-name");
		}
		
		if(interface_ instanceof FieldNameInterface){
			readString(((FieldNameInterface) interface_).getName(), element, "name");
		}
		
		if(interface_ instanceof FieldPathInterface){
			readPath(((FieldPathInterface)interface_).getPath(), element, "path");
		}
		
		if(interface_ instanceof FieldToolChainInterface){
			readString(((FieldToolChainInterface) interface_).getToolChain(), element, "toolchain");
		}
		
//		TODO
//		if(interface_ instanceof ComponentValueInterface){
//			readValue(((ComponentValueInterface) interface_).getValue(), element, "value");
//		}
		
		if(interface_ instanceof FieldVersionInterface){
			readString(((FieldVersionInterface) interface_).getVersion(), element, "version");
		}
	}

	private void readString(FieldString fieldString, Element element, String key) {
		fieldString.set(element.getAttributeValue(key));
	}
	
	private void readBool(FieldBool fieldBool, Element element, String key) {
		fieldBool.set(element.getAttributeValue(key));
	}

	private void readFile(FieldFile fieldFile, Element element, String key) {
		fieldFile.set(element.getAttributeValue(key));
	}

	private void readPath(FieldPath fieldPath, Element element, String key) {
		fieldPath.set(element.getAttributeValue(key));
	}

	/////////
	// DOM //
	/////////
	
    public static Document parserFile(File sbsFile){
        Document doc = null;
        try{
                SAXBuilder db = new SAXBuilder();
                doc = db.build(sbsFile);
        }catch(IOException e){
                e.printStackTrace();
        } catch (JDOMException e) {
                e.printStackTrace();
        }
        return doc;             
}
}
