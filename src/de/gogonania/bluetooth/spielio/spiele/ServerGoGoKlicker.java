package de.gogonania.bluetooth.spielio.spiele;
import de.gogonania.bluetooth.MainActivity;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.IPerson;
import de.gogonania.bluetooth.spielio.Saveable;
import de.gogonania.bluetooth.spielio.SpielServer;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.Addon;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.Addons;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketAddon;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketCookieClick;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketUpdate;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketUpgrade;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketGold;

public class ServerGoGoKlicker extends SpielServer implements Saveable{
	private long geld = 0;
	private int progress;
	private int pmax = 60;
	private Addons as = new Addons();
	
	public void onPacket(IPerson p, Object o){
		if(o instanceof PacketCookieClick){
			geld += as.getPerClick();
			if(!as.gold){
			    progress++;
				if(progress == pmax){
					progress = 0;
					as.gold = true;
					pmax *= 1.5F;
					sendGold();
				}
			}
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
	
	public void startThread(){
		while(isRunning()){
			long p = as.getPlus();
			if(as.gold && Util.chance(6)){
				as.gold = false;
				sendGold();
			}
			if(p != 0 && !MainActivity.isPaused()){
				geld += p;
				sendStatus();
			}
			Util.sleep(1000);
		}
	}
	
	public void sendGold(){getServer().sendAll(new PacketGold());}
	public void sendStatus(){getServer().sendAll(new PacketUpdate(geld, progress, pmax));}

	public String getData(){
		String addons = "";
		for(Addon a : as.addons){
			addons += "-"+a.items+","+a.upgraded;
		}
		addons = addons.substring(1);
		return ""+geld+"<>"+as.perclick+"<>"+((float) progress)/((float) pmax)+"<>"+as.gold+"<>"+addons+"";
	}

	public String spielstandSave() {
		String addons = "";
		for(Addon a : as.addons){
			addons += "-"+a.items+","+a.upgraded;
		}
		addons = addons.substring(1);
		return ""+geld+"<>"+as.perclick+"<>"+progress+"<>"+pmax+"<>"+as.gold+"<>"+addons+"";
	}
	
	public void spielstandLoad(String data) {
		String[] dataparts = data.split("<>");
		geld = Long.parseLong(dataparts[0]);
		as.perclick = Long.parseLong(dataparts[1]);
		progress = Integer.parseInt(dataparts[2]);
		pmax = Integer.parseInt(dataparts[3]);
		as.gold = Boolean.parseBoolean(dataparts[4]);
		int i = 0;
		for(String s : dataparts[5].split("-")){
			String[] dp = s.split(",");
			as.addons.get(i).items = Integer.parseInt(dp[0]);
			as.addons.get(i).upgraded = Integer.parseInt(dp[1]);
			i++;
	 	}
	}
}
