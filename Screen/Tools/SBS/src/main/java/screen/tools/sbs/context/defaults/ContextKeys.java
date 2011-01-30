package screen.tools.sbs.context.defaults;

import screen.tools.sbs.context.ContextKey;

public class ContextKeys {
	public final static ContextKey PACK;
	public final static ContextKey TEST_PACK;
	public final static ContextKey XML_DOCUMENT;
	
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
			XML_DOCUMENT = tmp;	
		}
	}
}
