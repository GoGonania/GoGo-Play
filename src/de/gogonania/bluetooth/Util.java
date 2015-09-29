package de.gogonania.bluetooth;

import java.net.URLEncoder;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import de.gogonania.bluetooth.gdx.Background;
import de.gogonania.bluetooth.objekte.overlays.Overlays;
import de.gogonania.bluetooth.screens.SzeneStartup;
import de.gogonania.bluetooth.util.Bild;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.Notification;
import de.gogonania.bluetooth.util.Zeit;

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
	public static boolean b;
	
	private static float lasty = -1;
	
	public void create(){
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(Gdx.graphics.getWidth()/2F, Gdx.graphics.getHeight()/2F, 0);
		cam.update();
	}

	public void render(){
		if(szene == null) setSzene(new SzeneStartup(), null);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		
		if(szene.isClosed()) szene.onOpen();
		
		if(next != null){
			animstep++;
			anim.anim(szene.cam, animstep);
			if(animstep >= anim.frames()){
				szene = next;
				next = null;
			}
		} else{
			if(Szene.dialog == null && !isSwitching() && Gdx.input.isTouched() && szene.canScroll() && Overlays.canScroll()){
				if(lasty != -1) szene.cam.translate(0, scroll*(Gdx.input.getY()-lasty));
				lasty = Gdx.input.getY();
				if(szene.cam.position.y > Gdx.graphics.getHeight()/2F) undoScroll();
				if(szene.cam.position.y < szene.getMaxY()) szene.cam.position.y = szene.getMaxY();
			} else{
				lasty = -1;
			}
		}
		
		sleep(rt);
		
		if(szene.isClosed()) szene.onOpen();
		
		try{Szene.batch.begin();}catch(Exception e){e.printStackTrace(); return;}
		Szene.batch.setProjectionMatrix(cam.combined);
		Bilder.background.render(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if(next != null) next.onRender();
		szene.onRender();
		Szene.batch.setProjectionMatrix(cam.combined);
		if(Szene.dialog != null) Szene.dialog.render();
		Overlays.render();
		szene.batch.end();
	}
	
	public static void ping(final String text, boolean t){
		if(Util.name == null) return;
		if(t){
			new Thread(new Runnable(){
					public void run(){
					    ping(text);
					}
				}).start();
		} else{
			ping(text);
		}
	}
	
	public static void kill(){
		Util.ping("Kill", false);
		MainActivity.close();
		System.exit(0);
	}
	
	private static void ping(String text){
		Internet.request("http://www.gogonania.de/Java/ping.php?text="+URLEncoder.encode(text+" - "+Util.name)+"&extra="+URLEncoder.encode("GoGoPlay-Log ("+Zeit.tag()+" "+Zeit.zeit()+")")+"&farbe=aaaaaa", true);
	}
	
	public static void setSzene(Szene s, Anim an){
		if(an != null){
			undoScroll();
			Background.reset();
		}
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
	
	public void resize(int arg0, int arg1){}
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
	public static void undoScroll(){szene.cam.position.y = Gdx.graphics.getHeight()/2F;}
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
	public static float getY(){return szene.cam.unproject(new Vector3(0, Gdx.input.getY(), 0)).y;}
	public static void vibBig(){vib(100);}
	public static void vib(){vib(30);}
	private static void vib(long time){if(vib){MainActivity.getVibrator().vibrate(time);} else{}}
	public static float score(float f){return Math.round(f*100F)/100F;}
	public static int getAppVersionCode(){return getPackageInfo().versionCode;}
}
