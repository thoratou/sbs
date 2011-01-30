package screen.tools.sbs.context;


public class ContextKey {
	private String key;
	
	public ContextKey() {
		key = null;
	}
	
	public void setKey(String key){
		this.key = key;
	}
	
	public String getKey(){
		return key;
	}
	
	@Override
	public boolean equals(Object arg0) {
		ContextKey key = (ContextKey) arg0;
		if(key == null)
			return false;
		if(this.getKey() == null)
			return false;
		return this.getKey().equals(key.getKey());
	}
	
	@Override
	public int hashCode() {
		if(getKey() == null)
			return 0;
		return getKey().hashCode();
	}
}
