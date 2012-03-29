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
import screen.tools.sbs.component.ComponentFlag;
import screen.tools.sbs.component.ComponentPack;
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ComponentPackContext;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.fields.FieldJSONObject;

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
		ComponentPack pack = contextHandler.<ComponentPackContext>get(ContextKeys.COMPONENT_PACK).getPack();
		addFromField(pack,field);
	}
	
	public void setContext(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

	public static void addFromField(ComponentPack pack, String field) throws FieldException {
		FieldJSONObject fieldJSONObject = new FieldJSONObject();
		fieldJSONObject.set(field);
		JSONObject flagsObject = fieldJSONObject.getJSONObject();
		
		Set<?> keySet = flagsObject.keySet();			
		Iterator<?> iterator = keySet.iterator();			
		while(iterator.hasNext()){
			Object next = iterator.next();
			String key = (String) next;
			ComponentFlag flag = new ComponentFlag();
			flag.getKey().set(key);
			flag.getValue().setObject(flagsObject.get(key));
			pack.getFlagList().allocate().merge(flag);
		}
	}
	
	/**
	 * @param field
	 */
	public void setField(String field) {
		this.field = field;
	}
}
