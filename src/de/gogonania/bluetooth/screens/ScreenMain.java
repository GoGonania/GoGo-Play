package de.gogonania.bluetooth.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.Wifi;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.objekte.TextObjekt;
import de.gogonania.bluetooth.sparts.ScreenBase;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.Grid;
import de.gogonania.bluetooth.util.Listener;
import de.gogonania.bluetooth.spielio.save.Spielsaves;

public class ScreenMain extends ScreenBase{
	private Grid g = new Grid(1, 0.5F, 0.2F);
	public Szene getPreSzene(){return null;}
	public String getTitle(){return "Startseite";}
	
	public void open(){
		if(Spielsaves.hatSpielstände()) setAction("Spielstände verwalten", new ScreenSpielstände());
		setAction("Einstellungen", new ScreenEinstellungen());
		setAction("Extras & Infos", new ScreenExtras());
		
		setActionButtonText("Menü");
		
		setObjekt(new Button("Name ändern", getBarHeight()*4.9F, 0, getBarHeight()*2.4F, getBarHeight()/1.5F, Bilder.cgreen, Color.BLACK, fontlittle){
			public void click(){
				Util.vib();
				Fenster.prompt("Wie willst du denn heißen?", "Name ändern", Util.name, new Listener(){
					public void ready(String s){
						if(s != null){
							s = s.trim();
							if(!s.isEmpty()){
								Util.name = s;
								Util.refreshScreen();
							}
						}
					}
				});
			}
		});
		TextObjekt name = new TextObjekt("Eingeloggt als "+Util.name, 0, 0, getBarHeight()*5, getBarHeight()/1.5F, Bilder.bar, Color.BLACK, fontlittle);
		name.setActive(false);
		setObjekt(name);
		
		add("Spiel erstellen", new ScreenModeErstellen());
		add("Einem Spiel beitreten", Wifi.games.isEmpty()?new ScreenModeBeitreten():new ScreenGefundeneSpiele());
	}
	
	public void add(String text, final Szene s){
		Rectangle r = g.getRectangle();
		setObjekt(new Button(text, r.getX(), r.getY()-getBarHeight()*2, r.getWidth(), r.getHeight(), Bilder.cyellow, Color.BLACK, fontbig){
			public void click(){
				Util.vib();
				Util.setSzene(s);
			}
		});
	}
}
