package dkeep.logic;

import java.io.Serializable;

public class Door implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -428123756815578585L;
	private int pos[];
	private char openS;
	
	public int[] getPos() {
		return pos;
	}
	public char getOpenS() {
		return openS;
	}
	
	public Door(int[] pos, char openS) {
		super();
		this.pos = pos;
		this.openS = openS;
	}
	
	
}
