package de.gogonania.bluetooth.spielio.spiele;
import de.gogonania.bluetooth.spielio.Saveable;
import de.gogonania.bluetooth.spielio.SpielServer;
import de.gogonania.bluetooth.io.IPerson;
import de.gogonania.bluetooth.spielio.spiele.gogocraft.Biom;
import java.util.ArrayList;
import de.gogonania.bluetooth.spielio.spiele.gogocraft.Material;
import de.gogonania.bluetooth.spielio.spiele.gogocraft.packete.PacketBlock;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.SelectListener;
import de.gogonania.bluetooth.Util;

public class ServerGoGoCraft extends SpielServer implements Saveable{
	private Biom currentBiom = Biom.Normal;
	private ArrayList<Material> blocks = new ArrayList<Material>();
	private int inaline = 16;
	
	public void init(){
		for(int s = 0; s < currentBiom.getStufen(); s++){
			for(int i = 0; i < inaline; i++){
				int id = s * inaline + i;
				blocks.add(currentBiom.getMaterial(s, s == 0?null:blocks.get(id - inaline)));
			}
		}
	}
	
	public void startThread(){
		while(isRunning()){
			Util.sleep(300);
			tickAll();
		}
	}
	
	public void create(){
		Fenster.select("Welche Welt willst du laden?", Biom.values(), true, new SelectListener(){
				public void selected(int id){
					currentBiom = Biom.values()[id];
				}
			});
	}
	
	public void onPacket(IPerson p, Object o){
		if(o instanceof PacketBlock){
			PacketBlock b = (PacketBlock) o;
			modify(b.id, Material.valueOf(b.material));
		}
	}
	
	public void tickAll(){
		for(int i = 0; i < blocks.size(); i++){
			Material m = blocks.get(i);
			if(i >= inaline){
				Material up = blocks.get(i - inaline);
				if(Util.chance(20) && up.equals(Material.Himmel) && m.equals(Material.Erde)){
					modify(i, Material.Gras);
				}
			}
		}
	}
	
	public void modify(int id, Material m){
		blocks.set(id, m);
		getServer().sendAll(new PacketBlock(id, m));
	}

	public String getData(){
		String blocks = "";
		for(Material m : this.blocks){
			blocks += "-"+m.name();
		}
		blocks = blocks.substring(1);
		return ""+currentBiom.name()+"<>"+inaline+"<>"+blocks+"";
	}

	public String spielstandSave() {
		return getData();
	}

	public void spielstandLoad(String data) {
		String[] dataparts = data.split("<>");
		currentBiom = Biom.valueOf(dataparts[0]);
		inaline = Integer.parseInt(dataparts[1]);
		for(String m : dataparts[2].split("-")){
			blocks.add(Material.valueOf(m));
		}
	}
}
