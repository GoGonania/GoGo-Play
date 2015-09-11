package de.gogonania.bluetooth.spielio.spiele.reaction;
import de.gogonania.bluetooth.util.Grid;
import android.widget.Gallery;
import com.badlogic.gdx.Gdx;
import de.gogonania.bluetooth.util.Bild;
import com.badlogic.gdx.math.Rectangle;
import de.gogonania.bluetooth.Util;
import com.badlogic.gdx.graphics.Color;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.Szene;

public class Options{
	private ReactionGame game;
	private Grid grid;
	
	public Options(Grid g, ReactionGame ga){
		grid = g;
		game = ga;
	}
	
	public void add(String text, Bild b, Color c, final boolean right){
		Rectangle r = grid.getRectangle();
		Util.getSzene().setObjekt(new Button(text, r.getX(), r.getY(), r.getWidth(), r.getHeight(), b, c, Szene.fontbig){
			public void click(){
				if(game.result(right)) Util.vib();
			}
		});
	}
}
