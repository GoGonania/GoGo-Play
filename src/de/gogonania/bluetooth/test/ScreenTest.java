package de.gogonania.bluetooth.test;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import com.badlogic.gdx.Gdx;

import de.gogonania.bluetooth.Szene;
import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.util.Bilder;
import de.gogonania.bluetooth.util.GoGoMath;

public class ScreenTest extends Szene implements TileBasedMap{
	public static final float items = 500F;
	public boolean[][] felder = new boolean[(int)items][(int)items];
	public Path p;
	public int id;
	float zielx;
	float ziely;
	int cx;
	int cy;
	public AStarPathFinder finder;
	
	public ScreenTest() {
		super(Bilder.background);
	}
	
	public void open(){
		finder = new AStarPathFinder(this, 1000000000, true);
	}
	
	public void find(){
		id = 0;
		zielx = Util.getX();
		ziely = Util.getY();
		p = finder.findPath(new Mover(){}, cx, cy, (int)(Util.getX()*items/Gdx.graphics.getWidth()), (int)(Util.getY()*items/Gdx.graphics.getHeight()));
	}
	
	public void r(){
		felder[Util.random(0, (int)items-1)][Util.random(0, (int)items-1)] = true;
	}
	
	public void render(){
		float width = Gdx.graphics.getWidth()/items;
		float height = Gdx.graphics.getHeight()/items;
		if(p == null) return;
		Path.Step s = p.getStep(id);
		cx = s.getX();
		cy = s.getY();
		float x = s.getX()*width;
		float y = s.getY()*height;
		Bilder.panzer.render(x, y, width*40F, height*40F, 1, GoGoMath.winkel(x, y, zielx, ziely));
		if(id < p.getLength()-2) id++;
	}
	
	public void click(){
		find();
	}

	public int getWidthInTiles() {
		return (int) items;
	}

	public int getHeightInTiles() {
		return (int) items;
	}

	public void pathFinderVisited(int x, int y) {}

	public boolean blocked(PathFindingContext context, int tx, int ty) {
		return felder[tx][ty];
	}

	public float getCost(PathFindingContext context, int tx, int ty) {
		return 0;
	}
}
