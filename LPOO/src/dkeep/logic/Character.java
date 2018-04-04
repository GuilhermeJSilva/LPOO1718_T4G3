package dkeep.logic;

import java.io.Serializable;

/**
 * Stores the base for all entities.
 *
 */
public abstract class Character implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7645820509950821758L;
	
	/**
	 * Position of the character.
	 */
	protected int pos[];
	
	/**
	 * Symbol to be displayed.
	 */
	protected char symbol;

	/**
	 * Creates a character.
	 * @param pos Coordinates.
	 * @param symbol Symbol to be displayed.
	 */
	Character(int pos[], char symbol)
	{
		this.pos = pos;
		this.symbol = symbol;
	}

	/**
	 * Returns the symbol to be displayed.
	 * @return Symbol.
	 */
	public char getSymbol() {
		return symbol;
	}

	/**
	 * Returns the position of the character.
	 * @return Position.
	 */
	public int[] getPos() {
		return pos;
	}

	/**
	 * Tries to move a character into a new position.
	 * @param newPos New position.
	 * @param map Map to move on.
	 * @return True if the move is valid.
	 */
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

	/**
	 * Returns a new position based on a command.
	 * @param command Command to generate the new position.
	 * @return New position.
	 */
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


