package de.gogonania.bluetooth.spielio;
import de.gogonania.bluetooth.io.GameUtil;

public abstract class SpielClient{
	private boolean d;
	public abstract void onPacket(Object o);
	
	public void daten(String d){
		this.d = true;
	}
	
	public boolean hatDaten(){return d;}
	public void send(Object o){GameUtil.game.send(o);}
}
