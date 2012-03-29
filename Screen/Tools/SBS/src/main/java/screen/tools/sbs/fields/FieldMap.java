package screen.tools.sbs.fields;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import screen.tools.sbs.objects.Entry;

public class FieldMap<Key extends Entry<Key>, Value extends Entry<Value>> implements Entry< FieldMap<Key, Value> > {
	Hashtable<Key, Value> table;
	Value prototype;
	
	public FieldMap(Value prototype) {
		table = new Hashtable<Key, Value>();
		this.prototype = prototype;
	}
	
	public Value allocate(Key key){
		Value ret = null;
		if(key != null)
		{
			Value value = prototype.copy();
			table.put(key.copy(), value);
			ret = value;
		}
		return ret;
	}
	
	public void clear(){
		table.clear();
	}
	
	public boolean containsKey(Key key){
		return table.contains(key);
	}
	
	public boolean containsValue(Value value){
		return table.containsValue(value);
	}
	
	public Enumeration<Value> elements(){
		return table.elements();
	}
	
	public Set<java.util.Map.Entry<Key, Value>> entrySet(){
		return table.entrySet();
	}
	
	public boolean isEmpty(){
		return table.isEmpty();
	}
	
	public Enumeration<Key> keys(){
		return table.keys();
	}
	
	public Set<Key> keySet(){
		return table.keySet();
	}
	
	public Value get(Key key){
		Value value = table.get(key);
		return value == null ? allocate(key) : value;
	}
	
	public Value remove(Key key){
		return table.remove(key);
	}
	
	public Collection<Value> values(){
		return table.values();
	}

	@Override
	public void merge(FieldMap<Key, Value> tableEntry) {
		Set<java.util.Map.Entry<Key, Value>> entrySet = tableEntry.table.entrySet();
		Iterator<java.util.Map.Entry<Key, Value>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			java.util.Map.Entry<Key, Value> next = iterator.next();

			Key key = next.getKey();
			Value value = next.getValue();

			Key keyCopy = key.copy();
			Value valueCopy = value.copy();
			
			this.table.put(keyCopy, valueCopy);
		}
	}

	@Override
	public FieldMap<Key, Value> copy() {
		FieldMap<Key, Value> fieldMap = new FieldMap<Key, Value>(prototype.copy());
		fieldMap.merge(this);
		return fieldMap;
	}
}
