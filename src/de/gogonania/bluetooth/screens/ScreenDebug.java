package de.gogonania.bluetooth.screens;
import de.gogonania.bluetooth.sparts.ScreenAktionenBase;
import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.objekte.Text;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.Gdx;
import de.gogonania.bluetooth.Util;
import com.badlogic.gdx.graphics.Color;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.SliderListener;
import de.gogonania.bluetooth.Anim;
import de.gogonania.bluetooth.Registry;

public class ScreenDebug extends ScreenAktionenBase{
	private Text debug;
	
	public int getItemsInARow(){
		return 4;
	}

	public Szene getPreSzene(){
		return new ScreenExtras();
	}

	public String getTitle(){
		return "Entwickler-Bereich";
	}
	
	public Anim getOpenAnimation(){
		return Registry.animations[1];
	}

	public Anim getCloseAnimation(){
		return Registry.animations[1];
	}
	
	public void open(){
		debug = new Text("", 0, (Gdx.graphics.getHeight()-getBarHeight())/2F, Gdx.graphics.getWidth(), Color.WHITE);
		debug.setAlignment(Align.left);
		setObjekt(debug);
		
		add("Render-Timeout\nanpassen", new Runnable(){
			public void run(){
				Fenster.slider("Jetziges Timeout: [value]ms", "Render-Timeout anpassen", new SliderListener(){
						public void ok(int progress){
							Util.rt = progress;
						}
					}, 0, 300, Util.rt);
			}
		});
	}
	
	public void update(){
		debug.setText("FPS: "+Gdx.graphics.getFramesPerSecond()+"\nSpeicher: "+Util.makeNiceZahl(Gdx.app.getJavaHeap()/1000)+" - "+Util.makeNiceZahl(Gdx.app.getNativeHeap()/1000)+"");
	}
}
