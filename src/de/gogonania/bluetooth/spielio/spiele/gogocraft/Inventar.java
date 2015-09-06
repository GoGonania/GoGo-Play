package de.gogonania.bluetooth.spielio.spiele.gogocraft;
import de.gogonania.bluetooth.util.Info;

public class Inventar{
	private Info<Material, Integer> blocks = new Info<Material, Integer>();
	private Material m;
	
	public Inventar(){
		for(Material m : Material.values()){
			blocks.set(m, 0);
		}
	}
	
	public void add(Material m){
		Integer i = blocks.get(m);
		i += 1;
		blocks.set(m, i);
		if(getCurrent() == null) switchCurrent();
	}
	
	public void remove(Material m){
		Integer i = blocks.get(m);
		i -= 1;
		blocks.set(m, i);
		if(i == 0) switchCurrent();
	}
	
	public void switchCurrent(){
		if(m == null){
			for(Material m : blocks.getKeys()){
				if(blocks.get(m) > 0){
					this.m = m;
					return;
				}
			}
		} else{
			int stop = blocks.pos(m);
			if(check(stop+1, blocks.size())) return;
			check(0, stop+1);
		}
	}
	
	private boolean check(int from, int to){
		for(int i = from; i < to; i++){
			if(blocks.getValue(i) > 0){
				this.m = blocks.getKey(i);
				return true;
			}
		}
		return false;
	}
	
	public Material getCurrent(){
		if(m != null && blocks.get(m) > 0) return m;
		return null;
	}
}
