package de.gogonania.bluetooth.spielio.spiele;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

import de.gogonania.bluetooth.spielio.SpielClient;
import de.gogonania.bluetooth.spielio.spiele.gogocraft.Biom;
import de.gogonania.bluetooth.spielio.spiele.gogocraft.Block;
import de.gogonania.bluetooth.spielio.spiele.gogocraft.Inventar;
import de.gogonania.bluetooth.spielio.spiele.gogocraft.Material;
import de.gogonania.bluetooth.spielio.spiele.gogocraft.packete.PacketBlock;
import de.gogonania.bluetooth.util.Grid;

public class ClientGoGoCraft extends SpielClient{
	public ArrayList<Block> blocks = new ArrayList<Block>();
	public int inaline;
	public Biom currentBiom;
	public Inventar inv;
	
	public void onPacket(Object o){
		if(o instanceof PacketBlock){
			PacketBlock b = (PacketBlock) o;
			blocks.get(b.id).setMaterial(Material.valueOf(b.material));
		}
	}

	public void daten(String d){
		String[] dataparts = d.split("<>");
		currentBiom = Biom.valueOf(dataparts[0]);
		inaline = Integer.parseInt(dataparts[1]);
		for(String m : dataparts[2].split("-")){
			blocks.add(new Block(Material.valueOf(m), blocks.size()));
		}
		inv = new Inventar();
		final float height = Gdx.graphics.getHeight() / ((float) currentBiom.getStufen());
	    Grid g = new Grid(inaline, 0, 1){public float getHeight(){return height;}};
		for(Block b : blocks){
			b.setRectangle(g);
		}
		super.daten(d);
	}
}
