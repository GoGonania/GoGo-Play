package de.gogonania.bluetooth;

import de.gogonania.bluetooth.spielio.save.Spielsave;
import de.gogonania.bluetooth.spielio.save.Spielsaves;
import de.gogonania.bluetooth.util.io.GoGoFile;
import de.gogonania.bluetooth.util.io.SaveItem;
import de.gogonania.bluetooth.util.io.SaveManager;

public class Spielstand{
	private static SaveItem save;
	public static SaveManager saveSpiele;
	
	public static boolean save(){
		if(save == null) return false;
		
		save.set("s", ""+Util.scroll);
		save.set("a", ""+Util.a);
		save.set("n", Util.name);
		save.set("v", ""+Util.vib);
		save.set("b", ""+Util.b);
		
		save.save();
		
		return true;
	}
	
	public static void load(){
		SaveManager general = new SaveManager("0");
		save = general.get(0);
		if(save == null) save = general.create();
		save.disableAutoSave();
		
		saveSpiele = new SaveManager("1");
		
		Util.scroll = Float.parseFloat(save.get("s", ""+Registry.scrollspeed));
		Util.a = Boolean.parseBoolean(save.get("a", true+""));
		Util.name = save.get("n", "User"+Util.random(10000, 99999)+"");
		Util.vib = Boolean.parseBoolean(save.get("v", true+""));
		Util.b = Boolean.parseBoolean(save.get("b", true+""));
		
		for(SaveItem s: saveSpiele.list()){
			Spielsaves.saves.add(new Spielsave(s));
		}
		
		Util.ping("Start", true);
	}
	
	public static void clear(){
		GoGoFile.fromFile(save.getParentFile().getParentFile()).delete();
	}
}
