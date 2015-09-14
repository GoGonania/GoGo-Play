package de.gogonania.bluetooth.util;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

import de.gogonania.bluetooth.Szene;

public class Bild{
	private Sprite s;
	private Color c;
	
	public Bild(Texture t){
		s = new Sprite(t);
	}
	
	public Bild(String s){
		this(new Texture(s));
	}
	
	public Bild(Color c){
	    this("button.png");
		this.c = c;
		s.setColor(c);
	}
	
	public void render(float x, float y, float width, float height, float alpha, int r){
		s.setOrigin(width/2F, width/2F);
		s.setRotation(r);
		s.setBounds(x, y, width, height);
		s.draw(Szene.batch, alpha);
	}
	
	public void render(float x, float y, float width, float height){
		render(x, y, width, height, 1, 0);
	}
	
	public Color getColor(){return c;}
	public boolean hatColor(){return c != null;}
}
