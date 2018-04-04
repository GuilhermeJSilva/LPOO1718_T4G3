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
	
	/**
	 * Position of the mechanism.
	 */
	protected int pos[];
	
	/**
	 * Doors that the mechanism opens.
	 */
	protected ArrayList< Door >doors;
	
	/**
	 * Symbol of the key.
	 */
	protected char symbol;

	/**
	 * Empty Constructor.
	 */
	public DoorMechanism() {
		super();
	}

	/**
	 * Returns the array of doors.
	 * @return Array of doors.
	 */
	public  ArrayList< Door > getDoors() {
		return doors;
	}

	/**
	 * Return the position of the mechanism.
	 * @return Position.
	 */
	public int[] getPos() {
		return pos;
	}

	/**
	 * Changes the position of the mechanism.
	 * @param pos New position.
	 */
	public void setPos(int[] pos) {
		this.pos = pos;
	}
	
	/**
	 * Returns the mechanism's symbol.
	 * @return mechanism's symbol.
	 */
	public char getSymbol() {
		return symbol;
	}

	/**
	 * Change the mechanisms's symbol.
	 * @param symbol New symbol.
	 */
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Constructs and adds a door.
	 * @param door Position of the door.
	 * @param openS Open symbol of the door.
	 */
	public void addDoor(int[] door, char openS) {
		if(door.length < 2)
			return;
		
		doors.add(new Door(new int[] {door[0],  door[1]}, openS));
	}
	
	/**
	 * Adds a door to the key.
	 * @param door Door to add.
	 */
	public void addDoor(Door door) {
		doors.add(door);
	}
	
	/**
	 * Removes a door from a mechanism.
	 * @param door Position of the door to remove.
	 */
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