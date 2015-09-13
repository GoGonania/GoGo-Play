package de.gogonania.bluetooth.objekte;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.Listener;

public class Input extends TextObjekt{
	private String label;
	private boolean empty;
	
	public Input(String text, String label, float x, float y, float width, float height, BitmapFont f, boolean empty){
		super("", x, y, width, height, null, Color.WHITE, f);
		setText(text);
		setAlignment(Align.left);
		this.label = label;
		this.empty = empty;
	}
	
	public void click(){
		Fenster.prompt("Bitte hier eingeben:", label + " "+getVerb()+"", getText(), new Listener(){
				public void ready(String s){
					if(s != null){
						s = s.trim();
						if(!empty && s.isEmpty()){
							Util.notificationRed(""+label+" darf nicht leer sein");
						} else{
							setText(s);
						}
					}
				}
			});
	}

	public void render(){
		Bilder.cwhite.render(getX(), getY(), getWidth(), 1);
		super.render();
	}

	public void renderText(String text){
		super.renderText(isHovered(Gdx.input.isTouched())?" "+label+" "+getVerb()+"":text);
	}

	private String getVerb(){return getText().isEmpty()?"setzen":"ändern";}
	public void setText(String s){super.setText(" "+s);}
	public String getText(){return super.getText().substring(1);}
}
