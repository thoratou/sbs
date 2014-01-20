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
import com.thoratou.exact.bom.Extension;
import com.thoratou.exact.bom.InnerExtension;
import com.thoratou.exact.fields.FieldFactory;
import com.thoratou.exact.fields.FieldString;

@ExactNode
public class OtherExtensionBom implements Extension<OtherExtensionBom, FieldString> {
    private FieldString text;
    private ChildBom childBom;

    public OtherExtensionBom() {
        text = FieldFactory.createMandatoryFieldString();
        childBom = new ChildBom();
    }

    public OtherExtensionBom(OtherExtensionBom bom) {
        text = bom.text.copy();
        childBom = bom.childBom.copy();
    }

    @Override
    public FieldString getExtensionFilter(){
        FieldString filter = FieldFactory.createMandatoryFieldString();
        filter.set("other");
        return filter;
    }

    @ExactPath("text()")
    public FieldString getText(){
        return text;
    }

    @ExactPath("bom")
    public ChildBom getBom(){
        return childBom;
    }

    @Override
    public void merge(OtherExtensionBom entry) {
        text.merge(entry.text);
        childBom.merge(entry.childBom);
    }

    @Override
    public void merge(InnerExtension<FieldString> entry) {
        if(entry instanceof OtherExtensionBom){
            merge((OtherExtensionBom) entry);
        }
    }

    @Override
    public OtherExtensionBom copy() {
        return new OtherExtensionBom(this);
    }

    @Override
    public InnerExtension<FieldString> innerCopy() {
        return copy();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        OtherExtensionBom that = (OtherExtensionBom) o;

        if (text != null ? !text.equals(that.text) : that.text != null)
            return false;
        if (childBom != null ? !childBom.equals(that.childBom) : that.childBom != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (childBom != null ? childBom.hashCode() : 0);
        return result;
    }
}
