package de.gogonania.bluetooth.spielio.spiele.gogocraft;
import com.badlogic.gdx.math.Rectangle;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.spielio.spiele.gogocraft.packete.PacketBlock;
import de.gogonania.bluetooth.util.Bild;
import de.gogonania.bluetooth.util.Grid;

public class Block{
	private int id;
	private Material material;
	private Rectangle r;
	
	public Block(Material m, int i){
		material = m;
		id = i;
	}
	
	public void draw(){
		Bild b = material.getBild();
		if(b != null){
			b.getSprite().setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
			b.getSprite().draw(Szene.batch, 1);
		}
	}
	
	public PacketBlock destroy(){return modifyMaterial(Material.Himmel);}
	public PacketBlock modifyMaterial(Material m){return new PacketBlock(id, m);}
	public boolean isClicked(){return r.contains(Util.getX(), Util.getY());}
	public boolean istFrei(){return material.equals(Material.Himmel);}
	public void setMaterial(Material m){material = m;}
	public void setRectangle(Grid g){this.r = g.getRectangle(id);}
}
