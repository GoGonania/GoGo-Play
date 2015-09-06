package de.gogonania.bluetooth.objekte;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.Listener;

public class Input extends TextObjekt{
	private String label;
	private boolean empty;
	private boolean zahl;
	
	public Input(String text, String label, float x, float y, float width, float height, BitmapFont f, boolean empty, boolean zahl){
		super("", x, y, width, height, null, Color.WHITE, f);
		setText(text);
		setAlignment(Align.left);
		this.label = label;
		this.empty = empty;
		this.zahl = zahl;
	}
	
	public void click(){
		Fenster.prompt("", label + " "+getVerb()+"", getText(), new Listener(){
				public void ready(String s){
					if(s != null){
						s = s.trim();
						if(!empty && s.isEmpty()){
							Util.notificationRed(""+label+" darf nicht leer sein");
						} else{
							if(!zahl || Util.isNumeric(s)){
								setText(s);
							} else{
								Util.notificationRed("Das ist keine Zahl!");
							}
						}
					}
				}
			});
	}

	public void render(ShapeRenderer x, SpriteBatch batch){
		setBorder(isHovered(Gdx.input.isTouched())?null:Color.WHITE);
		super.render(x, batch);
	}

	public void renderText(SpriteBatch batch, String text){
		super.renderText(batch, isHovered(Gdx.input.isTouched())?" "+label+" "+getVerb()+"":text);
	}

	private String getVerb(){return getText().isEmpty()?"setzen":"ändern";}
	public void setText(String s){super.setText(" "+s);}
	public String getText(){return super.getText().substring(1);}
}
