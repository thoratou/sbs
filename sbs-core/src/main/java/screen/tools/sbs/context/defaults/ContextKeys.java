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

package screen.tools.sbs.context.defaults;

import screen.tools.sbs.context.ContextKey;

public class ContextKeys {
	public final static ContextKey PACK;
	public final static ContextKey TEST_PACK;
	public final static ContextKey COMPONENT_PACK;
	public final static ContextKey COMPONENT_TEST_PACK;
	public final static ContextKey SBS_FILE_AND_PATH;
	public final static ContextKey REPOSITORIES;
	public final static ContextKey ENV_VARIABLES;
	public final static ContextKey PROFILE;
	public final static ContextKey RUNTIME_PATHS;
	public final static ContextKey TEST_RUNTIME_PATHS;
	
	static{
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("pack");
			PACK = tmp;
		}
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("test-pack");
			TEST_PACK = tmp;
		}
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("component-pack");
			COMPONENT_PACK = tmp;
		}
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("component-test-pack");
			COMPONENT_TEST_PACK = tmp;
		}
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("sbs-file-and-path");
			SBS_FILE_AND_PATH = tmp;	
		}
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("repositories");
			REPOSITORIES = tmp;	
		}
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("env-variables");
			ENV_VARIABLES = tmp;	
		}
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("profile");
			PROFILE = tmp;	
		}
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("runtime-paths");
			RUNTIME_PATHS = tmp;	
		}
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("test-runtime-paths");
			TEST_RUNTIME_PATHS = tmp;	
		}
	}
}
