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
import com.thoratou.exact.fields.FieldList;
import com.thoratou.exact.fields.FieldString;

@ExactNode
public class ListBom implements Entry<ListBom>{

    private FieldList<FieldString> list;

    public ListBom() {
        list = new FieldList<FieldString>(FieldFactory.createMandatoryFieldString());
    }

    public ListBom(ListBom bom) {
        list = bom.list.copy();
    }

    @ExactPath("list/item/@value")
    public FieldList<FieldString> getList(){
        return list;
    }

    @Override
    public void merge(ListBom entry) {
        list.merge(entry.list);
    }

    @Override
    public ListBom copy() {
        return new ListBom(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ListBom listBom = (ListBom) o;

        if (list != null ? !list.equals(listBom.list) : listBom.list != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }
}
