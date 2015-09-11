package de.gogonania.bluetooth.spielio.spiele;
import de.gogonania.bluetooth.spielio.GameSzene;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.spielio.spiele.reaction.ReactionGame;
import de.gogonania.bluetooth.Util;
import com.badlogic.gdx.Gdx;
import de.gogonania.bluetooth.objekte.Progress;
import com.badlogic.gdx.graphics.Color;
import de.gogonania.bluetooth.io.GameUtil;

public class SzeneReaction extends GameSzene<ClientReaction>{
	private Progress p;
	
	public SzeneReaction(){
		super(Bilder.background);
	}
	
	public static float getBarHeight(){
		return Gdx.graphics.getHeight()/10F;
	}
	
	public void render(){
		if(!GameUtil.hatSpiel()) return;
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
		p = new Progress(0, Gdx.graphics.getHeight()-getBarHeight(), Gdx.graphics.getWidth(), getBarHeight(), Bilder.bar, Bilder.cyellow, Color.BLACK, fontbig);
		p.setActive(false);
		setObjekt(p);
	}
	
	public ReactionGame getGame(){return getClient().game;}
	public boolean hatGame(){return getGame() != null;}
}
