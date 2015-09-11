package de.gogonania.bluetooth.spielio.spiele.reaction.spiele;
import de.gogonania.bluetooth.spielio.spiele.reaction.ReactionGame;
import de.gogonania.bluetooth.spielio.spiele.reaction.Options;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.util.Bilder;
import com.badlogic.gdx.graphics.Color;
import de.gogonania.bluetooth.util.Grid;
import de.gogonania.bluetooth.spielio.spiele.SzeneReaction;
import com.badlogic.gdx.Gdx;

public class ReactionGameColor extends ReactionGame{
	private boolean[] ops;
	private int inaline;
	
	public void open(){
		Options o = new Options(new Grid(inaline, 0F, 0F){
			public float getHeightMargin(){
				return SzeneReaction.getBarHeight();
			}
			public float getHeight(){
				return (Gdx.graphics.getHeight()-getHeightMargin())/inaline;
			}
		}, this);
		
		
		for(boolean b : ops){
			o.add("", b?Bilder.cred:Bilder.clgray, Color.BLACK, b);
		}
	}

	public String getText(){
		return "Wo ist das rote Feld?";
	}

	public long getTime(){
		return 1500;
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
		inaline = data[0];
		ops = (boolean[]) data[1];
	}
	
	public Object[] getData(){
		Object[] o = new Object[2];
		o[0] = inaline;
		o[1] = ops;
		return o;
	}
}
