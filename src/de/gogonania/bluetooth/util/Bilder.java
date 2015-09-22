package de.gogonania.bluetooth.util;
import com.badlogic.gdx.graphics.Color;

public class Bilder{
	public static Bild background;
	public static Bild logo;
	public static Bild bar;
	
	public static Bild normal;
	public static Bild cred;
	public static Bild cgreen;
	public static Bild clgray;
	public static Bild cyellow;
	public static Bild cwhite;
	public static Bild cdgray;
	public static Bild corange;
	
	public static Bild cookie;
	public static Bild cookiegold;
	
	public static Bild panzer;
	public static Bild tilegras;
	public static Bild tilewasser;
	
	public static void init(){
		background = new Bild("background.jpg");
		logo = new Bild("logo.png");
		bar = new Bild("bar.jpg");
		
		normal = new Bild(Color.valueOf("3399ff"));
		cred = new Bild(Color.valueOf("dd3333"));
		cgreen = new Bild(Color.valueOf("33dd33"));
		cyellow = new Bild(Color.valueOf("ffff55"));
		clgray = new Bild(Color.valueOf("aaaaaa"));
		cdgray = new Bild(Color.DARK_GRAY);
		cwhite = new Bild(Color.WHITE);
		corange = new Bild(Color.valueOf("ffa500"));
		
		cookie = new Bild("cookie.png");
		cookiegold = new Bild("cookie_gold.png");
		
		panzer = new Bild("tank.png");
		tilegras = tile("gras");
		tilewasser = tile("wasser");
	}
	
	private static Bild tile(String name){
		return new Bild("tiles/"+name+".png");
	}
}
