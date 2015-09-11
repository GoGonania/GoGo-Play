package de.gogonania.bluetooth.spielio.spiele.reaction;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.Util;

public abstract class ReactionGame{
	public long endtime;
	private boolean wrong;
	private boolean open;
	
	public abstract void open();
	public abstract String getText();
	public abstract long getTime();
	public abstract void create();
	public abstract void load(Object[] data);
	public abstract Object[] getData();
	
	private void richtig(){
		wrong = true;
		GameUtil.game.send(new PacketReactionRight());
	}
	
	private void falsch(){
		wrong = true;
		Util.notificationRed("Das war leider falsch");
	}
	
	public void onOpen(){open = true; open();}
	public boolean result(boolean right){if(right && !wrong){richtig(); return false;} else{falsch(); return false;}}
	public float getProgress(){return 1F - (((float) (endtime - System.currentTimeMillis())) / ((float) getTime()));}
	public boolean isTimeout(){return System.currentTimeMillis() >= endtime;}
	public boolean isOpened(){return open;}
}
