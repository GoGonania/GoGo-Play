package de.gogonania.bluetooth.util;

public class Notification{
	public String text;
	public Bild c;
	public long z;
	
	public Bild getBild(Bild b){if(c == null){return b;} else{return c;}}
}
