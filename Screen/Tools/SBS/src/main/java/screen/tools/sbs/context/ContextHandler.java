package screen.tools.sbs.context;

import java.util.Hashtable;

public class ContextHandler {
	private Hashtable<ContextKey, Context> contextTable;
	
	public ContextHandler() {
		contextTable = new Hashtable<ContextKey, Context>();
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
