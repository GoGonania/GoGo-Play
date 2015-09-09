package de.gogonania.bluetooth.spielio.spiele.gogoklicker;

public class PacketUpdate{
	public long geld;
	public float progress;
	
	public PacketUpdate(){}
	public PacketUpdate(long g, long p, long m){geld = g; progress = ((float) p)/((float) m);}
}
