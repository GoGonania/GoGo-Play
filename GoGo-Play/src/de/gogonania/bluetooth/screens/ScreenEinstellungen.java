package de.gogonania.bluetooth.screens;

import java.io.File;import android.content.Intent;
import android.net.Uri;
import de.gogonania.bluetooth.Anim;
import de.gogonania.bluetooth.MainActivity;
import de.gogonania.bluetooth.Registry;
import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.sparts.ScreenAktionenBase;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.SliderListener;

public class ScreenEinstellungen extends ScreenAktionenBase{
    public Szene getPreSzene(){
		return new ScreenMain();
	}
	
	public String getTitle(){
		return "Einstellungen v"+Util.getPackageInfo().versionName+"";
	}
	
	public Anim getOpenAnimation(){
		return Registry.animations[1];
	}
	
	public Anim getCloseAnimation(){
		return Registry.animations[1];
	}
	
	public void open(){
		add("Scrollgeschwindigkeit", new Runnable(){
			public void run(){
				Fenster.slider("Derzeitige Geschwindigkeit: [value]%", "Scrollgeschwindigkeit anpassen", new SliderListener(){
					public void ok(int progress){
						Util.scroll = Registry.scrollspeed * (((float) progress) / 50);
					}
				}, 0, 100, (int) ((Util.scroll/Registry.scrollspeed)*50F));
			}
		});
		
		add("Animationen", Util.a?Bilder.cgreen:Bilder.cred, new Runnable(){
			public void run(){
				Util.a = !Util.a;
				Util.vib();
				Util.refreshScreen();
			}
		});
		
		add("Vibrieren", Util.vib?Bilder.cgreen:Bilder.cred, new Runnable(){
				public void run(){
					Util.vib = !Util.vib;
					Util.vib();
					Util.refreshScreen();
				}
			});
		
		add(""+Util.getAppName()+"\nweiterschicken", new Runnable(){
			public void run() {
				try{
					String url = MainActivity.getThis().getApplicationInfo().publicSourceDir;
					Intent intent = new Intent();  
				    intent.setAction(Intent.ACTION_SEND);  
				    intent.setType("application/vnd.android.package-archive");
				    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(url)));
				    MainActivity.getThis().startActivity(intent);
				}catch(Exception e){e.printStackTrace();}
			}});
		
		update();
	}
	
	public int getItemsInARow() {
		return 4;
	}
}
