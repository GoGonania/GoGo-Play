package de.gogonania.bluetooth.util.io;
import de.gogonania.bluetooth.util.GoGoMap;

public class GoGoSpeicher extends GoGoFile{
	private static final String line = ";;";
	private static final String kv = "::";
	
	private GoGoMap<String, String> lines = new GoGoMap<String, String>();
	private boolean s = true;
	
	public GoGoSpeicher(String path){
		super(path);
		if(!exists()) return;
		String s = read();
		for(String l : s.split(line)){
			if(l.contains(kv)){
				lines.set(l.substring(0, l.indexOf(kv)), l.substring(l.indexOf(kv)+kv.length()));
			}
		}
	}
	
	public void remove(String key){
		lines.remove(key);
	}
	
	public String get(String key, String a){
		return lines.get(key, a);
	}
	
	public void set(String key, String value){
		lines.set(key, value);
		if(s) save();
	}
	
	public void save(){
		String s = "";
		for(String key : lines.getKeys()){
			s += key+""+kv+""+lines.get(key)+""+line;
		}
		write(s);
	}
	
	public void disableAutoSave(){s = false;}
	public boolean exists(String key){return lines.exists(key);}
	public String get(String key){return get(key, null);}
}
