package de.gogonania.bluetooth.io;

import java.net.InetAddress;

import com.esotericsoftware.kryonet.Connection;

public class Person extends IPerson{
	private InetAddress a;
	private Connection c;
	
	public Person(InetAddress a){
		this.a = a;
	}
	
	public Person(Connection c){
		this(c.getRemoteAddressTCP().getAddress());
		this.c = c;
	}
	
	public void send(Object o) {
		if(c != null){
			c.sendTCP(o);
		} else{
			Wifi.send(o);
		}
		
	}
	
	public void close(){c.close();}
	public void connect() throws Exception{Wifi.connect(a);}
	public boolean equals(Object o){return o instanceof Person && equals(((Person) o).a);}
	public boolean equals(InetAddress aa){return aa.getHostAddress().equals(a.getHostAddress());}
}
