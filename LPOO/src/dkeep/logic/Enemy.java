package dkeep.logic;

import java.io.Serializable;

public abstract class Enemy extends Character implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6545261780854204145L;

	/**
	 * Constructs an enemy.
	 * @param pos Position of the enemy.
	 * @param symbol Symbol of the enemy.
	 */
	Enemy(int[] pos, char symbol) {
		super(pos, symbol);
	}
	
	/**
	 * Move template.
	 * @param map Map to move in relation to.
	 */
	public abstract void move(char[][] map);
	
	/**
	 * Killed hero template.
	 * @param hero Hero to check state.
	 * @return True if hero killed.
	 */
	public abstract boolean killedHero(Hero hero);
	
	/**
	 * Prints the character to a map.
	 * @param map Map to print to.
	 */
	public abstract void print(char map[][]);
	
}
