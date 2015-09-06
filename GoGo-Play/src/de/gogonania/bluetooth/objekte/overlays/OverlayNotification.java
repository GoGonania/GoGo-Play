package de.gogonania.bluetooth.objekte.overlays;
import com.badlogic.gdx.Gdx;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.util.Bilder;

public class OverlayNotification extends Overlay{
	private static final long shown = 3000;
	private static final long timeout = 1500;
	
	public void update(){
		setText(" "+Util.n.text+" ");
		setBackground(Util.n.getBild(Bilder.clgray));
	}
	
	public void onDrag(){
		Util.n.z = System.currentTimeMillis()-shown;
	}
	
	public float getAlpha(){
		long d = System.currentTimeMillis() - Util.n.z;
		if(d <= shown) return 1;
		return 1F - (((float)(d-shown)) / ((float) timeout));
	}

	public boolean isShown(){
		return Util.n != null && getAlpha() > 0;
	}

	public OverlayNotification(){
		super(null, new Runnable(){public void run(){}});
		setWidth(Gdx.graphics.getWidth()/3F);
		setHeight(Gdx.graphics.getHeight()/10F);
	}
}
