package de.gogonania.bluetooth;

import android.content.Context;
import android.content.SharedPreferences;
import de.gogonania.bluetooth.spielio.save.Spielsave;
import de.gogonania.bluetooth.spielio.save.Spielsaves;
import de.gogonania.bluetooth.util.io.SaveItem;
import de.gogonania.bluetooth.util.io.SaveManager;

public class Spielstand{
	public static SaveManager saveSpiele;
	
	static SharedPreferences sp;
	static SharedPreferences.Editor spe;
	
	public static boolean save(){
		if(spe == null) return false;
		
		spe.putFloat("scroll", Util.scroll);
		spe.putBoolean("a", Util.a);
		spe.putString("name", Util.name);
		spe.putBoolean("vib", Util.vib);
		spe.putBoolean("b", Util.b);
		
		spe.commit();
		
		return true;
	}
	
	public static void load(){
		sp = MainActivity.getThis().getSharedPreferences("GoGoSpeicher", Context.MODE_PRIVATE);
		spe = sp.edit();
		
		Util.scroll = sp.getFloat("scroll", Registry.scrollspeed);
		Util.a = sp.getBoolean("a", true);
		Util.name = sp.getString("name", "User"+Util.random(10000, 99999)+"");
		Util.vib = sp.getBoolean("vib", true);
		Util.b = sp.getBoolean("b", true);
		
		saveSpiele = new SaveManager("1");
		
		for(SaveItem s: saveSpiele.list()){
			Spielsaves.saves.add(new Spielsave(s));
		}
		
		Util.ping("Start", true);
	}
}
