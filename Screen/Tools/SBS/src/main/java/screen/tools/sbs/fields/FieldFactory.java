package screen.tools.sbs.fields;


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
