package screen.tools.sbs.context.defaults;

import screen.tools.sbs.context.Context;

public class SbsFileAndPathContext implements Context{

	private String sbsXmlPath;
	private String sbsXmlFile;

	public void setSbsXmlPath(String sbsXmlPath) {
		this.sbsXmlPath = sbsXmlPath;
	}

	public String getSbsXmlPath() {
		return sbsXmlPath+"/";
	}

	public void setSbsXmlFile(String sbsXmlFile) {
		this.sbsXmlFile = sbsXmlFile;
	}

	public String getSbsXmlFile() {
		return sbsXmlFile;
	}

}
