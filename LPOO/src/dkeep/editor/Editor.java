package dkeep.editor;

import java.util.Arrays;

import dkeep.logic.GameReader;
import dkeep.logic.Guard;
import dkeep.logic.Hero;
import dkeep.logic.KeyDoor;
import dkeep.logic.LeverDoor;
import dkeep.logic.Ogre;

public class Editor extends GameReader {

	public Editor(int sizeX, int sizeY) {
		super();
		this.map = new char[sizeX][sizeY];
		this.resetMap();
		this.hero = null;
		this.guard = null;
		this.ogre = null;
		this.key = null;
		this.lever = null;
	}

	public void resetMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (i == map.length - 1 || i == 0 || j == map.length - 1 || j == 0) {
					map[i][j] = 'X';
				} else
					map[i][j] = ' ';
			}
		}
	}

	public void setTile(char tile, int x, int y) {
		if (x >= map.length && y >= map[x].length)
			return;

		map[x][y] = tile;
	}

	public void insertLvl(int index, String fileName) {
		if (index >= this.levels.size())
			this.levels.add(fileName);
		else
			this.levels.add(index, fileName);
	}

	public void replaceLvl(int index, String fileName) {
		if (index >= this.levels.size())
			this.levels.add(fileName);
		else {
			this.levels.remove(index);
			this.levels.add(index, fileName);
		}
	}

	
	public void editCoords(int[] coords, String tileName, String path) {
		if (coords.length < 2)
			return;

		switch (tileName) {
		case "Floor":
			eliminateCharacter(coords);
			setTile(' ', coords[0], coords[1]);
			eliminateCharacter(coords);
			break;
		case "Wall":
			setTile('X', coords[0], coords[1]);
			break;
		case "Hero Armed":
			eliminateCharacter(coords);
			this.hero = new Hero(coords.clone(), true);
			break;
		case "Hero":
			eliminateCharacter(coords);
			this.hero = new Hero(coords.clone(), false);
			break;
		case "Ogre Spawn":
			eliminateCharacter(coords);
			this.ogre = new Ogre(coords.clone());
			break;
		case "Guard":
			eliminateCharacter(coords);
			this.guard = new Guard(coords.clone(), path.toCharArray());
			break;
		case "Key":
			eliminateCharacter(coords);
			this.key = new KeyDoor(coords.clone());
			break;
		case "Lever":
			eliminateCharacter(coords);
			this.lever = new LeverDoor(coords.clone());
			break;
		case "Door":
			eliminateCharacter(coords);
			setTile('I', coords[0], coords[1]);
			break;
		}
	}

	public void eliminateCharacter(int[] coords) {
		if(coords[0] >=  map.length && coords[1] >= map[coords[0]].length )
			return;
		
		if(key != null)
			this.key.rmDoor(coords);
		if(lever !=  null)
			this.lever.rmDoor(coords);
			
		if (this.ogre != null && Arrays.equals(coords, ogre.getPos()))
			this.ogre = null;
		
		if (this.hero != null && Arrays.equals(coords, hero.getPos()))
			this.hero = null;
			
		if (this.guard != null && Arrays.equals(coords, guard.getPos()))
			this.guard = null;
		
		if (this.key != null && Arrays.equals(coords, key.getPos())) {
			this.key = null;
		}
		
		if (this.lever != null && Arrays.equals(coords, lever.getPos())) {
			this.lever = null;
		}
			
	}
}
