package screen.tools.sbs.profile;

import screen.tools.sbs.fields.FieldList;
import screen.tools.sbs.objects.Entry;

public class ProfileHierarchie implements Entry<ProfileHierarchie> {
	public FieldList<ProfileHierarchieItem> items;
	
	public ProfileHierarchie() {
		items = new FieldList<ProfileHierarchieItem>(new ProfileHierarchieItem());
	}
	
	public ProfileHierarchie(ProfileHierarchie profileHierarchie) {
		items = profileHierarchie.items.copy();
	}

	public FieldList<ProfileHierarchieItem> getItems() {
		return items;
	}
	
	@Override
	public void merge(ProfileHierarchie profileHierarchie) {
		items.merge(profileHierarchie.items);
	}

	@Override
	public ProfileHierarchie copy() {
		return new ProfileHierarchie(this);
	}
	
}
