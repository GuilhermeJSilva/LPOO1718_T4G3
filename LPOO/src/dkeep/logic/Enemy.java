package dkeep.logic;

public abstract class Enemy extends Character{

	Enemy(int[] pos, char symbol) {
		super(pos, symbol);
	}
	
	public abstract void move(char[][] map);
	
	public abstract boolean killedHero(Hero hero);
	
	public abstract void print(char map[][]);
}
