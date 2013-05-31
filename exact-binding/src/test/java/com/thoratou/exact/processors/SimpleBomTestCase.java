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
import com.thoratou.exact.fields.FieldBase;
import com.thoratou.exact.fields.FieldException;
import junit.framework.TestCase;
import org.jdom.JDOMException;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class SimpleBomTestCase extends TestCase{
	
	@Test
    public void testBasic() throws FieldException, JDOMException, IOException, ExactReadException {
        SimpleBom bom = new SimpleBom();

        SimpleBomXmlReader bomReader = new SimpleBomXmlReader();
        Reader inputXml = new StringReader("<root><dummy>toto</dummy></root>");
        bomReader.read(bom, inputXml);

        assertEquals("toto", bom.getDummy().get());
    }

    @Test
    public void testAttribute() throws FieldException, JDOMException, IOException, ExactReadException {
        SimpleBom bom = new SimpleBom();

        SimpleBomXmlReader bomReader = new SimpleBomXmlReader();
        Reader inputXml = new StringReader("<root><dummy value=\"titi\">toto</dummy></root>");
        bomReader.read(bom, inputXml);

        assertEquals("toto", bom.getDummy().get());
        assertEquals("titi", bom.getValue().get());
    }

    @Test
    public void testDefaultAttributeValue() throws FieldException, JDOMException, IOException, ExactReadException {
        SimpleBom bom = new SimpleBom();

        SimpleBomXmlReader bomReader = new SimpleBomXmlReader();
        Reader inputXml = new StringReader("<root><dummy>toto</dummy></root>");
        bomReader.read(bom, inputXml);

        assertEquals("toto", bom.getDummy().get());
        assertEquals("novalue", bom.getValue().get());
    }
}
