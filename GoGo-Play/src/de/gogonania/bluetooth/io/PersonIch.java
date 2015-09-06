package de.gogonania.bluetooth.io;

import de.gogonania.bluetooth.Util;

public class PersonIch extends IPerson{
	private boolean s;
	
	public PersonIch(boolean server){
		s = server;
	}
	
	public void send(Object o) {
		if(s){
			GameUtil.game.server.onPacket(null, new PersonIch(false), o);
		} else{
			GameUtil.game.onPacket(o);
		}
	}
	
	public String getName(){
		return Util.name;
	}
}
