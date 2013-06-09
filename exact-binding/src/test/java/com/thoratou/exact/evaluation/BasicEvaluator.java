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

package com.thoratou.exact.evaluation;

import com.thoratou.exact.fields.FieldBase;
import com.thoratou.exact.fields.FieldException;
import com.thoratou.exact.fields.FieldString;

public class BasicEvaluator extends AbstractEvaluator<String> {
    public BasicEvaluator(EnvironmentVariables environmentVariables) {
        super(environmentVariables);
    }

    @Override
    public String eval(FieldBase<String> field) throws FieldException {
        return convertFromOriginalToFinal(field.get(), null);
    }

    @Override
    public String eval(FieldBase<String> field, EnvironmentVariables additionalEnvironment) throws FieldException {
        return convertFromOriginalToFinal(field.get(), additionalEnvironment);
    }

    public boolean isValid(FieldBase<String> field) throws FieldException {
        return isValid(field, null);
    }

    public boolean isValid(FieldBase<String> field, EnvironmentVariables additionalVars) throws FieldException {
        return !field.isEmpty() && (convertFromOriginalToFinal(field.get(),additionalVars)!=null);
    }

    private String convertFromOriginalToFinal(String originalString, EnvironmentVariables additionalVars) throws FieldException {
        if(additionalVars == null)
            additionalVars = new EnvironmentVariables();

        String finalString = "";
        int currentIndex = 0;
        int returnedIndex = 0;

        while((returnedIndex = originalString.indexOf("${", currentIndex)) != -1){
            finalString = finalString.concat(originalString.substring(currentIndex, returnedIndex));
            int endVarIndex = 0;
            if((endVarIndex = originalString.indexOf("}", returnedIndex)) == -1){
                throw new FieldException(originalString);
            }
            else{
                String var = originalString.substring(returnedIndex+2, endVarIndex);
                StringBuffer buffer = new StringBuffer();
                boolean stringOK = convertVariable(var, environmentVariables, buffer, originalString);
                if(!stringOK)
                {
                    stringOK = convertVariable(var, additionalVars, buffer, originalString);
                }
                if(!stringOK){
                    throw new FieldException(originalString);
                }
                else{
                    finalString = finalString.concat(buffer.toString());
                }
                currentIndex = endVarIndex + 1;
            }
        }
        finalString = finalString.concat(originalString.substring(currentIndex));

        return finalString;
    }

    private boolean convertVariable(String var, EnvironmentVariables env, StringBuffer buffer, String originalString) throws FieldException {
        FieldString fieldString = env.getFieldString(var);

        if(fieldString.isEmpty())
            throw new FieldException(originalString);

        String value = convertFromOriginalToFinal(
                fieldString.getOriginal(),
                env);
        if(value != null){
            buffer.append(value);
            return true;
        }
        return false;
    }
}
