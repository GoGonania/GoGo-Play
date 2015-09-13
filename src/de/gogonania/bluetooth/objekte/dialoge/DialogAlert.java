package de.gogonania.bluetooth.objekte.dialoge;

public class DialogAlert extends Dialog{
	public DialogAlert(String message, String titel) {
		super(titel, true);
		setText(message);
	}
}
