package dkeep.logic;

public class LeverDoor {
	private int pos[];
	private int [][] doors;
	private char symbol;
	private char openSymbol;
	
	public LeverDoor(int[] pos, int[][] is) {
		super();
		this.pos = pos;
		this.doors = is;
		this.symbol = 'k';
		this.openSymbol ='S';
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

	public void pullLever(Hero hero, char map[][])
	{
		if(hero.getPos()[0] ==  pos[0] && hero.getPos()[1] == pos[1])
		{
			for (int i = 0; i < doors.length; i++) {
				map[doors[i][0]][doors[i][1]] = this.openSymbol;
			}
		}
	}
	
}
