package de.gogonania.bluetooth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.io.Wifi;
import de.gogonania.bluetooth.objekte.Wait;
import de.gogonania.bluetooth.util.Bilder;

public class MainActivity extends AndroidApplication{
	private static MainActivity a;
	private static boolean p;
	private static Vibrator v;
	private static PowerManager.WakeLock wakeLock;
	
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
		a = this;
		Window w = getWindow();
		w.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, Util.getAppName());
		wakeLock.acquire();
		Wait.init();
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		AndroidApplicationConfiguration conf = new AndroidApplicationConfiguration();
		conf.useAccelerometer = true;
		conf.numSamples = 2;
		conf.disableAudio = true;
		conf.useCompass = false;
		initialize(new Util(), conf);
		Gdx.graphics.setVSync(false);
		Wifi.init();
    }

	protected void onResume(){
		p = false;
		Util.ping("Resume", true);
		GameUtil.notifyPause();
		Gdx.app.postRunnable(new Runnable(){
			public void run(){
				Szene.init();
				Bilder.init();
			}
		});
		super.onResume();
	}
	
	protected void onPause(){
		p = true;
		Util.ping("Pause", true);
		GameUtil.notifyPause();
		Spielstand.save();
		super.onPause();
	}

	public void onBackPressed(){
		if(Util.isSwitching() || Szene.dialog != null){
			if(Szene.dialog != null) Szene.dialog.cancel();
		} else{
			Util.getSzene().onBack();
		}
	}
	
	public static void close(){wakeLock.release();}
	public static Vibrator getVibrator(){return v;}
	public static boolean isPaused(){return p;}
	public static MainActivity getThis(){return a;}
}
