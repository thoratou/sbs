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

package com.thoratou.exact.xpath;

import com.thoratou.exact.xpath.ast.XPathPathExpr;
import junit.framework.TestCase;
import org.junit.Test;

public class XPathW3cTestCase extends TestCase{
	
	@Test
	public void testW3c1() throws Exception{
		checkString("child::para",
					"{path-expr:rel,{{step:child,para}}}");
	}
	
	@Test
	public void testW3c2() throws Exception{
		checkString("child::*",
					"{path-expr:rel,{{step:child,*}}}");
	}
	
	@Test
	public void testW3c3() throws Exception{
		checkString("child::text()",
					"{path-expr:rel,{{step:child,text()}}}");
	}
	
	@Test
	public void testW3c4() throws Exception{
		checkString("child::node()",
					"{path-expr:rel,{{step:child,node()}}}");
	}
	
	@Test
	public void testW3c5() throws Exception{
		checkString("attribute::name",
					"{path-expr:rel,{{step:attribute,name}}}");
	}
	
	@Test
	public void testW3c6() throws Exception{
		checkString("attribute::*",
					"{path-expr:rel,{{step:attribute,*}}}");
	}
	
	@Test
	public void testW3c7() throws Exception{
		checkString("descendant::para",
					"{path-expr:rel,{{step:descendant,para}}}");
	}
	
	@Test
	public void testW3c8() throws Exception{
		checkString("ancestor::div",
					"{path-expr:rel,{{step:ancestor,div}}}");
	}
	
	@Test
	public void testW3c9() throws Exception{
		checkString("ancestor-or-self::div",
					"{path-expr:rel,{{step:ancestor-or-self,div}}}");
	}
	
	@Test
	public void testW3c10() throws Exception{
		checkString("descendant-or-self::para",
					"{path-expr:rel,{{step:descendant-or-self,para}}}");
	}
	
	@Test
	public void testW3c11() throws Exception{
		checkString("self::para",
					"{path-expr:rel,{{step:self,para}}}");
	}
	
	@Test
	public void testW3c12() throws Exception{
		checkString("child::chapter/descendant::para",
					"{path-expr:rel,{{step:child,chapter},{step:descendant,para}}}");
	}
	
	@Test
	public void testW3c13() throws Exception{
		checkString("child::*/child::para",
					"{path-expr:rel,{{step:child,*},{step:child,para}}}");
	}
	
	@Test
	public void testW3c14() throws Exception{
		checkString("/",
					"{path-expr:abs,{}}");
	}
	
	@Test
	public void testW3c15() throws Exception{
		checkString("/descendant::para",
					"{path-expr:abs,{{step:descendant,para}}}");
	}
	
	@Test
	public void testW3c16() throws Exception{
		checkString("/descendant::olist/child::item",
					"{path-expr:abs,{{step:descendant,olist},{step:child,item}}}");
	}
	
	@Test
	public void testW3c17() throws Exception{
		checkString("child::para[position()=1]",
					"{path-expr:rel,{{step:child,para,{predicate,[{binop-expr:==,{func-expr:position,()},{num:1.0}}]}}}}");
	}
	
	@Test
	public void testW3c18() throws Exception{
		checkString("child::para[position()=last()]",
					"{path-expr:rel,{{step:child,para,{predicate,[{binop-expr:==,{func-expr:position,()},{func-expr:last,()}}]}}}}");
	}
	
	@Test
	public void testW3c19() throws Exception{
		checkString("child::para[position()=last()-1]",
					"{path-expr:rel,{{step:child,para,{predicate,[{binop-expr:==,{func-expr:position,()},{binop-expr:-,{func-expr:last,()},{num:1.0}}}]}}}}");
	}
	
	@Test
	public void testW3c20() throws Exception{
		checkString("child::para[position()>1]",
					"{path-expr:rel,{{step:child,para,{predicate,[{binop-expr:>,{func-expr:position,()},{num:1.0}}]}}}}");
	}
	
	@Test
	public void testW3c21() throws Exception{
		checkString("following-sibling::chapter[position()=1]",
					"{path-expr:rel,{{step:following-sibling,chapter,{predicate,[{binop-expr:==,{func-expr:position,()},{num:1.0}}]}}}}");
	}
	
