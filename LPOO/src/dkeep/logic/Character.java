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

	public boolean moveInto(int newPos[], char map[][]) {
		if(newPos == null || map == null)
			return false;
		
		if(newPos.length < 2)
			return false;
		
		if(map.length < newPos[0] || map[0].length < newPos[1])
			return false;
		
		if (map[newPos[0]][newPos[1]] == ' '
				|| map[newPos[0]][newPos[1]] == 'S') {
			pos = newPos;
			return true;
		}
		return false;
	}

	protected int[] getNewPos(char command) {
		int newPos[] =  null;
		switch (command) {
		case 'w':
			newPos =  new int[] {this.getPos()[0] - 1 ,this.getPos()[1]};
			break;

		case 'a':
			newPos = new int[] { this.getPos()[0], this.getPos()[1] - 1 };
			break;

		case 's':
			newPos = new int[] { this.getPos()[0] + 1, this.getPos()[1] };
			break;
		case 'd':
			newPos = new int[] { this.getPos()[0], this.getPos()[1] + 1 };
			break;

		default:
			break;
		}
		return newPos;
	}
}


