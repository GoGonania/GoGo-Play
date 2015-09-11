package de.gogonania.bluetooth.spielio.spiele;
import de.gogonania.bluetooth.spielio.GameSzene;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.spielio.spiele.reaction.ReactionGame;
import com.badlogic.gdx.Gdx;
import de.gogonania.bluetooth.objekte.Progress;
import de.gogonania.bluetooth.objekte.Text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;

import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.io.PlayerInfo;

public class SzeneReaction extends GameSzene<ClientReaction>{
	public static final float barheight = Gdx.graphics.getHeight()/10F;
	private Progress p;
	private Text player;
	
	public SzeneReaction(){
		super(Bilder.background);
	}
	
	public void render(){
		if(!GameUtil.hatSpiel()) return;
		String aus = "";
		for(PlayerInfo p : GameUtil.game.getInfo().getInfos()){
			aus += "\n"+p.getName()+": "+p.getPoints()+"";
		}
		player.setText(aus.substring(1));
		if(hatGame()){
			ReactionGame g = getGame();
			p.setText(g.getText());
			p.setProgress(g.getProgress());
			if(!g.isOpened()){
				g.onOpen();
			}
		} else{
			if(getClient().time == -1){
				p.setText("Das Spiel beginnt gleich...");
			} else{
				p.setProgress(((float) (System.currentTimeMillis()-getClient().time)) / ((float) ServerReaction.timeout));
				p.setText("Nächstes Spiel wird geladen...");
			}
		}
	}
	
	public void open(){
		player = new Text("", 0, (Gdx.graphics.getHeight()-barheight)/2F, Gdx.graphics.getWidth(), Color.WHITE);
		player.setAlignment(Align.left);
		setObjekt(player);
		p = new Progress(0, Gdx.graphics.getHeight()-barheight, Gdx.graphics.getWidth(), barheight, Bilder.bar, Bilder.cyellow, Color.BLACK, fontbig);
		p.setActive(false);
		p.setAlignment(Align.center);
		setObjekt(p);
	}
	
	public ReactionGame getGame(){return getClient().game;}
	public boolean hatGame(){return getGame() != null;}
}
