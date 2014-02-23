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

public class BomWithBomFilterExtensionTestCase extends TestCase{

    @Test
    public void testBasic() throws FieldException, JDOMException, IOException, ExactReadException {
        BomWithBomFilterExtension bom = new BomWithBomFilterExtension();

        BomWithBomFilterExtensionXmlReader reader = new BomWithBomFilterExtensionXmlReader();
        reader.childXmlReader.registerExtension(new ExtensionBomWithBomFilter(), new ExtensionBomWithBomFilterXmlReader());

        Reader inputXml = new StringReader(
                "<root value=\"not in extension\">" +
                    "<children>" +
                        "<child>"+
                            "<filter>"+
                                "<dummy value=\"extension\">dummy</dummy>"+
                            "</filter>"+
                            "some text" +
                            "<bom value=\"some data\" />" +
                        "</child>"+
                    "</children>" +
                "</root>"
        );


        reader.read(bom, inputXml);

        assertEquals("not in extension", bom.getValue().get());
        assertEquals(1, bom.getChildren().size());
        assertEquals("extension", bom.getChildren().get(0).getExtensionFilter().getValue().get());
        assertEquals("dummy", bom.getChildren().get(0).getExtensionFilter().getDummy().get());

        ExtensionBomWithBomFilter extensionBom = (ExtensionBomWithBomFilter) bom.getChildren().get(0);
        assertEquals("some text", extensionBom.getText().get());
        assertEquals("some data", extensionBom.getBom().getValue().get());

    }
}
