package dkeep.logic;

import java.util.Random;

public class Drunken extends Suspicious {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5512628551857065458L;
	/**
	 * 0 if the guard is awake.
	 */
	private int sleep;
	
	/**
	 * Sleeping symbol.
	 */
	private char sSymbol;

	/**
	 * Constructs a drunken guard.
	 * @param pos Position of the guard.
	 * @param path Path of the guard.
	 */
	public Drunken(int[] pos, char[] path) {
		super(pos, path);
		this.sleep = 0;
		this.sSymbol = 'g';
	}

	/**
	 * Return the current symbol being displayed.
	 * @return symbol being displayed.
	 */
	@Override
	public char getSymbol() {
		if (sleep == 1)
			return sSymbol;
		else
			return super.getSymbol();
	}

	/**
	 * Moves the guard in accord with a map.
	 */
	@Override
	public void move(char[][] map) {
		Random generator = new Random();
		int newSleep = generator.nextInt(2);

		if (newSleep == 1) {
			sleep = 1;
			return;
		}

		if (sleep == 1 && newSleep == 0) {
			sleep = 0;
			 super.move(map);
		} else {
			char command = this.getPath()[this.getCounter()];
			super.movement(map, command);
		}
		fixCounter();
	}


	/**
	 * Returns the current sleep state.
	 * @return Sleep state.
	 */
	public int getSleep() {
		return sleep;
	}

	/**
	 * Changes the sleep state.
	 * @param sleep New sleep state.
	 */
	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

	/**
	 * Checks if the hero is killed by this character.
	 * @param hero Check if this Hero died.
	 * @return True if the hero was killed.
	 */
	@Override
	public boolean killedHero(Hero hero) {
		if (sleep == 1)
			return false;
		return super.killedHero(hero);
	}

}
