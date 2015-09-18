package de.gogonania.bluetooth.util.io;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Environment;
import de.gogonania.bluetooth.MainActivity;
import de.gogonania.bluetooth.Util;

public class GoGoFile extends File{
	public GoGoFile(String path){
		super(Environment.getExternalStorageDirectory()+"/"+Util.getAppName()+"/"+path);
	}
	
	public void write(String s){
		if(!getParentFile().exists()) getParentFile().mkdirs();
		try{
			FileOutputStream out = new FileOutputStream(this);
			for(char c : s.toCharArray()){
				out.write(c);
			}
			out.close();
		} catch (IOException e){
			Util.error(e);
		}
	}
	
	public String read(){
		String s = "";
		try{
			InputStream in = new FileInputStream(this);
			int b = 0;
			while((b = in.read()) != -1){s += (char) b;}
			in.close();
			return s;
		}catch(Exception e){
			Util.error(e);
			return null;
		}
	}
	
	public static GoGoFile fromFile(File f){return new GoGoFile(f.getAbsolutePath().substring(MainActivity.getThis().getFilesDir().getAbsolutePath().length()));}
}
