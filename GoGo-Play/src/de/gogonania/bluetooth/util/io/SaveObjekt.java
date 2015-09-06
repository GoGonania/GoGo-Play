package de.gogonania.bluetooth.util.io;

public class SaveObjekt{
	SaveItem s;
	
	public SaveObjekt(SaveItem s){
		this.s = s;
	}
	
	public long getID(){return s.getId();}
	public void remove(){s.getManager().remove(s);}
	public SaveItem getSave(){return s;}
	public boolean equals(Object o){return o.getClass().equals(getClass()) && ((SaveObjekt) o).getID() == getID();}
}
