package de.gogonania.bluetooth.screens;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.Game;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.io.Wifi;
import de.gogonania.bluetooth.packete.PacketJoin;
import de.gogonania.bluetooth.sparts.SzeneLoading;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.Listener;

public class SzeneJoin extends SzeneLoading{
	private boolean j;
	
	public String getText() {
		return "Verbinde zu "+GameUtil.game.getName()+"...";
	}
	
	public void cancel(){
		Wifi.close();
		Util.setSzene(new ScreenMain());
	}
	
	public void open(){
		if(GameUtil.game.hatPasswort()){
			Fenster.prompt("Du benötigst ein Passwort um zum Spiel '" + GameUtil.game.getName() + "' zu verbinden", "Passworteingabe", new Listener(){
					public void ready(String s){
						if(s == null){
							cancel();
						} else{
							s = s.trim();
							if(s.isEmpty()){
								cancel();
							} else{
								send(s);
							}
						}
					}
				});
		} else{
			send("");
		}
	}
	
	public void update(){
		if(GameUtil.hatSpiel() && !j && GameUtil.game.isJoined()){
			setText("Verbunden!");
			if(!Util.isSwitching()){
				j = true;
				if(GameUtil.game.isInGame()){
					Util.setSzene(new SzeneGameLoading());
				} else{
					Util.setSzene(new ScreenLobby());
				}
			}
		}
	}
	
	public void send(String p){
		GameUtil.game.send(new PacketJoin(Util.name, p));
	}
	
	public void onBack(){
		cancel();
	}
	
	public static void join(Game g){
		try{
			g.connect();
			Util.setSzene(new SzeneJoin());
		}catch(Exception e){
			weiterleiten();
		}
	}
	
	public static void weiterleiten(){
		Wifi.close();
		Util.notificationRed("Dieses Spiel ist nicht mehr erreichbar");
		if(Wifi.games.isEmpty()){
			Util.setSzene(new ScreenMain());
		} else{
			Util.setSzene(new ScreenGefundeneSpiele());
		}
	}
}
