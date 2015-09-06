package de.gogonania.bluetooth.util.io;

public class SaveItem extends GoGoSpeicher{
	private int id;
	private SaveManager m;
	
	public int compareTo(SaveItem s){
		if(s.getId() < id) return 1;
		if(s.getId() > id) return -1;
		return 0;
	}
	
	public SaveItem(SaveManager m, int id){
		super(m.getDir()+"/"+id);
		this.id = id;
		this.m = m;
	}
	
	public SaveItem getSaveItem(SaveManager s, String key){return s.get(Integer.parseInt(get(key)));}
	public int getId(){return id;}
	public SaveManager getManager(){return m;}
}
