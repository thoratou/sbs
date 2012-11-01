package screen.tools.fields;

import junit.framework.TestCase;

import org.junit.Test;

import screen.tools.sbs.fields.FieldBase;
import screen.tools.sbs.fields.FieldBase.Type;
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.objects.EnvironmentVariables;

public class FieldStringTestCase extends TestCase{
	
	@Test
	public void testBasic() throws FieldException{
		//clean up global env for contextless test
		FieldBase.getCurrentEnvironmentVariables().clear();

		FieldString fieldString = new FieldString();
		fieldString.set("toto");
		
		assertFalse(fieldString.isEmpty());
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
		assertNull(fieldString.getOriginal());
		assertEquals("default", fieldString.get());
	}

}
