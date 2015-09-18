package de.gogonania.bluetooth.spielio.spiele;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.io.GameUtil;
import de.gogonania.bluetooth.objekte.Button;
import de.gogonania.bluetooth.objekte.Image;
import de.gogonania.bluetooth.objekte.Text;
import de.gogonania.bluetooth.spielio.GameSzene;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.Group;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketCookieClick;
import de.gogonania.bluetooth.spielio.spiele.gogoklicker.PacketUpgrade;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.Grid;
import de.gogonania.bluetooth.objekte.Progress;

public class SzeneGoGoKlicker extends GameSzene<ClientGoGoKlicker>{
	private Image cookie;
	private Text geld;
	private Text info;
	private Button upgrade;
	private Progress p;
	private ArrayList<Group> groups = new ArrayList<Group>();
	
	public SzeneGoGoKlicker(){
		super(Bilder.background);
	}
	
	public void open(){
		final float cookiesize = Gdx.graphics.getWidth()/4F;
		cookie = new Image(cookiesize/10F, Gdx.graphics.getHeight()-cookiesize-cookiesize/8F, cookiesize, cookiesize, Bilder.cookie){
			public void click(){
				Util.vib();
				getClient().send(new PacketCookieClick());
			}
			public float getScale(){
				return isHovered(Gdx.input.isTouched())?0.9F:1;
			}
		};
		geld = new Text("", cookiesize*1.1F, Gdx.graphics.getHeight()*0.92F, Gdx.graphics.getWidth()-cookiesize*1.1F, Color.WHITE, fontbigbig);
		info = new Text("", cookiesize*1.1F, Gdx.graphics.getHeight()*0.83F, Gdx.graphics.getWidth()-cookiesize*1.1F, Color.WHITE, font);
		upgrade = new Button("", cookiesize*1.5F, Gdx.graphics.getHeight()-cookiesize*0.9F, Gdx.graphics.getWidth()-cookiesize*2F, cookiesize/5F, Bilder.cyellow, Color.BLACK){
			public void click(){
				if(getClient().geld >= getClient().a.getUpgradePreis()){
					Util.vib();
					getClient().send(new PacketUpgrade());
				}
			}
		};
		
		p = new Progress(cookiesize/10F, Gdx.graphics.getHeight()-cookiesize/6F, cookiesize, cookiesize/8F, Bilder.bar, Bilder.cyellow, Color.WHITE, font);
		
		setObjekte(cookie, geld, info, upgrade, p);
		
		Grid g = new Grid(4, 0.15F, 0.2F){
			public float getHeightMargin(){
				return cookiesize*1.1F;
			}
		};
		
		for(int i = 0; i < getClient().a.addons.size(); i++){
			groups.add(new Group(g, getClient().a.addons.get(i), i));
		}
		
		update();
	}
	
	public void update(){
		if(!GameUtil.hatSpiel()) return;
		p.setHide(getClient().a.gold || getClient().a.progress == 0);
		cookie.setBackground(getClient().a.gold?Bilder.cookiegold:Bilder.cookie);
		p.setProgress(getClient().a.progress);
		Color c = getClient().a.gold?Bilder.cyellow.getColor():Color.WHITE;
		geld.setTextColor(c);
		info.setTextColor(c);
		geld.setText(Util.makeNiceGeld(getClient().geld));
		info.setText("Pro Klick: "+Util.makeNiceGeld(getClient().a.getPerClick())+""+(getClient().a.gold?" (x2)":"")+"\nPro Sekunde: "+Util.makeNiceGeld(getClient().a.getPlus())+"");
		upgrade.setText("Upgraden ("+Util.makeNiceGeld(getClient().a.getUpgradePreis())+")");
		upgrade.setBackground(getClient().geld >= getClient().a.getUpgradePreis()?Bilder.cyellow:Bilder.clgray);
		for(Group g : groups){
			g.update();
		}
	}
}
