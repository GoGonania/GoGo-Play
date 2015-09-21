package de.gogonania.bluetooth.test;

import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;

import com.badlogic.gdx.Gdx;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.util.Bild;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.tilegame.Welt;

public class ScreenTest2 extends Szene{
	private Welt welt;
	private Path p;
	private int step = -1;
	private int lilstep = 0;
	
	public ScreenTest2() {
		super(Bilder.background);
	}
	
	public void open(){
		welt = new Welt(30, 20, false){
			public Bild getBild(int id) {
				switch(id){
				case 0:
					return Bilder.tilegras;
				case 1:
					return Bilder.tilewasser;
				}
				return null;
			}
			public boolean isBlocked(int id) {
				return id == 1;
			}
		};
		
		for(int i = 0; i < 4; i++){
			Path p = null;
			while(p == null){
				p = welt.findPath(new Mover(){}, welt.randomX(), welt.randomY(), welt.randomX(), welt.randomY());
			}
			for(int s = 0; s < p.getLength(); s++){
				Path.Step step = p.getStep(s);
				welt.set(step.getX(), step.getY(), 1);
			}
		}
		
		welt.setDiagonal(true);
	}
	
	public void onBack(){
		Util.refreshScreen();
	}
	
	public void render(){
		welt.render();
		
		if(p != null){
			if(step == -1 || step < p.getLength()-1){
				if(lilstep < 4){
					lilstep++;
				} else{
					lilstep = 0;
					step++;
				}
			}
			Path.Step s = p.getStep(step);
			Bilder.panzer.render(s.getX()*welt.getTileWidth(), s.getY()*welt.getTileHeight(), welt.getTileWidth(), welt.getTileHeight());
		}
		
		if(Gdx.input.isTouched()){
			int x = (step==-1)?0:p.getStep(step).getX();
			int y = (step==-1)?0:p.getStep(step).getY();
			Path n = welt.findPath(new Mover(){}, x, y, welt.getClickedX(), welt.getClickedY());
			if(n != null){
				Util.vib();
				p = n;
				step = 0;
			}
		}
	}
}
