package de.gogonania.bluetooth;
import com.badlogic.gdx.graphics.OrthographicCamera;

public interface Anim{
	int frames();
    void up(OrthographicCamera cam);
	void down(OrthographicCamera cam);
}
