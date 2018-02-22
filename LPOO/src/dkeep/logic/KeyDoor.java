package dkeep.logic;

public class KeyDoor {
	private int pos[];
	private int [][] doors;
	private char symbol;
	private char openSymbol;
	private boolean picked;
	
	public KeyDoor(int[] pos, int[][] is, char symbol, char openSymbol) {
		this.pos = pos;
		this.doors = is;
		this.symbol = symbol;
		this.openSymbol =openSymbol;
		this.picked = false;
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


}
