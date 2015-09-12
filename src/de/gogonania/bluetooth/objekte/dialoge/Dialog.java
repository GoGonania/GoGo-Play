package de.gogonania.bluetooth.objekte.dialoge;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.objekte.Objekt;
import de.gogonania.bluetooth.util.Bilder;

public class Dialog extends Objekt{
	private ArrayList<Objekt> objekte = new ArrayList<Objekt>();
	
	public Dialog(){
		super(getDX(), getDY(), getDWidth(), getDHeight(), Bilder.clgray);	
	}
	
	private Objekt background = new Objekt(Gdx.graphics.getWidth()/-2F, Gdx.graphics.getHeight()/-2F, Gdx.graphics.getWidth()*2, Gdx.graphics.getHeight()*2, Bilder.cdgray){
		public float getAlpha(){
			return 0.5F;
		}
	};
	
	public boolean onClick(){
		for(Objekt o : objekte){
			if(o.onClick()) break;
		}
		return true;
	}
	
	public void render(){
		background.render();
		super.render();
		for(Objekt o : objekte){
			o.render();
		}
	}
	
	public void addObjekt(Objekt o){objekte.add(o);}
	public void hide(){Szene.dialog = null;}
	public void show(){Szene.dialog = this;}
	public static float getDY(){return (Gdx.graphics.getHeight()-getDHeight())/2F;}
	public static float getDX(){return (Gdx.graphics.getWidth()-getDWidth())/2F;}
	public static float getDHeight(){return Gdx.graphics.getHeight()/3F;}
	public static float getDWidth(){return Gdx.graphics.getWidth()/2F;}
}
