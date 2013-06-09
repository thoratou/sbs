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

package com.thoratou.exact.bom;

import com.thoratou.exact.exception.ExactReadException;
import com.thoratou.exact.Entry;
import com.thoratou.exact.fields.FieldBase;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

public abstract class XmlReader<T extends Entry<T>>{
    public abstract void read(T bom, Element rootElement) throws ExactReadException;

    HashMap<String, XmlReader> registeredStreamer;

    protected XmlReader() {
        registeredStreamer = new HashMap<String, XmlReader>();
    }

    protected void registerStreamer(String className, XmlReader instance){
        registeredStreamer.put(className, instance);
    }

    public XmlReader getStreamer(String className){
        return registeredStreamer.get(className);
    }

    public void read(T bom, File file) throws JDOMException, IOException, ExactReadException {
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(file);
        Element rootElement = document.getRootElement();
        read(bom, rootElement);
    }

    public void read(T bom, Reader reader) throws JDOMException, IOException, ExactReadException {
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(reader);
        Element rootElement = document.getRootElement();
        read(bom, rootElement);
    }

    protected void checkBasicField(FieldBase field, String value) throws ExactReadException {
        if(!field.isEmpty()){
            throw new ExactReadException("Field already set, cannot set with value \""+value+"\"");
        }
    }
}
