package de.gogonania.bluetooth;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import de.gogonania.bluetooth.objekte.Objekt;
import de.gogonania.bluetooth.objekte.overlays.Overlays;
import de.gogonania.bluetooth.util.Bild;
import java.util.ArrayList;
import de.gogonania.bluetooth.objekte.Button;
import com.badlogic.gdx.graphics.Color;
import de.gogonania.bluetooth.util.Bilder;

public class Szene{
	private boolean open;
	private Bild background;
	
	private static boolean clicked;
	private static long last;
	public static SpriteBatch batch;
	public static ShapeRenderer x;
	private static FreeTypeFontGenerator generator;
	
	public static BitmapFont fontlittle;
	public static BitmapFont fontnotification;
	public static BitmapFont font;
	public static BitmapFont fontbig;
	public static BitmapFont fontbigbig;
	
	private ArrayList<Objekt> objekte = new ArrayList<Objekt>();
	
	public Szene(Bild c){
		background = c;
	}
	
	public static void init(){
		batch = new SpriteBatch();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		x = new ShapeRenderer();
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
	public void onMenu(){}
	public Anim getOpenAnimation(){return null;}
	public Anim getCloseAnimation(){return null;}
	
	public void onOpen(){
		open = true;
		open();
	}
	
	public void onRender(OrthographicCamera cam){
		x.setProjectionMatrix(cam.combined);
		batch.setProjectionMatrix(cam.combined);
		Vector3 v = cam.unproject(new Vector3(0, Gdx.graphics.getHeight(), 0));
		
		if(isClosed()) onOpen();
		
		if(background.hatColor()){
			x.begin(ShapeRenderer.ShapeType.Filled);
			x.setColor(background.getColor());
			x.rect(0, v.y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			x.end();
		} else{
			batch.begin();
			background.getSprite().setBounds(0, v.y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			background.getSprite().draw(batch);
			batch.end();
		}
		
		if(isClosed()) onOpen();
		
		renderObjekts();
		render();
		Overlays.render();
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
		parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZöäüÖÄÜ,.!?()/-ß1234567890=+:\"'*_|<>%&€";
		return generator.generateFont(parameter);
	}
}
