package de.gogonania.bluetooth.objekte.dialoge;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.objekte.Objekt;
import de.gogonania.bluetooth.objekte.TextObjekt;
import de.gogonania.bluetooth.util.Bilder;

public class Dialog extends TextObjekt{
	private ArrayList<Objekt> objekte = new ArrayList<Objekt>();
	private boolean c;
	
	public Dialog(String titletext, boolean cancelable){
		super("", getDX(), getDY(), getDWidth(), getDHeight(), Bilder.clgray, Color.BLACK);	
		c = cancelable;
		addObjekt(new TextObjekt(titletext, getDX()+getDWidth()*0.05F, getDY()+getDHeight()*0.9F, getDWidth()*0.9F, getDHeight()/3F, Bilder.clgray, Color.BLACK));
	}
	
	private Objekt background = new Objekt(Gdx.graphics.getWidth()/-2F, Gdx.graphics.getHeight()/-2F, Gdx.graphics.getWidth()*2, Gdx.graphics.getHeight()*2, Bilder.cdgray){
		public float getAlpha(){
			return 0.6F;
		}
	};
	
	public boolean onClick(){
		for(Objekt o : objekte){
			if(o.onClick()) return true;
		}
		cancel();
		return true;
	}
	
	public void render(){
		background.render();
		super.render();
		for(Objekt o : objekte){
			o.render();
		}
	}
	
	public void cancel(){if(c){hide(); Util.vib();} else{}}
	public void addObjekt(Objekt o){objekte.add(o); o.setDialog();}
	public void hide(){Szene.dialog = null;}
	public void show(){Szene.dialog = this;}
	public static float getDY(){return (Gdx.graphics.getHeight()-getDHeight())/2F;}
	public static float getDX(){return (Gdx.graphics.getWidth()-getDWidth())/2F;}
	public static float getDHeight(){return Gdx.graphics.getHeight()/3F;}
	public static float getDWidth(){return Gdx.graphics.getWidth()/2F;}
}
