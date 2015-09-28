package de.gogonania.bluetooth.util;
import java.util.ArrayList;

public class GoGoMap<K, V>{
	private ArrayList<K> keys = new ArrayList<K>();
	private ArrayList<V> values = new ArrayList<V>();
	
	public int pos(K key){
		int i = 0;
		for(K k : keys){
			if(k.equals(key)) return i;
			i++;
		}
		return -1;
	}
	
	public void set(K key, V value){
		int i = pos(key);
		if(i == -1){
			keys.add(key);
			values.add(value);
		} else{
			values.set(i, value);
		}
	}
	
	public V get(K key, V a){
		int i = pos(key);
		if(i != -1) return values.get(i);
		return a;
	}
	
	public void remove(K key){
		int i = pos(key);
		if(i != -1){
			keys.remove(i);
			values.remove(i);
		}
	}
	
	public boolean getBoolean(K key, boolean a){
		return Boolean.parseBoolean((String) get(key, (V) (""+a+"")));
	}

	public K getKey(int id){return keys.get(id);}
	public V getValue(int id){return values.get(id);}
	public ArrayList<V> getValues(){return values;}
	public ArrayList<K> getKeys(){return keys;}
    public int size(){return keys.size();}
	public V get(K key){return get(key, null);}
	public boolean exists(K key){return pos(key) != -1;}
}
