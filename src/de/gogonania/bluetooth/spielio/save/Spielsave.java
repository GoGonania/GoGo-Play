package de.gogonania.bluetooth.spielio.save;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.spielio.SpielInfo;
import de.gogonania.bluetooth.spielio.Spielhalle;
import de.gogonania.bluetooth.util.io.SaveItem;
import de.gogonania.bluetooth.util.io.SaveObjekt;

public class Spielsave extends SaveObjekt{
	private SpielInfo info;
	
	public Spielsave(SaveItem s) {
		super(s);
	}
	
	public void create(String spielname, String[] para, String data){
		getSave().set("name", spielname);
		String aus = "";
		for(String s : para){
			aus += ":"+s+"";
		}
		aus = aus.substring(1);
		getSave().set("paras", aus);
		getSave().set("version", ""+Util.getAppVersionCode());
		getSave().set("data", data);
		getSave().set("time", System.currentTimeMillis()+"");
		Spielsaves.saves.add(this);
	}
	
	public long getTime(){return (System.currentTimeMillis() - Long.parseLong(getSave().get("time")))/1000/60;}
	public void register(){GameUtil.registerServer(getSave().get("paras").split(":"), Spielhalle.get(getSave().get("name")), this);}
	public String getData(){return getSave().get("data");}
	public boolean istKompatibel(){return Util.getAppVersionCode() == Integer.valueOf(getSave().get("version"));}
	
	public String getDetail(){
		if(info == null){
			info = Spielhalle.get(getSave().get("name")).getInfo();
		}
		return info.getName()+" (vor "+getTime()+" min)";
	}
	
	public void remove() {
		super.remove();
		Spielsaves.saves.remove(this);
	}
}
