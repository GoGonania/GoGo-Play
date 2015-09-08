package de.gogonania.bluetooth.objekte;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.util.Bild;
import de.gogonania.bluetooth.Szene;

public class Objekt{
	private boolean activ = true;
	private boolean hide;

	private Rectangle r;
	
	private Bild cNormal;
	private Color border;

	public Objekt(float x, float y, float width, float height, Bild background){
		r = new Rectangle(x, y, width, height);
		this.cNormal = background;
	}

	public void render(){
		if(border != null){
			Szene.x.begin(ShapeRenderer.ShapeType.Line);
			Szene.x.setColor(border);
			Szene.x.rect(getX()-1, getY()-1, getWidth()+2, getHeight()+2);
			Szene.x.end();
		}
		
		Bild b = getBild();
		if(b != null){
			if(b.hatColor() && !(this instanceof Button)){
				Szene.x.begin(ShapeRenderer.ShapeType.Filled);
				Szene.x.setColor(b.getColor());
				Szene.x.rect(getX(), getY(), getWidth(), getHeight());
				Szene.x.end();
			} else{
				Szene.batch.begin();
				b.getSprite().setBounds(getX(), getY(), getWidth(), getHeight());
				b.getSprite().draw(Szene.batch, getAlpha());
				Szene.batch.end();
			}
		}
	}

	public boolean onClick(){
		if(isHovered(true)){
			click();
			return true;
		}
		return false;
	}

	public float getX(){return r.getX()+(r.getWidth()/2F*(1-getScale()));}
	public float getY(){return r.getY()+(r.getHeight()/2F*(1-getScale()));}
	public float getWidth(){return r.getWidth()*getScale();}
	public float getHeight(){return r.getHeight()*getScale();}
	public boolean isHidden(){return hide;}

	public void setBorder(Color c){border = c;}
	public void setX(float n){r.setX(n);}
	public void setY(float n){r.setY(n);}
	public void setWidth(float n){r.setWidth(n);}
	public void setHeight(float n){r.setHeight(n);}
	public void setHide(boolean h){hide = h;}
	public void setActive(boolean a){activ = a;}
	public void setBackground(Bild b){cNormal = b;}
	
	public Bild getBild(){return cNormal;}
	public boolean isHovered(boolean t){return t && !hide && activ && r.contains(Util.getX(), Util.getY());}
	public float getAlpha(){return 1;}
	public float getScale(){return 1;}
	public void click(){}
}
