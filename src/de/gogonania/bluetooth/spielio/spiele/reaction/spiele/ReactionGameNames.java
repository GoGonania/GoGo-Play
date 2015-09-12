package de.gogonania.bluetooth.spielio.spiele.reaction.spiele;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.io.PlayerInfo;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.spielio.spiele.reaction.ReactionGame;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.Grid;
import java.util.Collections;

public class ReactionGameNames extends ReactionGame{
	private String[] namen;
	
	public void open(){
		Grid g = new Grid(namen.length, 0.1F, 0.4F){
			public float getHeightMargin(){
				return Gdx.graphics.getHeight()*0.6F;
			}
			public float getHeight(){
				return Gdx.graphics.getHeight()/7F;
			}
		};
		
		for(final String n: namen){
			Rectangle r = g.getRectangle();
			Util.getSzene().setObjekt(new Button(n, r.getX(), r.getY(), r.getWidth(), r.getHeight(), Bilder.normal, Color.BLACK, Szene.fontbig){
				public void click(){
					if(result(n.equals(Util.name))) Util.vib();
				}
			});
		}
	}

	public String getText(){
		return "Welcher Name ist deiner?";
	}
	
	public int getChance(){
		return 30;
	}

	public long getTime(){
		return 2000;
	}

	public void create(){
		ArrayList<String> namen = new ArrayList<String>();
		for(PlayerInfo p : GameUtil.game.getInfo().getInfos()){
			namen.add(p.getName());
		}
		Collections.shuffle(namen);
		this.namen = (String[]) namen.toArray();
	}
	
	public void load(Object[] data){
		namen = (String[]) data;
	}
	
	public Object[] getData(){
		return namen;
	}
}
