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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathStep {
    public enum Kind{
        UNKNOWN,
        CHILD_ELEMENT,
        TEXT,
        ATTRIBUTE,
        BOM,
        START,
    }

    public enum StartKind{
        UNKNOWN,
        RELATIVE,
        ABSOLUTE
    }

    static final Map<Kind, String> KindMap = new HashMap<Kind, String>();
    static{
        KindMap.put(Kind.UNKNOWN, "unknown");
        KindMap.put(Kind.CHILD_ELEMENT, "child");
        KindMap.put(Kind.TEXT, "text");
        KindMap.put(Kind.ATTRIBUTE, "attribute");
        KindMap.put(Kind.BOM, "bom");
        KindMap.put(Kind.START, "start");
    }

    static final Map<String, Kind> ReverseKindMap = new HashMap<String, Kind>();
    static{
        ReverseKindMap.put("unknown", Kind.UNKNOWN);
        ReverseKindMap.put("child", Kind.CHILD_ELEMENT);
        ReverseKindMap.put("text", Kind.TEXT);
        ReverseKindMap.put("attribute", Kind.ATTRIBUTE);
        ReverseKindMap.put("bom", Kind.BOM);
        ReverseKindMap.put("start", Kind.START);
    }

    static final Map<StartKind, String> StartMap = new HashMap<StartKind, String>();
    static{
        StartMap.put(StartKind.UNKNOWN, "unknown");
        StartMap.put(StartKind.RELATIVE, "relative");
        StartMap.put(StartKind.ABSOLUTE, "absolute");
    }

    static final Map<String, StartKind> ReverseStartMap = new HashMap<String, StartKind>();
    static{
        ReverseStartMap.put("unknown", StartKind.UNKNOWN);
        ReverseStartMap.put("relative", StartKind.RELATIVE);
        ReverseStartMap.put("absolute", StartKind.ABSOLUTE);
    }

    private Kind stepKind;
    private StartKind startKind;
    private String stepValue;
    private String methodName;
    private String returnType;
    private List<PathStep> childSteps;

    public PathStep() {
        stepKind = Kind.UNKNOWN;
        startKind = StartKind.UNKNOWN;
        stepValue = "";
        methodName = "";
        returnType = "";
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

    public List<PathStep> getChildSteps() {
        return childSteps;
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PathStep pathStep = (PathStep) o;

        if (stepKind != pathStep.stepKind)
            return false;
        if (stepValue != null ? !stepValue.equals(pathStep.stepValue) : pathStep.stepValue != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stepKind.hashCode();
        result = 31 * result + (stepValue != null ? stepValue.hashCode() : 0);
        return result;
    }
}
