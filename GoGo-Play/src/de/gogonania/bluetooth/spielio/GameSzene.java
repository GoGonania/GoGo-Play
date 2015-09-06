package de.gogonania.bluetooth.spielio;
import com.badlogic.gdx.graphics.OrthographicCamera;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.util.Bild;
import de.gogonania.bluetooth.util.Confirms;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.SelectListener;

public class GameSzene <T> extends Szene{
	public GameSzene(Bild b){
		super(b);
	}

	public void render(OrthographicCamera cam){
		try{
			super.onRender(cam);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void onBack(){
		Fenster.select("Was willst du machen?", !GameUtil.isOwner() ?new String[]{"Nachricht senden", "Ping testen", "Spiel verlassen"}: new String[]{"Nachricht senden", "Spieler entfernen", "Spiel beenden"}, false, new SelectListener(){
				public void selected(int id){
					switch(id){
						case 0:
							Confirms.sendMessage();
						break;
						case 1:
							if(GameUtil.isOwner()){
								Confirms.kickPlayer();
							} else{
								GameUtil.game.testPing();
							}
						break;
						case 2:
							Confirms.closeGame();
						break;
					}
				}
			});
	}
	
	public T getClient(){return (T) GameUtil.game.getClient();}
}
