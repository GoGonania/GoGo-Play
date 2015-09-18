package de.gogonania.bluetooth.spielio.spiele.gogoklicker;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.spielio.spiele.ClientGoGoKlicker;

public class Addon{
	public String name;
	public long preis;
	public int items;
	public int upgraded;
	public long plus;
	
	public long getPreis(){
		long p = preis;
		for(int i = 0; i < items; i++){
			p += p/8;
		}
		return p;
	}
	
	public boolean kannKaufen(){
		return getPreis() <= ((ClientGoGoKlicker) GameUtil.game.getClient()).geld;
	}
	
	public boolean kannUpgraden(){
		return getUpgradePreis() <= ((ClientGoGoKlicker) GameUtil.game.getClient()).geld;
	}
	
	public long getUpgradePreis(){
		long p = preis*5;
		for(int i = 0; i < upgraded; i++){
			p += p/2;
		}
		return p;
	}
	
	public long getGesamtPlus(){
		long p = plus*items;
		for(int i = 0; i < upgraded; i++){
			p += Math.max(1, p / 3);
		}
		return p;
	}
}
