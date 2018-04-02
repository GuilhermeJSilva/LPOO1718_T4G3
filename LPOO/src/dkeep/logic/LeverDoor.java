package dkeep.logic;

import java.util.ArrayList;
import java.util.Arrays;

public class LeverDoor extends DoorMechanism{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7280934952309291058L;

	public LeverDoor(int[] pos, ArrayList<Door> is) {
		super();
		this.pos = pos;
		this.doors = new ArrayList< Door >();
		for (Door is2 : is) {
			this.addDoor(is2);
		}
		this.symbol = 'k';
	}
	
	
	public LeverDoor(int[] pos) {
		this.pos = pos;
		this.doors = new ArrayList<Door >();
		this.symbol = 'k';
	}

	public boolean activateMechanism(Hero hero, char map[][])
	{
		if(hero.getPos()[0] ==  pos[0] && hero.getPos()[1] == pos[1])
		{
			for (int i = 0; i < doors.size(); i++) {
				map[doors.get(i).getPos()[0]][doors.get(i).getPos()[1]] = doors.get(i).getOpenS();
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Lever " + Arrays.toString(pos);
	}
	
}
