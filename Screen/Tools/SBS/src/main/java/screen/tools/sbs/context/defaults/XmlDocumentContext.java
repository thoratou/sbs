package screen.tools.sbs.context.defaults;

import org.w3c.dom.Document;

import screen.tools.sbs.context.Context;

public class XmlDocumentContext implements Context{

	private Document document;

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
