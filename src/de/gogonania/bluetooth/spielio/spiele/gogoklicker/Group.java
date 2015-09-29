package de.gogonania.bluetooth.spielio.spiele.gogoklicker;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.objekte.Text;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.Grid;
import de.gogonania.bluetooth.util.Fenster;

public class Group{
	private Button kaufen;
	private Button info2;
	private Button upgrade;
	private Text info;
	private Addon addon;
	private int id;
	private Szene szene;
	
	public Group(Szene s, Grid g, Addon a, int i){
		addon = a;
		szene = s;
		id = i;
		
		final Rectangle r1 = g.getRectangle();
		Rectangle r2 = g.getRectangle();
		Rectangle r5 = g.getRectangle();
		Rectangle r4 = g.getRectangle();
		
		info = new Text(addon.name, r1.getX(), r1.getY(), r1.getWidth(), Color.WHITE, Szene.font){
			public float getHeight(){
				return r1.getHeight();
			}
		};
		kaufen = new Button("", r2.getX(), r2.getY(), r2.getWidth(), r2.getHeight(), Bilder.cgreen, Color.BLACK, Szene.fontnotification){
			public void click(){
				if(addon.kannKaufen()){
					Util.vib();
					GameUtil.game.getClient().send(new PacketAddon(id, AddonAktion.Kaufen));
				}
			}
		};
		upgrade = new Button("", r5.getX(), r5.getY(), r5.getWidth(), r5.getHeight(), Bilder.cyellow, Color.BLACK, Szene.fontnotification){
			public void click(){
				if(addon.kannUpgraden()){
					Util.vib();
					GameUtil.game.getClient().send(new PacketAddon(id, AddonAktion.Upgraden));
				}
			}
		};
		info2 = new Button("Info", r4.getX(), r4.getY(), r4.getWidth(), r4.getHeight(), Bilder.normal, Color.BLACK, Szene.fontnotification){
			public void click(){
				Util.vib();
				Fenster.alert("Gekauft: "+addon.items+"\nUpgegradet: "+addon.upgraded+" Mal\n\nEinkommen (Einzeln): "+Util.makeNiceGeld(addon.plus)+"\nEinkommen (Alle): "+Util.makeNiceGeld(addon.items*addon.plus)+"\nEinkommen (Alle + Upgrades): "+Util.makeNiceGeld(addon.getGesamtPlus())+"", "Info über "+addon.name+"");
			}
		};
		
		szene.setObjekte(info, kaufen, upgrade, info2);
	}
	
	public void update(){
		info2.setHide(addon.items == 0);
		upgrade.setHide(addon.items == 0);
		upgrade.setText("Upgraden ("+Util.makeNiceGeld(addon.getUpgradePreis())+")");
		kaufen.setText("Kaufen ("+Util.makeNiceGeld(addon.getPreis())+")");
		kaufen.setBackground(addon.kannKaufen()?Bilder.cgreen:Bilder.clgray);
		upgrade.setBackground(addon.kannUpgraden()?Bilder.cyellow:Bilder.clgray);
	}
}
