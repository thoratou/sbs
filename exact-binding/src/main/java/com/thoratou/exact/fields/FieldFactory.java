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

public class FieldFactory {
    public static FieldString createMandatoryFieldString(){
        return new FieldString(FieldBase.Type.MANDATORY, null);
    }

    public static FieldString createOptionalFieldString(){
        return new FieldString(FieldBase.Type.OPTIONAL, null);
    }

    public static FieldString createOptionalFieldString(String defaultValue){
        return new FieldString(FieldBase.Type.OPTIONAL, defaultValue);
    }

    public static FieldPath createMandatoryFieldPath(){
        return new FieldPath(FieldBase.Type.MANDATORY, null);
    }

    public static FieldPath createOptionalFieldPath(){
        return new FieldPath(FieldBase.Type.OPTIONAL, null);
    }

    public static FieldPath createOptionalFieldPath(String defaultValue){
        return new FieldPath(FieldBase.Type.OPTIONAL, defaultValue);
    }

    public static FieldFile createMandatoryFieldFile(){
        return new FieldFile(FieldBase.Type.MANDATORY, null);
    }

    public static FieldFile createOptionalFieldFile(){
        return new FieldFile(FieldBase.Type.OPTIONAL, null);
    }

    public static FieldFile createOptionalFieldFile(String defaultValue){
        return new FieldFile(FieldBase.Type.OPTIONAL, defaultValue);
    }

    public static FieldBool createMandatoryFieldBool(){
        return new FieldBool(FieldBase.Type.MANDATORY, null);
    }

    public static FieldBool createOptionalFieldBool() {
        return new FieldBool(FieldBase.Type.MANDATORY, null);
    }

    public static FieldBool createOptionalFieldBool(String defaultValue) {
        return new FieldBool(FieldBase.Type.MANDATORY, defaultValue);
    }

}
