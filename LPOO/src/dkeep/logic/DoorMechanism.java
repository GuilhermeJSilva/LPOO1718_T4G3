package dkeep.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public abstract class DoorMechanism implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8961286346021737109L;
	protected int pos[];
	protected ArrayList< Door >doors;
	protected char symbol;

	public DoorMechanism() {
		super();
	}

	public  ArrayList< Door > getDoors() {
		return doors;
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
	
	public void addDoor(int[] door, char openS) {
		if(door.length < 2)
			return;
		
		doors.add(new Door(new int[] {door[0],  door[1]}, openS));
	}
	
	public void addDoor(Door door) {
		doors.add(door);
	}
	
	public void rmDoor(int[] door) {
		if(door.length < 2)
			return;
		
		for (Iterator<Door> iterator = doors.iterator(); iterator.hasNext(); ) {
			Door d1 = iterator.next();
			if(d1.getPos()[0] == door[0] && d1.getPos()[0] == door[1]) {
				iterator.remove();
				System.out.println("Removing door " + Arrays.toString(door));
			}
		}
	}
	
	public abstract boolean activateMechanism(Hero hero, char map[][]);

	
}