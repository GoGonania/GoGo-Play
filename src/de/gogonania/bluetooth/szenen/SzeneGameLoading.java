package de.gogonania.bluetooth.szenen;
import de.gogonania.bluetooth.sparts.SzeneLoading;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.packete.PacketBereit;
import de.gogonania.bluetooth.io.PlayerInfo;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.packete.PacketRequestData;

public class SzeneGameLoading extends SzeneLoading{
	private Button b;
	private boolean loaded;
	
	public String getText(){
		return ""+GameUtil.game.getSpielName()+" wird geladen...";
	}
	
	public void open(){
		Fenster.progress(new Runnable(){
			public void run(){
				GameUtil.game.initGame();
				GameUtil.send(new PacketRequestData());
				while(!GameUtil.game.getClient().hatDaten()){Util.sleep(20);}
			}
		}, new Runnable(){
			public void run(){
				setText(""+GameUtil.game.getSpielName()+" wurde geladen!");
				hideWait(true);
				b.setHide(false);
				loaded = true;
				if(GameUtil.isOwner() && GameUtil.game.server.personen.size() == 1){
					while(Util.isSwitching()){Util.sleep(20);}
					b.click();
				}
			}
		}, ""+GameUtil.game.getSpielName()+" wird geladen...");
		b = setBigButton("Ich bin bereit!", new Runnable(){
			public void run(){
				Util.vib();
				GameUtil.game.send(new PacketBereit());
				b.setHide(true);
			}
		});
		b.setHide(true);
	}
	
	public void update(){
		if(loaded && b.isHidden()){
			if(Util.isSwitching()){
				hideWait(true);
				setText("Spiel wird gestartet...");
			} else{
				String aus = "";
				for(PlayerInfo p : GameUtil.game.getInfo().getInfos()){
					if(p.getName().equals(Util.name)) continue;
					if(!p.isBereit()) aus += "\n"+p.getName()+"";
				}
				hideWait(false);
				setText("Warte auf Spieler...\n"+aus+"");
			}
		}
	}
}
