package screen.tools.sbs.profile;

import screen.tools.sbs.objects.Entry;

public class Profile implements Entry<Profile>{
	public ProfileHierarchie buildTypeHierarchie;
	public ProfileHierarchie buildModeHierarchie;
	public ProfileHierarchie toolChainHierarchie;
	
	public Profile() {
		buildTypeHierarchie = new ProfileHierarchie();
		buildModeHierarchie = new ProfileHierarchie();
		toolChainHierarchie = new ProfileHierarchie();
	}

	public Profile(Profile profile) {
		buildTypeHierarchie = profile.buildTypeHierarchie.copy();
		buildModeHierarchie = profile.buildModeHierarchie.copy();
		toolChainHierarchie = profile.toolChainHierarchie.copy();
	}
	
	public ProfileHierarchie getBuildModeHierarchie() {
		return buildModeHierarchie;
	}
	
	public ProfileHierarchie getBuildTypeHierarchie() {
		return buildTypeHierarchie;
	}
	
	public ProfileHierarchie getToolChainHierarchie() {
		return toolChainHierarchie;
	}

	@Override
	public void merge(Profile profile) {
		buildTypeHierarchie.merge(profile.buildTypeHierarchie);
		buildModeHierarchie.merge(profile.buildModeHierarchie);
		toolChainHierarchie.merge(profile.toolChainHierarchie);
	}
	@Override
	public Profile copy() {
		return new Profile(this);
	}
}
