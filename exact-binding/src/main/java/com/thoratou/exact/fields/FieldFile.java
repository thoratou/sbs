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

package com.thoratou.exact.fields;

import com.thoratou.exact.Entry;

public class FieldFile extends FieldBase<String> implements Entry<FieldFile> {
    FieldString fieldString;

    public FieldFile() {
        super(Type.MANDATORY);
        fieldString = new FieldString();
    }

    public FieldFile(FieldFile fieldFile) {
        super(fieldFile.getType());
        fieldString = fieldFile.fieldString.copy();
    }

    public FieldFile(Type type, String defaultValue) {
        super(type);
        fieldString = new FieldString(type, defaultValue);
    }

    @Override
    public boolean isEmpty(){
        return fieldString.isEmpty();
    }

    @Override
    public void set(String path) {
        fieldString.set(path);
    }

    @Override
    public String getOriginal() {
        return fieldString.getOriginal();
    }

    @Override
    public String getDefault() {
        return fieldString.getDefault();
    }

    @Override
    public String get() throws FieldException {
        String ret = fieldString.get();
        if(ret == null)
            return null;
        ret = ret.replaceAll("\\\\ ", " ");
        ret = ret.replaceAll("\\\\", "/");
        if(!ret.endsWith("/"))
            ret += "/";
        return ret;
    }

    public String getCMakeString() throws FieldException{
        return get().replaceAll(" ", "\\\\ ");
    }

    @Override
    public boolean equals(Object obj) {
        return fieldString.equals(obj);
    }

    @Override
    public int hashCode() {
        return fieldString.hashCode();
    }

    @Override
    public void merge(FieldFile fieldPath) {
        fieldString.merge(fieldPath.fieldString);
    }

    @Override
    public FieldFile copy() {
        return new FieldFile(this);
    }
}
