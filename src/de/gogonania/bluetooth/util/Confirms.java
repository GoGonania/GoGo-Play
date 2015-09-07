package de.gogonania.bluetooth.util;
import de.gogonania.bluetooth.io.Wifi;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.screens.ScreenMain;
import de.gogonania.bluetooth.spielio.save.Spielsave;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.io.IPerson;
import de.gogonania.bluetooth.packete.PacketKick;
import java.util.ArrayList;
import de.gogonania.bluetooth.packete.PacketMessage;
import de.gogonania.bluetooth.io.Person;

public class Confirms{
	public static void closeGameClient(){
		c("das", "Spiel verlassen", new Runnable(){
			public void run(){
				Wifi.close();
				Util.setSzene(new ScreenMain());
			}
		});
	}
	
	public static void closeGameServer(){
		c("das", "Spiel beenden", new Runnable(){
			public void run(){
				GameUtil.game.server.close();
			}
		});
	}
	
	public static void sendMessage(){
		Fenster.prompt("Was willst du schreiben?", "Nachricht senden", new Listener(){
				public void ready(String s) {
					if(s != null && !s.trim().isEmpty()){
						GameUtil.game.send(new PacketMessage(s.trim()));
					}
				}});
	}
	
	public static void kickPlayer(){
		ArrayList<IPerson> ps = GameUtil.game.server.personen;
		final String[] pn = new String[ps.size()-1];
		for(int i = 1; i < ps.size(); i++){
			pn[i - 1] = ps.get(i).getName();
		}
		Fenster.select("Welchen Spieler willst du entfernen?", pn, false, new SelectListener(){
				public void selected(int id){
					kickPlayer(GameUtil.game.server.getPerson(pn[id]));
				}
			});
	}
	
	public static void kickPlayer(final IPerson p){
		c("", p.getName()+" entfernen", new Runnable(){
			public void run(){
				GameUtil.game.server.getWarteschlange().send(PacketKick.create("Du wurdest vom Besitzer entfernt"), p);
				GameUtil.game.server.remove((Person) p);
			}
		});
	}
	
	public static void closeGame(){
		if(GameUtil.isOwner()){
			Confirms.closeGameServer();
		} else{
			Confirms.closeGameClient();
		}
	}
	
	public static void removeSpielstand(final Spielsave s){
		c("diesen Spielstand:", s.getDetail()+" löschen", new Runnable(){
			public void run() {
				s.remove();
				Util.refreshScreen();
			}});
	}
	
	private static void c(String m, String t, final Runnable r){
		if(!m.isEmpty()) m += " ";
		Fenster.confirm("Willst du wirklich "+m+""+t+"?", t+"?", new Listener(){
			public void ready(String s){
				if(s != null){
					try{
					    r.run();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		});
	}
}
