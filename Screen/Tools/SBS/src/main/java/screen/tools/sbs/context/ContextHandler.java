package screen.tools.sbs.context;

import java.util.Hashtable;

import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.EnvironmentVariablesContext;
import screen.tools.sbs.utils.FieldString;

public class ContextHandler {
	private Hashtable<ContextKey, Context> contextTable;
	
	public ContextHandler() {
		contextTable = new Hashtable<ContextKey, Context>();
		registerMandatoryContexts();
	}
	
	private void registerMandatoryContexts() {
		//create context for environment variable and fill it with some values
		EnvironmentVariablesContext environmentVariablesContext = new EnvironmentVariablesContext();

		String root = System.getProperty("SBS_ROOT");
		environmentVariablesContext.getEnvironmentVariables().put("SBS_ROOT", root);
		
		// set variable context to field string process
		FieldString.setCurrentEnvironmentVariables(environmentVariablesContext.getEnvironmentVariables());

		addContext(ContextKeys.ENV_VARIABLES,environmentVariablesContext);
	}

	public void addContext(ContextKey key, Context context){
		contextTable.put(key, context);
	}
	
	@Deprecated
	public Context getContext(ContextKey key){
		return contextTable.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Context> T get(ContextKey key) throws ContextException{
		T concreteContext = (T) contextTable.get(key);
		if(concreteContext == null){
			throw new ContextException("unable to retrieve concrete context : key = " + key.getKey());
		}
		return concreteContext;
	}
}
