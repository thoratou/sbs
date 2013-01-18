package screen.tools.sbs.pack;

import java.util.Iterator;
import java.util.Stack;

import screen.tools.sbs.component.ComponentDependency;
import screen.tools.sbs.component.ComponentDomReader;
import screen.tools.sbs.component.ComponentPack;
import screen.tools.sbs.context.ContextException;
import screen.tools.sbs.context.ContextHandler;
import screen.tools.sbs.context.defaults.ComponentPackContext;
import screen.tools.sbs.context.defaults.ContextKeys;
import screen.tools.sbs.context.defaults.ProfileContext;
import screen.tools.sbs.fields.FieldException;
import screen.tools.sbs.fields.FieldList;
import screen.tools.sbs.fields.FieldMap;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.objects.Entry;
import screen.tools.sbs.profile.Profile;
import screen.tools.sbs.utils.Logger;

public class PackReader {
	private final Profile profile;
	private final ContextHandler contextHandler;
	private Stack<ComponentPack> stack;
	
	private class Key implements Entry<Key>{
		private final FieldString name;
		private final FieldString version;

		public Key(FieldString name, FieldString version) {
			this.name = name;
			this.version = version;
		}
		
		@Override
		public int hashCode() {
			try {
				return name.get().hashCode() + version.get().hashCode();
			} catch (FieldException e) {
				Logger.error(e.getMessage());
			}
			return 0;
		}
		
		@Override
		public boolean equals(Object obj) {
			Key key = (Key) obj;
			try {
				return name.get().equals(key.name.get()) && version.get().equals(key.version.get());
			} catch (FieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		public void merge(Key key) {
			name.merge(key.name);
			version.merge(key.version);
		}

		@Override
		public Key copy() {
			return new Key(name, version);
		}
	};
	
	private FieldMap<Key, ComponentPack> map;

	public PackReader(ContextHandler contextHandler) throws ContextException {
		profile = contextHandler.<ProfileContext>get(ContextKeys.PROFILE).getProfile();
		this.contextHandler = contextHandler;
		stack = new Stack<ComponentPack>();
		map = new FieldMap<Key,ComponentPack>(new ComponentPack());
	}
	
	public void read() throws FieldException, ContextException{
		//main pack
		ComponentDomReader reader = new ComponentDomReader(contextHandler);
		reader.read();
		
		ComponentPack pack = null;
		if(contextHandler.isAvailable(ContextKeys.COMPONENT_PACK)){
			pack = contextHandler.<ComponentPackContext>get(ContextKeys.COMPONENT_PACK).getPack();			
		}
		
//		ComponentPack testPack = null;
//		if(contextHandler.isAvailable(ContextKeys.COMPONENT_TEST_PACK)){
//			testPack = contextHandler.<ComponentPackContext>get(ContextKeys.COMPONENT_TEST_PACK).getPack();			
//		}
		
		if(pack!=null){
			stack.add(pack);
			
			retrieveImports(pack);
			retrieveDependencies(pack);
			
//			while(!stack.empty())
//			{
//				ComponentPack componentPack = stack.pop();
//			}
		}
	}

	private void retrieveImports(ComponentPack pack) {
		// TODO Auto-generated method stub
	}

	private void retrieveDependencies(ComponentPack pack) throws FieldException, ContextException {
		FieldList<ComponentDependency> dependencyList = pack.getDependencyList();
		Iterator<ComponentDependency> iterator = dependencyList.iterator();
		while (iterator.hasNext()) {
			ComponentDependency dependency = iterator.next();
			if(isEligible(dependency)){
				PackManifest manifest = new PackManifest();
				manifest.getName().set(dependency.getName().get());
				manifest.getVersion().set(dependency.getVersion().get());
				manifest.getBuildMode().set(dependency.getBuildMode().get());
				manifest.getToolChain().set(dependency.getToolChain().get());
				
				ComponentPack toFind = new ComponentPack();
				if(!findManifestInCache(manifest, toFind))
				{
					if(!findManifestInRepository(manifest, toFind))
					{
						//TODO missing dep
						throw new ContextException("No dep found");
					}
				}
				
			}			
		}
	}

	private boolean isEligible(ComponentDependency dependency) throws FieldException {
		boolean aRet = false;
		if(!dependency.getName().isEmpty() && !dependency.getVersion().isEmpty()){
			if(profile.getBuildModeHierarchie().match(dependency.getBuildMode().get()) &&
			   profile.getBuildTypeHierarchie().match(dependency.getBuildType().get()) &&
			   profile.getToolChainHierarchie().match(dependency.getToolChain().get())){
				aRet = true;
			}
		}
		return aRet;
	}

	private boolean findManifestInRepository(PackManifest manifest, ComponentPack toFind) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean findManifestInCache(PackManifest manifest, ComponentPack toFind) {
		boolean ret = false;
		Key key = new Key(manifest.getName(), manifest.getVersion());
		if(map.containsKey(key)){
			toFind.merge(map.get(key));
			ret = true;
		}
		return ret;
	}
}
