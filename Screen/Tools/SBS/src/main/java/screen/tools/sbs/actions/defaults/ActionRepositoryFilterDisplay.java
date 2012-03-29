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

package screen.tools.sbs.actions.defaults;

import java.util.Iterator;
import java.util.List;

import screen.tools.sbs.actions.Action;
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.RepositoryContext;
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.repositories.RepositoryFilter;
import screen.tools.sbs.repositories.RepositoryFilterTable;

/**
 * Action to display repository list into standard output
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionRepositoryFilterDisplay implements Action {
	private ContextHandler contextHandler;

	/**
	 * Print repository filter list
	 * @throws ContextException 
	 * @throws FieldException 
	 */
	public void perform() throws ContextException, FieldException {
		//retrieve repository filter list
		RepositoryFilterTable repositoryFilterTable = contextHandler.<RepositoryContext>get(ContextKeys.REPOSITORIES).getRepositoryFilterTable();
		List<RepositoryFilter> list = repositoryFilterTable.getSortedList();
		
		//define output format
		String format ="";
		format += "%1$5s";	// id
		format += "|";		// separator 
		format += "%2$7s";	// repo-id
		format += "|";		// separator
		format += "%3$16s";	// component-name
		format += "|";		// separator
		format += "%4$16s";	// component-version
		format += "|";		// separator
		format += "%5$16s";	// compiler
		format += "\n";		// end of line
		
		//print list
		System.out.println("Repository filters :");
		System.out.println();
		
		//table header
		System.out.format(format,"id","repo-id","component","version","compiler");
		System.out.format(format,"-----","-------","----------------","----------------","----------------");
		
		//table content
		Iterator<RepositoryFilter> iterator = list.iterator();
		while(iterator.hasNext()){
			RepositoryFilter next = iterator.next();
			String string = next.getId().get();
			String string2 = next.getData().id().get();
			String string3 = next.getComponentName().get();
			String string4 = next.getComponentVersion().get();
			String string5 = next.getCompiler().get();
			System.out.format(format,string,string2,string3,string4,string5);	
		}
		
		System.out.println();
	}

	public void setContext(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

}
