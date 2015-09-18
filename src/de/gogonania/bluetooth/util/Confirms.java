package de.gogonania.bluetooth.util;
import java.util.ArrayList;

import de.gogonania.bluetooth.Spielstand;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.io.IPerson;
import de.gogonania.bluetooth.io.Person;
import de.gogonania.bluetooth.io.Wifi;
import de.gogonania.bluetooth.packete.PacketKick;
import de.gogonania.bluetooth.packete.PacketMessage;
import de.gogonania.bluetooth.screens.ScreenMain;
import de.gogonania.bluetooth.screens.ScreenSpielstände;
import de.gogonania.bluetooth.spielio.save.Spielsave;
import de.gogonania.bluetooth.spielio.save.Spielsaves;

public class Confirms{
	public static void closeGameClient(){
		c("das Spiel verlassen", new Runnable(){
			public void run(){
				Wifi.close();
				Util.setSzene(new ScreenMain());
			}
		});
	}
	
	public static void clearData(){
		c("alle Daten löschen", new Runnable(){
			public void run() {
				Fenster.progress(
					new Runnable(){
					    public void run() {
					    	Spielstand.clear();
					    }}, 
					new Runnable(){
						public void run() {
							Util.kill();
						}}, 
					"Daten werden gelöscht");
			}});
	}

	public static void kill(){
		c(Util.getAppName()+" beenden", new Runnable(){
			public void run(){
				Fenster.progress(new Runnable(){
					public void run(){
						Spielstand.save();
						Util.kill();
					}
				}, null, "Spiel wird gespeichert...");
			}
		});
	}
	
	public static void closeGameServer(){
		c("das Spiel beenden", new Runnable(){
			public void run(){
				GameUtil.game.server.close("Spiel wurde vom Besitzer beendet");
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
		Fenster.select("Welchen Spieler willst du entfernen?", pn, new SelectListener(){
				public void selected(int id){
					kickPlayer(GameUtil.game.server.getPerson(pn[id]));
				}
			});
	}
	
	public static void kickPlayer(final IPerson p){
		c(""+p.getName()+" entfernen", new Runnable(){
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
		c("den Spielstand: '"+s.getDetail()+"' löschen", new Runnable(){
			public void run() {
				s.remove();
				Util.notificationRed(s.getDetail()+" wurde gelöscht");
				if(Spielsaves.saves.isEmpty()){
					Util.setSzene(new ScreenMain());
				} else{
					Util.refreshScreen();
				}
			}});
	}
	
	public static void removeSpielstand(){
		if(!Spielsaves.hatSpielstände())
		c("alle Spielstände löschen", new Runnable(){
			public void run(){
				while(Spielsaves.hatSpielstände()){
					Util.random(Spielsaves.saves).remove();
				}
				Util.notificationRed("Alle Spielstände wurden gelöscht");
				if(Util.getSzene() instanceof ScreenSpielstände){Util.setSzene(new ScreenMain());}else{Util.refreshScreen();}
			}
		});
	}
	
	public static void saveGame(final Spielsave s){
		c("den Spielstand: '"+s.getDetail()+"' überschreiben", new Runnable(){
				public void run() {
					GameUtil.game.server.save(s);
				}});
	}
	
	private static void c(String m, final Runnable r){
		Fenster.confirm("Willst du "+m+"?", new Listener(){
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
