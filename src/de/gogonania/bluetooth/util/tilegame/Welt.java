package de.gogonania.bluetooth.util.tilegame;

import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import com.badlogic.gdx.Gdx;

import de.gogonania.bluetooth.Util;
import de.gogonania.bluetooth.util.Bild;

public abstract class Welt implements TileBasedMap{
	private AStarPathFinder finder;
	private int[][] map;
	
	public abstract Bild getBild(int id);
	public abstract boolean isBlocked(int id);
	
	public Welt(int width, int height, boolean diagonal){
		map = new int[width][height];
		setDiagonal(diagonal);
	}
	
	public void render(){
		float w = getTileWidth();
		float h = getTileHeight();
		for(int x = 0; x < getWidthInTiles(); x++){
			for(int y = 0; y < getHeightInTiles(); y++){
				float xx = w*x;
				float yy = h*y;
				int id = get(x, y);
				getBild(id).render(xx, yy, w, h);
			}
		}
	}
	
	public void set(int x, int y, int id){
		try{
			map[x][y] = id;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean equals(int x, int y, int id){
		try{
			return map[x][y] == id;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public int get(int x, int y){
		try{
			return map[x][y];
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	public boolean blocked(PathFindingContext context, int tx, int ty) {
		return isBlocked(get(tx, ty));
	}

	public float getCost(PathFindingContext context, int tx, int ty) {
		return 0;
	}
	
	public int getClickedX(){return (int) (Util.getX()*getWidthInTiles()/Gdx.graphics.getWidth());}
	public int getClickedY(){return (int) (Util.getY()*getHeightInTiles()/Gdx.graphics.getHeight());}
	public void setDiagonal(boolean diagonal){finder = new AStarPathFinder(this, 230000000, diagonal);}
	public float getTileWidth(){return ((float)Gdx.graphics.getWidth())/((float)getWidthInTiles());}
	public float getTileHeight(){return ((float)Gdx.graphics.getHeight())/((float)getHeightInTiles());}
	public void remove(int x, int y){set(x, y, 0);}
	public Path findPath(Mover m, int x, int y, int toX, int toY){return finder.findPath(m, x, y, toX, toY);}
	public int randomX(){return Util.random(0, getWidthInTiles()-1);}
	public int randomY(){return Util.random(0, getHeightInTiles()-1);}
	public int getWidthInTiles(){return map.length;}
	public int getHeightInTiles(){return map[0].length;}
	public void pathFinderVisited(int x, int y){}
}
