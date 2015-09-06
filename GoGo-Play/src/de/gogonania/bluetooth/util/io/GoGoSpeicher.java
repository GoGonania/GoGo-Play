package de.gogonania.bluetooth.util.io;
import java.util.ArrayList;

public class GoGoSpeicher extends GoGoFile{
	private static final String line = ";;";
	private static final String kv = "::";
	
	class Line{
		String key;
		String value;
	}
	
	private ArrayList<Line> lines = new ArrayList<Line>();
	private boolean s = true;
	
	public GoGoSpeicher(String path){
		super(path);
		if(!exists()) return;
		String s = read();
		for(String l : s.split(line)){
			if(l.contains(kv)){
				Line n = new Line();
				n.key = l.substring(0, l.indexOf(kv));
				n.value = l.substring(l.indexOf(kv)+kv.length());
				lines.add(n);
			}
		}
	}
	
	public void remove(String key){
		int i = pos(key);
		if(i != -1){
			lines.remove(i);
			if(s) save();
		}
	}
	
	public String get(String key, String a){
		int i = pos(key);
		if(i != -1){
			return lines.get(i).value;
		} else{
			return a;
		}
	}
	
	public void set(String key, String value){
		int i = pos(key);
		if(i == -1){
			Line l = new Line();
			l.key = key;
			l.value = value;
			lines.add(l);
		} else{
			lines.get(i).value = value;
		}
		if(s) save();
	}
	
	public void save(){
		String s = "";
		for(Line l : lines){
			s += l.key+""+kv+""+l.value+""+line;
		}
		write(s);
	}
	
	private int pos(String key){
		int i = 0;
		for(Line l : lines){
			if(l.key.equals(key)) return i;
			i++;
		}
		return -1;
	}
	
	public void disableAutoSave(){s = false;}
	public boolean existsKey(String key){return pos(key) != -1;}
	public String get(String key){return get(key, null);}
}
