package de.gogonania.bluetooth.io;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.packete.PacketBereit;
import de.gogonania.bluetooth.packete.PacketChanged;
import de.gogonania.bluetooth.packete.PacketData;
import de.gogonania.bluetooth.packete.PacketJoined;
import de.gogonania.bluetooth.packete.PacketKick;
import de.gogonania.bluetooth.packete.PacketMessage;
import de.gogonania.bluetooth.packete.PacketPing;
import de.gogonania.bluetooth.packete.PacketPlayerList;
import de.gogonania.bluetooth.packete.PacketScreen;
import de.gogonania.bluetooth.packete.PacketServerInfo;
import de.gogonania.bluetooth.screens.ScreenKick;
import de.gogonania.bluetooth.spielio.Spiel;
import de.gogonania.bluetooth.spielio.SpielClient;
import de.gogonania.bluetooth.spielio.Spielhalle;

public class Game implements Comparable{
	public int compareTo(Object p1){
		return name.compareTo(((Game) p1).name);
	}
	
	private IPerson owner;
	private String name;
	private String detail;
	private boolean ingame;
	private Spiel spiel;
	private boolean p;
	private boolean isjoined;
	private Warteschlange w;
	public GameServer server;
	private SpielClient client;
	private GameInfo info = new GameInfo();
	private long ping;
	
	public void onPacket(Object o){
		if(o instanceof PacketServerInfo){
			name = ((PacketServerInfo) o).name;
			p = ((PacketServerInfo) o).sicher;
			detail = ((PacketServerInfo) o).detail;
			spiel = Spielhalle.get(((PacketServerInfo) o).spieltyp);
			ingame = ((PacketServerInfo) o).ingame;
		} else{
			if(o instanceof PacketKick){
				ScreenKick.show(((PacketKick) o).grund); 
			} else{
				if(o instanceof PacketJoined){
					isjoined = true;
					ingame = ((PacketJoined) o).ingame;
				} else{
					if(o instanceof PacketMessage){
						((PacketMessage) o).show();
					} else{
						if(o instanceof PacketPlayerList){
							info.onPacket(((PacketPlayerList) o).player);
						} else{
							if(o instanceof PacketPing){
								Util.notification("Ping: "+(System.currentTimeMillis() - ping)+"");
							} else{
								if(o instanceof PacketChanged){
									PacketChanged c = ((PacketChanged) o);
									info.changed(c.player, c.key, c.value);
								} else{
									if(o instanceof PacketBereit){
										Util.setSzene(getSpiel().getSzene());
									} else{
										if(o instanceof PacketScreen){
											((PacketScreen) o).show();
										} else{
											if(o instanceof PacketData){
												client.daten(((PacketData) o).data);
											} else{
												if(client != null) client.onPacket(o);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public Game(IPerson o){
		owner = o;
		w = new Warteschlange();
	}
	
	public void initGame(){
		client = getSpiel().getClient();
	}
	
	public SpielClient getClient(){return client;}
	public String getSpielName(){return getSpiel().getInfo().getName();}
	public boolean isInGame(){return ingame;}
	public GameInfo getInfo(){return info;}
	public Spiel getSpiel(){return spiel;}
	public void testPing(){ping = System.currentTimeMillis(); send(new PacketPing());}
	public boolean isJoined(){return isjoined;}
	public boolean hatBeschreibung(){return !detail.isEmpty();}
	public String getBeschreibung(){return detail;}
	public boolean hatPasswort(){return p;}
	public void close(){w.close();}
	public void send(Object o){w.send(o, owner);}
	public String getName(){return name;}
	public boolean isOwner(){return owner instanceof PersonIch;}
	public void connect() throws Exception{((Person)owner).connect();}
	public boolean equals(Object o) {return o instanceof Game && ((Game)o).owner.equals(owner);}
}
