package de.gogonania.bluetooth.spielio.spiele.reaction.spiele;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.spielio.spiele.SzeneReaction;
import de.gogonania.bluetooth.spielio.spiele.reaction.ReactionGame;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.Grid;

public class ReactionGameColor extends ReactionGame{
	private boolean[] ops;
	private int inaline;
	
	public void open(){
		float height = (Gdx.graphics.getHeight()-SzeneReaction.barheight)/inaline;
		float width = Gdx.graphics.getWidth()/inaline;
		
		int id = 0;
		for(int l = 0; l < inaline; l++){
			for(int i = 0; i < inaline; i++){
				final boolean b = ops[id];
				Util.getSzene().setObjekt(new Button("", i*width, l*height, width, height, b?Bilder.cred:Bilder.clgray, Color.BLACK, Szene.fontbig){
						public void click(){
							if(result(b)) Util.vib();
						}
					});
				id++;
			}
		}
	}

	public String getText(){
		return "Wo ist das rote Feld?";
	}
	
	public int getChance(){
		return 70;
	}

	public long getTime(){
		return 2000;
	}

	public void create(){
		inaline = Util.random(8, 12);
		ops = new boolean[inaline*inaline];
		int r = Util.random(0, ops.length-1);
		for(int i = 0; i < ops.length; i++){
			ops[i] = (i == r);
		}
	}
	
	public void load(Object[] data){
		inaline = (int) data[0];
		ops = (boolean[]) data[1];
	}
	
	public Object[] getData(){
		Object[] o = new Object[2];
		o[0] = inaline;
		o[1] = ops;
		return o;
	}
}
