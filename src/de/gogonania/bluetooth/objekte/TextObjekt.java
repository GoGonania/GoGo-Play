package de.gogonania.bluetooth.objekte;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.*;
import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.util.Bild;

public class TextObjekt extends Objekt{
	private String text = "";
	private Color color;
	private BitmapFont font;
	private GlyphLayout glyph;
	private int a = Align.center;

	public TextObjekt(String t, float x, float y, float width, float height, Bild n, Color textcolor, BitmapFont f){
		super(x, y, width, height, n);
		text = t;
		color = textcolor;
		font = f;
		glyph = new GlyphLayout();
	}
	
	public TextObjekt(String text, float x, float y, float width, float height, Bild n, Color textcolor){
		this(text, x, y, width, height, n, textcolor, Szene.font);
	}

	public void render(){
		super.render();
		renderText(text);
	}
	
	public void renderText(String text){
		Szene.batch.begin();
		color.a = getAlpha();
		glyph.setText(font, text, color, getWidth(), a, true);
	    font.draw(Szene.batch, glyph, getX(), getY() + getHeight()/2 + glyph.height/2);
		Szene.batch.end();
	}

	public Color getTextColor(){return color;}
	public void setTextColor(Color c){color = c;}
	public void setBitmapFont(BitmapFont f){font = f;}
	public String getText(){return text;}
	public void setText(String s){text = s;}
	public void setAlignment(int a){this.a = a;}
}
