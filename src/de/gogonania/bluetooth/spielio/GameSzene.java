package de.gogonania.bluetooth.spielio;
import com.badlogic.gdx.graphics.OrthographicCamera;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.util.Bild;
import de.gogonania.bluetooth.util.Confirms;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.SelectListener;

public class GameSzene <T> extends Szene{
	public GameSzene(Bild b){
		super(b);
	}

	public void onRender(){
		try{
			super.onRender();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void onBack(){
		Fenster.select("Was willst du machen?", !GameUtil.isOwner()?new String[]{"Nachricht senden", "Ping testen", "Spiel verlassen"}: new String[]{"Spiel speichern", "Nachricht senden", "Spieler entfernen", "Spiel beenden"}, new SelectListener(){
				public void selected(int id){
					if(GameUtil.isOwner()){
						switch(id){
							case 0:
								if(!GameUtil.save()) Util.notificationRed("Dieses Spiel kann nicht gespeichert werden");
								break;
							case 1:
								Confirms.sendMessage();
								break;
							case 2:
								Confirms.kickPlayer();
								break;
							case 3:
								Confirms.closeGame();
								break;
						}
					} else{
						switch(id){
							case 0:
								Confirms.sendMessage();
								break;
							case 1:
								GameUtil.game.testPing();
								break;
							case 2:
								Confirms.closeGame();
								break;
						}
					}
				}
			});
	}
	
	public T getClient(){return (T) GameUtil.game.getClient();}
}
