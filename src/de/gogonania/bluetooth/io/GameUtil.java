package de.gogonania.bluetooth.io;
import de.gogonania.bluetooth.spielio.Spiel;
import de.gogonania.bluetooth.spielio.save.Spielsave;
import de.gogonania.bluetooth.packete.PacketPause;
import de.gogonania.bluetooth.MainActivity;

public class GameUtil {
	public static Game game;
	
	public static void registerServer(String[] p, Spiel s, Spielsave save){
		Game g = new Game(new PersonIch(true));
		GameUtil.registerGame(g);
		g.server = new GameServer(p, s, save);
		g.server.doStuff();
	}
	
	public static boolean save(){
		if(game.server.gs.isSaveAble()){
			game.server.save();
			return true;
		}
		return false;
	}
	
	public static void send(Object o){game.send(o);}
	public static void notifyPause(){if(hatSpiel() && game.isJoined()){game.send(new PacketPause(MainActivity.isPaused()));} else{}}
	public static void registerGame(Game g){game = g;}
	public static boolean hatSpiel(){return game != null;}
	public static boolean isOwner(){return hatSpiel() && game.isOwner();}
}
