package de.gogonania.bluetooth.sparts;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.gdx.AnimatedBackground;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.objekte.Logo;
import de.gogonania.bluetooth.objekte.TextObjekt;
import de.gogonania.bluetooth.util.Bild;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.Confirms;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.SelectListener;

public abstract class ScreenBase extends Szene implements AnimatedBackground{
	public abstract Szene getPreSzene();
	public abstract String getTitle();
	
	private Button action;
	private ArrayList<String> actiontext = new ArrayList<String>();
	private ArrayList<Runnable> actions = new ArrayList<Runnable>();
	
	public static final float getBarHeight(){return Gdx.graphics.getHeight()/11F;}
	private static final float getBarItemsMargin(){return getBarHeight()/12F;}
	
	public float getSize() {
		return Util.chance(15)?(Util.chance(15)?4:2):1;
	}
	
	public Bild getBild() {
		return Bilder.logo;
	}
	
	public boolean ground() {
		return true;
	}
	
	public boolean rect() {
		return true;
	}
	
	public boolean chance(){
		return Util.chance(2) && Util.chance(60);
	}

	public ScreenBase() {
		super(Bilder.background);
	}
	
	public void onOpen(){
		float actionwidth = getBarHeight()*4F;
		action = new Button("", Gdx.graphics.getWidth()-actionwidth-getBarItemsMargin(), Gdx.graphics.getHeight()-getBarHeight()+getBarItemsMargin(), actionwidth, getBarHeight()-2*getBarItemsMargin(), Bilder.normal, Color.BLACK){
			public void click(){
				Util.vib();
				Fenster.select(action.getText(), actiontext.toArray(), new SelectListener() {
						public void selected(int id) {
							actions.get(id).run();
						}
				   });
			}
		};
		action.setHide(true);
		Logo logo = new Logo(0, Gdx.graphics.getHeight()-getBarHeight(), getBarHeight());
		TextObjekt bar = new TextObjekt(" "+getTitle(), getBarHeight(), Gdx.graphics.getHeight()-getBarHeight(), Gdx.graphics.getWidth()-getBarHeight(), getBarHeight(), Bilder.bar, Color.BLACK, fontbigbig);
		bar.setActive(false);
		bar.setAlignment(Align.left);
		setObjekte(action, logo, bar);
		super.onOpen();
		onUpdate();
	}
	
	public void onBack(){
		Szene s = getPreSzene();
		if(s == null){
			Confirms.kill();
		} else{
			Util.setSzene(s);
		}
	}

	public void setActionButtonText(String text){action.setText(text);}
	public void setActionHide(boolean h){action.setHide(h);}
	public void setActionRefresh(){setActionRefresh("Aktualisieren");}
	public void setActionRefresh(String text){setAction(text, new Runnable(){public void run(){Util.vib(); Util.refreshScreen();}});}
	public void setAction(String text, Runnable r){action.setHide(false); actions.add(r); actiontext.add(text); action.setText(actions.size() == 1?text:"Fehler");}
	public void setAction(String text, Szene s){setAction(text, Util.getRunnable(s, false));}
}
