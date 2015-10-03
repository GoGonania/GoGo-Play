package de.gogonania.bluetooth.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.util.Bild;

public class Background {
	public static class Item{
		Bild b;
		float size;
	}
	
	private static boolean r;
	private static Array<Body> bodies = new Array<Body>();
	private static final float d = ((float) Gdx.graphics.getHeight())/1600F;
	private static World world = new World(new Vector2(0, -16*d), false);
	
	public static void render(){
		if(r){
			for(Body b : bodies){
				world.destroyBody(b);
			}
			bodies.clear();
			r = false;
		} else{
			if(bodies.size == 0){
				addWall(-1, 0, 1, Gdx.graphics.getHeight());
				addWall(Gdx.graphics.getWidth()+2, 0, 1, Gdx.graphics.getHeight());
				if(get().ground()) addWall(0, -1, Gdx.graphics.getWidth(), 1);
			}
			if(get().chance()) add();
			Vector2 g = world.getGravity();
			g.x = SensorInfo.getRotation()*6*d;
			world.setGravity(g);
			world.step(Gdx.graphics.getDeltaTime(), (int)(18F*d), (int)(6F*d));
			world.getBodies(bodies);
			for(Body b : bodies){
				if(b.getUserData() != null){
					Item i = (Item) b.getUserData();
					i.b.render(b.getPosition().x-i.size/2F, b.getPosition().y-i.size/2F, i.size, i.size, 1, (int)(MathUtils.radiansToDegrees * b.getAngle()));
				}
			}
		}
	}
	
	public static void reset(){
		r = true;
	}
	
	public static int getObjekte(){
		return bodies.size;
	}
	
	public static void add(){
		if(!(Util.getSzene() instanceof AnimatedBackground)) return;
		float s = get().getSize();
		float size = Gdx.graphics.getWidth()/50F*s;
		Item i = new Item();
		i.size = size;
		i.b = get().getBild();
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(Util.random(0, Gdx.graphics.getWidth()), Gdx.graphics.getHeight()+size);
		Body body = world.createBody(bodyDef);
		Shape shape = null;
		if(get().rect()){
			shape = new PolygonShape();
		    ((PolygonShape)shape).setAsBox(size/2F, size/2F);
		} else{
			shape = new CircleShape();
			shape.setRadius(size/2F);
		}
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = 0.4F - (s * 1F);
		fixtureDef.density = 30*s;
		fixtureDef.friction = 0;
	    body.createFixture(fixtureDef);
	    body.setUserData(i);
		shape.dispose();
	}
	
	private static void addWall(float x, float y, float width, float height){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(x, y);
		Body body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
	    body.createFixture(fixtureDef);
		shape.dispose();
	}
	
	private static AnimatedBackground get(){return (AnimatedBackground) Util.getSzene();}
}
