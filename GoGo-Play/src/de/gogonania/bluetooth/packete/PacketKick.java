package de.gogonania.bluetooth.packete;

public class PacketKick{
	public String grund;
	
	public static PacketKick create(String grund){
		PacketKick k = new PacketKick();
		k.grund = grund;
		return k;
	}
}
