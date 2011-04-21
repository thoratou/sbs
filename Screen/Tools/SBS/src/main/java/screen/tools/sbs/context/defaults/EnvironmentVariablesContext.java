package screen.tools.sbs.context.defaults;

import screen.tools.sbs.context.Context;
import screen.tools.sbs.objects.EnvironmentVariables;

public class EnvironmentVariablesContext implements Context {
	private EnvironmentVariables environmentVariables;

	public EnvironmentVariablesContext() {
		environmentVariables = new EnvironmentVariables();
	}

	public EnvironmentVariables getEnvironmentVariables() {
		return environmentVariables;
	}
}
