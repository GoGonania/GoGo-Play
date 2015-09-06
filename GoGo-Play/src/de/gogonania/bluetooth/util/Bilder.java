package de.gogonania.bluetooth.util;
import com.badlogic.gdx.graphics.Color;

public class Bilder{
	public static Bild background;
	public static Bild logo;
	public static Bild himmel;
	
	public static Bild normal;
	public static Bild cred;
	public static Bild cgreen;
	public static Bild clgray;
	public static Bild cyellow;
	public static Bild cwhite;
	public static Bild cdgray;
	
	public static Bild blockgras;
	public static Bild blockerde;
	public static Bild blockstein;
	
	public static Bild cookie;
	
	public static void init(){
		background = new Bild("background.jpg");
		logo = new Bild("logo.png");
		himmel = new Bild("himmel.jpg");
		
		normal = new Bild(Color.valueOf("3399ff"));
		cred = new Bild(Color.valueOf("dd3333"));
		cgreen = new Bild(Color.valueOf("33dd33"));
		cyellow = new Bild(Color.valueOf("ffff55"));
		clgray = new Bild(Color.valueOf("aaaaaa"));
		cdgray = new Bild(Color.DARK_GRAY);
		cwhite = new Bild(Color.WHITE);
		
		blockerde = block("erde");
		blockgras = block("gras");
		blockstein = block("stein");
		
		cookie = new Bild("cookie.png");
	}
	
	private static Bild block(String name){
		return new Bild("blocks/"+name+".png");
	}
}
