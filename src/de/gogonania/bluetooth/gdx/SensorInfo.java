package de.gogonania.bluetooth.gdx;

import com.badlogic.gdx.Gdx;

public class SensorInfo {
	public static float getRotation(){
		return (Gdx.input.getRotation() == 90?1:-1)*Gdx.input.getAccelerometerY();
	}
}
