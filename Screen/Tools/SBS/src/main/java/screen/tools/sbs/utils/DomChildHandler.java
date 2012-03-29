package screen.tools.sbs.utils;

import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

public abstract class DomChildHandler {
	private final Element root;
	private final String childName;

	public DomChildHandler(Element root, String childName) {
		this.root = root;
		this.childName = childName;
	}
	
	public void process(){
		List<?> childList = root.getChildren(childName);
		Iterator<?> childIterator = childList.iterator();
		while(childIterator.hasNext()){
			Element child = (Element) childIterator.next();
			processChild(child);
		}	
	}
	
	protected abstract void processChild(Element child);
}
