package de.gogonania.bluetooth.spielio.spiele;
import de.gogonania.bluetooth.spielio.SpielServer;
import de.gogonania.bluetooth.io.IPerson;
import de.gogonania.bluetooth.spielio.spiele.reaction.ReactionGame;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.spielio.spiele.reaction.ReactionGames;
import de.gogonania.bluetooth.spielio.spiele.reaction.PacketReactionStart;
import de.gogonania.bluetooth.spielio.spiele.reaction.PacketReactionEnd;
import de.gogonania.bluetooth.spielio.spiele.reaction.PacketReactionRight;
import de.gogonania.bluetooth.MainActivity;
import de.gogonania.bluetooth.io.GameUtil;

public class ServerReaction extends SpielServer{
	private ReactionGame g;
	public static final int timeout = 3000;
	
	public void onPacket(IPerson p, Object o){
		if(o instanceof PacketReactionRight){
			if(g != null){
				getServer().getInfo().getInfo(p.getName()).addPoints(1);
				closeGame(p.getName());
			}
		}
	}

	public void startThread(){
		while(isRunning()){
			if(!MainActivity.isPaused()){
				if(g == null){
					Util.sleep(timeout);
					if(GameUtil.hatSpiel()){
						g = ReactionGames.random();
						getServer().sendAll(new PacketReactionStart(g));
					}
				} else{
					if(g.isTimeout()){
						closeGame(null);
					}
				}
			}
			Util.sleep(20);
		}
	}
	
	public void closeGame(String won){
		getServer().sendAll(new PacketReactionEnd(won));
		g = null;
	}
}
