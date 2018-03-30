package dkeep.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class DoorMechanism {

	protected int pos[];
	protected ArrayList< ArrayList<Integer> >doors;
	protected char symbol;
	protected char openSymbol;

	public DoorMechanism() {
		super();
	}

	public  ArrayList< ArrayList<Integer> > getDoors() {
		return doors;
	}

	public char getOpenSymbol() {
		return openSymbol;
	}

	public void setOpenSymbol(char openSymbol) {
		this.openSymbol = openSymbol;
	}

	public int[] getPos() {
		return pos;
	}

	public void setPos(int[] pos) {
		this.pos = pos;
	}
	
	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	
	public void addDoor(int[] door) {
		if(door.length < 2)
			return;
		
		ArrayList<Integer> d = new ArrayList<Integer>();
		d.add(door[0]);
		d.add(door[1]);
		doors.add(d);
	}
	
	public void rmDoor(int[] door) {
		if(door.length < 2)
			return;
		
		for (Iterator<ArrayList<Integer>> iterator = doors.iterator(); iterator.hasNext(); ) {
			ArrayList<Integer> d1 = iterator.next();
			if(d1.get(0) == door[0] && d1.get(1) == door[1]) {
				iterator.remove();
				System.out.println("Removing door " + Arrays.toString(door));
			}
		}
	}

	@Override
	public String toString() {
		return "Mechanism " + Arrays.toString(pos);
	}

}