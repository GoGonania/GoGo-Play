package de.gogonania.bluetooth.screens;
import de.gogonania.bluetooth.sparts.ScreenAktionenBase;
import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.Anim;
import de.gogonania.bluetooth.Registry;
import android.content.Intent;
import de.gogonania.bluetooth.MainActivity;
import android.net.Uri;
import java.io.File;

public class ScreenExtras extends ScreenAktionenBase{
    public Szene getPreSzene(){
		return new ScreenMain();
	}

	public String getTitle(){
		return "Extras & Infos (Appversion: "+Util.getPackageInfo().versionName+")";
	}

	public Anim getOpenAnimation(){
		return Registry.animations[1];
	}

	public Anim getCloseAnimation(){
		return Registry.animations[1];
	}

	public void open(){
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

		add(Util.getAppName()+" auf\nGitHub ansehen", new Runnable(){
				public void run(){
					Util.vib();
					Util.browse("https://github.com/GoGonania/GoGo-Play/");
				}
			});
			
		skip(2);
			
		add(new ScreenDebug());
	}

	public int getItemsInARow() {
		return 4;
	}
}
