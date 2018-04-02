package dkeep.logic;

import java.util.ArrayList;
import java.util.Arrays;

public class KeyDoor extends DoorMechanism {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1025689212098839298L;
	private boolean picked;
	
	public KeyDoor(int[] pos, ArrayList<Door> is) {
		this.pos = pos;
		this.doors = new ArrayList< Door >();
		for (Door is2 : is) {
			this.addDoor(is2);
		}
		
		this.symbol = 'k';
		this.picked = false;
	}
	
	public KeyDoor(int[] pos) {
		this.pos = pos;
		this.doors = new ArrayList< Door >();
		this.symbol = 'k';
		this.picked = false;
	}

	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}

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
