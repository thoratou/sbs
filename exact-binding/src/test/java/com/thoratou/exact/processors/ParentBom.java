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
import com.thoratou.exact.fields.Entry;
import com.thoratou.exact.fields.FieldFactory;
import com.thoratou.exact.fields.FieldList;
import com.thoratou.exact.fields.FieldString;

@ExactNode
public class ParentBom implements Entry<ParentBom> {

    private ChildBom childBom;
    private FieldList<ChildBom> childBomList;
    private FieldString value;

    public ParentBom() {
        childBom = new ChildBom();
        childBomList = new FieldList<ChildBom>(new ChildBom());
        value = FieldFactory.createMandatoryFieldString();
    }

    public ParentBom(ParentBom bom) {
        childBom = bom.childBom.copy();
        childBomList = bom.childBomList.copy();
        value = bom.value.copy();
    }

    @ExactPath("@value")
    public FieldString getValue(){
        return value;
    }

    @ExactPath("child")
    public ChildBom getChildBom(){
        return childBom;
    }

    @ExactPath("children/child")
    public FieldList<ChildBom> getOtherChildren(){
        return childBomList;
    }

    @Override
    public void merge(ParentBom entry) {
        childBom.merge(entry.childBom);
        childBomList.merge(entry.childBomList);
        value.merge(entry.value);
    }

    @Override
    public ParentBom copy() {
        return new ParentBom(this);
    }
}
