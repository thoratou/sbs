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
public class BomWithExtension implements Entry<BomWithExtension> {

    private FieldExtensionList<FieldString> childExtensionList;
    private FieldString value;

    public BomWithExtension() {
        childExtensionList = new FieldExtensionList<FieldString>();
        value = FieldFactory.createMandatoryFieldString();
    }

    public BomWithExtension(BomWithExtension bom) {
        childExtensionList = bom.childExtensionList.copy();
        value = bom.value.copy();
    }

    @ExactPath("@value")
    public FieldString getValue(){
        return value;
    }

    @ExactExtension(name = "child", element = "children/child", filter = "@filter")
    public FieldExtensionList<FieldString> getChildren(){
        return childExtensionList;
    }

    @Override
    public void merge(BomWithExtension entry) {
        childExtensionList.merge(entry.childExtensionList);
        value.merge(entry.value);
    }

    @Override
    public BomWithExtension copy() {
        return new BomWithExtension(this);
    }
}
