package dkeep.logic;

import java.util.ArrayList;

public class Hero extends Character {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2037315788131079148L;
	
	/**
	 * Array of keys in the hero's possession.
	 */
	private ArrayList<KeyDoor> keys = new ArrayList<KeyDoor>();
	
	/**
	 * True if the hero is armed.
	 */
	private boolean armed;

	/**
	 * Creates an hero.
	 * @param pos Position of the hero.
	 * @param armed True if the hero is armed.
	 */
	public Hero(int[] pos, boolean armed) {
		super(pos, 'H');
		this.armed = armed;
	}
	
	/**
	 * Returns the active symbol.
	 * @return Active symbol.
	 */
	@Override
	public char getSymbol() {

		if (keys.size() == 0)
			if (armed)
				return 'A';
			else
				return super.getSymbol();
		else
			return 'K';
	}

	/**
	 * Returns true if the hero is armed.
	 * @return True if the hero is armed.
	 */
	public boolean isArmed() {
		return armed;
	}

	/**
	 * Change the armed field.
	 * @param armed New value for armed.
	 */
	public void setArmed(boolean armed) {
		this.armed = armed;
	}

	

	/**
	 * Moves the hero in relation to a map and a command.
	 * @param command Command to move the hero.
	 * @param map Move in relation to this map.
	 * @return True if the hero moved.
	 */
	public boolean move(char command, char[][] map) {
		int[] newPos = getNewPos(command);
		if(newPos == null)
			return false;
		
		if(moveInto(newPos, map))
			return true;

		return openDoor(map, newPos);
	}


	/**
	 * Tries to open a door.
	 * @param map Map
	 * @param pos Position of the door.
	 * @return True if a door was opened.
	 */
	protected boolean openDoor(char map[][], int pos[]) {
		if (map[pos[0]][pos[1]] == 'I') {
			if (keys.size() != 0) {
				for (KeyDoor key : keys) {
					Door dToOpen = null;
					if ((dToOpen = key.isADoor(pos)) != null) {
						map[pos[0]][pos[1]] = dToOpen.getOpenS();
						return true;
					}
				}

			}
		}
		return false;
	}

	/**
	 * Adds a key to array of keys.
	 * @param key Key to add.
	 */
	public void addKey(KeyDoor key) {
		this.keys.add(key);
	}

}
