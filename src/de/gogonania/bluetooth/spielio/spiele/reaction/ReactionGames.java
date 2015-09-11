package de.gogonania.bluetooth.spielio.spiele.reaction;
import java.util.ArrayList;
import de.gogonania.bluetooth.spielio.spiele.reaction.spiele.ReactionGameMath;
import de.gogonania.bluetooth.spielio.spiele.reaction.spiele.ReactionGameNames;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.spielio.spiele.reaction.spiele.ReactionGameColor;

public class ReactionGames{
	private static ArrayList<Class<? extends ReactionGame>> games = new ArrayList<Class<? extends ReactionGame>>();
	
	public static void init(){
		games.add(ReactionGameMath.class);
		games.add(ReactionGameColor.class);
		games.add(ReactionGameNames.class);
	}
	
	public static ReactionGame random(){
		try{
			ReactionGame g = Util.random(games).newInstance();
			if(!Util.chance(g.getChance())) return random();
			g.create();
			return g;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
