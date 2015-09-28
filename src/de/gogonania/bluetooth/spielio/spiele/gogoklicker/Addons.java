package de.gogonania.bluetooth.spielio.spiele.gogoklicker;
import java.util.ArrayList;

public class Addons{
	public ArrayList<Addon> addons = new ArrayList<Addon>();
	public long perclick = 1;
	public boolean gold;
	public float progress;
	
	public Addons(){
		add("Toilette", 100, 1);
		add("Getränkeautomat", 800, 3);
		add("Bratwurststand", 3000, 10);
		add("Eiscafe", 20000, 60);
		add("Fast-Food Laden", 160000, 360);
		add("Burger-Kette", 1000000, 2000);
	}
	
	private void add(String name, long preis, long plus){
		Addon a = new Addon();
		a.name = name;
		a.preis = preis;
		a.plus = plus;
		addons.add(a);
	}
	
	public long onPacket(PacketAddon p){
		AddonAktion a = AddonAktion.valueOf(p.aktion);
		Addon addon = addons.get(p.id);
		switch(a){
			case Kaufen:
				long v = addon.getPreis();
				addon.items++;
				return v;
			case Upgraden:
				long u = addon.getUpgradePreis();
				addon.upgraded++;
				return u;
		}
		return 0;
	}
	
	public long getPlus(){
		long p = 0;
		for(Addon a : addons){
			p += a.getGesamtPlus();
		}
		return p;
	}
	
	public long getUpgradePreis(){
		long p = 400;
		for(int i = 1; i < perclick; i++){
			p *= 1.3F;
		}
		return p;
	}
	
	public long getPerClick(){
		return perclick * (gold?2:1);
	}
}
