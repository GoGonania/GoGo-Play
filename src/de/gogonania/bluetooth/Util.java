package de.gogonania.bluetooth;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import de.gogonania.bluetooth.szenen.SzeneStartup;
import de.gogonania.bluetooth.util.Bild;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.Fenster;
import de.gogonania.bluetooth.util.Listener;
import de.gogonania.bluetooth.util.Notification;
import java.util.ArrayList;
import de.gogonania.bluetooth.objekte.overlays.Overlays;

public class Util implements ApplicationListener{
	private static Szene szene;
	private static Szene next;
	private static Anim anim;
	private static OrthographicCamera cam;
	private static int animstep = 0;
	
	public static Notification n;
	public static float scroll;
	public static boolean a;
	public static String name;
	public static boolean vib;
	public static int rt;
	
	private static float lasty = -1;
	
	public void create(){
		Szene.init();
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(Gdx.graphics.getWidth()/2F, Gdx.graphics.getHeight()/2F, 0);
	}

	public void render(){
		if(szene == null) setSzene(new SzeneStartup(), null);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		
		if(szene.isClosed()) szene.onOpen();
		
		if(next != null){
			animstep++;
			if(animstep <= anim.frames()){
				anim.up(cam);
				if(animstep == anim.frames()){
					szene = next;
				}
			} else{
				anim.down(cam);
				if(anim.frames()*2 == animstep){
					next = null;
				}
			}
		} else{
			if(!isSwitching() && Gdx.input.isTouched() && Overlays.canScroll()){
				if(lasty != -1) cam.translate(0, scroll*(Gdx.input.getY()-lasty));
				lasty = Gdx.input.getY();
				if(cam.position.y > Gdx.graphics.getHeight()/2F) undoScroll();
				if(cam.position.y < szene.getMaxY()) cam.position.y = szene.getMaxY();
			} else{
				lasty = -1;
			}
		}
		
		sleep(rt);
		
		if(szene.isClosed()) szene.onOpen();
		
		cam.update();
		szene.onRender(cam);
	}
	
	public static void setSzene(Szene s, Anim an){
		if(an != null) undoScroll();
		if(an != null && a){
			if(!isSwitching()){
				anim = szene.getCloseAnimation() != null?szene.getCloseAnimation():(s.getOpenAnimation() != null)?s.getOpenAnimation():an;
				animstep = 0;
			}
			next = s;
		} else{
			szene = s;
		}
	}
	
	public static void kill(){
		Spielstand.save();
		MainActivity.close();
		vib();
		System.exit(0);
	}
	
	public static void killConfirm(){
		Fenster.confirm("Willst du das Spiel wirklich beenden?", ""+getAppName()+" beenden", new Listener(){
				public void ready(String s){
					if(s != null){
						Util.kill();
					}
				}
			});
	}
	
	public static String makeNiceZahl(long geld){
		String e = geld < 0?"-":""; 
		geld = Math.abs(geld); 
		StringBuilder s = new StringBuilder(""+Math.abs(geld));
		int in = 3;
		while(s.length() > in){
			s.insert(s.length()-in, ".");
			in += 4;
		}
		return ""+e+""+s.toString()+"";
	}
	
	public void resize(int arg0, int arg1) {}
    public void pause(){}
	public void resume(){}
	public void dispose(){}
	public static boolean isNumeric(String s){try{Integer.parseInt(s); return true;}catch(Exception e){return false;}}
	public static Runnable getRunnable(final Szene s, final boolean v){return new Runnable(){public void run(){if(v){Util.vib();} else{} setSzene(s);}};}
	public static void error(Exception e){notificationRed("Fehler! "+e.getClass().getSimpleName()+"\n"+e.getMessage()+""); e.printStackTrace();}
	public static String makeNiceGeld(long geld){return makeNiceZahl(geld)+" €";}
	public static Szene instanceCurrentScreen(){try{return getSzene().getClass().newInstance();}catch(Exception e){Util.error(e);} return null;}
	public static String getText(boolean b){return b?"ja":"nein";}
	public static boolean chance(int c){return random(1, 100) <= c;}
	public static String getAppName(){return MainActivity.getThis().getResources().getString(R.string.app_name);}
	public static String getAppVersion(){return "Version: "+getPackageInfo().versionName+" ("+getPackageInfo().versionCode+". Build)";}
	public static PackageInfo getPackageInfo(){try{return MainActivity.getThis().getPackageManager().getPackageInfo(MainActivity.getThis().getPackageName(), 0);}catch (PackageManager.NameNotFoundException e){error(e);} return null;}
	public static <T> T random(ArrayList<T> l){return l.get(random(0, l.size()-1));}
	public static <T> T random(T[] l){return l[random(0, l.length-1)];}
	public static void undoScroll(){cam.position.y = Gdx.graphics.getHeight()/2F;}
	public static void refreshScreen(){setSzene(instanceCurrentScreen(), null);}
	public static void setSzene(Szene s){setSzene(s, Registry.animations[0]);}
	public static Szene getSzene(){return szene;}
	public static boolean isSwitching(){return next != null;}
	public static void notification(String text){notification(text, null);}
	private static void notification(String text, Bild c){Notification n = new Notification(); n.c = c; n.text = text; n.z = System.currentTimeMillis(); Util.n = n;}
	public static void notificationRed(String text){notification(text, Bilder.cred);}
	public static void notificationGreen(String text){notification(text, Bilder.cgreen);}
	public static int random(int min, int max){return min + (int) Math.round(Math.random() * (max-min));}
	public static void sleep(long time){if(time > 0 ){try{Thread.sleep(time);}catch(Exception e){}} else{}}
	public static float getX(){return Gdx.input.getX();}
	public static float getY(){return cam.unproject(new Vector3(0, Gdx.input.getY(), 0)).y;}
	public static void vibBig(){vib(100);}
	public static void vib(){vib(30);}
	private static void vib(long time){if(vib){MainActivity.getVibrator().vibrate(time);} else{}}
	public static float score(float f){return Math.round(f*100F)/100F;}
	public static int getAppVersionCode(){return getPackageInfo().versionCode;}
}
