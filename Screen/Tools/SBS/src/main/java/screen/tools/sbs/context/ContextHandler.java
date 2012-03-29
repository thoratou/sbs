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

package screen.tools.sbs.context;

import java.util.Hashtable;

import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.EnvironmentVariablesContext;
import screen.tools.sbs.fields.FieldString;

public class ContextHandler {
	private Hashtable<ContextKey, Context> contextTable;
	
	public ContextHandler() {
		contextTable = new Hashtable<ContextKey, Context>();
		registerMandatoryContexts();
	}
	
	private void registerMandatoryContexts() {
		//create context for environment variable and fill it with some values
		EnvironmentVariablesContext environmentVariablesContext = new EnvironmentVariablesContext();

		String home = System.getProperty("SBS_HOME");
		environmentVariablesContext.getEnvironmentVariables().put("SBS_HOME", home);		

		String root = System.getProperty("SBS_ROOT");
		environmentVariablesContext.getEnvironmentVariables().put("SBS_ROOT", root);		

		// set variable context to field string process
		FieldString.setCurrentEnvironmentVariables(environmentVariablesContext.getEnvironmentVariables());

		addContext(ContextKeys.ENV_VARIABLES,environmentVariablesContext);
	}

	public void addContext(ContextKey key, Context context){
		contextTable.put(key, context);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Context> T get(ContextKey key) throws ContextException{
		T concreteContext = (T) contextTable.get(key);
		if(concreteContext == null){
			throw new ContextException("unable to retrieve concrete context : key = " + key.getKey());
		}
		return concreteContext;
	}
}
