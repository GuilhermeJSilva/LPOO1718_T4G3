package dkeep.gameManipulator;

import java.util.Arrays;
import java.util.Iterator;

import dkeep.logic.Door;
import dkeep.logic.DoorMechanism;
import dkeep.logic.Enemy;
import dkeep.logic.Guard;
import dkeep.logic.Hero;
import dkeep.logic.KeyDoor;
import dkeep.logic.LeverDoor;
import dkeep.logic.Ogre;

public class Editor extends SimpleReader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5762702670337539602L;
	
	public Editor(int sizeX, int sizeY) {
		super();
		this.map = new char[sizeX][sizeY];
		this.resetMap();
		this.hero = null;
	}

	public void resetMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (i == map.length - 1 || i == 0 || j == map[i].length - 1 || j == 0) {
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

	public void editCoords(int[] coords, String tileName) {
		if (coords.length < 2)
			return;

		switch (tileName) {
		case "Floor":
			eliminateCharacter(coords);
			setTile(' ', coords[0], coords[1]);
			break;
		case "Wall":
			eliminateCharacter(coords);
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
			this.enemies.add(new Ogre(coords.clone()));
			break;
		case "Guard":
			eliminateCharacter(coords);
			this.enemies.add(new Guard(coords.clone(), new char[0]));
			break;
		case "Key":
			eliminateCharacter(coords);
			this.addMechanism(new KeyDoor(coords.clone()));
			break;
		case "Lever":
			eliminateCharacter(coords);
			this.addMechanism(new LeverDoor(coords.clone()));
			break;

		case "Door": case "Exit":
			eliminateCharacter(coords);
			setTile('I', coords[0], coords[1]);
			break;
		
		}
	}

	public void eliminateCharacter(int[] coords) {
		if (coords[0] >= map.length && coords[1] >= map[coords[0]].length)
			return;

		for (DoorMechanism dMecha : dMechanism) {
			dMecha.rmDoor(coords);
		}

		for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
			Enemy enemy = (Enemy) iterator.next();
			if(Arrays.equals(enemy.getPos(), coords)) {
				iterator.remove();
			}
		}
		
		if (this.hero != null && Arrays.equals(coords, hero.getPos()))
			this.hero = null;

		for (DoorMechanism dMecha : dMechanism) {
			if (Arrays.equals(coords, dMecha.getPos())) {
				dMechanism.remove(dMecha);
				break;
			}
		}

	}
	
	public boolean checkLevel() {
		if (this.getHero() == null) {
			return false;
		}

		if (this.getdMechanism().size() == 0) {
			return false;
		}

		boolean exit = false;
		for (DoorMechanism dMecha : this.getdMechanism()) {

			for (Door d : dMecha.getDoors()) {
				if (d.getOpenS() == 'S')
					exit = true;
			}

		}
		return exit;
	}
}
