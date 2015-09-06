package de.gogonania.bluetooth.objekte;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import de.gogonania.bluetooth.Szene;

public class Text extends TextObjekt{
	public Text(String t, float x, float y, float width, Color text, BitmapFont f){
		super(t, x, y, width, Szene.font.getCapHeight(), null, text, f);
		setActive(false);
	}

	public Text(String t, float x, float y, float width, Color text){
		this(t, x, y, width, text, Szene.font);
	}
}
