package de.gogonania.bluetooth.packete;

public class PacketServerInfo{
	public String name;
	public String detail;
	public boolean sicher;
	public String spieltyp;
	public boolean ingame;
	
	public static PacketServerInfo create(String name, boolean sicher, String detail, String spieltyp, boolean ingame){
		PacketServerInfo p = new PacketServerInfo();
		p.name = name;
		p.sicher = sicher;
		p.detail = detail;
		p.spieltyp = spieltyp;
		p.ingame = ingame;
		return p;
	}
}
