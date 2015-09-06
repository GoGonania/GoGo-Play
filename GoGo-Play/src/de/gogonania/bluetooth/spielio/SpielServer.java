package de.gogonania.bluetooth.spielio;
import de.gogonania.bluetooth.io.GameServer;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.io.IPerson;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.packete.PacketScreen;
import de.gogonania.bluetooth.szenen.SzeneGameLoading;
import de.gogonania.bluetooth.io.PlayerInfo;
import de.gogonania.bluetooth.packete.PacketBereit;

public abstract class SpielServer{
	private Thread t;
	private boolean ingame;
	
	public abstract void onPacket(IPerson p, Object o);
	
	public SpielServer(){
		t = new Thread(new Runnable(){
			public void run(){
				while(!i() && getServer().inLobby()){Util.sleep(50);}
				if(i()) return;
				init();
				send(PacketScreen.create(SzeneGameLoading.class));
				while(!i() && !sindAlleBereit()){Util.sleep(50);}
				if(i()) return;
				send(new PacketBereit());
				ingame = true;
				start();
				
			}
		});
		t.start();
	}
	
	public void start(){}
	public void init(){}
	public void create(){}
	
	public boolean isSaveAble(){return this instanceof Saveable;}
	public void send(IPerson p, Object o){getServer().getWarteschlange().send(o, p);}
	public String getData(){return "";}
	public boolean isInGame(){return ingame;}
	public boolean sindAlleBereit(){for(PlayerInfo p : getServer().getInfo().getInfos()){if(p.isBereit()){} else{return false;}} return true;}
	public void send(Object o){getServer().sendAll(o);}
	private boolean i(){return t.isInterrupted() || !GameUtil.hatSpiel();}
	public boolean isRunning(){return !i();}
	public void close(){t.interrupt();}
	public GameServer getServer(){return GameUtil.game.server;}
}
