package screen.tools.sbs.utils;

public class FieldException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -760543069990600753L;

	public FieldException(String toParse) {
		super(
			(toParse == null) ?
				"Unable to parse a null field string"
			:
				"Can't parse field string : "+toParse
		);
	}
	
	public FieldException() {
		super("Unable to parse field string");
	}	
}
