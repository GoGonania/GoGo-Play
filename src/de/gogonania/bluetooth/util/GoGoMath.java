package de.gogonania.bluetooth.util;

public class GoGoMath {
	public static int winkel(float x1, float y1, float x2, float y2){
		 int w = (int) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
		 if(w < 0) w += 360;
		 return w;
	}
}
