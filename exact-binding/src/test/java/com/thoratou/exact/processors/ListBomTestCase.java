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

package com.thoratou.exact.processors;

import com.thoratou.exact.fields.FieldBase;
import com.thoratou.exact.fields.FieldException;
import junit.framework.TestCase;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class ListBomTestCase extends TestCase{
	
	@Test
         public void testBasic() throws FieldException, JDOMException, IOException {

        //clean up global env for contextless test
        FieldBase.getCurrentEnvironmentVariables().clear();

        ListBom bom = new ListBom();

        SAXBuilder builder = new SAXBuilder();
        Reader inputXml = new StringReader(
                "<root>" +
                    "<list>" +
                        "<item value=\"tata\"/>" +
                        "<item value=\"titi\"/>" +
                        "<item value=\"toto\"/>" +
                    "</list>" +
                "</root>"
        );
        Document document = builder.build(inputXml);
        Element rootElement = document.getRootElement();

        ListBomXmlReader bomReader = new ListBomXmlReader();
        bomReader.read(bom, rootElement);

        assertEquals("tata", bom.getList().get(0).get());
        assertEquals("titi", bom.getList().get(1).get());
        assertEquals("toto", bom.getList().get(2).get());
    }
}
