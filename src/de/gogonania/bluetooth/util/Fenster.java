package de.gogonania.bluetooth.util;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import de.gogonania.bluetooth.MainActivity;
import de.gogonania.bluetooth.R;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.objekte.dialoge.Dialog;
import de.gogonania.bluetooth.objekte.dialoge.DialogConfirm;
import de.gogonania.bluetooth.objekte.dialoge.DialogWait;

public class Fenster{
	private static void v(){
		Util.vib();
	}
	
	private static void s(final AlertDialog.Builder a){
		MainActivity.getThis().runOnUiThread(new Runnable(){
			public void run(){
				a.show();
			}
		});
	}
	
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
							v();
							Gdx.app.postRunnable(new Runnable(){
									public void run(){
										l.ready(input.getText().toString().replace("\n", "").trim());
									}
								});
						}
					});
				alert.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							v();
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
		final AlertDialog.Builder alert = create(m, t);
		alert.setPositiveButton("Verstanden", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					v();
				}
			});
		s(alert);
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
					v();
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
	
	public static void select(String titel, final Object[] options, boolean z, final SelectListener s){
		if(options.length == 1){
			s.selected(0);
			return;
		}
		final AlertDialog.Builder b = create("", titel);
		if(z) b.setPositiveButton("Zufall", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					v();
					s.selected(Util.random(0, options.length-1));
				}
			});
		b.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					v();
				}
			});
		MainActivity.getThis().runOnUiThread(new Runnable(){
			public void run(){
				ListView lv = new ListView(MainActivity.getThis());
				b.setView(lv);
				final AlertDialog a = b.create();
				List<String> l = new ArrayList<String>();
				for(Object o : options){
					l.add(o.toString());
				}
				ArrayAdapter<String> aa = new ArrayAdapter<String>(MainActivity.getThis(), android.R.layout.simple_list_item_1, l);
				lv.setAdapter(aa);
				lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
						public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4){
							Util.vib();
							a.dismiss();
							s.selected(p3);
						}
					});
				a.show();
			}
		});
	}
	
	public static void progress(final Runnable r, final Runnable d, final String text){
		final Dialog dialog = new DialogWait(text);
		dialog.show();
		new Thread(new Runnable(){
			public void run(){
				r.run();
				MainActivity.getThis().runOnUiThread(new Runnable(){
					public void run(){
						dialog.hide();
						if(d != null) d.run();
					}
				});
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
