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

package screen.tools.sbs.actions.defaults;

import java.util.Iterator;

import screen.tools.sbs.actions.Action;
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.RepositoryContext;
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.fields.FieldList;
import screen.tools.sbs.repositories.RepositoryData;

/**
 * Action to display repository list into standard output
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionRepositoryDisplay implements Action {
	private ContextHandler contextHandler;

	/**
	 * Print repository list.
	 * @throws ContextException 
	 * @throws FieldException 
	 */
	public void perform() throws ContextException, FieldException {
		//retrieve repository list
		FieldList<RepositoryData> repositoryList = contextHandler.<RepositoryContext>get(ContextKeys.REPOSITORIES).getRepositoryList();
		
		//define output format
		String format ="";
		format += "%1$5s";	// id
		format += "|";		// separator 
		format += "%2$4s";	// type
		format += "|";		// separator
		format += "%3$30s";	// path
		format += "\n";		// end of line
		
		//print list
		System.out.println("Repository list :");
		System.out.println();
		
		//table header
		System.out.format(format,"id","type","path");
		System.out.format(format,"-----","----","------------------------------");
		
		//table content
		Iterator<RepositoryData> iterator = repositoryList.iterator();
		while(iterator.hasNext()){
			RepositoryData next = iterator.next();
			String string = next.getRepositoryId().get();
			String string2 = next.getLocationType().toString();
			String string3 = next.getDeliveryType().get();
			//String string4 = next.getRemotePath().get();
			//String string5 = next.getLocalPath().get();			
			System.out.format(format,string,string2,string3);	
		}
		
		System.out.println();
	}

	public void setContext(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}
}
