package de.gogonania.bluetooth.objekte.dialoge;

import com.badlogic.gdx.graphics.Color;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.util.Bild;
import de.gogonania.bluetooth.util.Bilder;

public abstract class DialogConfirm extends Dialog {
	private static final float h = getDHeight()/3F;
	private static final float bw = getDWidth()/3.5F;
	
	public abstract void ok(String s);
	
	public DialogConfirm(String message){
		super(message, true);
		addButton("Nein", Bilder.cred, bw/2, null);
		addButton("Ja", Bilder.cgreen, bw*2, "");
	}
	
	public void addButton(String text, Bild b, float x, final String s){
		addObjekt(new Button(text, getDX()+x, getDY()+h, bw, bw/4F, b, Color.BLACK){
			public void click(){
				Util.vib();
				hide();
				ok(s);
			}
		});
	}
}
