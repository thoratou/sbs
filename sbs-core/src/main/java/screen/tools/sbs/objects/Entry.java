package screen.tools.sbs.objects;

public interface Entry <T>{
	void merge(T entry);
	T copy();
}
