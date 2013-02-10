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

public class XPathQName {
	public String namespace;
	public String name;
	
	public XPathQName (String qname) {
		int sep = (qname == null ? -1 : qname.indexOf(":"));
		if (sep == -1) {
			init(null, qname);
		} else {
			init(qname.substring(0, sep), qname.substring(sep + 1));
		}
	}

	public XPathQName (String namespace, String name) {
		init(namespace, name);
	}

	private void init (String namespace, String name) {
		if (name == null ||
				(name != null && name.length() == 0) ||
				(namespace != null && namespace.length() == 0))
			throw new IllegalArgumentException("Invalid QName");

		this.namespace = namespace;
		this.name = name;
	}

	public String toString () {
		return (namespace == null ? name : namespace + ":" + name);
	}
	
	public boolean equals (Object o) {
		if (o instanceof XPathQName) {
			XPathQName x = (XPathQName)o;
			return toString().equals(x.toString());
		} else {
			return false;
		}
	}
}
