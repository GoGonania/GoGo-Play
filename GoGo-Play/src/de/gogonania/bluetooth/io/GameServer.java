package de.gogonania.bluetooth.io;

import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.packete.PacketBereit;
import de.gogonania.bluetooth.packete.PacketChanged;
import de.gogonania.bluetooth.packete.PacketData;
import de.gogonania.bluetooth.packete.PacketJoin;
import de.gogonania.bluetooth.packete.PacketJoined;
import de.gogonania.bluetooth.packete.PacketKick;
import de.gogonania.bluetooth.packete.PacketMessage;
import de.gogonania.bluetooth.packete.PacketPause;
import de.gogonania.bluetooth.packete.PacketPing;
import de.gogonania.bluetooth.packete.PacketPlayerList;
import de.gogonania.bluetooth.packete.PacketRequestData;
import de.gogonania.bluetooth.packete.PacketRequestInfo;
import de.gogonania.bluetooth.packete.PacketServerInfo;
import de.gogonania.bluetooth.screens.ScreenLobby;
import de.gogonania.bluetooth.spielio.Spiel;
import de.gogonania.bluetooth.spielio.SpielServer;

public class GameServer {
	private Spiel spiel;
	private String name;
	private String passwort;
	private String detail;
	private boolean lobby = true;
	private Server s;
	private SpielServer gs;
	private Warteschlange w;
	private boolean shutdown;
	public ArrayList<IPerson> personen = new ArrayList<IPerson>();
	private GameInfo info = new GameInfo();
	
	public void onPacket(final Connection c, IPerson p, Object o){
		if(o instanceof PacketRequestInfo){
			c.sendTCP(createInfo());
		} else{
			if(o instanceof PacketJoin){
				final String name = ((PacketJoin) o).name;
				IPerson pp = getPerson(name);
				if(spiel.getInfo().darfVerbinden()){
					if(pp == null){
						if(!hatPasswort() || passwort.equals(((PacketJoin) o).passwort)){
							Person np = new Person(c);
							np.setName(name);
							c.setName(name);
							add(np);
						} else{
							w.send(PacketKick.create("Falsches Passwort"), c);
						}
					} else{
						w.send(PacketKick.create("Dieser Name ist bereits im Spiel"), c);
					}
				} else{
					w.send(PacketKick.create("Das Spiel ist bereits voll"), c);
				}
			} else{
				if(o instanceof PacketMessage){
					String m = ((PacketMessage) o).message;
					sendMessage(p.getName()+": "+m+"", "");
				} else{
					if(o instanceof PacketPing){
						w.send(o, c);
					} else{
						if(o instanceof PacketPause){
							boolean pp = ((PacketPause) o).p;
							boolean ch = pp != info.getInfo(p.getName()).isPaused();
							if(ch){
								info.changedNotify(p.getName(), "paused", pp+"");
								sendMessage(""+p.getName()+" "+(pp?"hat das Spiel pausiert":"ist wieder da")+"", "");
							}
						} else{
							if(o instanceof PacketBereit){
								if(gs.isInGame()){
									w.send(new PacketBereit(), p);
								} else{
									info.changedNotify(p.getName(), "ready", "true");
								}
							} else{
								if(o instanceof PacketRequestData){
									PacketData d = new PacketData();
									d.data = gs.getData();
									w.send(d, p);
								} else{
									gs.onPacket(p, o);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void initServer(){
		gs.init();
	}
	
	public void startGame(){
		lobby = false;
	}
	
	public void doStuff(){
		gs = spiel.getServer();
		PersonIch p = new PersonIch(false);
		w.send(createInfo(), p);
		add(p);
		gs.create();
		Util.setSzene(new ScreenLobby());
	}
	
	public void remove(Person p){
		personen.remove(p);
		PacketPlayerList pp = PacketPlayerList.create();
		info.onPacket(pp.player);
		sendAll(pp);
		sendMessage(p.getName()+" hat das Spiel verlassen", "r");
	}
	
	public void add(IPerson p){
		personen.add(p);
		w.send(new PacketJoined(!inLobby()), p);
		PacketPlayerList pp = PacketPlayerList.create();
		info.onPacket(pp.player);
		sendAll(pp);
		for(PlayerInfo pi : getInfo().getInfos()){
			for(String key : pi.getInfo().getKeys()){
				w.send(new PacketChanged(pi.getName(), key, pi.getInfo().get(key)), p);
			}
		}
		sendMessage(""+p.getName()+" ist dem Spiel beigetreten", "g");
	}

	public GameServer(String[] p, Spiel spiel){
		this.spiel = spiel;
		this.name = p[0];
		this.detail = p[1];
		this.passwort = p[2];
		w = new Warteschlange();
		try {
			s = Wifi.server();
			s.addListener(new Listener(){
				public void received(Connection c, Object o) {
					onPacket(c, getPerson(c), o);
				}
				public void disconnected(Connection c){
					if(shutdown) return;
					IPerson p = getPerson(c.toString());
					if(p != null){
						Person pp = (Person) p;
						remove(pp);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public IPerson getPerson(Connection c){
		for(IPerson p : personen){
			if(p instanceof Person && ((Person) p).equals(c.getRemoteAddressTCP().getAddress())){
				return p;
			}
		}
		return null;
	}
	
	public IPerson getPerson(String name){
		for(IPerson p : personen){
			if(p.getName().equalsIgnoreCase(name)){
				return p;
			}
		}
		return null;
	}
	
	public void sendMessage(String message, String color){
		if(shutdown) return;
		sendAll(new PacketMessage(message, color));
	}
	
	public void sendAll(Object o){
		for(IPerson p : personen){
			w.send(o, p);
		}
	}
	
	public void close(){
		shutdown = true;
		sendAll(PacketKick.create("Spiel wurde vom Besitzer beendet"));
		new Thread(new Runnable(){
			public void run() {
				while(w.isWorking()){Util.sleep(10);}
				gs.close();
				w.close();
				s.close();
		        s.stop();
			}}).start();
	}
	
	public Warteschlange getWarteschlange(){return w;}
	public GameInfo getInfo(){return info;}
	public boolean inLobby(){return lobby;}
	public PacketServerInfo createInfo(){return PacketServerInfo.create(name, hatPasswort(), detail, spiel.getTyp(), !lobby);}
	public boolean hatPasswort(){return !passwort.isEmpty();}
}
