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

import junit.framework.TestCase;

import org.junit.Test;

import com.thoratou.exact.fields.FieldBase.Type;

public class FieldStringTestCase extends TestCase{
	
	@Test
	public void testBasic() throws FieldException{
		
		//clean up global env for contextless test
		FieldBase.getCurrentEnvironmentVariables().clear();

		FieldString fieldString = new FieldString();
		fieldString.set("toto");
		
		assertFalse(fieldString.isEmpty());
		assertNull(fieldString.getDefault());
		assertEquals("toto", fieldString.getOriginal());
		assertEquals("toto", fieldString.get());
	}
	
	@Test
	public void testLocalEnv() throws FieldException{
		//clean up global env for contextless test
		FieldBase.getCurrentEnvironmentVariables().clear();

		FieldString fieldString = new FieldString();
		
		EnvironmentVariables environmentVariables = new EnvironmentVariables();
		environmentVariables.put("TOTO", "Value");
		
		fieldString.set("${TOTO}");
		
		assertFalse(fieldString.isEmpty());
		assertNull(fieldString.getDefault());
		assertEquals("${TOTO}", fieldString.getOriginal());
		assertEquals("Value", fieldString.get(environmentVariables));
	}
	
	@Test
	public void testGlobalEnv() throws FieldException{
		//clean up global env for contextless test
		FieldBase.getCurrentEnvironmentVariables().clear();

		FieldString fieldString = new FieldString();
		FieldBase.getCurrentEnvironmentVariables().put("TOTO", "Value");
		
		fieldString.set("${TOTO}");
		
		assertFalse(fieldString.isEmpty());
		assertNull(fieldString.getDefault());
		assertEquals("${TOTO}", fieldString.getOriginal());
		assertEquals("Value", fieldString.get());
	}
	
	@Test
	public void testUnknownVariable(){
		//clean up global env for contextless test
		FieldBase.getCurrentEnvironmentVariables().clear();

		FieldString fieldString = new FieldString();
		
		fieldString.set("${TOTO}");
		
		assertFalse(fieldString.isEmpty());
		assertNull(fieldString.getDefault());
		assertEquals("${TOTO}", fieldString.getOriginal());
		try {
			fieldString.get();
			assertFalse("No exception thrown",true);
		} catch (FieldException e) {
			assertEquals("Can't parse field string : ${TOTO}", e.getMessage());
		}
	}
	
	@Test
	public void testEmptyMandatory(){
		//clean up global env for contextless test
		FieldBase.getCurrentEnvironmentVariables().clear();

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
	}

	@Test
	public void testEmptyOptional(){
		//clean up global env for contextless test
		FieldBase.getCurrentEnvironmentVariables().clear();

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
	}
	
	@Test
	public void testEmptyOptionalWithDefaultValue() throws FieldException{
		//clean up global env for contextless test
		FieldBase.getCurrentEnvironmentVariables().clear();

		FieldString fieldString = new FieldString(Type.OPTIONAL,"default");
		assertTrue(fieldString.isEmpty());
		assertEquals("default", fieldString.getDefault());
		assertNull(fieldString.getOriginal());
		assertEquals("default", fieldString.get());
	}

	@Test
	public void testDefaultValueEvaluation() throws FieldException{
		//clean up global env for contextless test
		FieldBase.getCurrentEnvironmentVariables().clear();
		FieldBase.getCurrentEnvironmentVariables().put("DEFAULT", "default");

		FieldString fieldString = new FieldString(Type.OPTIONAL,"${DEFAULT}");
		assertTrue(fieldString.isEmpty());
		assertEquals("${DEFAULT}", fieldString.getDefault());
		assertNull(fieldString.getOriginal());
		assertEquals("default", fieldString.get());
	}
	
	@Test
	public void testDefaultOverwrite() throws FieldException{
		//clean up global env for contextless test
		FieldBase.getCurrentEnvironmentVariables().clear();
		FieldBase.getCurrentEnvironmentVariables().put("DEFAULT", "default");
		FieldBase.getCurrentEnvironmentVariables().put("VALUE", "toto");

		//get -> default
		FieldString fieldString = new FieldString(Type.OPTIONAL,"${DEFAULT}");
		assertTrue(fieldString.isEmpty());
		assertEquals("${DEFAULT}", fieldString.getDefault());
		assertNull(fieldString.getOriginal());
		assertEquals("default", fieldString.get());
		
		//get -> toto
		fieldString.set("${VALUE}");
		assertFalse(fieldString.isEmpty());
		assertEquals("${DEFAULT}", fieldString.getDefault());
		assertEquals("${VALUE}", fieldString.getOriginal());
		assertEquals("toto", fieldString.get());
		
		//get -> default
		fieldString.set(null);
		assertTrue(fieldString.isEmpty());
		assertEquals("${DEFAULT}", fieldString.getDefault());
		assertNull(fieldString.getOriginal());
		assertEquals("default", fieldString.get());
	}

	@Test
	public void testComplexEvaluation() throws FieldException{
		//clean up global env for contextless test
		FieldBase.getCurrentEnvironmentVariables().clear();

		FieldString fieldString = new FieldString();
		fieldString.set("Hello ${FIELD1} !!, I am a ${FIELD2} evaluation");
		
		FieldBase.getCurrentEnvironmentVariables().put("FIELD1", "World");
		FieldBase.getCurrentEnvironmentVariables().put("FIELD2", "longer");
		
		assertFalse(fieldString.isEmpty());
		assertNull(fieldString.getDefault());
		assertEquals("Hello ${FIELD1} !!, I am a ${FIELD2} evaluation", fieldString.getOriginal());
		assertEquals("Hello World !!, I am a longer evaluation", fieldString.get());
	}
}
