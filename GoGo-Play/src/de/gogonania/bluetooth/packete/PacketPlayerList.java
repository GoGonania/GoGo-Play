package de.gogonania.bluetooth.packete;

import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.io.IPerson;

public class PacketPlayerList {
	public String[] player;
	
	public static PacketPlayerList create(){
		PacketPlayerList p = new PacketPlayerList();
		p.player = new String[GameUtil.game.server.personen.size()];
		int i = 0;
		for(IPerson pp : GameUtil.game.server.personen){
			p.player[i] = pp.getName();
			i++;
		}
		return p;
	}
}
