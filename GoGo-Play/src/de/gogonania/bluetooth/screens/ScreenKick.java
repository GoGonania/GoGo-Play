package de.gogonania.bluetooth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import de.gogonania.bluetooth.Anim;
import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.io.Wifi;
import de.gogonania.bluetooth.objekte.Text;
import de.gogonania.bluetooth.util.Bilder;

public class ScreenKick extends Szene{
	private static String text;
	
	public ScreenKick() {
		super(Bilder.background);
	}
	
	public void open(){
		setBigButton("Zurück zur Startseite", Util.getRunnable(new ScreenMain(), true));
		setObjekt(new Text(text, 0, Gdx.graphics.getHeight()*0.7F, Gdx.graphics.getWidth(), Color.WHITE, fontbig));
	}

	public Anim getOpenAnimation(){
		return null;
	}
	
	public Anim getCloseAnimation(){
		return null;
	}
	
	public static void show(String grund){
		ScreenKick k = new ScreenKick();
		text = "Du wurdest von '"+GameUtil.game.getName()+"' ausgeschlossen!\n\nGrund:\n"+grund;
		Wifi.close();
		Util.vibBig();
		Util.setSzene(k);
	}
}
