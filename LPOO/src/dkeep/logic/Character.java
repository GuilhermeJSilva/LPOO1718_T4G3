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

	public boolean move(char command, char map[][])
	{
		switch (command) {
		case 'w':
			if(map[pos[0] - 1][pos[1]] == ' ' || map[pos[0] - 1][pos[1]] == 'S')
			{
				pos[0]--;
				return true;
			}
			break;

		case 'a':
			if(map[pos[0]][pos[1] - 1] == ' '|| map[pos[0]][pos[1] - 1] == 'S')
			{
				pos[1]--;
				return true;
			}
			break;

		case 's':
			if(map[pos[0] + 1][pos[1]] == ' '|| map[pos[0] - 1][pos[1]] == 'S')
			{
				pos[0]++;
				return true;
			}
			break;
		case 'd':
			if(map[pos[0]][pos[1] + 1] == ' '|| map[pos[0]][pos[1] + 1] == 'S')
			{
				pos[1]++;
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}

}


