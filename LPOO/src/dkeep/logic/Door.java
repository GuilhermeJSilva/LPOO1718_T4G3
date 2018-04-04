package dkeep.logic;

import java.io.Serializable;

public class Door implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -428123756815578585L;
	
	/*
	 * Position of the door.
	 */
	private int pos[];
	
	/**
	 * Open symbol.
	 */
	private char openS;
	
	/**
	 * Returns the position.
	 * @return Position.
	 */
	public int[] getPos() {
		return pos;
	}
	
	/**
	 * Returns open symbol.
	 * @return Open symbol.
	 */
	public char getOpenS() {
		return openS;
	}
	
	/**
	 * Creates a door in a position.
	 * @param pos Position of the door.
	 * @param openS Open symbol of a door.
	 */
	public Door(int[] pos, char openS) {
		super();
		this.pos = pos;
		this.openS = openS;
	}
	
	
}
