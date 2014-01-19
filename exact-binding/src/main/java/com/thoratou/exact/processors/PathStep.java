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
import com.thoratou.exact.fields.FieldBase;
import com.thoratou.exact.fields.FieldExtensionList;
import com.thoratou.exact.fields.FieldList;

import java.util.*;

public class PathStep {
    public enum Kind{
        UNKNOWN,
        CHILD_ELEMENT,
        TEXT,
        ATTRIBUTE,
        BOM,
        EXTENSION,
        START,
    }

    public enum StartKind{
        UNKNOWN,
        RELATIVE,
        ABSOLUTE,
    }

    public enum TypeKind{
        UNKNOWN,
        FIELD,
        LIST,
        BOM,
    }

    public enum ListTypeKind{
        UNKNOWN,
        FIELD,
        BOM,
    }

    public static final Map<Kind, String> KindMap = new HashMap<Kind, String>();
    static{
        KindMap.put(Kind.UNKNOWN, "unknown");
        KindMap.put(Kind.CHILD_ELEMENT, "child");
        KindMap.put(Kind.TEXT, "text");
        KindMap.put(Kind.ATTRIBUTE, "attribute");
        KindMap.put(Kind.BOM, "bom");
        KindMap.put(Kind.EXTENSION, "extension");
        KindMap.put(Kind.START, "start");
    }

    public static final Map<String, Kind> ReverseKindMap = new HashMap<String, Kind>();
    static{
        ReverseKindMap.put("unknown", Kind.UNKNOWN);
        ReverseKindMap.put("child", Kind.CHILD_ELEMENT);
        ReverseKindMap.put("text", Kind.TEXT);
        ReverseKindMap.put("attribute", Kind.ATTRIBUTE);
        ReverseKindMap.put("bom", Kind.BOM);
        ReverseKindMap.put("extension", Kind.EXTENSION);
        ReverseKindMap.put("start", Kind.START);
    }

    public static final Map<StartKind, String> StartMap = new HashMap<StartKind, String>();
    static{
        StartMap.put(StartKind.UNKNOWN, "unknown");
        StartMap.put(StartKind.RELATIVE, "relative");
        StartMap.put(StartKind.ABSOLUTE, "absolute");
    }

    public static final Map<String, StartKind> ReverseStartMap = new HashMap<String, StartKind>();
    static{
        ReverseStartMap.put("unknown", StartKind.UNKNOWN);
        ReverseStartMap.put("relative", StartKind.RELATIVE);
        ReverseStartMap.put("absolute", StartKind.ABSOLUTE);
    }

    public static final Map<TypeKind, String> TypeMap = new HashMap<TypeKind, String>();
    static{
        TypeMap.put(TypeKind.UNKNOWN, "unknown");
        TypeMap.put(TypeKind.FIELD, "field");
        TypeMap.put(TypeKind.LIST, "list");
        TypeMap.put(TypeKind.BOM, "bom");
    }

    public static final Map<String, TypeKind> ReverseTypeMap = new HashMap<String, TypeKind>();
    static{
        ReverseTypeMap.put("unknown", TypeKind.UNKNOWN);
        ReverseTypeMap.put("field", TypeKind.FIELD);
        ReverseTypeMap.put("list", TypeKind.LIST);
        ReverseTypeMap.put("bom", TypeKind.BOM);
    }

    public static final Map<ListTypeKind, String> ListTypeMap = new HashMap<ListTypeKind, String>();
    static{
        ListTypeMap.put(ListTypeKind.UNKNOWN, "unknown");
        ListTypeMap.put(ListTypeKind.FIELD, "field");
        ListTypeMap.put(ListTypeKind.BOM, "bom");
    }

    public static final Map<String, ListTypeKind> ReverseListTypeMap = new HashMap<String, ListTypeKind>();
    static{
        ReverseListTypeMap.put("unknown", ListTypeKind.UNKNOWN);
        ReverseListTypeMap.put("field", ListTypeKind.FIELD);
        ReverseListTypeMap.put("bom", ListTypeKind.BOM);
    }

    private Kind stepKind;
    private StartKind startKind;
    private String stepValue;
    private String methodName;
    private String returnType;
    private String extensionName;
    private List<PathStep> childSteps;

    public PathStep() {
        stepKind = Kind.UNKNOWN;
        startKind = StartKind.UNKNOWN;
        stepValue = "";
        methodName = "";
        returnType = "";
        extensionName = "";
        childSteps = new ArrayList<PathStep>();
    }

    public Kind getStepKind() {
        return stepKind;
    }

    public void setStepKind(Kind stepKind) {
        this.stepKind = stepKind;
    }

    public StartKind getStartKind() {
        return startKind;
    }

