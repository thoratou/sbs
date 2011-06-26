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

package screen.tools.sbs.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import screen.tools.sbs.utils.FieldString;
import screen.tools.sbs.utils.Logger;

public class EnvironmentVariables {
	private Hashtable<String, FieldString> variableTable;
	
	public EnvironmentVariables() {
		variableTable = new Hashtable<String, FieldString>();
	}
	
	public void putFromFile(String filePath){
		File path = new File(filePath);
		Properties properties = new Properties();
		try{
			properties.load(new FileInputStream(path));
		}catch(IOException e){
			Logger.debug(e.getMessage());
			Logger.warning("file "+path.getAbsolutePath()+" not found => no environment variable");
		}
		
		Enumeration<Object> en = properties.keys();
		while(en.hasMoreElements()){
			String key = (String) en.nextElement();
			String value = properties.getProperty(key);
			variableTable.put(key, new FieldString(value));
		}
	}
	
	public void put(String variable, String value) {
		Logger.debug("add var");
		Logger.debug("   key : "+variable);
		Logger.debug("   value : "+value);
		variableTable.put(variable, new FieldString(value));
	}
	
	@Deprecated
	public boolean contains (String variable){
		if(!variableTable.containsKey(variable))
			return false;
		return getFieldString(variable).getOriginalString() != null;
	}
	
	@Deprecated
	public String getValue(String variable){
		return variableTable.get(variable).getString();
	}
	
	@Deprecated
	public String getValue(String variable, EnvironmentVariables addVars){
		return variableTable.get(variable).getString(addVars);
	}
	
	public FieldString getFieldString(String variable){
		FieldString fieldString = variableTable.get(variable);
		if(fieldString == null){
			fieldString = new FieldString();
		}
		return fieldString;
	}
}
