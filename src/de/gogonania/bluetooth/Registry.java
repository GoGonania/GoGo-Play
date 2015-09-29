package de.gogonania.bluetooth;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;

public class Registry{
	public final static int clicktimeout = 250;
	public final static float scrollspeed = 1.4F;
	public final static String downloadlink = "gogonania.de/download";
	public final static String zeichen = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZöäüÖÄÜ,.!?()/-ß1234567890=+:\"'*_|<>%&€";
	
	public final static Anim[] animations;
	
	static{
		animations = new Anim[]{
			new Anim(){
				public int frames(){return 8;}
				public void anim(OrthographicCamera cam, int step){
					cam.zoom += 0.06*(float)step;
					cam.position.add((float)Gdx.graphics.getWidth()/(float)frames(), 0, 0);
				}
			},
			new Anim(){
				public int frames(){return 15;}
				public void anim(OrthographicCamera cam, int step){
					cam.position.add(0, (float)Gdx.graphics.getHeight()/(float)frames(), 0);
				}
			}
		};
	}
	
	public static String remove(String s){
		char[] cs = s.toCharArray();
		for(char c : cs){
			if(c == ' ' || zeichen.contains(""+c)){} else{s = s.replace(""+c, "");}
		}
		return s.trim();
	}
}
