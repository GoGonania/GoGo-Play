package de.gogonania.bluetooth.spielio.save;

import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.spielio.Spielhalle;
import de.gogonania.bluetooth.util.Zeit;
import de.gogonania.bluetooth.util.io.SaveItem;
import de.gogonania.bluetooth.util.io.SaveObjekt;

public class Spielsave extends SaveObjekt{
	public Spielsave(SaveItem s) {
		super(s);
	}
	
	public void create(String spielname, String[] para, String data){
		getSave().disableAutoSave();
		getSave().set("n", spielname);
		String aus = "";
		for(String s : para){
			aus += "-"+s+"";
		}
		aus = aus.substring(1);
		getSave().set("p", aus);
		getSave().set("d", data);
		getSave().set("t", Zeit.tag()+" "+Zeit.zeit());
		getSave().save();
	}
	
	public void register(){GameUtil.registerServer(getSave().get("p").split("-"), Spielhalle.get(getSave().get("n")), this);}
	public String getData(){return getSave().get("d");}
	public String getTime(){return getSave().get("t");}
	public String getName(){return Spielhalle.get(getSave().get("n")).getInfo().getName();}
	public String getDetail(){return getName()+" ("+getTime()+")";}
	
	public void remove() {
		super.remove();
		Spielsaves.saves.remove(this);
	}
}
