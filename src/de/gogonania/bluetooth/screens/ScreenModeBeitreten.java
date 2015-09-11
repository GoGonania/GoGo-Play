package de.gogonania.bluetooth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.Wifi;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.objekte.Text;
import de.gogonania.bluetooth.objekte.Wait;
import de.gogonania.bluetooth.sparts.ScreenBase;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.SelectListener;

public class ScreenModeBeitreten extends ScreenBase{
	public Szene getPreSzene() {return new ScreenMain();}
	public String getTitle() {return "Einem Spiel beitreten";}
	
	private Wait w;
	private Text info;
	private Button weiter;
	private int m;
	
	public ScreenModeBeitreten(){this(1);}
	public ScreenModeBeitreten(int m){this.m = m;}
	
	public void open(){
		setAction("Nochmal suchen", new Runnable(){
			public void run(){
				Fenster.select("Wie willst du suchen?", new String[]{"Normal", "Doppelte Länge", "Dreifache Länge"}, false, new SelectListener(){
						public void selected(int id){
							Util.setSzene(new ScreenModeBeitreten(id+1), null);
						}
					});
			}
		});
		Wifi.scan(m);
		w = new Wait();
		info = new Text("", 0, Gdx.graphics.getHeight()*0.6F, Gdx.graphics.getWidth(), Color.WHITE);
		weiter = new Button("Gefundene Spiele ansehen", Gdx.graphics.getWidth()*0.3F, Gdx.graphics.getHeight()*0.5F, Gdx.graphics.getWidth()*0.4F, Gdx.graphics.getHeight()*0.08F, Bilder.cgreen, Color.BLACK){
			public void click(){
				Util.vib();
				Util.setSzene(new ScreenGefundeneSpiele());
			}
		};
		setObjekte(w, info, weiter);
	}
	
	public void onBack() {
		if(Wifi.isDiscovering()) return;
		super.onBack();
	}
	
	public void update(){
		w.setHide(!Wifi.isDiscovering());
		int s = Wifi.games.size();
		setActionHide(Wifi.isDiscovering());
		weiter.setHide(s == 0 || Wifi.isDiscovering());
		if(!Wifi.isDiscovering() && s == 0){
			info.setText("Leider keine Spiele gefunden!\nEinfach nochmal suchen...");
		} else{
			info.setText(""+(Wifi.isDiscovering()?"Suche lokale Spiele...\n\nGefundene Geräte: "+Wifi.getFoundDevices()+"\n":"")+"Gefundene Spiele: "+s+"");
		}
	}
}
