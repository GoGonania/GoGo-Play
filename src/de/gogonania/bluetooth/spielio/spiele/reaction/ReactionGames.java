package de.gogonania.bluetooth.spielio.spiele.reaction;
import java.util.ArrayList;
import de.gogonania.bluetooth.spielio.spiele.reaction.spiele.ReactionGameMath;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.spielio.spiele.reaction.spiele.ReactionGameColor;

public class ReactionGames{
	private static ArrayList<Class<? extends ReactionGame>> games = new ArrayList<Class<? extends ReactionGame>>();
	
	public static void init(){
		games.add(ReactionGameMath.class);
		games.add(ReactionGameColor.class);
	}
	
	public static ReactionGame random(){
		try{
			ReactionGame g = Util.random(games).newInstance();
			g.create();
			g.endtime = System.currentTimeMillis()+g.getTime();
			return g;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
