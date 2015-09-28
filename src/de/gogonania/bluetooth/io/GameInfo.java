package de.gogonania.bluetooth.io;
import java.util.ArrayList;

import de.gogonania.bluetooth.packete.PacketChanged;
import de.gogonania.bluetooth.util.GoGoMap;

public class GameInfo{
	private String[] last;
	private GoGoMap<String, PlayerInfo> players = new GoGoMap<String, PlayerInfo>();
	
	public void onPacket(String[] names){
		if(last != null){
			for(String n : last){
				boolean f = false;
				for(String nn : names){
					if(nn.equals(n)){
						f = true;
						break;
					}
				}
				if(!f){
					players.remove(n);
				}
			}
		}
		for(String name : names){
			PlayerInfo p = players.get(name);
			if(p == null){
				players.set(name, new PlayerInfo(name));
			}
		}
		last = names;
	}
	
	public void changedNotify(String p, String k, String v){changed(p, k, v); GameUtil.game.server.sendAll(new PacketChanged(p, k, v));}
	public void changed(String p, String k, String v){try{players.get(p).getInfo().set(k, v);}catch(Exception e){}}
	public ArrayList<PlayerInfo> getInfos(){return players.getValues();}
	public ArrayList<String> getKeys(){return players.getKeys();}
	public PlayerInfo getInfo(String player){return players.get(player);}
	public int getPlayerCount(){return players.size();}
}
