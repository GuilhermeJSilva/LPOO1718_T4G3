package dkeep.logic;

import java.io.Serializable;

public abstract class Enemy extends Character implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6545261780854204145L;

	Enemy(int[] pos, char symbol) {
		super(pos, symbol);
	}
	
	public abstract void move(char[][] map);
	
	public abstract boolean killedHero(Hero hero);
	
	public abstract void print(char map[][]);
	
}
