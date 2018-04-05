package dkeep.logic;

import java.util.Random;

public class Suspicious extends Guard {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4734790203016569031L;

	/**
	 * Different from one if it is moving backwards.
	 */
	protected int backwards;

	/**
	 * Previous value of backwards.
	 */
	protected int oldBackwards;

	/**
	 * Creates a suspicious guard in a position with a given path.
	 * 
	 * @param pos
	 *            Position of the guard.
	 * @param path
	 *            Path of the guard.
	 */
	public Suspicious(int[] pos, char[] path) {
		super(pos, path);
		this.backwards = 0;
		this.oldBackwards = 0;
	}

	@Override
	public void move(char[][] map) {
		Random generator = new Random();
		this.setBackwards(generator.nextInt(2));

		char command = this.getPath()[this.getCounter()];
		choseMovement(map, command);
		fixCounter();

	}

	/**
	 * Makes sure the index of the next move does not surpass the bounds of the
	 * path.
	 */
	protected void fixCounter() {
		if (this.getCounter() < 0)
			this.setCounter(this.getCounter() + this.getPath().length);
		this.setCounter(this.getCounter() % this.getPath().length);
	}

	/**
	 * Chooses at kind of movement the guard is going to do.
	 * 
	 * @param map
	 *            Map to move in relation to.
	 * @param command
	 *            Command to determine the direction.
	 */
	public void choseMovement(char[][] map, char command) {
		if (backwards != 0) {
			movementBack(map, command);

		} else
			super.move(map);
	}

	/**
	 * Moves the hero backwards.
	 * 
	 * @param map
	 *            Map to move in relation to.
	 * @param command
	 *            Command to determine the direction.
	 */
	public void movementBack(char[][] map, char command) {
		int[] newPos = getNewPosBack(command);

		if (newPos != null && moveInto(newPos, map))
			counter--;
	}

	/**
	 * Gets the new position for a backwards movement.
	 * 
	 * @param command
	 *            Command to determine the direction.
	 * @return New position.
	 */
	protected int[] getNewPosBack(char command) {
		int[] newPos = null;
		switch (command) {
		case 'w':
			newPos = new int[] { this.getPos()[0] + 1, this.getPos()[1] };
			break;

		case 'a':
			newPos = new int[] { this.getPos()[0], this.getPos()[1] + 1 };
			break;

		case 's':
			newPos = new int[] { this.getPos()[0] - 1, this.getPos()[1] };
			break;

		case 'd':
			newPos = new int[] { this.getPos()[0], this.getPos()[1] - 1 };
			break;

		default:
			break;
		}
		return newPos;
	}

	/**
	 * Returns the backwards field.
	 * 
	 * @return Backwards field.
	 */
	public int getBackwards() {
		return backwards;
	}

	/**
	 * Changes the backwards field and updates the counter accordingly.
	 * 
	 * @param backwards New backwards value.
	 */
	public void setBackwards(int backwards) {
		oldBackwards = this.backwards;
		this.backwards = backwards;
		if (oldBackwards != this.backwards) {
			if (this.backwards != 0)
				this.setCounter((this.getCounter() - 1 + (this.getCounter() == 0 ? this.getPath().length : 0)) % this.getPath().length);
			else
				this.setCounter((this.getCounter() + 1) % this.getPath().length);
		}
	}
}
