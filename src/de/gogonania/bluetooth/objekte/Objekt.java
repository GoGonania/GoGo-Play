package de.gogonania.bluetooth.objekte;
import com.badlogic.gdx.math.Rectangle;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.util.Bild;

public class Objekt{
	private boolean activ = true;
	private boolean hide;
	private Rectangle r;
	private Bild cNormal;
	private boolean dialog;

	public Objekt(float x, float y, float width, float height, Bild background){
		r = new Rectangle(x, y, width, height);
		this.cNormal = background;
	}

	public void render(){
		if(getBild() != null) getBild().render(getX(), getY(), getWidth(), getHeight(), getAlpha(), 0);
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

	public void setX(float n){r.setX(n);}
	public void setY(float n){r.setY(n);}
	public void setWidth(float n){r.setWidth(n);}
	public void setHeight(float n){r.setHeight(n);}
	public void setHide(boolean h){hide = h;}
	public void setActive(boolean a){activ = a;}
	public void setBackground(Bild b){cNormal = b;}
	public void setDialog(){dialog = true;}
	
	public Bild getBild(){return cNormal;}
	public boolean isHovered(boolean t){return t && (dialog || Szene.dialog == null) && !hide && activ && r.contains(Util.getX(), Util.getY());}
	public float getAlpha(){return 1;}
	public float getScale(){return 1;}
	public void click(){}
}
