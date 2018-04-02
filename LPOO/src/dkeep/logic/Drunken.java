package dkeep.logic;

import java.util.Random;

public class Drunken extends Suspicious {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5512628551857065458L;
	private int sleep;
	private char sSymbol;

	public Drunken(int[] pos, char[] path) {
		super(pos, path);
		this.sleep = 0;
		this.sSymbol = 'g';
	}

	@Override
	public char getSymbol() {
		if (sleep == 1)
			return sSymbol;
		else
			return super.getSymbol();
	}

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


	public int getSleep() {
		return sleep;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

	@Override
	public boolean killedHero(Hero hero) {
		if (sleep == 1)
			return false;
		return super.killedHero(hero);
	}

}
