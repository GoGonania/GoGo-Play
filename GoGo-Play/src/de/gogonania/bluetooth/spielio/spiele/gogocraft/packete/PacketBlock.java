package de.gogonania.bluetooth.spielio.spiele.gogocraft.packete;
import de.gogonania.bluetooth.spielio.spiele.gogocraft.Material;

public class PacketBlock{
	public int id;
	public String material;
	
	public PacketBlock(){}
	public PacketBlock(int i, Material m){id = i; material = m.name();}
}
