package de.gogonania.bluetooth.io;

import java.util.ArrayList;
import com.esotericsoftware.kryonet.Connection;
import de.gogonania.bluetooth.Util;

public class Warteschlange {
	class Item{
		IPerson p;
		Object o;
	}
	private Thread t;
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public Warteschlange(){
		t = new Thread(new Runnable(){
			public void run() {
				while(!Thread.currentThread().isInterrupted()){
					if(items.size() == 0){
						Util.sleep(1);
						continue;
					}
					Item i = items.get(0);
					if(i == null) continue;
					i.p.send(i.o);
					items.remove(0);
				}
			}
		});
		t.start();
	}
	
	public boolean isWorking(){return !items.isEmpty();}
	public void close(){t.interrupt();}
	public void send(Object o, IPerson p){Item i = new Item(); i.o = o; i.p = p; items.add(i);}
	public void send(Object o, Connection c){send(o, new Person(c));}
}
