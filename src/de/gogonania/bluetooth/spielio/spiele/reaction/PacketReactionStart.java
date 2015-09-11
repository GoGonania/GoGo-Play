package de.gogonania.bluetooth.spielio.spiele.reaction;

public class PacketReactionStart{
	public String name;
	public Object[] data;
	
	public PacketReactionStart(){}
	public PacketReactionStart(ReactionGame g){name = g.getClass().getCanonicalName(); data = g.getData();}
	
	public ReactionGame load(){
		try{
			ReactionGame g = (ReactionGame) Class.forName(name).newInstance();
			g.load(data);
			return g;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		} 
	}
}
