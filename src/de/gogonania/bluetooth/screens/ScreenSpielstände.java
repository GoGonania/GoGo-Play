package de.gogonania.bluetooth.screens;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.sparts.ScreenAktionenBase;
import de.gogonania.bluetooth.spielio.save.Spielsave;
import de.gogonania.bluetooth.spielio.save.Spielsaves;
import de.gogonania.bluetooth.util.Confirms;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.SelectListener;
import de.gogonania.bluetooth.Anim;
import de.gogonania.bluetooth.Registry;
import de.gogonania.bluetooth.Util;

public class ScreenSpielstände extends ScreenAktionenBase{
	public int getItemsInARow() {
		return 2;
	}

	public Szene getPreSzene() {
		return new ScreenMain();
	}
	
	public Anim getOpenAnimation(){
		return Registry.animations[1];
	}

	public Anim getCloseAnimation(){
		return Registry.animations[1];
	}
	
	public float getWidthHeight() {
		return 0.2F;
	}

	public String getTitle() {
		return "Spielstände verwalten";
	}
	
	public void open(){
		setAction("Alle löschen", new Runnable(){
				public void run(){
					Util.vib();
					Confirms.removeSpielstand();
				}
			});
			
		for(final Spielsave s : Spielsaves.saves){
			add(s.getName()+"\n"+s.getTime(), new Runnable(){
				public void run(){
					Util.vib();
					Fenster.select(s.getDetail(), new String[]{"Spielstand laden", "Spielstand löschen"}, false, new SelectListener() {
						public void selected(int id) {
							switch(id){
							case 0:
								s.register();
							break;
							case 1:
								Confirms.removeSpielstand(s);
							break;
							}
						}
					});
				}
			});
		}
	}
}
