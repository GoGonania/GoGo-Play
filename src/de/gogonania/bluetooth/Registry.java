package de.gogonania.bluetooth;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;

public class Registry{
	public final static int clicktimeout = 250;
	public final static float scrollspeed = 1.4F;
	public final static String downloadlink = "gogonania.de/download";
	public final static String zeichen = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZöäüÖÄÜ,.!?()/-ß1234567890=+:\"'*_|<>%&€";
	
	public final static Anim[] animations = new Anim[]{
		new Anim(){
			public int frames(){return 7;}
			public void up(OrthographicCamera cam){cam.zoom -= 0.05F;}
			public void down(OrthographicCamera cam){cam.zoom += 0.05F;}
		},
	    new Anim(){
	        public int frames(){return 18;}
	        public void up(OrthographicCamera cam){cam.position.y += Gdx.graphics.getHeight()/frames();}
	        public void down(OrthographicCamera cam){cam.position.y -= Gdx.graphics.getHeight()/frames();}
	    }
	};
	
	public static String remove(String s){
		char[] cs = s.toCharArray();
		for(char c : cs){
			if(c == ' ' || zeichen.contains(""+c)){} else{s = s.replace(""+c, "");}
		}
		return s.trim();
	}
}
