package de.gogonania.bluetooth.util.io;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import de.gogonania.bluetooth.MainActivity;
import de.gogonania.bluetooth.Util;

public class GoGoFile extends File{
	public GoGoFile(String path){
		super(MainActivity.getThis().getFilesDir()+"/"+path);
	}
	
	public void write(String s){
		if(!getParentFile().exists()) getParentFile().mkdirs();
		try{
			PrintWriter w = new PrintWriter(this);
			w.print(s);
			w.flush();
			w.close();
		} catch (IOException e){
			Util.error(e);
		}
	}
	
	public String read(){
		String s = "";
		try{
			Scanner sc = new Scanner(this);
			while(sc.hasNext()){
				s += sc.next();
			}
			sc.close();
			return s;
		}catch(Exception e){
			Util.error(e);
			return null;
		}
	}
	
	public static GoGoFile fromFile(File f){return new GoGoFile(f.getAbsolutePath().substring(MainActivity.getThis().getFilesDir().getAbsolutePath().length()));}
}
