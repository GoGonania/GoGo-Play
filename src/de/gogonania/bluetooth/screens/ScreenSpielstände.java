package de.gogonania.bluetooth.screens;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.sparts.ScreenAktionenBase;
import de.gogonania.bluetooth.spielio.save.Spielsave;
import de.gogonania.bluetooth.spielio.save.Spielsaves;
import de.gogonania.bluetooth.util.Confirms;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.SelectListener;

public class ScreenSpielstände extends ScreenAktionenBase{
	public int getItemsInARow() {
		return 2;
	}

	public Szene getPreSzene() {
		return new ScreenMain();
	}
	
	public float getWidthHeight() {
		return 0.2F;
	}

	public String getTitle() {
		return "Spielstände verwalten";
	}
	
	public void open(){
		for(final Spielsave s : Spielsaves.saves){
			add(s.getDetail(), new Runnable(){
				public void run(){
					Fenster.select(s.getDetail(), new String[]{"Spielstand löschen", "Spielstand laden"}, false, new SelectListener() {
						public void selected(int id) {
							switch(id){
							case 0:
								Confirms.removeSpielstand(s);
							break;
							case 1:
								s.register();
							break;
							}
						}
					});
				}
			});
		}
	}
}
