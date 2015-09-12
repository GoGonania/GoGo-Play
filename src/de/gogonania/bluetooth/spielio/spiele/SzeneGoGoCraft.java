package de.gogonania.bluetooth.spielio.spiele;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.spielio.GameSzene;
import de.gogonania.bluetooth.spielio.spiele.gogocraft.Block;
import de.gogonania.bluetooth.util.Bilder;

public class SzeneGoGoCraft extends GameSzene<ClientGoGoCraft>{
	public SzeneGoGoCraft(){
		super(Bilder.himmel);
	}
	
	public void render(){
		if(!GameUtil.hatSpiel()) return;
		for(Block b : getClient().blocks){
			b.draw();
		}
	}
	
	public void click(){
		for(Block b : getClient().blocks){
			if(b.isClicked()){
				if(!b.istFrei()){
					Util.vib();
					getClient().send(b.destroy());
				}
				break;
			}
		}
	}
}
