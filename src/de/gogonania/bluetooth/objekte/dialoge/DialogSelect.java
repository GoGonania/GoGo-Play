package de.gogonania.bluetooth.objekte.dialoge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.Grid;

public abstract class DialogSelect extends Dialog {
	public abstract void selected(int id);

	public DialogSelect(String text, Object[] options, int inarow) {
		super(text, true);
		if(options.length == 0){
			setText("Es gibt hier nichts zum Auswählen");
		} else{
			Grid g = new Grid(inarow, 0.1F, 0.45F/3F*((float)inarow)){
				public float getHeightMargin(){
					return Gdx.graphics.getHeight()-(getDY()+getDHeight()*0.88F);
				}
				public float getWidth(){
					return getDWidth();
				}
			};
			for(int i = 0; i < options.length; i++){
				Rectangle r = g.getRectangle(i);
				final int id = i;
				addObjekt(new Button(options[i].toString(), r.getX()+getDX(), r.getY(), r.getWidth(), r.getHeight(), Bilder.normal, Color.BLACK){
					public void click(){
						Util.vib();
						hide();
						selected(id);
					}
				});
			}
		}
	}
}
