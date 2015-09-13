package de.gogonania.bluetooth.objekte.dialoge;

import de.gogonania.bluetooth.objekte.Wait;

public class DialogWait extends Dialog {
	public DialogWait(String text){
		super(text, false);
		float size = getDWidth()/5f;
		addObjekt(new Wait(getDX()+((getDWidth()-size)/2F), getDY()+((getDHeight()-size)/2F), size));
	}
}
