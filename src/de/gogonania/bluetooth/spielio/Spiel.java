package de.gogonania.bluetooth.spielio;

public class Spiel implements Comparable<Spiel>{
	public int compareTo(Spiel p1){
		return name.compareTo(p1.name);
	}

	private SpielInfo info;
	private String name;
	
	public Spiel(String name){
		this.name = name;
		info = (SpielInfo) instance("Info");
	}
	
	public SpielClient getClient(){return (SpielClient) instance("Client");}
	public SpielServer getServer(){return (SpielServer) instance("Server");}
	public GameSzene getSzene(){return (GameSzene) instance("Szene");}
	private Object instance(String typ){return Spielhalle.instance(""+typ+""+name+"");}
	public SpielInfo getInfo(){return info;}
	public String getTyp(){return name;}
}
