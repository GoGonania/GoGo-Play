package de.gogonania.bluetooth.spielio.spiele.gogocraft;
import de.gogonania.bluetooth.util.Bild;
import de.gogonania.bluetooth.util.Bilder;

public enum Material{
	Himmel(null), Erde(Bilder.blockerde), Gras(Bilder.blockgras), Stein(Bilder.blockstein);
	
	private Bild b;
	
	Material(Bild b){
		this.b = b;
	}
	
	public Bild getBild(){return b;}
}
