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

import com.thoratou.exact.fields.FieldBase;

class StepImplementation {
    enum Action{
        NEXT_STEP,
        BOM,
        FIELD,
    }

    Action action;

    //NEXT_STEP case only
    PathStep nextPathStep;

    //BOM and FIELD cases only
    String type;

    public StepImplementation(PathStep nextPathStep){
        action = Action.NEXT_STEP;
        this.nextPathStep = nextPathStep;
    }

    public StepImplementation(String type) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(type);
        if(FieldBase.class.isAssignableFrom(clazz)){
            action = Action.FIELD;
        }
        else{
            action = Action.BOM;
        }
        this.type = type;
    }

    public Action getAction() {
        return action;
    }

    public PathStep getNextPathStep() {
        return nextPathStep;
    }

    public String getType() {
        return type;
    }
}
