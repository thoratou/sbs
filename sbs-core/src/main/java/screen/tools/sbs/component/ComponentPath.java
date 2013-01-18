package screen.tools.sbs.component;

import screen.tools.sbs.fields.FieldFactory;
import screen.tools.sbs.fields.FieldPath;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.fields.interfaces.FieldBuildModeInterface;
import screen.tools.sbs.fields.interfaces.FieldPathInterface;
import screen.tools.sbs.fields.interfaces.FieldToolChainInterface;
import screen.tools.sbs.objects.Entry;

public class ComponentPath implements 	Entry<ComponentPath>,
										FieldPathInterface, 
										FieldToolChainInterface,
										FieldBuildModeInterface {
	private FieldPath path;
	private FieldString toolChain;
	private FieldString buildMode;
	
	public ComponentPath() {
		path = FieldFactory.createMandatoryFieldPath();
		toolChain = FieldFactory.createOptionalFieldString("all");
		buildMode = FieldFactory.createOptionalFieldString("all");
	}
	
	public ComponentPath(ComponentPath componentPath) {
		path = componentPath.path.copy();
		toolChain = componentPath.toolChain.copy();
		buildMode = componentPath.buildMode.copy();
	}
	
	public FieldPath getPath() {
		return path;
	}
	
	public FieldString getToolChain() {
		return toolChain;
	}
	
	public FieldString getBuildMode() {
		return buildMode;
	}

	@Override
	public void merge(ComponentPath componentPath) {
		path.merge(componentPath.path);
		buildMode.merge(componentPath.buildMode);
	}
	
	@Override
	public ComponentPath copy() {
		return new ComponentPath(this);
	}
}
