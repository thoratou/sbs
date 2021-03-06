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

public class FieldString extends FieldBase<String> implements Entry<FieldString> {
    private String originalString;
    private String defaultValue;

    public FieldString() {
        super(Type.MANDATORY);
        originalString = null;
        defaultValue = null;
    }

    public FieldString(FieldString fieldString) {
        super(fieldString.getType());

        if(originalString != null){
            originalString = new String(fieldString.originalString);
        }
        else{
            originalString = null;
        }

        if(defaultValue != null){
            defaultValue = new String(fieldString.defaultValue);
        }
        else{
            defaultValue = null;
        }
    }

    public FieldString(Type type, String defaultValue) {
        super(type);
        originalString = null;
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean isEmpty(){
        return originalString == null;
    }

    @Override
    public void set(String originalString) {
        if(originalString == null)
            this.originalString = null;
        else
            this.originalString = new String(originalString);
    }

    @Override
    public String getDefault() {
        return defaultValue;
    }

    @Override
    public String getOriginal() {
        return originalString;
    }

    @Override
    public String get() throws FieldException {
        String ret = null;
        if(!isEmpty()){
            ret = originalString;
        }

        if(ret == null){
            if(defaultValue != null){
                ret = defaultValue;
            }
            else{
                throw new FieldException(originalString);
            }
        }

        return ret;
    }

    @Override
    public boolean equals(Object arg0) {
        FieldString fieldString = (FieldString) arg0;
        if(arg0==null)
            return false;
        try {
            return get().equals(fieldString.get());
        } catch (FieldException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        try {
            return get().hashCode();
        } catch (FieldException e) {
            return 0;
        }
    }

    @Override
    public void merge(FieldString fieldString) {
        if(fieldString.originalString != null)
            originalString = new String(fieldString.originalString);
        else
            originalString = null;

        //do not merge type and default value
    }

    @Override
    public FieldString copy() {
        return new FieldString(this);
    }
}
