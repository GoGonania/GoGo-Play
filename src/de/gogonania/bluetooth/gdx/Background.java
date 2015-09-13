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
	private static final float size = Gdx.graphics.getWidth()/25F;
	
	public static void init(){
		world = new World(new Vector2(0, -20), false);
		addWall(0, 0, 1, Gdx.graphics.getHeight());
		addWall(Gdx.graphics.getWidth(), 0, 1, Gdx.graphics.getHeight());
		addWall(0, 0, Gdx.graphics.getWidth(), 1);
	}
	
	public static void render(){
		if(Util.chance(1)) add(Util.random((int)size, (int)(Gdx.graphics.getWidth()-size)), Gdx.graphics.getHeight());
		Vector2 g = world.getGravity();
		g.x = SensorInfo.getRotation()*5;
		world.setGravity(g);
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		world.getBodies(bodies);
		for(Body b : bodies){
			if(b.getUserData() == null){
				Bilder.logo.render(b.getPosition().x, b.getPosition().y, size, size, 1, (int)(MathUtils.radiansToDegrees * b.getAngle()));
			}
		}
	}
	
	public static void reset(){
		for(Body b : bodies){
			if(b.getUserData() == null) world.destroyBody(b);
		}
	}
	
	public static void add(float x, float y){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		Body body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(size/2F, size/2F);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = 0.3F;
	    body.createFixture(fixtureDef);
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
	    body.setUserData(new Object());
		shape.dispose();
	}
}
