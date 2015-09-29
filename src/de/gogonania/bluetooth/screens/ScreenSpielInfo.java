package de.gogonania.bluetooth.screens;
import com.badlogic.gdx.Gdx;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.Game;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.sparts.ScreenBase;
import de.gogonania.bluetooth.util.GridText;

public class ScreenSpielInfo extends ScreenBase{
	private static Game game;
	
	public ScreenSpielInfo(){}
	public ScreenSpielInfo(Game g){game = g;}
	
	public Szene getPreSzene(){return new ScreenGefundeneSpiele();}
	public String getTitle(){return game.getName();}
	
	public void open(){
		GameUtil.registerGame(game);
		setAction("Ping testen", new Runnable(){
			public void run(){
				Util.vib();
				try{
					game.connect();
					game.testPing();
				} catch (Exception e){
					SzeneJoin.weiterleiten();
				}
			}
		});
		final Szene s = this;
		GridText g = new GridText(){
			public float getHeightMargin(){
				return Gdx.graphics.getHeight()/3F;
			}
			public Szene getSzene(){
				return s;
			}
		};
		
		if(game.hatBeschreibung()) g.add("Beschreibung", game.getBeschreibung());
		g.add("Passwortgeschützt", Util.getText(game.hatPasswort()));
		g.add("Im Spiel", Util.getText(game.isInGame()));
		g.add("Spiel", game.getSpiel().getInfo().getName());
		
		setBigButton("Verbinden", new Runnable(){
			public void run(){
				Util.vib();
				SzeneJoin.join(game);
			}
		});
	}
}
