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

public class ParentBomTestCase extends TestCase{

    @Test
    public void testBasic() throws FieldException, JDOMException, IOException, ExactReadException {
        ParentBom parentBom = new ParentBom();
        ChildBom childBom = parentBom.getChildBom();

        ParentBomXmlReader bomReader = new ParentBomXmlReader();
        Reader inputXml = new StringReader("<root value=\"parent\"><child value=\"child\" /></root>");
        bomReader.read(parentBom, inputXml);

        assertEquals("parent", parentBom.getValue().get());
        assertEquals("child", childBom.getValue().get());
    }

    @Test
    public void testBomList() throws FieldException, JDOMException, IOException, ExactReadException {
        ParentBom parentBom = new ParentBom();

        ParentBomXmlReader bomReader = new ParentBomXmlReader();
        Reader inputXml = new StringReader(
                "<root value=\"parent\">" +
                    "<child value=\"child\" />" +
                    "<children>" +
                        "<child value=\"first\" />" +
                        "<child value=\"second\" />" +
                        "<child value=\"third\" />" +
                    "</children>" +
                "</root>");
        bomReader.read(parentBom, inputXml);

        assertEquals("parent", parentBom.getValue().get());
        assertEquals("child", parentBom.getChildBom().getValue().get());
        assertEquals("first", parentBom.getOtherChildren().get(0).getValue().get());
        assertEquals("second", parentBom.getOtherChildren().get(1).getValue().get());
        assertEquals("third", parentBom.getOtherChildren().get(2).getValue().get());
    }
}
