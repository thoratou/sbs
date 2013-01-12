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

package screen.tools.sbs.actions;

import java.util.ArrayList;
import java.util.List;

import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.objects.ErrorList;

/**
 * Class to handler, register and launch actions.
 * 
 * @author Ratouit Thomas
 *
 */
public class ActionManager {
	private List<Action> actions;
	private ContextHandler contextHandler;
	
	
	/**
	 * Default ActionManager constructor.
	 */
	public ActionManager() {
		actions = new ArrayList<Action>();
	}
	
	/**
	 * Registers an action to perform.
	 * 
	 * @param action
	 */
	public void pushAction(Action action){
		actions.add(action);
	}
	
	/**
	 * Performs all registered actions.
	 */
	public void processActions(){
		if(contextHandler==null){
			ErrorList.instance.addError("Internal error : missing context handler to process actions");
			return;
		}
		
		try {
			for(int i=0; i<actions.size(); i++){
				Action action = actions.get(i);
				action.setContext(contextHandler);
				action.perform();
				if(ErrorList.instance.hasErrors())
					break;
			}
		} catch (ContextException e) {
			ErrorList.instance.addError("Internal error : " + e.getMessage());
			StackTraceElement[] stackTrace = e.getStackTrace();
			ErrorList.instance.addError("Stack = ");
			for(int i=0; i<stackTrace.length; i++){
				ErrorList.instance.addError(stackTrace[i].getClassName()+"."+
											stackTrace[i].getMethodName()+" / "+
											stackTrace[i].getFileName()+":"+
											stackTrace[i].getLineNumber());
			}
		} catch (FieldException e) {
			ErrorList.instance.addError("Unable to complete actions successfully : "+
										e.getMessage());
			StackTraceElement[] stackTrace = e.getStackTrace();
			ErrorList.instance.addError("Stack = ");
			for(int i=0; i<stackTrace.length; i++){
				ErrorList.instance.addError(stackTrace[i].getClassName()+"."+
											stackTrace[i].getMethodName()+" / "+
											stackTrace[i].getFileName()+":"+
											stackTrace[i].getLineNumber());
			}
		}
	}

	public void setContext(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

	public ContextHandler getContext() {
		return contextHandler;
	}
}
