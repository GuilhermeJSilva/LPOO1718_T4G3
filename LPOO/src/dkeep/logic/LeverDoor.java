package dkeep.logic;

import java.util.ArrayList;
import java.util.Arrays;

public class LeverDoor extends DoorMechanism{
	
	public LeverDoor(int[] pos, int[][] is) {
		super();
		this.pos = pos;
		this.doors = new ArrayList< ArrayList<Integer> >();
		for (int[] is2 : is) {
			this.addDoor(is2);
		}
		
		this.symbol = 'k';
		this.openSymbol ='S';
	}
	
	public LeverDoor(int[] pos) {
		this.pos = pos;
		this.doors = new ArrayList< ArrayList<Integer> >();
		this.symbol = 'k';
		this.openSymbol = 'S';
	}

	public void pullLever(Hero hero, char map[][])
	{
		if(hero.getPos()[0] ==  pos[0] && hero.getPos()[1] == pos[1])
		{
			for (int i = 0; i < doors.size(); i++) {
				map[doors.get(i).get(0)][doors.get(i).get(1)] = this.openSymbol;
			}
		}
	}

	@Override
	public String toString() {
		return "Lever " + Arrays.toString(pos);
	}
	
}
