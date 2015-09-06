package de.gogonania.bluetooth.spielio.spiele;
import de.gogonania.bluetooth.spielio.SpielClient;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketGeldUpdate;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.Addons;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketAddon;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketUpgrade;

public class ClientGoGoKlicker extends SpielClient{
	public long geld;
	public Addons a;
	
	public void onPacket(Object o){
		if(o instanceof PacketGeldUpdate){
			geld = ((PacketGeldUpdate) o).geld;
		} else{
			if(o instanceof PacketAddon){
				a.onPacket((PacketAddon) o);
			} else{
				if(o instanceof PacketUpgrade){
					a.perclick++;
				}
			}
		}
	}

	public void daten(String d){
		a = new Addons();
		String[] dataparts = d.split("<>");
		geld = Long.parseLong(dataparts[0]);
		a.perclick = Long.parseLong(dataparts[1]);
		int i = 0;
		for(String s : dataparts[2].split("-")){
			String[] dp = s.split(",");
			a.addons.get(i).items = Integer.parseInt(dp[0]);
			a.addons.get(i).upgraded = Integer.parseInt(dp[1]);
			i++;
		}
		super.daten(d);
	}
}
