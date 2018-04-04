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

/**
 * Allows you to edit levels.
 */
public class Editor extends SimpleReader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5762702670337539602L;

	/**
	 * Constructs an Editor with a certain map size
	 * 
	 * @param sizeX
	 *            Vertical size
	 * @param sizeY
	 *            Horizontal size
	 */
	public Editor(int sizeX, int sizeY) {
		super();
		this.map = new char[sizeX][sizeY];
		this.resetMap();
		this.hero = null;
	}

	/**
	 * Resets the map of the editor to be floor surrounded by a one tile wall
	 */
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

	/**
	 * Changes a tile at a certain coordinate
	 * 
	 * @param tile
	 *            New value
	 * @param x
	 *            X coordinate
	 * @param y
	 *            T coordinate
	 */
	public void setTile(char tile, int x, int y) {
		if (x >= map.length && y >= map[x].length)
			return;

		map[x][y] = tile;
	}

	/**
	 * Inserts a level in the filename array
	 * 
	 * @param index
	 *            Index on the array
	 * @param fileName
	 *            Filename to insert
	 */
	public void insertLvl(int index, String fileName) {
		if (index >= this.levels.size())
			this.levels.add(fileName);
		else
			this.levels.add(index, fileName);
	}

	/**
	 * Changes the value of a element in the filename array
	 * 
	 * @param index
	 *            Index on the array
	 * @param fileName
	 *            New filename
	 */
	public void replaceLvl(int index, String fileName) {
		if (index >= this.levels.size())
			this.levels.add(fileName);
		else {
			this.levels.set(index, fileName);
		}
	}

	/**
	 * Edits a tile
	 * 
	 * @param coords
	 *            Tile coordinates
	 * @param tileName
	 *            Character to put on those coordinates
	 */
	public void editCoords(int[] coords, String tileName) {
		if (coords.length < 2)
			return;

		eliminateCharacter(coords);
		switch (tileName) {
		case "Floor":
			setTile(' ', coords[0], coords[1]);
			break;
		case "Wall":
			setTile('X', coords[0], coords[1]);
			break;
		case "Hero Armed":
		case "Hero":
			this.hero = new Hero(coords.clone(), tileName.equals("Hero Armed"));
			break;
		case "Ogre Spawn":
			this.enemies.add(new Ogre(coords.clone()));
			break;
		case "Guard":
			this.enemies.add(new Guard(coords.clone(), new char[0]));
			break;
		case "Key":
			this.addMechanism(new KeyDoor(coords.clone()));
			break;
		case "Lever":
			this.addMechanism(new LeverDoor(coords.clone()));
			break;
		case "Door":
		case "Exit":
			setTile('I', coords[0], coords[1]);
			break;

		}
	}

	/**
	 * Eliminate all character in certain coordinates
	 * 
	 * @param coords
	 *            Coordinates
	 */
	public void eliminateCharacter(int[] coords) {
		if (coords[0] >= map.length && coords[1] >= map[coords[0]].length)
			return;

		removeDoor(coords);
		removeEnemies(coords);

		if (this.hero != null && Arrays.equals(coords, hero.getPos()))
			this.hero = null;

		removeDoorMechanism(coords);

	}

	/**
	 * Removes all door mechanism in certain coordinates
	 * 
	 * @param coords
	 *            Coordinates
	 */
	protected void removeDoorMechanism(int[] coords) {
		for (DoorMechanism dMecha : dMechanism) {
			if (Arrays.equals(coords, dMecha.getPos())) {
				dMechanism.remove(dMecha);
				break;
			}
		}
	}

	/**
	 * Removes all enemies in a certain coordinate
	 * 
	 * @param coords
	 *            Coordinates
	 */
	protected void removeEnemies(int[] coords) {
		for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
			Enemy enemy = (Enemy) iterator.next();
			if (Arrays.equals(enemy.getPos(), coords)) {
				iterator.remove();
			}
		}
	}

	/**
	 * Removes all doors in a certain coordinates
	 * 
	 * @param coords
	 *            Coordinates
	 */
	protected void removeDoor(int[] coords) {
		for (DoorMechanism dMecha : dMechanism) {
			dMecha.rmDoor(coords);
		}
	}

	/**
	 * Checks if the level is valid. A valid level as an hero and an exit.
	 * 
	 * @return True if the level is valid.
	 */
	public boolean checkLevel() {
		if (this.getHero() == null || this.getdMechanism().size() == 0) {
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
