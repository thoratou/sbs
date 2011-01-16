package screen.tools.sbs.repositories;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import screen.tools.sbs.utils.FieldPath;
import screen.tools.sbs.utils.FieldString;
import screen.tools.sbs.utils.Logger;

public class RepositoryComponent {
	private FieldString componentName;
	private FieldString componentVersion;
	private FieldString compiler;
	
	public RepositoryComponent(FieldString componentName, 
							FieldString componentVersion,
							FieldString compiler) {
		this.componentName = componentName;
		this.componentVersion = componentVersion;
		this.compiler = compiler;
	}
	
	public RepositoryFilter retrieve(RepositoryFilterTable table){
		RepositoryFilter inputFilter = new RepositoryFilter();
		inputFilter.setComponentName(componentName);
		inputFilter.setComponentVersion(componentVersion);
		inputFilter.setCompiler(compiler);
		
		List<RepositoryFilter> list = table.filter(inputFilter);
		Iterator<RepositoryFilter> iterator = list.iterator();
		while(iterator.hasNext()){
			RepositoryFilter next = iterator.next();
			String componentPath = getComponentPath(next).getString();
			Logger.debug(componentPath);
			Logger.debug(""+new File(componentPath+"/component.xml").exists());
			Logger.debug(""+new File(componentPath+"/lib/"+compiler.getString()).exists());
			
			if(new File(componentPath+"/component.xml").exists()
				//&& new File(componentPath+"/lib/"+compiler.getString()).exists()
				){
				return next;
			}
		}
		return null;
	}
	
	public FieldPath getComponentPath(RepositoryFilter filter){
		return new FieldPath(filter.getData().getPath().getString()
		+"/"
		+componentName.getString()
		+"/"
		+componentVersion.getString());
	}
}
