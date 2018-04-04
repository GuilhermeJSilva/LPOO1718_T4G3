package dkeep.logic;

import java.util.ArrayList;
import java.util.Arrays;

public class KeyDoor extends DoorMechanism {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1025689212098839298L;
	/**
	 * True if the key was picked.
	 */
	private boolean picked;
	
	/**
	 * Creates a key with doors that it can open.
	 * @param pos Position of the key.
	 * @param is Doors that it can open.
	 */
	public KeyDoor(int[] pos, ArrayList<Door> is) {
		this.pos = pos;
		this.doors = new ArrayList< Door >();
		for (Door is2 : is) {
			this.addDoor(is2);
		}
		
		this.symbol = 'k';
		this.picked = false;
	}

	/**
	 * Creates a key without doors.
	 * @param pos Position of the key.
	 */
	public KeyDoor(int[] pos) {
		this.pos = pos;
		this.doors = new ArrayList< Door >();
		this.symbol = 'k';
		this.picked = false;
	}

	/**
	 * Returns true if anyone picked the key.
	 * @return True if anyone picked the key.
	 */
	public boolean isPicked() {
		return picked;
	}

	/**
	 * Changes the picked field.
	 * @param picked New value of picked.
	 */
	public void setPicked(boolean picked) {
		this.picked = picked;
	}

	/**
	 * Activates the key (the key is picked)
	 * 
	 * @param hero Hero that triggers the mechanism.
	 * @return True if the mechanism was triggered.
	 */
	public boolean activateMechanism(Hero hero, char map[][])
	{
		if(hero.getPos()[0] ==  pos[0] && hero.getPos()[1] == pos[1])
		{
			hero.addKey(this);
			picked = true;
			return true;
		}
		return false;
	}

	/**
	 * Checks if there is  a door associated with the key in this position.
	 * @param door Coordinates of the door.
	 * @return True if there is a door in that position.
	 */
	public Door isADoor(int door[])
	{
		if(door.length < 2)
			return null;
		
		for (int i = 0; i < doors.size(); i++) {
			if(doors.get(i).getPos()[0] == door[0] && doors.get(i).getPos()[1] == door[1])
				return doors.get(i);
		}
		return null;
	}

	@Override
	public String toString() {
		return "Key " + Arrays.toString(pos);
	}

}
