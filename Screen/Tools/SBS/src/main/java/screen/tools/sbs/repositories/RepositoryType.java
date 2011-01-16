package screen.tools.sbs.repositories;

public class RepositoryType {
	// no type
	public static final int NO_TYPE_FLAG = 0x01;	  
	// is remote or local repository
	public static final int REMOTE_LOCAL_FLAG = 0x02;
	// is release or snapshot repository
	public static final int RELEASE_SNAPSHOT_FLAG = 0x04;
	// is release or snapshot external or internal repository
	public static final int EXTERNAL_INTERNAL_FLAG = 0x08;
	
	private int type;
	
	public RepositoryType() {
		setType(NO_TYPE_FLAG);
	}
	
	public RepositoryType(int type) {
		setType(type);
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}	
	
	@Override
	public String toString() {
		if((type & NO_TYPE_FLAG) != 0)
			return "E";
		
		String ret = "";
		
		if((type & REMOTE_LOCAL_FLAG) != 0)
			ret += "R";
		else
			ret += "L";
		
		if((type & RELEASE_SNAPSHOT_FLAG) != 0)
			ret += "R";
		else
			ret += "S";

		if((type & EXTERNAL_INTERNAL_FLAG) != 0)
			ret += "E";
		else
			ret += "I";

		return ret;
	}
}

