package de.gogonania.bluetooth.spielio.spiele.reaction.spiele;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.spielio.spiele.reaction.ReactionGame;
import de.gogonania.bluetooth.util.Bild;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.Grid;

public class ReactionGameFindColor extends ReactionGame{
	private int[] backgrounds;
	private int[] texts;
	
	private static final String[] texte = new String[]{"Rot", "Grün", "Gelb", "Blau", "Orange", "Weiss", "Grau"};
	private static final Bild[] bilder = new Bild[]{Bilder.cred, Bilder.cgreen, Bilder.cyellow, Bilder.normal, Bilder.corange, Bilder.cwhite, Bilder.clgray};
	
	public void open(){
		Grid g = new Grid(backgrounds.length, 0.1F, 0.4F){
			public float getHeightMargin(){
				return Gdx.graphics.getHeight()*0.6F;
			}
		};
		
		for(int i = 0; i < backgrounds.length; i++){
			Rectangle r = g.getRectangle();
			final int id = i;
			Util.getSzene().setObjekt(new Button(texte[texts[i]], r.getX(), r.getY(), r.getWidth(), r.getHeight(), bilder[backgrounds[i]], Color.BLACK, Szene.fontbig){
				public void click(){
					if(result(backgrounds[id] == texts[id])) Util.vib();
				}
			});
		}
	}

	public String getText(){
		return "Welcher Knopf hat seine Farbe?";
	}
	
	public int getChance(){
		return 80;
	}

	public long getTime(){
		return 4000;
	}

	public void create(){
		int r = Util.random(4, bilder.length);
		backgrounds = new int[r];
		texts = new int[r];
		
		for(int i = 0; i < backgrounds.length; i++){
			backgrounds[i] = Util.random(0, r-1);
		}
		
		for(int i = 0; i < texts.length; i++){
			do{
				texts[i] = Util.random(0, r-1);
			}while(texts[i] == backgrounds[i]);
		}
		
		r = Util.random(0, r-1);
		texts[r] = backgrounds[r];
	}
	
	public void load(Object[] data){
		backgrounds = (int[]) data[0];
		texts = (int[]) data[1];
	}
	
	public Object[] getData(){
		Object[] o = new Object[2];
		o[0] = backgrounds;
		o[1] = texts;
		return o;
	}
}
