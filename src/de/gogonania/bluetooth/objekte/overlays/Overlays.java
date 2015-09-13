package de.gogonania.bluetooth.objekte.overlays;
import java.util.ArrayList;
import java.util.Collections;
import com.badlogic.gdx.Gdx;
import de.gogonania.bluetooth.Util;

public class Overlays{
	private static ArrayList<Overlay> overlays = new ArrayList<Overlay>();
	private static ArrayList<Overlay> roverlays = new ArrayList<Overlay>();
	
	public static void init(){
		overlays.clear();
		roverlays.clear();
		
		add(new OverlayNotification());
		
		sort();
	}
	
	public static boolean onClick(){
		for(int i = roverlays.size()-1; i >= 0; i--){
			Overlay o = roverlays.get(i);
			if(o.isShown() && o.onClick()) return true;
		}
		return false;
	}
	
	public static boolean canScroll(){
		for(Overlay o : overlays){
			if(o.isShown() && o.isHovered(Gdx.input.isTouched())){
				return false;
			}
		}
		return true;
	}
	
	public static boolean istGemeint(Overlay o){
		for(int i = roverlays.size()-1; i >= 0; i--){
			Overlay oo = roverlays.get(i);
			if(oo.getClass().equals(o.getClass())){
				return true;
			} else{
				if(oo.isShown() && oo.isHovered(Gdx.input.isTouched())) return false;
			}
		}
		return true;
	}
	
	public static void render(){
		for(Overlay o : roverlays){
			if(!o.isShown()) continue;
			o.update();
			o.render();
		}
	}
	
	public static void sort(){
		Collections.sort(roverlays);
	}
	
	private static void add(Overlay v){
		v.setX(Util.random(0, (int) (Gdx.graphics.getWidth()-v.getWidth())));
		v.setY(Util.random(0, (int) (Gdx.graphics.getHeight()-v.getHeight())));
		overlays.add(v);
		roverlays.add(v);
	}
}
