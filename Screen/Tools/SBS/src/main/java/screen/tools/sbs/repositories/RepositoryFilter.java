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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import screen.tools.sbs.utils.FieldException;
import screen.tools.sbs.utils.FieldString;

public class RepositoryFilter {
	private FieldString id;
	private FieldString componentName;
	private FieldString componentVersion;
	private FieldString compiler;
	private RepositoryData data;
	
	public RepositoryFilter() {
		id = new FieldString();
		data = new RepositoryData();
		componentName = new FieldString();
		componentVersion = new FieldString();
		compiler = new FieldString();
	}

	public void setId(FieldString id) {
		this.id = id;
	}

	public FieldString getId() {
		return id;
	}

	public void setComponentName(FieldString componentName) {
		this.componentName = componentName;
	}

	public FieldString getComponentName() {
		return componentName;
	}

	public void setComponentVersion(FieldString componentVersion) {
		this.componentVersion = componentVersion;
	}

	public FieldString getComponentVersion() {
		return componentVersion;
	}

	public void setCompiler(FieldString compiler) {
		this.compiler = compiler;
	}

	public FieldString getCompiler() {
		return compiler;
	}

	public boolean match(RepositoryFilter inputFilter) throws FieldException {
		Pattern namePattern = Pattern.compile(componentName.getString());
		Pattern versionPattern = Pattern.compile(componentVersion.getString());
		Pattern compilerPattern = Pattern.compile(compiler.getString());
		
		Matcher nameMatcher =
			namePattern.matcher(inputFilter.getComponentName().getString());
		Matcher versionMatcher =
			versionPattern.matcher(inputFilter.getComponentVersion().getString());
		Matcher compilerMatcher =
			compilerPattern.matcher(inputFilter.getCompiler().getString());
		
		boolean nameMatch = nameMatcher.matches();
		boolean versionMatch = versionMatcher.matches();
		boolean compilerMatch = compilerMatcher.matches();
		
		return nameMatch && versionMatch && compilerMatch;
	}

	public void setData(RepositoryData data) {
		this.data = data;
	}

	public RepositoryData getData() {
		return data;
	}
}
