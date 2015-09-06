package de.gogonania.bluetooth.screens;

import com.badlogic.gdx.Gdx;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.sparts.ScreenBase;
import de.gogonania.bluetooth.util.Formular;
public class ScreenModeErstellen extends ScreenBase {
	public Szene getPreSzene() {
		return new ScreenMain();
	}

	public String getTitle() {
		return "Spiel-Konfigurator";
	}
	
	public void open(){
		final Formular f = new Formular(){
			public float getHeightMargin(){
				return Gdx.graphics.getHeight()/3F;
			}
		};
		
		f.addInput("Spiel von "+Util.name+"", "Spielname", false, false);
		f.addInput("Besitzer: "+Util.name+"", "Beschreibung", true, false);
		f.addInput("", "Passwort", true, false);
		
		setBigButton("Spiel erstellen", Util.getRunnable(new ScreenChooseGame(f), true));
	}
}
