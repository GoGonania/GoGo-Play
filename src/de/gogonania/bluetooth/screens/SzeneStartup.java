package de.gogonania.bluetooth.screens;

import de.gogonania.bluetooth.Spielstand;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.objekte.overlays.Overlays;
import de.gogonania.bluetooth.sparts.SzeneLoading;
import de.gogonania.bluetooth.spielio.Spielhalle;
import de.gogonania.bluetooth.spielio.spiele.reaction.ReactionGames;

public class SzeneStartup extends SzeneLoading {
	private Thread thread;
	private boolean ready;
	
	public void loadAll(){
		Spielstand.load();
		Overlays.init();
		Spielhalle.init();
		ReactionGames.init();
	}
	
	public void update(){
		if(thread == null){
			thread = new Thread(new Runnable(){
					public void run(){
						loadAll();
						ready = true;
					}
				});
			thread.start();
		} else{
			if(ready){
				ready = false;
				Util.setSzene(new ScreenMain());
			}
		}
	}

	public String getText() {
		return Util.getAppName()+" wird geladen...";
	}
}
