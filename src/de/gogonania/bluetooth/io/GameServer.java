package de.gogonania.bluetooth.io;

import de.gogonania.bluetooth.packete.*;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import de.gogonania.bluetooth.Spielstand;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.screens.ScreenLobby;
import de.gogonania.bluetooth.spielio.Spiel;
import de.gogonania.bluetooth.spielio.SpielServer;
import de.gogonania.bluetooth.spielio.save.Spielsave;
import de.gogonania.bluetooth.spielio.save.Spielsaves;
import de.gogonania.bluetooth.util.Confirms;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.SelectListener;
import java.util.ArrayList;

public class GameServer {
	private Spiel spiel;
	private String name;
	private String passwort;
	private String detail;
	private boolean lobby = true;
	private Server s;
	public SpielServer gs;
	private Warteschlange w;
	private boolean shutdown;
	public ArrayList<IPerson> personen = new ArrayList<IPerson>();
	private GameInfo info = new GameInfo();
	private String[] para;
	private Spielsave save;
	
	public void onPacket(final Connection c, IPerson p, Object o){
		if(o instanceof PacketRequestInfo){
			c.sendTCP(createInfo());
		} else{
			if(o instanceof PacketJoin){
				final String name = ((PacketJoin) o).name;
				IPerson pp = getPerson(name);
				if(spiel.getInfo().darfVerbinden()){
					if(!gs.isInGame() || GameUtil.game.getSpiel().getInfo().erlaubtNachjoiner()){
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
						w.send(PacketKick.create("Das Spiel wurde bereits gestartet"), c);
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
	
	public void doStuff(){
		gs = spiel.getServer();
		PersonIch p = new PersonIch(false);
		w.send(createInfo(), p);
		add(p);
		gs.start(save);
		if(save == null) gs.create();
		Util.setSzene(new ScreenLobby());
	}
	
	public void remove(Person p){
		personen.remove(p);
		PacketPlayerList pp = PacketPlayerList.create();
		info.onPacket(pp.player);
		sendAll(pp);
		sendMessage(p.getName()+" hat das Spiel verlassen", "r");
		if(!shutdown && !GameUtil.game.getSpiel().getInfo().darfSpielStarten()) close("Die Mindestanzahl an Spielern wurde nicht eingehalten");
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

	public GameServer(String[] p, Spiel spiel, Spielsave save){
		this.spiel = spiel;
		name = p[0];
		try{detail = p[1];}catch(Exception e){detail = "";}
		try{passwort = p[2];}catch(Exception e){passwort = "";}
		para = p;
		this.save = save;
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
		for(int i = 1; i < personen.size(); i++){
			w.send(o, personen.get(i));
		}
		w.send(o, personen.get(0));
	}
	
	public void save(Spielsave s){
		s.create(spiel.getTyp(), para, gs.getSaveable().spielstandSave());
		Util.notificationGreen("Spiel wurde gespeichert!");
	}
	
	public void save(){
		String[] o = new String[Spielsaves.saves.size()+1];
		for(int i = 0; i < Spielsaves.saves.size(); i++){
			o[i] = Spielsaves.saves.get(i).getDetail();
		}
		o[o.length-1] = "Neuen Spielstand anlegen...";
		Fenster.select("Wo willst du das Spiel speichern?", o, 2, new SelectListener(){
				public void selected(int id){
					if(id < Spielsaves.saves.size()){
						Confirms.saveGame(Spielsaves.saves.get(id));
					} else{
						Spielsave s = new Spielsave(Spielstand.saveSpiele.create());
						Spielsaves.saves.add(s);
						save(s);
					}
				}
			});
	}
	
	public void close(String grund){
		shutdown = true;
		sendAll(PacketKick.create(grund));
		new Thread(new Runnable(){
			public void run() {
				while(w.isWorking()){Util.sleep(10);}
				gs.close();
				w.close();
				s.close();
		        s.stop();
			}}).start();
	}
	
	public void startGame(){lobby = false;}
	public Warteschlange getWarteschlange(){return w;}
	public GameInfo getInfo(){return info;}
	public boolean inLobby(){return lobby;}
	public PacketServerInfo createInfo(){return PacketServerInfo.create(name, hatPasswort(), detail, spiel.getTyp(), !lobby);}
	public boolean hatPasswort(){return !passwort.isEmpty();}
}
