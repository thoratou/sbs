package screen.tools.sbs.fields;

import java.util.ArrayList;
import java.util.Iterator;

import screen.tools.sbs.objects.Entry;

public class FieldList <T extends Entry<T> > implements Entry<FieldList<T> >{
	private ArrayList<T> array;
	final T prototype;
	
	public FieldList(final T prototype) {
		array = new ArrayList<T>();
		this.prototype = prototype;
	}
	
	public T allocate(){
		T instance = prototype.copy();
		array.add(instance);
		return instance;
	}
	
	public T allocate(int i){
		T instance = prototype.copy();
		array.add(i, instance);
		return instance;
	}
	
	public void clear(){
		array.clear();
	}
	
	public boolean contains(T instance){
		return array.contains(instance);
	}
	
	public T get(int i){
		T instance = array.get(i);
		return instance == null ? prototype.copy() : instance;
	}
	
	public int indexOf(T instance){
		return array.indexOf(instance);
	}
	
	public boolean isEmpty(){
		return array.isEmpty();
	}
	
	public Iterator<T> iterator() {
		return array.iterator();
	}
	
	public int lastIndexOf(T instance){
		return array.lastIndexOf(instance);
	}
	
	public T remove(int i){
		return array.remove(i);
	}
	
	public boolean remove(T instance){
		return array.remove(instance);
	}
	
	public T replace(int i){
		T instance = prototype.copy();
		array.set(i, instance);
		return instance;
	}
	
	public int size(){
		return array.size();
	}
	
	@SuppressWarnings("unchecked")
	public T[] toArray(){
		return (T[]) array.toArray();
	}
	
	public T[] toArray(T[] table){
		return array.toArray(table);
	}
	
	@Override
	public void merge(FieldList<T> listEntry) {
		Iterator<T> iterator = listEntry.array.iterator();
		while (iterator.hasNext()) {
			T item = (T) iterator.next();
			T copy = item.copy();
			this.array.add(copy);
		}
	}
	
	@Override
	public FieldList<T> copy() {
		FieldList<T> clone = new FieldList<T>(this.prototype);
		clone.merge(this);
		return clone;
	}
}
