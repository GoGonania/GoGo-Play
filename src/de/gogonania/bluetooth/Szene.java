package de.gogonania.bluetooth;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import de.gogonania.bluetooth.gdx.AnimatedBackground;
import de.gogonania.bluetooth.gdx.Background;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.objekte.Objekt;
import de.gogonania.bluetooth.objekte.dialoge.Dialog;
import de.gogonania.bluetooth.objekte.overlays.Overlays;
import de.gogonania.bluetooth.util.Bild;
import de.gogonania.bluetooth.util.Bilder;

public class Szene{
	private boolean open;
	private Bild background;
	public OrthographicCamera cam;
	public static Dialog dialog;
	
	private static boolean clicked;
	private static long last;
	public static SpriteBatch batch;
	private static FreeTypeFontGenerator generator;
	public static BitmapFont fontlittle;
	public static BitmapFont fontnotification;
	public static BitmapFont font;
	public static BitmapFont fontbig;
	public static BitmapFont fontbigbig;
	
	private ArrayList<Objekt> objekte = new ArrayList<Objekt>();
	
	public Szene(Bild c){
		background = c;
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(Gdx.graphics.getWidth()/2F, Gdx.graphics.getHeight()/2F, 0);
	}
	
	public static void init(){
		batch = new SpriteBatch();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		font = generateFont(1);
		fontlittle = generateFont(0.75F);
		fontnotification = generateFont(0.9F);
		fontbig = generateFont(1.5F);
		fontbigbig = generateFont(2);
	}
	
	public void open(){}
	public void render(){}
	public void update(){}
	public void click(){}
	public void onBack(){}
	public Anim getOpenAnimation(){return null;}
	public Anim getCloseAnimation(){return null;}
	public boolean canScroll(){return true;}
	
	public void onOpen(){
		open = true;
		open();
	}
	
	public void onRender(){
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		background.render(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		if(this instanceof AnimatedBackground && Util.b && !Util.isSwitching()) Background.render();
		if(isClosed()) onOpen();
		
		renderObjekts();
		render();
		onUpdate();
	}
	
	public void onUpdate(){
		if(Gdx.input.isTouched()){
			if(!clicked){clicked = true; last = System.currentTimeMillis();} else{}
		} else{
			if(clicked){
				if(!Util.isSwitching() && (last >= System.currentTimeMillis()-Registry.clicktimeout || getLowestY() >= 0)) onClick();
				clicked = false;
			}
		}
		update();
	}
	
	public void renderObjekts(){
		for(Objekt o : objekte){
			if(o.isHidden()) continue;
			o.render();
		}
	}
	
	public void onClick(){
		if(dialog != null && dialog.onClick()) return;
		if(Overlays.onClick()) return;
		for(Objekt o : objekte){
			if(o.onClick()) return;
		}
		click();
	}

	public Button setBigButton(String text, final Runnable r){Button b = new Button(text, Gdx.graphics.getWidth()*0.2F, Gdx.graphics.getHeight()/60F, Gdx.graphics.getWidth()*0.6F, Gdx.graphics.getHeight()*0.1F, Bilder.cgreen, Color.BLACK, fontbig){public void click(){r.run();}}; setObjekt(b); return b;}
	public void setObjekt(Objekt o){objekte.add(0, o);}
	public void setObjekte(Objekt... o){for(Objekt k : o){setObjekt(k);}}
	public boolean isClosed(){return !open;}
	public float getMaxY(){float l = getLowestY(); if(l == 0){} else{l -= Gdx.graphics.getHeight()/20F;} return l + Gdx.graphics.getHeight()/2F;}
	
	public float getLowestY(){
		float l = 0;
		for(Objekt o : objekte){
			if(!o.isHidden() && o.getY() < l) l = o.getY();
		}
		return l;
	}
	
	private static BitmapFont generateFont(float scale){
		int s = (int)Math.ceil((int) (Gdx.graphics.getWidth()/45F*scale));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = s;
		generator.scaleForPixelHeight(s);
		parameter.minFilter = Texture.TextureFilter.Nearest;
		parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
		parameter.characters = Registry.zeichen;
		return generator.generateFont(parameter);
	}
}
