package de.gogonania.bluetooth.sparts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.objekte.Text;
import de.gogonania.bluetooth.objekte.Wait;
import de.gogonania.bluetooth.util.Bilder;

public abstract class SzeneLoading extends Szene{
	public abstract String getText();
	private Text t;
	private Wait w;
	
	public SzeneLoading() {
		super(Bilder.background);
	}
	
	public void onOpen(){
		super.onOpen();
		t = new Text(getText(), 0F, Gdx.graphics.getHeight()/2F, (float)Gdx.graphics.getWidth(), Color.WHITE, Szene.fontbigbig);
		w = new Wait();
		setObjekte(t, w);
	}
	
	public void setText(String text){
		t.setText(text);
	}
	
	public void hideWait(boolean h){
		w.setHide(h);
	}
	
	public boolean isWaiting(){
		return !w.isHidden();
	}
}
