package de.gogonania.bluetooth;
import com.badlogic.gdx.graphics.OrthographicCamera;

public interface Anim{
	public int frames();
	public void anim(OrthographicCamera cam, int step);
}
