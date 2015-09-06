package de.gogonania.bluetooth.io;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

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
import de.gogonania.bluetooth.packete.PacketScreen;
import de.gogonania.bluetooth.packete.PacketServerInfo;
import de.gogonania.bluetooth.screens.ScreenKick;
import de.gogonania.bluetooth.spielio.spiele.gogocraft.packete.PacketBlock;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketAddon;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketCookieClick;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketGeldUpdate;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketUpgrade;
import de.gogonania.bluetooth.spielio.spiele.test.PacketClick;
import de.gogonania.bluetooth.spielio.spiele.test.PacketClicked;

public class Wifi {
	public static ArrayList<Game> games = new ArrayList<Game>();
	private static final int tcpport = 8823+Util.getAppVersionCode();
	private static final int udpport = tcpport+1;
	private static Client c;
	private static boolean discovering;
	private static int f;
	
	public static void init(){
		Log.set(Log.LEVEL_TRACE);
		c = new Client();
		c.addListener(new Listener(){
			public void received(Connection arg0, Object arg1){
				if(GameUtil.hatSpiel()) GameUtil.game.onPacket(arg1);
			}
			public void disconnected(Connection c){
				if(GameUtil.hatSpiel() && GameUtil.game.isJoined()) ScreenKick.show("Spiel wurde unerwartet beendet");
			}
		});
		r(c);
		c.start();
	}
	
	public static Server server() throws Exception{
		Server s = new Server();
		r(s);
		s.bind(tcpport, udpport);
		s.start();
		return s;
	}
	
	public static void scan(final int m){
		games.clear();
		f = 0;
		discovering = true;
		new Thread(new Runnable(){
			public void run() {
				List<InetAddress> as = c.discoverHosts(udpport, 4000*m);
				if(!as.isEmpty()){
					f = as.size();
					ArrayList<Client> clients = new ArrayList<Client>();
					for(final InetAddress a : as){
						final Client c = new Client();
						clients.add(c);
						r(c);
						c.start();
						try {
							connect(c, a);
							c.addListener(new Listener(){
									public void received(Connection con, Object o){
										if(o instanceof PacketServerInfo){
											Game g = new Game(new Person(a));
											g.onPacket(o);
											games.add(g);
										}
									}
								});
							c.sendTCP(new PacketRequestInfo());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					long t = System.currentTimeMillis() + 4000*m;
					while(System.currentTimeMillis() < t && f > games.size()){Util.sleep(20);}
					for(Client c : clients){
						c.close();
						c.stop();
					}
					Collections.sort(games);
				}
		        discovering = false;
			}}).start();
	}
	
	public static boolean isDiscovering(){return discovering;}
	public static void connect(Client c, InetAddress a) throws Exception{c.connect(2000, a, tcpport, udpport);}
	public static void send(Object o){c.sendTCP(o);};
	public static void connect(InetAddress a) throws Exception{connect(c, a);}
	public static void close(){if(GameUtil.hatSpiel()){GameUtil.game.close(); games.remove(GameUtil.game); GameUtil.game = null;} else{} c.close();}
	public static int getFoundDevices(){return f;}
	
	private static void r(EndPoint e){
		Kryo k = e.getKryo();
		
		k.register(String.class);
		k.register(String[].class);
		k.register(Boolean.class);
		k.register(Long.class);
		k.register(PacketJoin.class);
		k.register(PacketJoined.class);
		k.register(PacketKick.class);
		k.register(PacketScreen.class);
		k.register(PacketServerInfo.class);
		k.register(PacketRequestInfo.class);
		k.register(PacketMessage.class);
		k.register(PacketPlayerList.class);
		k.register(PacketPing.class);
		k.register(PacketPause.class);
		k.register(PacketChanged.class);
		k.register(PacketBereit.class);
		k.register(PacketRequestData.class);
		k.register(PacketData.class);
		
		k.register(PacketClick.class);
		k.register(PacketClicked.class);
		
		k.register(PacketBlock.class);
		
		k.register(PacketCookieClick.class);
		k.register(PacketGeldUpdate.class);
		k.register(PacketAddon.class);
		k.register(PacketUpgrade.class);
	}
}
