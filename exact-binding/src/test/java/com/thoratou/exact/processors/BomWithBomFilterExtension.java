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

import com.thoratou.exact.Entry;
import com.thoratou.exact.annotations.ExactExtension;
import com.thoratou.exact.annotations.ExactNode;
import com.thoratou.exact.annotations.ExactPath;
import com.thoratou.exact.fields.FieldExtensionList;
import com.thoratou.exact.fields.FieldFactory;
import com.thoratou.exact.fields.FieldString;

@ExactNode
public class BomWithBomFilterExtension implements Entry<BomWithBomFilterExtension> {

    private FieldExtensionList<SimpleBom> childExtensionList;
    private FieldString value;

    public BomWithBomFilterExtension() {
        childExtensionList = new FieldExtensionList<SimpleBom>();
        value = FieldFactory.createMandatoryFieldString();
    }

    public BomWithBomFilterExtension(BomWithBomFilterExtension bom) {
        childExtensionList = bom.childExtensionList.copy();
        value = bom.value.copy();
    }

    @ExactPath("@value")
    public FieldString getValue(){
        return value;
    }

    @ExactExtension(name = "child", element = "children/child", filter = "filter")
    public FieldExtensionList<SimpleBom> getChildren(){
        return childExtensionList;
    }

    @Override
    public void merge(BomWithBomFilterExtension entry) {
        childExtensionList.merge(entry.childExtensionList);
        value.merge(entry.value);
    }

    @Override
    public BomWithBomFilterExtension copy() {
        return new BomWithBomFilterExtension(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        BomWithBomFilterExtension that = (BomWithBomFilterExtension) o;

        if (value != null ? !value.equals(that.value) : that.value != null)
            return false;
        if (childExtensionList != null ? !childExtensionList.equals(that.childExtensionList) : that.childExtensionList != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = childExtensionList != null ? childExtensionList.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
