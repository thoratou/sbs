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

package screen.tools.sbs.declaration;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Declaration{
	
	public enum Tag{
		ATTRIBUTE,
		TEXT,
		ELEMENT,
		ELEMENT_LIST
	}
	
	public class Key{
		private Tag tag;
		private String tagName;
		
		public Key(Tag tag, String tagName) {
			this.tag = tag;
			this.tagName = tagName;
		}

		public Tag getTag() {
			return tag;
		}

		public String getTagName() {
			return tagName;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Key){
				Key key = (Key)obj;
				if(tagName != null && key.tagName != null){
					return tag == key.tag && tagName.equals(key.tagName);					
				}
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return (tagName != null) ? tagName.hashCode() : 0;
		}
	};
	
	public interface DeclarationInterface{};
	
	public class ConcreteDeclaration implements DeclarationInterface{
		private String methodName;
		private String className;
		
		public ConcreteDeclaration(String methodName, String className) {
			this.methodName = methodName;
			this.className = className;
		}

		public String getMethodName() {
			return methodName;
		}

		public String getClassName() {
			return className;
		}
	};
	
	public class DelegatedDeclaration implements DeclarationInterface{
		private Declaration declaration;
		
		public DelegatedDeclaration(Declaration declaration) {
			this.declaration = declaration;
		}

		public Declaration getDeclaration() {
			return declaration;
		}
	};
	
	private List<Key> keyOrderedList;
	private Hashtable<Key, DeclarationInterface> declarationMap;
	
	public Declaration() {
		keyOrderedList = new ArrayList<Key>();
		declarationMap = new Hashtable<Key, DeclarationInterface>();
	}
	
	public Declaration add(Tag tag, String tagName, String methodName, String className){
		Key key = new Key(tag, tagName);
		keyOrderedList.add(key);
		declarationMap.put(key, new ConcreteDeclaration(methodName, className));
		return this;
	}
	
	public Declaration add(Tag tag, String tagName, Declaration declaration){
		Key key = new Key(tag, tagName);
		keyOrderedList.add(key);
		declarationMap.put(key, new DelegatedDeclaration(declaration));
		return this;
	}

	public List<Key> getKeyOrderedList() {
		return keyOrderedList;
	}

	public Hashtable<Key, DeclarationInterface> getDeclarationMap() {
		return declarationMap;
	}
}
