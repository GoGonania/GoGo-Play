package de.gogonania.bluetooth.spielio.spiele.reaction;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.Util;

public abstract class ReactionGame{
	public long starttime;
	private boolean wrong;
	private boolean open;
	
	public ReactionGame(){
		starttime = System.currentTimeMillis();
	}
	
	public abstract void open();
	public abstract String getText();
	public abstract long getTime();
	public abstract void create();
	public abstract void load(Object[] data);
	public abstract Object[] getData();
	
	public int getChance(){return 100;}
	
	private void richtig(){
		wrong = true;
		if(GameUtil.isOwner() && GameUtil.game.server.personen.size() > 1) Util.sleep(30);
		GameUtil.game.send(new PacketReactionRight());
	}
	
	private void falsch(){
		wrong = true;
		Util.notificationRed("Das war leider falsch");
	}
	
	public void onOpen(){open = true; open();}
	public boolean result(boolean right){if(wrong){return false;} else{if(right){richtig(); return true;} else{falsch(); return false;}}}
	public float getProgress(){return ((float)(System.currentTimeMillis()-starttime)) / ((float)getTime());}
	public boolean isTimeout(){return System.currentTimeMillis() >= starttime+getTime();}
	public boolean isOpened(){return open;}
}
