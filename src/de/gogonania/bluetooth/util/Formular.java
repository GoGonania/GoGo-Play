package de.gogonania.bluetooth.util;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import de.gogonania.bluetooth.Szene;
import com.badlogic.gdx.math.Rectangle;
import de.gogonania.bluetooth.objekte.Text;
import de.gogonania.bluetooth.Util;
import com.badlogic.gdx.graphics.Color;
import de.gogonania.bluetooth.objekte.Input;
import java.util.ArrayList;

public class Formular extends Grid{
	private ArrayList<Input> inputs = new ArrayList<Input>();
	
	public Szene getSzene(){
		return Util.getSzene();
	}
	public BitmapFont getFont(){
		return Szene.fontbig;
	}
	
	public float getHeight(){
		return getFont().getCapHeight()*2F;
	}
	
	public Formular(){
		super(2, 0.1F, 0.4F);
	}
	
	public void addInput(String text, String label, boolean empty, boolean z){
		final Rectangle r1 = getRectangle();
		Rectangle r2 = getRectangle();
		Text t = new Text(label+""+(empty?" (Optional)":"")+":", r1.getX(), r1.getY(), r1.getWidth(), Color.WHITE, getFont()){
			public float getHeight(){
				return r1.getHeight();
			}
		};
		Input i = new Input(text, label, r2.getX(), r2.getY(), r2.getWidth(), r2.getHeight(), getFont(), empty, z);
		inputs.add(i);
		getSzene().setObjekte(t, i);
	}
	
	public String[] getValues(){
		String[] v = new String[inputs.size()];
		int i = 0;
		for(Input in : inputs){
			v[i] = in.getText();
			i++;
		}
		return v;
	}
}
