package dkeep.logic;

public class Door {
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
