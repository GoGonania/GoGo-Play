package de.gogonania.bluetooth.io;

public abstract class IPerson {
	private String name;
	
	public abstract void send(Object o);
	public void setName(String n){name = n;}
	public String getName(){return name;}
	
	public boolean equals(Object o) {return o instanceof IPerson && ((IPerson)o).getName().equalsIgnoreCase(name);}
}