	@Test
	public void testW3c22() throws Exception{
		checkString("preceding-sibling::chapter[position()=1]",
					"{path-expr:rel,{{step:preceding-sibling,chapter,{predicate,[{binop-expr:==,{func-expr:position,()},{num:1.0}}]}}}}");
	}
	
	@Test
	public void testW3c23() throws Exception{
		checkString("/descendant::figure[position()=42]",
					"{path-expr:abs,{{step:descendant,figure,{predicate,[{binop-expr:==,{func-expr:position,()},{num:42.0}}]}}}}");
	}
	
	@Test
	public void testW3c24() throws Exception{
		checkString("/child::doc/child::chapter[position()=5]/child::section[position()=2]",
					"{path-expr:abs,{{step:child,doc},{step:child,chapter,{predicate,[{binop-expr:==,{func-expr:position,()},{num:5.0}}]}},{step:child,section,{predicate,[{binop-expr:==,{func-expr:position,()},{num:2.0}}]}}}}");
	}
	
	@Test
	public void testW3c25() throws Exception{
		checkString("child::para[attribute::type=\"warning\"]",
					"{path-expr:rel,{{step:child,para,{predicate,[{binop-expr:==,{path-expr:rel,{{step:attribute,type}}},{str:'warning'}}]}}}}");
	}
	
	@Test
	public void testW3c26() throws Exception{
		checkString("child::para[attribute::type='warning'][position()=5]",
					"{path-expr:rel,{{step:child,para,{predicate,[{binop-expr:==,{path-expr:rel,{{step:attribute,type}}},{str:'warning'}},{binop-expr:==,{func-expr:position,()},{num:5.0}}]}}}}");
	}
	
	@Test
	public void testW3c27() throws Exception{
		checkString("child::para[position()=5][attribute::type=\"warning\"]",
					"{path-expr:rel,{{step:child,para,{predicate,[{binop-expr:==,{func-expr:position,()},{num:5.0}},{binop-expr:==,{path-expr:rel,{{step:attribute,type}}},{str:'warning'}}]}}}}");
	}
	
	@Test
	public void testW3c28() throws Exception{
		checkString("child::chapter[child::title='Introduction']",
					"{path-expr:rel,{{step:child,chapter,{predicate,[{binop-expr:==,{path-expr:rel,{{step:child,title}}},{str:'Introduction'}}]}}}}");
	}
	
	@Test
	public void testW3c29() throws Exception{
		checkString("child::chapter[child::title]",
					"{path-expr:rel,{{step:child,chapter,{predicate,[{path-expr:rel,{{step:child,title}}}]}}}}");
	}
	
	@Test
	public void testW3c30() throws Exception{
		checkString("child::*[self::chapter or self::appendix]",
					"{path-expr:rel,{{step:child,*,{predicate,[{binop-expr:or,{path-expr:rel,{{step:self,chapter}}},{path-expr:rel,{{step:self,appendix}}}}]}}}}");
	}
	
	@Test
	public void testW3c31() throws Exception{
		checkString("child::*[self::chapter or self::appendix][position()=last()]",
					"{path-expr:rel,{{step:child,*,{predicate,[{binop-expr:or,{path-expr:rel,{{step:self,chapter}}},{path-expr:rel,{{step:self,appendix}}}},{binop-expr:==,{func-expr:position,()},{func-expr:last,()}}]}}}}");
	}	
	
	@Test
	public void testW3cAbridged1() throws Exception{
		checkString("para",
					"{path-expr:rel,{{step:child,para}}}");
	}
	
	@Test
	public void testW3cAbridged2() throws Exception{
		checkString("*",
					"{path-expr:rel,{{step:child,*}}}");
	}
	
	@Test
	public void testW3cAbridged3() throws Exception{
		checkString("text()",
					"{path-expr:rel,{{step:child,text()}}}");
	}
	
	@Test
	public void testW3cAbridged4() throws Exception{
		checkString("@name",
					"{path-expr:rel,{{step:attribute,name}}}");
	}
	
	@Test
	public void testW3cAbridged5() throws Exception{
		checkString("@*",
					"{path-expr:rel,{{step:attribute,*}}}");
	}
	
	@Test
	public void testW3cAbridged6() throws Exception{
		checkString("para[1]",
					"{path-expr:rel,{{step:child,para,{predicate,[{num:1.0}]}}}}");
	}
	
