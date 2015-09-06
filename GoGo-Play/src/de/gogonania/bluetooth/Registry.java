package de.gogonania.bluetooth;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;

public class Registry{
	public final static int clicktimeout = 250;
	public final static float scrollspeed = 1.4F;
	
	public final static Anim[] animations = new Anim[]{
		new Anim(){
			public int frames(){return 8;}
			public void up(OrthographicCamera cam){cam.zoom -= 0.045F;}
			public void down(OrthographicCamera cam){cam.zoom += 0.045F;}
		},
	    new Anim(){
	        public int frames(){return 20;}
	        public void up(OrthographicCamera cam){cam.position.y += Gdx.graphics.getHeight()/frames();}
	        public void down(OrthographicCamera cam){cam.position.y -= Gdx.graphics.getHeight()/frames();}
	    }
	};
}
