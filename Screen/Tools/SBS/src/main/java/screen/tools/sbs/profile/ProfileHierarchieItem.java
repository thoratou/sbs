package screen.tools.sbs.profile;

import screen.tools.sbs.fields.FieldFactory;
import screen.tools.sbs.fields.FieldString;
import screen.tools.sbs.objects.Entry;
import screen.tools.sbs.utils.Logger.LogLevel;

public class ProfileHierarchieItem implements Entry<ProfileHierarchieItem>{
	private FieldString field;
	LogLevel logLevel;
	
	public ProfileHierarchieItem() {
		field = FieldFactory.createMandatoryFieldString();
		logLevel = LogLevel.NO_LOG;
	}

	public ProfileHierarchieItem(ProfileHierarchieItem profileHierarchieItem) {
		field = profileHierarchieItem.field.copy();
		logLevel = profileHierarchieItem.logLevel;
	}
	
	public FieldString getField() {
		return field;
	}
	
	public LogLevel getLogLevel() {
		return logLevel;
	}

	@Override
	public void merge(ProfileHierarchieItem profileHierarchieItem) {
		field.merge(profileHierarchieItem.field);
		logLevel = profileHierarchieItem.logLevel;
	}

	@Override
	public ProfileHierarchieItem copy() {
		return new ProfileHierarchieItem(this);
	}

}
