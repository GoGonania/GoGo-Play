package de.gogonania.bluetooth.packete;

import de.gogonania.bluetooth.Util;

public class PacketMessage {
	public String message;
	public String color;
	
	public PacketMessage(){}
	public PacketMessage(String m){message = m;}
	public PacketMessage(String m, String c){this(m); color = c;}
	
	public void show(){
		switch(color){
		case "r":
		    Util.notificationRed(message);
		break;
		case "g":
			Util.notificationGreen(message);
		break;
		default:
			Util.notification(message);
		break;
		}
	}
}
