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
	
	public Context getContext(ContextKey key){
		return contextTable.get(key);
	}
}
