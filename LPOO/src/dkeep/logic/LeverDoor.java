package dkeep.logic;

import java.util.ArrayList;
import java.util.Arrays;

public class LeverDoor extends DoorMechanism{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7280934952309291058L;

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
	
	public LeverDoor(int[] pos, int[][] is, char openS) {
		super();
		this.pos = pos;
		this.doors = new ArrayList< ArrayList<Integer> >();
		for (int[] is2 : is) {
			this.addDoor(is2);
		}
		
		this.symbol = 'k';
		this.openSymbol = openS;
	}
	
	
	public LeverDoor(int[] pos) {
		this.pos = pos;
		this.doors = new ArrayList< ArrayList<Integer> >();
		this.symbol = 'k';
		this.openSymbol = 'S';
	}
	
	public LeverDoor(int[] pos, char openS) {
		this.pos = pos;
		this.doors = new ArrayList< ArrayList<Integer> >();
		this.symbol = 'k';
		this.openSymbol = openS;
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
