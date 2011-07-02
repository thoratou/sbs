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
import java.util.Set;

import org.json.simple.JSONObject;

import screen.tools.sbs.actions.Action;
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.TinyPackContext;
import screen.tools.sbs.objects.Flag;
import screen.tools.sbs.objects.TinyPack;
import screen.tools.sbs.utils.FieldException;
import screen.tools.sbs.utils.FieldJSONObject;

/**
 * Action to generate a basic component.
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionAddFlagsTinyPack implements Action {
	private String field;
	private ContextHandler contextHandler;
	
	/**
	 * 
	 */
	public ActionAddFlagsTinyPack() {
		field = null;
	}

	/**
	 * Performs create action.
	 * @throws ContextException 
	 * @throws FieldException 
	 */
	public void perform() throws ContextException, FieldException {
		TinyPack pack = contextHandler.<TinyPackContext>get(ContextKeys.TINY_PACK).getPack();
		addFromField(pack,field);
	}
	
	public void setContext(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

	public static void addFromField(TinyPack pack, String field) throws FieldException {
		FieldJSONObject fieldJSONObject = new FieldJSONObject(field);
		JSONObject flagsObject = fieldJSONObject.getJSONObject();
		
		Set<?> keySet = flagsObject.keySet();			
		Iterator<?> iterator = keySet.iterator();			
		while(iterator.hasNext()){
			Object next = iterator.next();
			String key = (String) next;
			Flag flag = new Flag();
			flag.setFlag(key);
			flag.setValue(flagsObject.get(key));
			pack.addFlag(flag);
		}
	}
	
	/**
	 * @param field
	 */
	public void setField(String field) {
		this.field = field;
	}
}
