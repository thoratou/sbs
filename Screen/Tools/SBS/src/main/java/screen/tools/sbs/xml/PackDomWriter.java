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
import screen.tools.sbs.objects.Library;
import screen.tools.sbs.objects.ProjectProperties;
import screen.tools.sbs.objects.TinyPack;
import screen.tools.sbs.utils.FieldBuildMode;
import screen.tools.sbs.utils.FieldException;
import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.utils.FieldString;

public class PackDomWriter {

	//private final ContextHandler contextHandler;

	public PackDomWriter(ContextHandler contextHandler) {
		//this.contextHandler = contextHandler;
	}

	public void write(TinyPack pack, String path, String file) throws FieldException {
		Element root = new Element("pack");
		Document document = new Document(root);
		
		ProjectProperties properties = pack.getProperties();
		writeProperties(properties,root);
		
		List<Dependency> dependencyList = pack.getDependencyList();
		writeDependencies(dependencyList,root);
		
		List<Description> descriptionList = pack.getDescriptionList();
		writeDescriptions(descriptionList,root);
		
		List<Flag> flagList = pack.getFlagList();
		writeFlags(flagList,root);
		
		//List<Import> importList = pack.getImportList();
		//writeImports(importList,root);
		
		writeDom(document,path,file);
	}

	private void writeProperties(ProjectProperties properties, Element root) throws FieldException {
		FieldString name = properties.getName();
		FieldString version = properties.getVersion();
		FieldString buildType = properties.getBuildType();
				
		Element propertiesElt = new Element("properties");

		Element nameElt = new Element("name");
		nameElt.setText(name.getString());
		propertiesElt.addContent(nameElt);
		
		Element versionElt = new Element("version");
		versionElt.setText(version.getString());
		propertiesElt.addContent(versionElt);
		
		Element buildTypeElt = new Element("buildtype");
		buildTypeElt.setText(buildType.getString());
		propertiesElt.addContent(buildTypeElt);
	
		root.addContent(propertiesElt);
	}

	private void writeDependencies(List<Dependency> dependencyList, Element root) throws FieldException {
		if(!dependencyList.isEmpty()){
			Element dependencyElt = new Element("dependencies");

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
		dependencyElt.setAttribute("name", dependency.getName().getString());
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
		pathElt.setText(fieldPath.getString());
		
		pathRoot.addContent(pathElt);
	}
	
	private void writeFlags(List<Flag> flagList, Element root) {
		// TODO Auto-generated method stub
		
	}

	private void writeDescriptions(List<Description> descriptionList,
			Element root) {
		// TODO Auto-generated method stub
		
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
