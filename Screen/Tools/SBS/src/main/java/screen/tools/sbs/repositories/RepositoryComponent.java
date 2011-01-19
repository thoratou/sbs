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

package screen.tools.sbs.repositories;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.utils.FieldString;
import screen.tools.sbs.utils.Logger;

public class RepositoryComponent {
	private FieldString componentName;
	private FieldString componentVersion;
	private FieldString compiler;
	
	public RepositoryComponent(FieldString componentName, 
							FieldString componentVersion,
							FieldString compiler) {
		this.componentName = componentName;
		this.componentVersion = componentVersion;
		this.compiler = compiler;
	}
	
	public RepositoryFilter retrieve(RepositoryFilterTable table){
		RepositoryFilter inputFilter = new RepositoryFilter();
		inputFilter.setComponentName(componentName);
		inputFilter.setComponentVersion(componentVersion);
		inputFilter.setCompiler(compiler);
		
		List<RepositoryFilter> list = table.filter(inputFilter);
		Iterator<RepositoryFilter> iterator = list.iterator();
		while(iterator.hasNext()){
			RepositoryFilter next = iterator.next();
			String componentPath = getComponentPath(next).getString();
			Logger.debug(componentPath);
			Logger.debug(""+new File(componentPath+"/component.xml").exists());
			Logger.debug(""+new File(componentPath+"/lib/"+compiler.getString()).exists());
			
			if(new File(componentPath+"/component.xml").exists()
				//&& new File(componentPath+"/lib/"+compiler.getString()).exists()
				){
				return next;
			}
		}
		return null;
	}
	
	public FieldPath getComponentPath(RepositoryFilter filter){
		return new FieldPath(filter.getData().getPath().getString()
		+"/"
		+componentName.getString()
		+"/"
		+componentVersion.getString());
	}
}
