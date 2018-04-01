package dkeep.logic;

import java.util.ArrayList;
import java.util.Arrays;

public class KeyDoor extends DoorMechanism {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1025689212098839298L;
	private boolean picked;
	
	public KeyDoor(int[] pos, int[][] is) {
		this.pos = pos;
		this.doors = new ArrayList< ArrayList<Integer> >();
		for (int[] is2 : is) {
			this.addDoor(is2);
		}
		
		this.symbol = 'k';
		this.openSymbol = 'S';
		this.picked = false;
	}
	
	public KeyDoor(int[] pos, int[][] is, char openS) {
		this.pos = pos;
		this.doors = new ArrayList< ArrayList<Integer> >();
		for (int[] is2 : is) {
			this.addDoor(is2);
		}
		
		this.symbol = 'k';
		this.openSymbol = openS;
		this.picked = false;
	}
	
	public KeyDoor(int[] pos) {
		this.pos = pos;
		this.doors = new ArrayList< ArrayList<Integer> >();
		this.symbol = 'k';
		this.openSymbol = 'S';
		this.picked = false;
	}
	
	public KeyDoor(int[] pos, char openS) {
		this.pos = pos;
		this.doors = new ArrayList< ArrayList<Integer> >();
		this.symbol = 'k';
		this.openSymbol = openS;
		this.picked = false;
	}

	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}

	public void pickKey(Hero hero, char map[][])
	{
		if(hero.getPos()[0] ==  pos[0] && hero.getPos()[1] == pos[1])
		{
			hero.setKey(this);
			picked = true;
		}
	}

	//NOT YET USED 
	public boolean isADoor(int door[])
	{
		for (int i = 0; i < doors.size(); i++) {
			if(doors.get(i).get(0) == door[0] && doors.get(i).get(1) == door[1])
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Key " + Arrays.toString(pos);
	}

}
