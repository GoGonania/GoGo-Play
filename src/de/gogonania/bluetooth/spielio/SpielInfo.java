package de.gogonania.bluetooth.spielio;
import de.gogonania.bluetooth.io.GameUtil;

public abstract class SpielInfo{
	public abstract String getName();
	public int getMinPlayer(){return 1;}
	public int getMaxPlayer(){return -1;}
	public boolean erlaubtNachjoiner(){return true;}
	
	public boolean darfSpielStarten(){
		return GameUtil.game.server.personen.size() >= getMinPlayer();
	}
	
	public boolean darfVerbinden(){
		return getMaxPlayer() == -1 || GameUtil.game.server.personen.size() < getMaxPlayer();
	}
	
	public String getPlayerText(){
		if(getMinPlayer() == getMaxPlayer()){
			return ""+getMinPlayer();
		} else{
			if(getMaxPlayer() == -1){
				return "Mindestens "+getMinPlayer()+"";
			} else{
				return ""+getMinPlayer()+"-"+getMaxPlayer()+"";
			}
		}
	}
}
