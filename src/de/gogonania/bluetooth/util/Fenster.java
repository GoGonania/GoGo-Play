package de.gogonania.bluetooth.util;
import com.badlogic.gdx.Gdx;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.SeekBar;
import de.gogonania.bluetooth.MainActivity;
import de.gogonania.bluetooth.R;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.objekte.dialoge.Dialog;
import de.gogonania.bluetooth.objekte.dialoge.DialogAlert;
import de.gogonania.bluetooth.objekte.dialoge.DialogConfirm;
import de.gogonania.bluetooth.objekte.dialoge.DialogSelect;
import de.gogonania.bluetooth.objekte.dialoge.DialogWait;

public class Fenster{
	public static void prompt(String m, String t, Listener l){
		prompt(m, t, "", l);
	}

	public static void prompt(final String m, final String t, final String p, final Listener l){
		MainActivity.getThis().runOnUiThread(new Runnable(){
			public void run(){
				final AlertDialog.Builder alert = create(m, t);
				final EditText input = new EditText(MainActivity.getThis());
				input.setText(p);
				input.setSelection(p.length());
				alert.setView(input);
				alert.setPositiveButton("Fertig", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							Util.vib();
							Gdx.app.postRunnable(new Runnable(){
									public void run(){
										l.ready(input.getText().toString().replace("\n", "").trim());
									}
								});
						}
					});
				alert.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							Util.vib();
							Gdx.app.postRunnable(new Runnable(){
									public void run(){
										l.ready(null);
									}
								});
						}
					});
				alert.show();
			}
		});
	}

	public static void confirm(String m, final Listener l){
		new DialogConfirm(m){
			public void ok(String s) {
				l.ready(s);
			}
		}.show();
	}

	public static void alert(String m, String t){
		new DialogAlert(m, t).show();
	}
	
	private static AlertDialog.Builder create(String m, String t){
		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.getThis());
		alert.setTitle(t);
		alert.setMessage(m);
		alert.setIcon(R.drawable.logo);
		alert.setCancelable(false);
		return alert;
	}
	
	public static void slider(final String m, String t, final SliderListener l, final int min, int max, int p){
		final AlertDialog.Builder b = create(m.replace("[value]", ""+p+""), t);
		b.setCancelable(false);
		final SeekBar bar = new SeekBar(MainActivity.getThis());
		bar.setMax(max-min);
		bar.setProgress(p - min);
		b.setView(bar); 
		b.setPositiveButton("Fertig", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton){
					Util.vib();
					l.ok(bar.getProgress()+min);
				}
			});
		MainActivity.getThis().runOnUiThread(new Runnable(){
			public void run(){
				final AlertDialog a = b.create();
				bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
						public void onProgressChanged(SeekBar p1, int p2, boolean p3){
							a.setMessage(m.replace("[value]", ""+(min + p2)+""));
						}
						public void onStartTrackingTouch(SeekBar p1){}
						public void onStopTrackingTouch(SeekBar p1){}
					});
				a.show();
			}
		});
	}
	
	public static void select(String t, Object[] os, SelectListener s){select(t, os, 3, s);}
	public static void select(String titel, Object[] options, int inarow, final SelectListener s){
		if(options.length == 1){
			s.selected(0);
			return;
		}
		new DialogSelect(titel, options, inarow) {
			public void selected(int id) {
				s.selected(id);
			}
		}.show();
	}
	
	public static void progress(final Runnable r, final Runnable d, final String text){
		final Dialog dialog = new DialogWait(text);
		dialog.show();
		new Thread(new Runnable(){
			public void run(){
				r.run();
				dialog.hide();
				if(d != null) d.run();
			}
		}).start();
	}

	public static void progress(final long time, Runnable r, String text){
		progress(new Runnable(){
				public void run(){
					Util.sleep(time);
				}
			}, r, text);
	}

	public static void progress(long time, String text){
		progress(time, null, text);
	}
}