    public void setStartKind(StartKind startKind) {
        this.startKind = startKind;
    }

    public String getStepValue() {
        return stepValue;
    }

    public void setStepValue(String stepValue) {
        this.stepValue = stepValue;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public List<PathStep> getChildSteps() {
        return childSteps;
    }

    public TypeKind getReturnTypeKind() throws ClassNotFoundException {
        TypeKind kind = TypeKind.UNKNOWN;

        if(ExactProcessor.classMap.containsKey(returnType)){
            //not compiled bom class, so mustn't create a Class<T> instance
            kind = TypeKind.BOM;
        }
        else{
            Class<?> clazz = Class.forName(getTypeWithoutParameters(returnType));

            if(FieldBase.class.isAssignableFrom(clazz)){
                kind = TypeKind.FIELD;
            }
            else if(FieldList.class.isAssignableFrom(clazz)){
                kind = TypeKind.LIST;
            }
            else if(FieldExtensionList.class.isAssignableFrom(clazz)){
                kind = TypeKind.LIST;
            }
            else if(Entry.class.isAssignableFrom(clazz)){
                //existing bom class (i.e external compilation)
                kind = TypeKind.BOM;
            }
        }

        return kind;
    }

    public ListTypeKind getReturnListTypeKind() throws ClassNotFoundException {
        ListTypeKind kind = ListTypeKind.UNKNOWN;

        List<String> typeParameterList = getTypeParameterList(returnType);
        if(typeParameterList.size() == 1){
            if(ExactProcessor.classMap.containsKey(typeParameterList.get(0))){
                //not compiled bom class, so mustn't create a Class<T> instance
                kind = ListTypeKind.BOM;
            }
            else{
                Class<?> clazz = Class.forName(getTypeWithoutParameters(typeParameterList.get(0)));

                if(FieldBase.class.isAssignableFrom(clazz)){
                    kind = ListTypeKind.FIELD;
                }
                else if(Entry.class.isAssignableFrom(clazz)){
                    //existing bom class (i.e external compilation)
                    kind = ListTypeKind.BOM;
                }
            }
        }

        return kind;
    }

    public String getReturnListType() {
        List<String> typeParameterList = getTypeParameterList(returnType);
        return typeParameterList.get(0);
    }

    public static String getTypeWithoutParameters(String type){
        String[] split = type.split("<");
        if(split.length == 1){
            return type;
        }
        else{
            return split[0];
        }
    }

    public static List<String> getTypeParameterList(String type){
        List<String> ret =  new ArrayList<String>();
        int leftBraceIndex = type.indexOf('<');
        if(leftBraceIndex != -1){
            int rightBraceIndex = type.lastIndexOf('>');
            if(rightBraceIndex != -1){
                String parameters = type.substring(leftBraceIndex+1, rightBraceIndex);
                String[] split = parameters.split(",");
                ret = Arrays.asList(split);
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{");
        stringBuffer.append(stepKind.name());
        if(stepKind == Kind.START){
            stringBuffer.append("(");
            stringBuffer.append(startKind.name());
            stringBuffer.append(")");
        }
        stringBuffer.append(",");
        stringBuffer.append(stepValue);
        stringBuffer.append(",");
        stringBuffer.append(methodName);
        stringBuffer.append(",");
        stringBuffer.append(returnType);
        stringBuffer.append(",");
        stringBuffer.append(extensionName);
        stringBuffer.append(",[");
        boolean isFirstChild = true;
        for(PathStep childStep : childSteps){
            if(isFirstChild){
                isFirstChild = false;
            }
            else{
                stringBuffer.append(",");
            }
            stringBuffer.append(childStep.toString());
        }
        stringBuffer.append("]}");

        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PathStep pathStep = (PathStep) o;

        if (extensionName != null ? !extensionName.equals(pathStep.extensionName) : pathStep.extensionName != null)
            return false;
        if (methodName != null ? !methodName.equals(pathStep.methodName) : pathStep.methodName != null) return false;
        if (returnType != null ? !returnType.equals(pathStep.returnType) : pathStep.returnType != null) return false;
        if (startKind != pathStep.startKind) return false;
        if (stepKind != pathStep.stepKind) return false;
        if (stepValue != null ? !stepValue.equals(pathStep.stepValue) : pathStep.stepValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stepKind.hashCode();
        result = 31 * result + startKind.hashCode();
        result = 31 * result + (stepValue != null ? stepValue.hashCode() : 0);
        result = 31 * result + (methodName != null ? methodName.hashCode() : 0);
        result = 31 * result + (returnType != null ? returnType.hashCode() : 0);
        result = 31 * result + (extensionName != null ? extensionName.hashCode() : 0);
        return result;
    }
}
