package dkeep.logic;

import java.util.Arrays;

public class Guard extends Enemy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9167529585915531192L;

	/**
	 * Path of the guard.
	 */
	protected char path[];

	/**
	 * Index of the next command.
	 */
	protected int counter;

	/**
	 * Constructs a guard.
	 * 
	 * @param pos
	 *            Position of the guard.
	 * @param path
	 *            Path of the guard.
	 */
	public Guard(int[] pos, char path[]) {
		super(pos, 'G');
		this.path = path;
		this.counter = 0;
	}

	@Override
	public String toString() {
		return "Guard " + Arrays.toString(pos);
	}

	/**
	 * Returns the guard path.
	 * 
	 * @return Guard path.
	 */
	public char[] getPath() {
		return path;
	}

	/**
	 * Change the guard path.
	 * 
	 * @param path
	 *            New guard path.
	 * @return True if the path is valid.
	 */
	public boolean setPath(char[] path) {
		if (!checkPath(path))
			return false;
		this.path = path;
		return true;
	}

	/**
	 * Checks if a path id valid.
	 * 
	 * @param path
	 *            Path to check.
	 * @return True if valid.
	 */
	public boolean checkPath(char[] path) {
		for (char c : path) {
			if (c != 'a' && c != 's' && c != 'd' && c != 'w')
				return false;
		}
		return true;
	}

	/**
	 * Returns the index of next command.
	 * 
	 * @return Index of next command.
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * Changes the index of next command.
	 * 
	 * @param counter
	 *            New index of next command.
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}

	/**
	 * Move the guard in relation to a map.
	 * 
	 * @param map
	 *            Move in relation to this map.
	 */
	@Override
	public void move(char[][] map) {
		if (path.length == 0)
			return;
		counter %= this.path.length;
		char command = path[counter];
		movement(map, command);
		counter %= this.path.length;
	}

	/**
	 * Moves in relation to map with a given command.
	 * 
	 * @param map
	 *            Move in relation to this map.
	 * @param command
	 *            Given command.
	 */
	public void movement(char[][] map, char command) {
		int newPos[] = getNewPos(command);
		if (newPos == null)
			return;
		if (moveInto(newPos, map))
			counter++;
	}

	/**
	 * Checks if the guard killed a hero.
	 * @param hero Hero to check.
	 * @return True is the hero was killed.
	 */
	@Override
	public boolean killedHero(Hero hero) {
		if ((Math.abs(hero.getPos()[0] - this.getPos()[0]) <= 1 && hero.getPos()[1] == this.getPos()[1])
				|| (Math.abs(hero.getPos()[1] - this.getPos()[1]) <= 1 && hero.getPos()[0] == this.getPos()[0])) {
			return true;
		}
		return false;
	}

	/**
	 * Prints the character to a given map.
	 * @param map Prints the character to this map.
	 */
	@Override
	public void print(char[][] map) {
		map[this.getPos()[0]][this.getPos()[1]] = this.getSymbol();
	}

}
