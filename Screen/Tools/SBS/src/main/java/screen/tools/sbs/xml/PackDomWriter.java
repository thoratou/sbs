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

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.objects.ProjectProperties;
import screen.tools.sbs.objects.TinyPack;
import screen.tools.sbs.utils.FieldString;

public class PackDomWriter {

	private final ContextHandler contextHandler;

	public PackDomWriter(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

	public void write(TinyPack pack, String path, String file) {
		Element root = new Element("pack");
		Document document = new Document(root);
		
		ProjectProperties properties = pack.getProperties();
		writeProperties(properties,root);
		
		writeDom(document,path,file);
	}

	private void writeProperties(ProjectProperties properties, Element root) {
		FieldString name = properties.getName();
		FieldString version = properties.getVersion();
		FieldString buildType = properties.getBuildType();
		
		boolean nameValid = name.isValid();
		boolean versionValid = version.isValid();
		boolean buildTypeValid = buildType.isValid();
		
		if(nameValid || versionValid || buildTypeValid){
			Element propertiesElt = new Element("properties");
			
			if(nameValid){
				Element nameElt = new Element("name");
				nameElt.setText(name.getString());
				propertiesElt.addContent(nameElt);
			}
			
			if(versionValid){
				Element versionElt = new Element("version");
				versionElt.setText(version.getString());
				propertiesElt.addContent(versionElt);
			}
			
			if(buildTypeValid){
				Element buildTypeElt = new Element("buildtype");
				buildTypeElt.setText(buildType.getString());
				propertiesElt.addContent(buildTypeElt);
			}
			
			root.addContent(propertiesElt);
		}
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
