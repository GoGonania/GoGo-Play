package de.gogonania.bluetooth.objekte;
import com.badlogic.gdx.Gdx;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.util.Bild;

public class Wait extends Objekt{
	private static int r = 360;
	private static Thread t;
	
	public static void init(){
		if(t != null) return;
		t = new Thread(new Runnable(){
			public void run(){
				while(true){
					Util.sleep(20);
					r -= 3;
					if(r <= 0) r = 360;
				}
			}
		});
		t.start();
	}
	
	public Wait(){
		this(Gdx.graphics.getWidth()/100F, Gdx.graphics.getWidth()/100F, Gdx.graphics.getWidth()/12F);
	}
	
	public Wait(float x, float y, float size){
		super(x, y, size, size, new Bild("wait.png"));
	}
	
	public int getRotation() {
		return r;
	}
}
