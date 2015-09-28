package de.gogonania.bluetooth.io;
import de.gogonania.bluetooth.packete.PacketChanged;
import de.gogonania.bluetooth.util.GoGoMap;

public class PlayerInfo{
	private GoGoMap<String, String> infos = new GoGoMap<String, String>();
	private String name;
	
	public PlayerInfo(String n){
		name = n;
	}
	
	public int getPoints(){return Integer.parseInt(infos.get("p", "0"));}
	public void setPoints(int points){infos.set("p", ""+points); GameUtil.game.server.sendAll(new PacketChanged(name, "p", ""+points));}
	public void addPoints(int points){setPoints(getPoints()+points);}
	public boolean isBereit(){return infos.getBoolean("ready", false);}
	public String getName(){return name;}
	public GoGoMap<String, String> getInfo(){return infos;}
	public boolean isPaused(){return infos.getBoolean("paused", false);}
}
