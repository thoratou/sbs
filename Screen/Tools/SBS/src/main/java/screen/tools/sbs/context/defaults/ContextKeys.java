package screen.tools.sbs.context.defaults;

import screen.tools.sbs.context.ContextKey;

public class ContextKeys {
	public final static ContextKey PACK;
	public final static ContextKey TEST_PACK;
	public final static ContextKey SBS_XML_DOCUMENT;
	
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
	}
}