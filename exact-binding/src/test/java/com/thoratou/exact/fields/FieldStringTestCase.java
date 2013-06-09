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

import com.thoratou.exact.evaluation.BasicEvaluator;
import com.thoratou.exact.evaluation.EnvironmentVariables;
import junit.framework.TestCase;

import org.junit.Test;

import com.thoratou.exact.fields.FieldBase.Type;

public class FieldStringTestCase extends TestCase{

    private static EnvironmentVariables environmentVariables = new EnvironmentVariables();
    private static BasicEvaluator evaluator = new BasicEvaluator(environmentVariables);

    @Test
    public void testBasic() throws FieldException{
        FieldString fieldString = new FieldString();
        fieldString.set("toto");

        assertFalse(fieldString.isEmpty());
        assertNull(fieldString.getDefault());
        assertEquals("toto", fieldString.getOriginal());
        assertEquals("toto", evaluator.eval(fieldString));
    }

    @Test
    public void testLocalEnv() throws FieldException{
        FieldString fieldString = new FieldString();

        EnvironmentVariables localVariables = new EnvironmentVariables();
        environmentVariables.put("TOTO", "Value");

        fieldString.set("${TOTO}");

        assertFalse(fieldString.isEmpty());
        assertNull(fieldString.getDefault());
        assertEquals("${TOTO}", fieldString.getOriginal());
        assertEquals("Value", evaluator.eval(fieldString, localVariables));
    }

    @Test
    public void testGlobalEnv() throws FieldException{
        FieldString fieldString = new FieldString();
        environmentVariables.put("TOTO", "Value");

        fieldString.set("${TOTO}");

        assertFalse(fieldString.isEmpty());
        assertNull(fieldString.getDefault());
        assertEquals("${TOTO}", fieldString.getOriginal());
        assertEquals("Value", evaluator.eval(fieldString));

        environmentVariables.clear();
    }

    @Test
    public void testUnknownVariable() throws FieldException {
        FieldString fieldString = new FieldString();

        fieldString.set("${TOTO}");

        assertFalse(fieldString.isEmpty());
        assertNull(fieldString.getDefault());
        assertEquals("${TOTO}", fieldString.getOriginal());
        assertEquals("${TOTO}", fieldString.get());
        try {
            evaluator.eval(fieldString);
            assertFalse("No exception thrown",true);
        } catch (FieldException e) {
            assertEquals("Can't parse field string : ${TOTO}", e.getMessage());
        }
    }

    @Test
    public void testEmptyMandatory(){
        FieldString fieldString = new FieldString();
        assertTrue(fieldString.isEmpty());
        assertNull(fieldString.getDefault());
        assertNull(fieldString.getOriginal());

        try {
            fieldString.get();
            assertFalse("No exception thrown",true);
        } catch (FieldException e) {
            assertEquals("Unable to parse a null field string", e.getMessage());
        }

        try {
            evaluator.eval(fieldString);
            assertFalse("No exception thrown",true);
        } catch (FieldException e) {
            assertEquals("Unable to parse a null field string", e.getMessage());
        }
    }

    @Test
    public void testEmptyOptional(){
        FieldString fieldString = new FieldString(Type.OPTIONAL,null);
        assertTrue(fieldString.isEmpty());
        assertNull(fieldString.getDefault());
        assertNull(fieldString.getOriginal());

        try {
            fieldString.get();
            assertFalse("No exception thrown",true);
        } catch (FieldException e) {
            assertEquals("Unable to parse a null field string", e.getMessage());
        }

        try {
            evaluator.eval(fieldString);
            assertFalse("No exception thrown",true);
        } catch (FieldException e) {
            assertEquals("Unable to parse a null field string", e.getMessage());
        }
    }

    @Test
    public void testEmptyOptionalWithDefaultValue() throws FieldException{
        FieldString fieldString = new FieldString(Type.OPTIONAL,"default");
        assertTrue(fieldString.isEmpty());
        assertEquals("default", fieldString.getDefault());
        assertNull(fieldString.getOriginal());
        assertEquals("default", fieldString.get());
        assertEquals("default", evaluator.eval(fieldString));
    }

    @Test
    public void testDefaultValueEvaluation() throws FieldException{
        environmentVariables.put("DEFAULT", "default");

        FieldString fieldString = new FieldString(Type.OPTIONAL,"${DEFAULT}");
        assertTrue(fieldString.isEmpty());
        assertEquals("${DEFAULT}", fieldString.getDefault());
        assertNull(fieldString.getOriginal());
        assertEquals("${DEFAULT}", fieldString.get());
        assertEquals("default", evaluator.eval(fieldString));

        environmentVariables.clear();
    }

    @Test
    public void testDefaultOverwrite() throws FieldException{
        environmentVariables.put("DEFAULT", "default");
        environmentVariables.put("VALUE", "toto");

        //get -> default
        FieldString fieldString = new FieldString(Type.OPTIONAL,"${DEFAULT}");
        assertTrue(fieldString.isEmpty());
        assertEquals("${DEFAULT}", fieldString.getDefault());
        assertNull(fieldString.getOriginal());
        assertEquals("${DEFAULT}", fieldString.get());
        assertEquals("default", evaluator.eval(fieldString));

        //get -> toto
        fieldString.set("${VALUE}");
        assertFalse(fieldString.isEmpty());
        assertEquals("${DEFAULT}", fieldString.getDefault());
        assertEquals("${VALUE}", fieldString.getOriginal());
        assertEquals("${VALUE}", fieldString.get());
        assertEquals("toto", evaluator.eval(fieldString));

        //get -> default
        fieldString.set(null);
        assertTrue(fieldString.isEmpty());
        assertEquals("${DEFAULT}", fieldString.getDefault());
        assertNull(fieldString.getOriginal());
        assertEquals("${DEFAULT}", fieldString.get());
        assertEquals("default", evaluator.eval(fieldString));

        environmentVariables.clear();
    }

    @Test
    public void testComplexEvaluation() throws FieldException{
        FieldString fieldString = new FieldString();
        fieldString.set("Hello ${FIELD1} !!, I am a ${FIELD2} evaluation");

        environmentVariables.put("FIELD1", "World");
        environmentVariables.put("FIELD2", "longer");

        assertFalse(fieldString.isEmpty());
        assertNull(fieldString.getDefault());
        assertEquals("Hello ${FIELD1} !!, I am a ${FIELD2} evaluation", fieldString.getOriginal());
        assertEquals("Hello ${FIELD1} !!, I am a ${FIELD2} evaluation", fieldString.get());
        assertEquals("Hello World !!, I am a longer evaluation", evaluator.eval(fieldString));

        environmentVariables.clear();
    }
}
