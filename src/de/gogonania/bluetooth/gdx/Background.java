package de.gogonania.bluetooth.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.util.Bilder;

public class Background {
	private static World world;
	private static Array<Body> bodies = new Array<Body>();
	private static final float d = 1600F/((float) Gdx.graphics.getHeight());
	
	public static void init(){
		world = new World(new Vector2(0, -15*d*d), false);
		addWall(-1, 0, 1, Gdx.graphics.getHeight());
		addWall(Gdx.graphics.getWidth()+2, 0, 1, Gdx.graphics.getHeight());
		addWall(0, -1, Gdx.graphics.getWidth(), 1);
	}
	
	public static void render(){
		if(Util.chance(1)) add(Util.random(0, Gdx.graphics.getWidth()), Gdx.graphics.getHeight());
		Vector2 g = world.getGravity();
		g.x = SensorInfo.getRotation()*6*d*d;
		world.setGravity(g);
		world.step(Gdx.graphics.getDeltaTime(), 50, 30);
		world.getBodies(bodies);
		for(Body b : bodies){
			if(b.getUserData() != null){
				float size = (float) b.getUserData();
				Bilder.logo.render(b.getPosition().x-size/2F, b.getPosition().y-size/2F, size, size, 1, (int)(MathUtils.radiansToDegrees * b.getAngle()));
			}
		}
	}
	
	public static void reset(){
		for(Body b : bodies){
			if(b.getUserData() != null) world.destroyBody(b);
		}
	}
	
	public static void add(float x, float y){
		float s = 1;
		if(Util.chance(15)) s = Util.chance(15)?4:2;
		float size = Gdx.graphics.getWidth()/50F*s;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		Body body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(size/2F, size/2F);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = 0.4F - (s * 1F);
		fixtureDef.density = 30*s;
		fixtureDef.friction = 0;
	    body.createFixture(fixtureDef);
	    body.setUserData(size);
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
}
