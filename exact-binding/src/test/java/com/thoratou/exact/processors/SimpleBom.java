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

import com.thoratou.exact.annotations.ExactNode;
import com.thoratou.exact.annotations.ExactPath;
import com.thoratou.exact.Entry;
import com.thoratou.exact.fields.FieldFactory;
import com.thoratou.exact.fields.FieldString;

@ExactNode
public class SimpleBom implements Entry<SimpleBom> {

    private FieldString dummy;
    private FieldString value;

    public SimpleBom() {
        dummy = FieldFactory.createMandatoryFieldString();
        value = FieldFactory.createOptionalFieldString("novalue");
    }

    public SimpleBom(SimpleBom bom) {
        dummy = bom.dummy.copy();
        value = bom.value.copy();
    }

    @ExactPath("dummy/text()")
    public FieldString getDummy(){
        return dummy;
    }

    @ExactPath("dummy/@value")
    public FieldString getValue(){
        return value;
    }

    @Override
    public void merge(SimpleBom entry) {
        dummy.merge(entry.dummy);
        value.merge(entry.value);
    }

    @Override
    public SimpleBom copy() {
        return new SimpleBom(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SimpleBom simpleBom = (SimpleBom) o;

        if (dummy != null ? !dummy.equals(simpleBom.dummy) : simpleBom.dummy != null)
            return false;
        if (value != null ? !value.equals(simpleBom.value) : simpleBom.value != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dummy != null ? dummy.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
