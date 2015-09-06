package de.gogonania.bluetooth.util.io;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class SaveManager{
	private String dir;
	private ArrayList<SaveItem> items = new ArrayList<SaveItem>();
	
	public SaveManager(String dir){
		this.dir = dir;
		GoGoFile d = new GoGoFile(dir);
		if(!d.exists()) d.mkdirs();
		for(File f : d.listFiles()){
			items.add(new SaveItem(this, Integer.parseInt(f.getName())));
		}
		Collections.sort(items);
	}
	
	public SaveItem get(String key, String value){for(SaveItem i : items){if(i.get(key).equals(value)){return i;} else{}} return null;}
	public SaveItem get(int id){int i = pos(id); if(i != -1){return items.get(i);} else{return null;}}
	public void remove(SaveItem s){int i = pos(s.getId()); if(i != -1){items.remove(i); s.delete();} else{}}
	public ArrayList<SaveItem> list(){return items;}
	public SaveItem create(){SaveItem s = new SaveItem(this, random()); items.add(s); return s;}
	private int random(){if(items.isEmpty()){return 0;} else{int id = items.get(items.size()-1).getId(); while(exists(id)){id++;} return id;}}
	public String getDir(){return dir;}
	public boolean exists(int id){return pos(id) != -1;}
	private int pos(int id){int i = 0; for(SaveItem s : items){if(s.getId() == id){return i;} else{} i++;} return -1;}
}
