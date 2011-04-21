package screen.tools.sbs.context.defaults;

import screen.tools.sbs.context.ContextKey;

public class ContextKeys {
	public final static ContextKey PACK;
	public final static ContextKey TEST_PACK;
	public final static ContextKey SBS_XML_DOCUMENT;
	public final static ContextKey SBS_FILE_AND_PATH;
	public final static ContextKey REPOSITORIES;
	public final static ContextKey ENV_VARIABLES;
	
	static{
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("pack");
			PACK = tmp;
		}
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("test-pack");
			TEST_PACK = tmp;
		}
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("xml-document");
			SBS_XML_DOCUMENT = tmp;	
		}
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("sbs-file-and-path");
			SBS_FILE_AND_PATH = tmp;	
		}
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("repositories");
			REPOSITORIES = tmp;	
		}
		{
			ContextKey tmp = new ContextKey();
			tmp.setKey("env-variables");
			ENV_VARIABLES = tmp;	
		}
	}
}
