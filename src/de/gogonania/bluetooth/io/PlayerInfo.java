package de.gogonania.bluetooth.io;
import de.gogonania.bluetooth.util.Info;

public class PlayerInfo{
	private Info<String, String> infos = new Info<String, String>();
	private String name;
	
	public PlayerInfo(String n){
		name = n;
	}
	
	public boolean isBereit(){return infos.getBoolean("ready", false);}
	public String getName(){return name;}
	public Info<String, String> getInfo(){return infos;}
	public boolean isPaused(){return infos.getBoolean("paused", false);}
}
