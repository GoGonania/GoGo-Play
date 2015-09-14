package de.gogonania.bluetooth;

import java.io.InputStream;
import java.net.URL;

import android.content.Intent;
import android.net.Uri;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.SelectListener;

public class Internet {
	public static void browse(String url){
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url)); 
		MainActivity.getThis().startActivity(i);
	}
	
	public static void link(final String url){
		Fenster.select(url, new String[]{"Öffnen", "Weiterschicken"}, new SelectListener(){
			public void selected(int id){
				if(id == 0){
					browse(url);
				} else{
					share(url);
				}
			}});
	}
	
	public static void share(String url){
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Link: "+url+"");
		sendIntent.setType("text/plain");
		MainActivity.getThis().startActivity(sendIntent);
	}
	
	public static String request(String url, boolean hidden){
		try{
			InputStream in = new URL(url).openStream();
			int b = 0;
			String s = "";
			while((b = in.read()) != -1){s += (char) b;}
			return s;
		}catch (Exception e){
			if(!hidden) Util.notificationRed("Keine Internetverbindung!");
			return null;
		}
	}
}
