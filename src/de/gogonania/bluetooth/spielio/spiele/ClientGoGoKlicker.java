package de.gogonania.bluetooth.spielio.spiele;
import de.gogonania.bluetooth.spielio.SpielClient;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketUpdate;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.Addons;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketAddon;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketUpgrade;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketGold;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.gdx.Background;

public class ClientGoGoKlicker extends SpielClient{
	public long geld;
	public Addons a = new Addons();
	
	public void onPacket(Object o){
		if(o instanceof PacketUpdate){
			geld = ((PacketUpdate) o).geld;
			a.progress = ((PacketUpdate) o).progress;
		} else{
			if(o instanceof PacketAddon){
				a.onPacket((PacketAddon) o);
			} else{
				if(o instanceof PacketUpgrade){
					a.perclick++;
				} else{
					if(o instanceof PacketGold){
						a.gold = !a.gold;
						if(a.gold) Background.reset();
						Util.vibBig();
					}
				}
			}
		}
	}

	public void daten(String d){
		String[] dataparts = d.split("<>");
		geld = Long.parseLong(dataparts[0]);
		a.perclick = Long.parseLong(dataparts[1]);
		a.progress = Float.parseFloat(dataparts[2]);
		a.gold = Boolean.parseBoolean(dataparts[3]);
		int i = 0;
		for(String s : dataparts[4].split("-")){
			String[] dp = s.split(",");
			a.addons.get(i).items = Integer.parseInt(dp[0]);
			a.addons.get(i).upgraded = Integer.parseInt(dp[1]);
			i++;
		}
		super.daten(d);
	}
}
