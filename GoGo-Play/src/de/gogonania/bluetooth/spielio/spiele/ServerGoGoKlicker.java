package de.gogonania.bluetooth.spielio.spiele;
import de.gogonania.bluetooth.MainActivity;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.IPerson;
import de.gogonania.bluetooth.spielio.SpielServer;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.Addon;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.Addons;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketAddon;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketCookieClick;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketGeldUpdate;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketUpgrade;

public class ServerGoGoKlicker extends SpielServer{
	private long geld;
	private Addons as;

	public void init(){
		as = new Addons();
		as.perclick = 1;
	}
	
	public void onPacket(IPerson p, Object o){
		if(o instanceof PacketCookieClick){
			geld += as.perclick;
			sendStatus();
		} else{
			if(o instanceof PacketAddon){
				PacketAddon packet = ((PacketAddon) o);
				long d = as.onPacket(packet);
				geld -= Math.min(geld, d);
				sendStatus();
				getServer().sendAll(packet);
			} else{
				if(o instanceof PacketUpgrade){
					geld -= Math.min(geld, as.getUpgradePreis());
					as.perclick++;
					sendStatus();
					getServer().sendAll(o);
				}
			}
		}
	}
	
	public void start(){
		while(isRunning()){
			long p = as.getPlus();
			if(p != 0 && !MainActivity.isPaused()){
				geld += p;
				sendStatus();
			}
			Util.sleep(1000);
		}
	}
	
	public void sendStatus(){
		getServer().sendAll(new PacketGeldUpdate(geld));
	}

	public String getData(){
		String addons = "";
		for(Addon a : as.addons){
			addons += "-"+a.items+","+a.upgraded;
		}
		addons = addons.substring(1);
		return ""+geld+"<>"+as.perclick+"<>"+addons+"";
	}
}
