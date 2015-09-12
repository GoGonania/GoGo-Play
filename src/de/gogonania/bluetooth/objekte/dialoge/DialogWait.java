package de.gogonania.bluetooth.objekte.dialoge;

import com.badlogic.gdx.graphics.Color;

import de.gogonania.bluetooth.objekte.Text;
import de.gogonania.bluetooth.objekte.Wait;

public class DialogWait extends Dialog {
	
	public DialogWait(String text){
		float size = getDWidth()/5f;
		addObjekt(new Wait(getDX()+size/4F, getDY()+((getDHeight()-size)/2F), size));
		addObjekt(new Text(text, getDX()+size*1.3F, getDY(), getDWidth()-size*1.3F, Color.BLACK){
			public float getHeight(){
				return getDHeight();
			}
		});
	}
}
