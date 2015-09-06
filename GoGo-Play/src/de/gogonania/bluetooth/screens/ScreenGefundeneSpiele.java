package de.gogonania.bluetooth.screens;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.Game;
import de.gogonania.bluetooth.io.Wifi;
import de.gogonania.bluetooth.sparts.ScreenAktionenBase;

public class ScreenGefundeneSpiele extends ScreenAktionenBase{
	public Szene getPreSzene() {
		return new ScreenMain();
	}

	public String getTitle() {
		return "Gefundene Spiele";
	}
	
	public void open(){
		setAction("Nochmal suchen", new ScreenModeBeitreten());
		for(final Game g : Wifi.games){
			add(""+g.getName()+"\n\nFür weitere Infos hier klicken", Util.getRunnable(new ScreenSpielInfo(g), true));
		}
	}

	public int getItemsInARow() {
		return 1;
	}
	
	public float getWidthHeight() {
		return 0.1F;
	}
}
