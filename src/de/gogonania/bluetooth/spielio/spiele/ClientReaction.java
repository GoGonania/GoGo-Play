package de.gogonania.bluetooth.spielio.spiele;
import de.gogonania.bluetooth.spielio.SpielClient;
import de.gogonania.bluetooth.spielio.spiele.reaction.ReactionGame;
import de.gogonania.bluetooth.spielio.spiele.reaction.PacketReactionStart;
import de.gogonania.bluetooth.spielio.spiele.reaction.PacketReactionEnd;
import de.gogonania.bluetooth.Util;

public class ClientReaction extends SpielClient{
	public ReactionGame game;
	public long time = -1;
	
	public void onPacket(Object o){
		if(o instanceof PacketReactionStart){
			game = ((PacketReactionStart) o).load();
		} else{
			if(o instanceof PacketReactionEnd){
				String won = ((PacketReactionEnd) o).name;
				game = null;
				time = System.currentTimeMillis();
				Util.refreshScreen();
				if(won != null){
					if(won.equals(Util.name)){
						Util.notificationGreen("Du hast gewonnen!");
					} else{
						Util.notification(""+won+" hat gewonnen!");
					}
				} else{
					Util.notification("Die Zeit ist abgelaufen");
				}
			}
		}
	}
}
