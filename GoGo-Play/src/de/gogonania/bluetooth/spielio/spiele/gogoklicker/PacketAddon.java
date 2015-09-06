package de.gogonania.bluetooth.spielio.spiele.gogoklicker;

public class PacketAddon{
	public int id;
	public String aktion;
	
	public PacketAddon(){}
	public PacketAddon(int i, AddonAktion a){id = i; aktion = a.name();}
}
