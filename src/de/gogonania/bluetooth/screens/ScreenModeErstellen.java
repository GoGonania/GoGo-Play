package de.gogonania.bluetooth.screens;

import com.badlogic.gdx.Gdx;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.sparts.ScreenBase;
import de.gogonania.bluetooth.util.Formular;
import de.gogonania.bluetooth.spielio.save.Spielsaves;
public class ScreenModeErstellen extends ScreenBase {
	public Szene getPreSzene() {
		return new ScreenMain();
	}

	public String getTitle() {
		return "Spiel-Konfigurator";
	}
	
	public void open(){
		if(Spielsaves.hatSpielstände()) setAction("Spiel laden", new ScreenSpielstände());
		
		final Formular f = new Formular(){
			public float getHeightMargin(){
				return Gdx.graphics.getHeight()/3F;
			}
		};
		
		f.addInput("Spiel von "+Util.name+"", "Spielname", false);
		f.addInput("Besitzer: "+Util.name+"", "Beschreibung", true);
		f.addInput("", "Passwort", true);
		
		setBigButton("Spiel erstellen", Util.getRunnable(new ScreenChooseGame(f), true));
	}
}
