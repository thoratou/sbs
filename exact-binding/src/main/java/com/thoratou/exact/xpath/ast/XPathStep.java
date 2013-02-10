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
package com.thoratou.exact.xpath.ast;


public class XPathStep {
	
	public enum Axis{
		CHILD,
		DESCENDANT,
		PARENT,
		ANCESTOR,
		FOLLOWING_SIBLING,
		PRECEDING_SIBLING,
		FOLLOWING,
		PRECEDING,
		ATTRIBUTE,
		NAMESPACE,
		SELF,
		DESCENDANT_OR_SELF,
		ANCESTOR_OR_SELF
		
	}
	
	public enum Test{
		NAME,
		NAME_WILDCARD,
		NAMESPACE_WILDCARD,
		TYPE_NODE,
		TYPE_TEXT,
		TYPE_COMMENT,
		TYPE_PROCESSING_INSTRUCTION,
	}

	public static XPathStep ABBR_SELF () {
		return new XPathStep(Axis.SELF, Test.TYPE_NODE);
	}

	public static XPathStep ABBR_PARENT () {
		return new XPathStep(Axis.PARENT, Test.TYPE_NODE);
	}

	public static XPathStep ABBR_DESCENDANTS () {
		return new XPathStep(Axis.DESCENDANT_OR_SELF, Test.TYPE_NODE);
	}

	public Axis axis;
	public Test test;
	public XPathExpression[] predicates;

	//test-dependent variables
	public XPathQName name; //TEST_NAME only
	public String namespace; //TEST_NAMESPACE_WILDCARD only
	public String literal; //TEST_TYPE_PROCESSING_INSTRUCTION only
	
	public XPathStep (Axis axis, Test test) {
		this.axis = axis;
		this.test = test;
		this.predicates = new XPathExpression[0];
	}

	public XPathStep (Axis axis, XPathQName name) {
		this(axis, Test.NAME);
		this.name = name;
	}

	public XPathStep (Axis axis, String namespace) {
		this(axis, Test.NAMESPACE_WILDCARD);
		this.namespace = namespace;
	}
	
	public static String axisStr (Axis axis) {
		switch (axis) {
			case CHILD: return "child";
			case DESCENDANT: return "descendant";
			case PARENT: return "parent";
			case ANCESTOR: return "ancestor";
			case FOLLOWING_SIBLING: return "following-sibling";
			case PRECEDING_SIBLING: return "preceding-sibling";
			case FOLLOWING: return "following";
			case PRECEDING: return "preceding";
			case ATTRIBUTE: return "attribute";
			case NAMESPACE: return "namespace";
			case SELF: return "self";
			case DESCENDANT_OR_SELF: return "descendant-or-self";
			case ANCESTOR_OR_SELF: return "ancestor-or-self";
			default: return null;
		}
	}
		
	public String testStr () {
		switch(test) {
		case NAME: return name.toString();
		case NAME_WILDCARD: return "*";
		case NAMESPACE_WILDCARD: return namespace + ":*";
		case TYPE_NODE: return "node()";
		case TYPE_TEXT: return "text()";
		case TYPE_COMMENT: return "comment()";
		case TYPE_PROCESSING_INSTRUCTION: return "proc-instr(" + (literal == null ? "" : "\'" + literal + "\'") + ")";
		default: return null;
		}
	}
	
	public String toString () {
		StringBuffer sb = new StringBuffer();
		
		sb.append("{step:");
		sb.append(axisStr(axis));
		sb.append(",");
		sb.append(testStr());
		
		if (predicates.length > 0) {
			sb.append(",{predicate,[");
			for (int i = 0; i < predicates.length; i++) {
				sb.append(predicates[i].toString());
				if (i < predicates.length - 1)
					sb.append(",");
			}
			sb.append("]}");
		}
		sb.append("}");
		
		return sb.toString();
	}
}
