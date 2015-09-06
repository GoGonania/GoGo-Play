package de.gogonania.bluetooth.objekte.overlays;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.util.Bild;
import de.gogonania.bluetooth.util.io.SaveItem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public abstract class Overlay extends Button implements Comparable<Overlay>{
	public abstract void update();
	public abstract boolean isShown();
	
	public void onDrag(){}
	
	public int compareTo(Overlay v){
		long l = v.last;
		if(last < l) return -1;
		if(last > l) return 1;
		return 0;
	}
	
	private Runnable r;
	private boolean f;
	public SaveItem save;
	public long last;
	
	public Overlay(Bild b, Runnable r){
		super("", 0, 0, Gdx.graphics.getHeight()/8F, Gdx.graphics.getHeight()/8F, b, Color.BLACK);
		this.r = r;
	}
	
	public void click(){
		r.run();
	}
	
	public void render(ShapeRenderer s, SpriteBatch batch){
		if((isHovered(Gdx.input.isTouched()) && Overlays.istGemeint(this)) || (f && Gdx.input.isTouched())){
			f = true;
			last = System.currentTimeMillis();
			setX(Util.getX()-getWidth()/2F);
			setY(Util.getY()-getHeight()/2F);
			Overlays.sort();
			onDrag();
		} else{
			f = false;
		}
		super.render(s, batch);
	}
	
	public float getScale(){return 1;}
	public void setX(float n){n = Math.max(0, n); n = Math.min(Gdx.graphics.getWidth()-getWidth(), n); super.setX(n);}
	public void setY(float n){n = Math.min(Gdx.graphics.getHeight()-getHeight(), n); super.setY(n);}
}
