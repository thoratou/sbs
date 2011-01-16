package screen.tools.sbs.repositories;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import screen.tools.sbs.utils.FieldString;

public class RepositoryFilter {
	private FieldString id;
	private FieldString componentName;
	private FieldString componentVersion;
	private FieldString compiler;
	private RepositoryData data;
	
	public RepositoryFilter() {
		id = new FieldString();
		data = new RepositoryData();
		componentName = new FieldString();
		componentVersion = new FieldString();
		compiler = new FieldString();
	}

	public void setId(FieldString id) {
		this.id = id;
	}

	public FieldString getId() {
		return id;
	}

	public void setComponentName(FieldString componentName) {
		this.componentName = componentName;
	}

	public FieldString getComponentName() {
		return componentName;
	}

	public void setComponentVersion(FieldString componentVersion) {
		this.componentVersion = componentVersion;
	}

	public FieldString getComponentVersion() {
		return componentVersion;
	}

	public void setCompiler(FieldString compiler) {
		this.compiler = compiler;
	}

	public FieldString getCompiler() {
		return compiler;
	}

	public boolean match(RepositoryFilter inputFilter) {
		Pattern namePattern = Pattern.compile(componentName.getString());
		Pattern versionPattern = Pattern.compile(componentVersion.getString());
		Pattern compilerPattern = Pattern.compile(compiler.getString());
		
		Matcher nameMatcher =
			namePattern.matcher(inputFilter.getComponentName().getString());
		Matcher versionMatcher =
			versionPattern.matcher(inputFilter.getComponentVersion().getString());
		Matcher compilerMatcher =
			compilerPattern.matcher(inputFilter.getCompiler().getString());
		
		boolean nameMatch = nameMatcher.matches();
		boolean versionMatch = versionMatcher.matches();
		boolean compilerMatch = compilerMatcher.matches();
		
		return nameMatch && versionMatch && compilerMatch;
	}

	public void setData(RepositoryData data) {
		this.data = data;
	}

	public RepositoryData getData() {
		return data;
	}
}
