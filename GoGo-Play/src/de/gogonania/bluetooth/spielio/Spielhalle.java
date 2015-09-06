package de.gogonania.bluetooth.spielio;
import java.util.ArrayList;
import java.util.Collections;

public class Spielhalle{
	private static ArrayList<Spiel> spiele = new ArrayList<Spiel>();
	
	public static void init(){
		spiele.clear();
		
		add("GoGoCraft");
		add("GoGoKlicker");
		
		Collections.sort(spiele);
	}
	
	public static Spiel get(String name){for(Spiel s : spiele){if(s.getTyp().equals(name)){return s;} else{}} return null;}
	public static Object instance(String name){try{return Class.forName("de.gogonania.bluetooth.spielio.spiele."+name+"").newInstance();}catch(Exception e){e.printStackTrace();} return null;}
	private static void add(String name){spiele.add(new Spiel(name));}
	public static ArrayList<Spiel> getSpiele(){return spiele;}
}
