package de.gogonania.bluetooth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.io.PlayerInfo;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.objekte.Text;
import de.gogonania.bluetooth.sparts.ScreenBase;
import de.gogonania.bluetooth.util.Confirms;

public class ScreenLobby extends ScreenBase{
	private Text t;
	private Button start;
	
	public Szene getPreSzene() {
		return null;
	}
	
	public void open(){
		t = new Text("", 0, (Gdx.graphics.getHeight()-getBarHeight())/2F, Gdx.graphics.getWidth(), Color.WHITE);
		setObjekt(t);
		setAction("Nachricht senden", new Runnable(){
			public void run(){
				Confirms.sendMessage();
			}
		});
		if(GameUtil.isOwner()){
			setAction("Spieler entfernen", new Runnable(){
				public void run(){
					Confirms.kickPlayer();
				}
			});
			setAction("Spiel beenden", new Runnable(){
			   public void run(){
				  Confirms.closeGameServer();
			   }
			});
		} else{
			setAction("Ping testen", new Runnable(){
				public void run(){
					GameUtil.game.testPing();
				}
			});
			setAction("Spiel verlassen", new Runnable(){
				public void run() {
					Confirms.closeGameClient();
				}});
		}
		setActionButtonText("Aktionen");
		
		start = setBigButton("Spiel starten", new Runnable(){
			public void run(){
				Util.vib();
				GameUtil.game.server.startGame();
			}
		});
	}
	
	public void onBack(){
		Confirms.closeGame();
	}
	
	public void update(){
		if(!GameUtil.hatSpiel()) return;
		start.setHide(!GameUtil.isOwner()?true:!GameUtil.game.getSpiel().getInfo().darfSpielStarten());
		String aus = "";
		for(PlayerInfo p : GameUtil.game.getInfo().getInfos()){
			aus += "\n"+p.getName()+""+(p.isPaused()?" (Pausiert)":"")+"";
		}
		t.setText(""+GameUtil.game.getInfo().getPlayerCount()+" Spieler:\n"+aus+"");
	}

	public String getTitle() {
		return "Lobby von '"+GameUtil.game.getName()+"'";
	}
}
