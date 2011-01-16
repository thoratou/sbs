package screen.tools.sbs.repositories;

import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.utils.FieldString;

public class RepositoryData {
	private FieldString id;
	private RepositoryType type;
	private FieldPath path;
	
	public RepositoryData() {
		id = new FieldString();
		type = new RepositoryType();
		path = new FieldPath();
	}
	
	public void setId(FieldString id) {
		this.id = id;
	}

	public FieldString getId() {
		return id;
	}

	public void setType(RepositoryType type) {
		this.type = type;
	}

	public RepositoryType getType() {
		return type;
	}

	public void setPath(FieldPath path) {
		this.path = path;
	}

	public FieldPath getPath() {
		return path;
	}
}
