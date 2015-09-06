package de.gogonania.bluetooth.objekte;
import com.badlogic.gdx.graphics.*;

import de.gogonania.bluetooth.util.Bild;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.Gdx;

public class Button extends TextObjekt{
	public Button(String text, float x, float y, float width, float height, Bild n, Color t, BitmapFont f){
		super(text, x, y, width, height, n, t, f);
	}
	
	public Button(String text, float x, float y, float width, float height, Bild n, Color t){
		super(text, x, y, width, height, n, t);
	}
	public float getAlpha(){
		return isHovered(Gdx.input.isTouched())?0.6F:1;
	}
	public float getScale(){
		return isHovered(Gdx.input.isTouched())?0.9F:1;
	}
}
