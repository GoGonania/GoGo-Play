package de.gogonania.bluetooth.spielio.spiele.gogocraft;
import java.util.ArrayList;
import de.gogonania.bluetooth.Util;

public enum Biom{
	Normal(15, new Material[]{Material.Himmel, Material.Himmel, Material.Himmel, Material.Erde, Material.Erde, Material.Erde, Material.Stein, Material.Stein, Material.Stein}),
	Steinwüste(20, new Material[]{Material.Himmel, Material.Himmel, Material.Himmel, Material.Stein, Material.Stein, Material.Stein, Material.Stein, Material.Stein, Material.Stein}),
	Höhle(30, new Material[]{Material.Stein, Material.Stein, Material.Himmel, Material.Himmel, Material.Erde, Material.Stein, Material.Stein, Material.Stein, Material.Stein});
	
	private ArrayList<Material> stufen = new ArrayList<Material>();
	private int chaos;
	
	Biom(int c, Material[] levels){
		chaos = c;
		for(Material m : levels) stufen.add(m);
	}
	
	public Material getMaterial(int level, Material m){
		if(m != null && Util.chance(chaos)) return m;
		return stufen.get(level);
	}
	
	public int getStufen(){
		return stufen.size();
	}
}
