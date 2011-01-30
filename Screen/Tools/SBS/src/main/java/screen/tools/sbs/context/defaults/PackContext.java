package screen.tools.sbs.context.defaults;

import screen.tools.sbs.context.Context;
import screen.tools.sbs.objects.Pack;

public class PackContext implements Context {
	private Pack pack;
	
	public PackContext() {
		pack = null;
	}

	public void setPack(Pack pack) {
		this.pack = pack;
	}

	public boolean hasPack(){
		return pack != null;
	}
	
	public Pack getPack() {
		return pack;
	}
}
