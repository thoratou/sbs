package screen.tools.sbs.fields;

import screen.tools.sbs.objects.EnvironmentVariables;

public abstract class FieldBase<T> {
	public enum Type{
		MANDATORY,
		OPTIONAL
	}
	
	private Type type;
	
	public FieldBase(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public boolean isMandatory(){
		return type == Type.MANDATORY;
	}
	
	public boolean isOptional(){
		return type == Type.OPTIONAL;
	}	
	
	public abstract T getDefault() throws FieldException;
	public abstract T get() throws FieldException;
	public abstract T get(EnvironmentVariables additionalVars) throws FieldException;
	public abstract T getOriginal();
	
	public abstract void set(T value);

	public abstract boolean isEmpty();
	
	// environment variables //
	
	private static EnvironmentVariables currentEnvironmentVariables = new EnvironmentVariables();

	public static void setCurrentEnvironmentVariables(
			EnvironmentVariables currentEnvironmentVariables) {
		FieldBase.currentEnvironmentVariables = currentEnvironmentVariables;
	}
	
	public static EnvironmentVariables getCurrentEnvironmentVariables() {
		return FieldBase.currentEnvironmentVariables;
	}

}
