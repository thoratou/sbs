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

import com.thoratou.exact.Entry;
import com.thoratou.exact.exception.ExactReadException;
import com.thoratou.exact.fields.FieldBase;
import com.thoratou.exact.fields.FieldExtensionList;
import org.jdom.Element;

import java.util.HashMap;

public abstract class XmlExtensionReader<T extends Entry<T>> {
    private final HashMap<T, InnerExtension<T>> extensionMap;
    private final HashMap<T,XmlReader> extensionReaderMap;
    private T filter;

    protected XmlExtensionReader(){
        this.filter = null;
        extensionMap = new HashMap<T, InnerExtension<T>>();
        extensionReaderMap = new HashMap<T, XmlReader>();
    }

    public void registerExtension(InnerExtension<T> prototype, XmlReader reader){
        T filter = prototype.getExtensionFilter();
        extensionMap.put(filter, prototype);
        extensionReaderMap.put(filter, reader);
    }

    public InnerExtension<T> allocateFromFilter(FieldExtensionList<T> extensionList, T filter){
        if(extensionMap.containsKey(filter))
        {
            InnerExtension<T> extension = extensionMap.get(filter);
            return extensionList.allocate(extension);
        }
        return null;
    }

    protected void setFilter(T filter){
        this.filter = filter;
    }

    protected abstract void readFilter(final org.jdom.Element rootElement)
        throws com.thoratou.exact.exception.ExactReadException;

    public void read(FieldExtensionList<T> extensionList, Element rootElement) throws ExactReadException {
        readFilter(rootElement);
        if(filter != null){
            InnerExtension<T> extension = allocateFromFilter(extensionList, filter);
            if(extension != null)
            {
                if(extensionReaderMap.containsKey(filter))
                {
                    XmlReader<InnerExtension<T>> reader = extensionReaderMap.get(filter);
                    reader.read(extension, rootElement);
                }
            }
            filter = null;
        }
    }
}
