package dkeep.logic;

public class KeyDoor {
	private int pos[];
	private int [][] doors;
	private char symbol;
	private char openSymbol;
	private boolean picked;
	
	public KeyDoor(int[] pos, int[][] is) {
		this.pos = pos;
		this.doors = is;
		this.symbol = 'k';
		this.openSymbol = 'S';
		this.picked = false;
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

	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
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

	public void pickKey(Hero hero, char map[][])
	{
		if(hero.getPos()[0] ==  pos[0] && hero.getPos()[1] == pos[1])
		{
			hero.setKey(this);
			picked = true;
		}
	}

	//NOT YET USED 
	public boolean isADoor(int door[])
	{
		for (int i = 0; i < doors.length; i++) {
			if(doors[i][0] == door[0] && doors[i][1] == door[1])
				return true;
		}
		return false;
	}

}
