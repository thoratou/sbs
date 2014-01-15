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

import com.thoratou.exact.exception.ExactReadException;
import com.thoratou.exact.fields.FieldException;
import junit.framework.TestCase;
import org.jdom.JDOMException;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class BomWithExtensionTestCase extends TestCase{

    @Test
    public void testBasic() throws FieldException, JDOMException, IOException, ExactReadException {
        BomWithExtension bom = new BomWithExtension();

        BomWithExtensionXmlReader bomWithExtensionXmlReader = new BomWithExtensionXmlReader();
        bomWithExtensionXmlReader.childXmlReader.registerExtension(new ExtensionBom(), new ExtensionBomXmlReader());

        Reader inputXml = new StringReader(
                "<root value=\"not in extension\">" +
                    "<children>" +
                        "<child filter=\"extension\" value=\"tata\"/>" +
                        "<child filter=\"extension\" value=\"toto\"/>" +
                        "<child filter=\"extension\" value=\"titi\"/>" +
                    "</children>" +
                "</root>"
        );


        bomWithExtensionXmlReader.read(bom, inputXml);

        assertEquals("not in extension", bom.getValue().get());
        assertEquals(3, bom.getChildren().size());
        assertEquals("extension", bom.getChildren().get(0).getExtensionFilter().get());
        assertEquals("extension", bom.getChildren().get(1).getExtensionFilter().get());
        assertEquals("extension", bom.getChildren().get(2).getExtensionFilter().get());
        assertEquals("tata", ((ExtensionBom) bom.getChildren().get(0)).getValue().get());
        assertEquals("toto", ((ExtensionBom) bom.getChildren().get(1)).getValue().get());
        assertEquals("titi", ((ExtensionBom) bom.getChildren().get(2)).getValue().get());
    }

    @Test
    public void test2Extensions() throws FieldException, JDOMException, IOException, ExactReadException {
        BomWithExtension bom = new BomWithExtension();

        BomWithExtensionXmlReader bomWithExtensionXmlReader = new BomWithExtensionXmlReader();
        bomWithExtensionXmlReader.childXmlReader.registerExtension(new ExtensionBom(), new ExtensionBomXmlReader());
        bomWithExtensionXmlReader.childXmlReader.registerExtension(new OtherExtensionBom(), new OtherExtensionBomXmlReader());

        Reader inputXml = new StringReader(
                "<root value=\"not in extension\">" +
                        "<children>" +
                        "<child filter=\"extension\" value=\"tata\"/>" +
                        "<child filter=\"other\">" +
                            "some text" +
                            "<bom value=\"some data\" />" +
                        "</child>" +
                        "<child filter=\"extension\" value=\"titi\"/>" +
                        "</children>" +
                        "</root>"
        );


        bomWithExtensionXmlReader.read(bom, inputXml);

        assertEquals("not in extension", bom.getValue().get());
        assertEquals(3, bom.getChildren().size());
        assertEquals("extension", bom.getChildren().get(0).getExtensionFilter().get());
        assertEquals("other", bom.getChildren().get(1).getExtensionFilter().get());
        assertEquals("extension", bom.getChildren().get(2).getExtensionFilter().get());
        assertEquals("tata", ((ExtensionBom) bom.getChildren().get(0)).getValue().get());

        OtherExtensionBom otherExtensionBom = (OtherExtensionBom) bom.getChildren().get(1);
        assertEquals("some text", otherExtensionBom.getText().get());
        assertEquals("some data", otherExtensionBom.getBom().getValue().get());

        assertEquals("titi", ((ExtensionBom) bom.getChildren().get(2)).getValue().get());
    }
}
