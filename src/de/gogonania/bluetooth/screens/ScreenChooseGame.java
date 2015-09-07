package de.gogonania.bluetooth.screens;
import de.gogonania.bluetooth.sparts.ScreenAktionenBase;
import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.spielio.Spielhalle;
import de.gogonania.bluetooth.spielio.Spiel;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.util.Formular;

public class ScreenChooseGame extends ScreenAktionenBase{
	private Formular form;
	
	public ScreenChooseGame(){}
	public ScreenChooseGame(Formular f){form = f;}
	
	public void open(){
		for(final Spiel s : Spielhalle.getSpiele()){
			add(s.getInfo().getName()+"\n\n("+s.getInfo().getPlayerText()+" Spieler)", new Runnable(){
					public void run(){
						Util.vib();
						GameUtil.registerServer(form.getValues(), s, null);
					}
			});
		}
	}
	
	public int getItemsInARow(){
		return 3;
	}

	public Szene getPreSzene(){
		return new ScreenMain();
	}

	public String getTitle(){
		return "Spiel auswählen";
	}
}
