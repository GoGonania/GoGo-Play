package de.gogonania.bluetooth.objekte;
import com.badlogic.gdx.utils.Align;

import de.gogonania.bluetooth.util.Bild;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Progress extends TextObjekt{
	private Bild pb;
	private float p;
	private float m = getHeight()*0.08F;
	
	public Progress(float x, float y, float width, float height, Bild n, Bild p, Color fc, BitmapFont f){
		super("", x, y, width, height, n, fc, f);
		pb = p;
		setAlignment(Align.left);
	}
	
	public void render(ShapeRenderer s, SpriteBatch batch){
		super.render(s, batch);
		batch.begin();
		pb.getSprite().setBounds(getX()+m, getY()+m, (getWidth()-2*m)*p, getHeight()-2*m);
		pb.getSprite().draw(batch);
		batch.end();
		if(!getText().isEmpty()) renderText(batch, getText());
	}
	
	public void setProgress(float n){p = n;}
	public void setText(String t){super.setText("  "+t+"");}
}
