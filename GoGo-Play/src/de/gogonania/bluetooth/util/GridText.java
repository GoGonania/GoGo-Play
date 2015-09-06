package de.gogonania.bluetooth.util;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.objekte.Text;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GridText extends Grid{
	private boolean left;
	
	public Szene getSzene(){
		return Util.getSzene();
	}
	
	public float getHeight(){
		return getFont().getCapHeight()*2F;
	}
	
	public float getVerteilung(){
		return 0.3F;
	}
	
	public BitmapFont getFont(){
		return Szene.font;
	}
	
	public GridText(){
		super(2, 0F, 0.07F);
	}
	
	public void add(String text){
		add(text, Color.WHITE);
	}
	
	public void add(String text, Color c){
		left = !left;
		Rectangle r = getRectangle();
		r.setWidth(r.getWidth()*(left?1-getVerteilung():1+getVerteilung()));
		if(!left) r.setX(getWidth()-r.getWidth());
		getSzene().setObjekt(new Text(text, r.getX(), r.getY(), r.getWidth(), c, getFont()));
	}
	
	public void add(String text, String value, Color c){
		add(text+":", c);
		add(value, c);
	}
	
	public void add(String text, String value){
		add(text+":");
		add(value);
	}
	
	public void add(){
		skip(2);
	}
}
