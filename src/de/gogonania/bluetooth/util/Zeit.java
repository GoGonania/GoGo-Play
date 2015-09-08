package de.gogonania.bluetooth.util;
import java.util.Date;

public class Zeit{
	private static String n(int z){
		return ""+(z > 9?"":"0")+""+z+"";
	}
	
	public static String minuten(){
		return n(new Date().getMinutes());
	}
	
	public static String stunden(){
		return ""+new Date().getHours();
	}
	
	public static String zeit(){
		return ""+stunden()+":"+minuten()+"";
	}
	
	public static String tag(){
		return "Sonntag Montag Dienstag Mittwoch Donnerstag Freitag Samstag".split(" ")[new Date().getDay()];
	}
}