	@Test
	public void testW3cAbridged7() throws Exception{
		checkString("para[last()]",
					"{path-expr:rel,{{step:child,para,{predicate,[{func-expr:last,()}]}}}}");
	}
	
	@Test
	public void testW3cAbridged8() throws Exception{
		checkString("*/para",
					"{path-expr:rel,{{step:child,*},{step:child,para}}}");
	}
	
	@Test
	public void testW3cAbridged9() throws Exception{
		checkString("/doc/chapter[5]/section[2]",
					"{path-expr:abs,{{step:child,doc},{step:child,chapter,{predicate,[{num:5.0}]}},{step:child,section,{predicate,[{num:2.0}]}}}}");
	}
	
	@Test
	public void testW3cAbridged10() throws Exception{
		checkString("chapter//para",
					"{path-expr:rel,{{step:child,chapter},{step:descendant-or-self,node()},{step:child,para}}}");
	}
	
	@Test
	public void testW3cAbridged11() throws Exception{
		checkString("//para",
					"{path-expr:abs,{{step:descendant-or-self,node()},{step:child,para}}}");
	}
	
	@Test
	public void testW3cAbridged12() throws Exception{
		checkString("//olist/item",
					"{path-expr:abs,{{step:descendant-or-self,node()},{step:child,olist},{step:child,item}}}");
	}
	
	@Test
	public void testW3cAbridged13() throws Exception{
		checkString(".",
					"{path-expr:rel,{{step:self,node()}}}");
	}
	
	@Test
	public void testW3cAbridged14() throws Exception{
		checkString(".//para",
					"{path-expr:rel,{{step:self,node()},{step:descendant-or-self,node()},{step:child,para}}}");
	}
	
	@Test
	public void testW3cAbridged15() throws Exception{
		checkString("..",
					"{path-expr:rel,{{step:parent,node()}}}");
	}
	
	@Test
	public void testW3cAbridged16() throws Exception{
		checkString("../@lang",
					"{path-expr:rel,{{step:parent,node()},{step:attribute,lang}}}");
	}
	
	@Test
	public void testW3cAbridged17() throws Exception{
		checkString("para[@type=\"warning\"]",
					"{path-expr:rel,{{step:child,para,{predicate,[{binop-expr:==,{path-expr:rel,{{step:attribute,type}}},{str:'warning'}}]}}}}");
	}
	
	@Test
	public void testW3cAbridged18() throws Exception{
		checkString("para[@type=\"warning\"][5]",
					"{path-expr:rel,{{step:child,para,{predicate,[{binop-expr:==,{path-expr:rel,{{step:attribute,type}}},{str:'warning'}},{num:5.0}]}}}}");
	}
	
	@Test
	public void testW3cAbridged19() throws Exception{
		checkString("para[5][@type=\"warning\"]",
					"{path-expr:rel,{{step:child,para,{predicate,[{num:5.0},{binop-expr:==,{path-expr:rel,{{step:attribute,type}}},{str:'warning'}}]}}}}");
	}
	
	@Test
	public void testW3cAbridged20() throws Exception{
		checkString("chapter[title=\"Introduction\"]",
					"{path-expr:rel,{{step:child,chapter,{predicate,[{binop-expr:==,{path-expr:rel,{{step:child,title}}},{str:'Introduction'}}]}}}}");
	}
	
	@Test
	public void testW3cAbridged21() throws Exception{
		checkString("chapter[title]",
					"{path-expr:rel,{{step:child,chapter,{predicate,[{path-expr:rel,{{step:child,title}}}]}}}}");
	}
	
	@Test
	public void testW3cAbridged22() throws Exception{
		checkString("employee[@secretary and @assistant]",
					"{path-expr:rel,{{step:child,employee,{predicate,[{binop-expr:and,{path-expr:rel,{{step:attribute,secretary}}},{path-expr:rel,{{step:attribute,assistant}}}}]}}}}");
	}
	
	public void checkString(String xpathString, String xpathExpr) throws Exception{
		
		XPathParser parser = new XPathParser(xpathString);
        XPathPathExpr finalExpr = parser.parse();
		assertEquals(finalExpr.toString(), xpathExpr);
	}
}
