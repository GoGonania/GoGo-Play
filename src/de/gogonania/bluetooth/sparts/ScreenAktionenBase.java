package de.gogonania.bluetooth.sparts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.util.Bild;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.Grid;
import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;

public abstract class ScreenAktionenBase extends ScreenBase {
	Grid g = new Grid(getItemsInARow(), 0.1F, getWidthHeight());
	
	public abstract int getItemsInARow();
	public float getWidthHeight(){return 0.45F;}
	
	public void add(String text, Bild c, final Runnable r){
		Rectangle rr = g.getRectangle();
	    setObjekt(new Button(text, rr.getX(), rr.getY()-getBarHeight()*2, rr.getWidth(), rr.getHeight(), c, Color.BLACK){
			public void click(){
				r.run();
			}
		});
	}
	
	public void add(String text, Runnable r){
		add(text, Bilder.normal, r);
	}
	
	public void add(String text, Szene s){
		add(text, Util.getRunnable(s, true));
	}
	
	public void add(ScreenBase s){
		add(s.getTitle(), s);
	}
	
	public void skip(int s){
		g.skip(s);
	}
}
