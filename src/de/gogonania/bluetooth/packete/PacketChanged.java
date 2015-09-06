package de.gogonania.bluetooth.packete;

public class PacketChanged{
	public String player;
	public String key;
	public String value;
	
	public PacketChanged(){}
	public PacketChanged(String p, String k, String v){player = p; key = k; value = v;}
}
