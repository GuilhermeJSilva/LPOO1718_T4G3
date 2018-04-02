package dkeep.logic;

import java.io.Serializable;

public abstract class Character implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7645820509950821758L;
	protected int pos[];
	protected char symbol;

	Character(int pos[], char symbol)
	{
		this.pos = pos;
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}

	public int[] getPos() {
		return pos;
	}

}


