package de.gogonania.bluetooth.packete;
import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;

public class PacketScreen{
	private String screensclass;
	
	public static PacketScreen create(Class<? extends Szene> c){
		PacketScreen s = new PacketScreen();
		s.screensclass = c.getCanonicalName();
		return s;
	}
	
	public void show(){
		try{Util.setSzene((Szene) Class.forName(screensclass).newInstance());} catch (Exception e){}
	}
}
