package screen.tools.sbs.context.defaults;

import screen.tools.sbs.context.Context;
import screen.tools.sbs.repositories.RepositoryDataTable;
import screen.tools.sbs.repositories.RepositoryFilterTable;

public class RepositoryContext implements Context {
	private RepositoryFilterTable repositoryFilterTable;
	private RepositoryDataTable repositoryDataTable;

	public RepositoryContext() {
		repositoryFilterTable = new RepositoryFilterTable();
		repositoryDataTable = new RepositoryDataTable();
	}

	public RepositoryFilterTable getRepositoryFilterTable() {
		return repositoryFilterTable;
	}

	public RepositoryDataTable getRepositoryDataTable() {
		return repositoryDataTable;
	}
}
