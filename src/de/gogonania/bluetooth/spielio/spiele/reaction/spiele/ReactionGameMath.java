package de.gogonania.bluetooth.spielio.spiele.reaction.spiele;
import de.gogonania.bluetooth.spielio.spiele.reaction.ReactionGame;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.util.Grid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import de.gogonania.bluetooth.util.Bilder;
import com.badlogic.gdx.graphics.Color;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.spielio.spiele.reaction.Options;

public class ReactionGameMath extends ReactionGame{
	private int zahl1;
	private int zahl2;
	private int[] ops;
	private int sum;
	
	public void open(){
		Options o = new Options(new Grid(ops.length, 0.1F, 0.4F){
			public float getMarginHeight(){
				return Gdx.graphics.getHeight()*0.6F;
			}
		}, this);
		
		for(int i : ops){
			o.add(""+i+"", Bilder.normal, Color.BLACK, i == sum);
		}
	}
	
	public Object[] getData(){
		Object[] d = new Object[3];
		d[0] = zahl1;
		d[1] = zahl2;
		d[2] = ops;
		return d;
	}

	public void create(){
		zahl1 = Util.random(20, 100);
		zahl2 = Util.random(20, 100);
		
		int options = Util.random(4, 6);
		ops = new int[options];
		int r = Util.random((options*-1)+1, 0);
		int s = Util.random(1, 3);

		for(int i = 0; i < options; i++){
			ops[i] = zahl1 + zahl2 +((r+i)*s);
		}
	}

	public void load(Object[] data){
		zahl1 = data[0];
		zahl2 = data[1];
		ops = (int[]) data[2];
		sum = zahl1 + zahl2;
	}
	
	public String getText(){
		return "Was ist "+zahl1+" + "+zahl2+"?";
	}

	public long getTime(){
		return 8000;
	}
}
