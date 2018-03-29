package dkeep.logic;

public class DoorMechanism {

	protected int pos[];
	protected int [][] doors;
	protected char symbol;
	protected char openSymbol;

	public DoorMechanism() {
		super();
	}

	public int[][] getDoors() {
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

}