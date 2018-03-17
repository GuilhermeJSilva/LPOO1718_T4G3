package dkeep.logic;

public abstract class Character {
	private int pos[];
	private char symbol;

	Character(int pos[], char symbol)
	{
		this.pos = pos;
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public int[] getPos() {
		return pos;
	}

	public void setPos(int[] pos) {
		this.pos = pos;
	}
}


