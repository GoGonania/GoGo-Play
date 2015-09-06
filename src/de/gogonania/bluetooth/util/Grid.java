package de.gogonania.bluetooth.util;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

public class Grid{
	private float wh;
	private float maxheight = Gdx.graphics.getHeight();
	private float line;
	private float width;
	private float marginw;
	private float marginh;
	
	public float getHeightMargin(){return 0;}
	public float getHeight(){return width * wh;}
	public float getWidth(){return Gdx.graphics.getWidth();}
	
	private int i = -1;
	
	public Grid(float l, float m, float wh){
		this.wh = wh;
		line = l;
		width = getWidth()*(1-m)/line;
		marginw = getWidth()*m/line;
		marginh = m*getHeight();
	}
	
	public Rectangle getRectangle(){
		skip(1);
		return getRectangle(i);
	}
	
	public Rectangle getRectangle(int id){
		return new Rectangle(marginw*getRow(id)+marginw/2+width*getRow(id), maxheight - marginh*(getLine(id)) - getHeight()*getLine(id) - getHeightMargin(), width, getHeight());
	}
	
	public int getLine(int i){
		return i / ((int) line) + 1;
	}
	
	public int getRow(int i){
		while(i >= line){
			i -= line;
		}
		return i;
	}
	
	public void skip(int steps){
		i += steps;
	}
	
	public int getID(){
		return i;
	}
	
	public void reset(){
		i = -1;
	}
}
