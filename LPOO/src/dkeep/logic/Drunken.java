package dkeep.logic;

import java.util.Arrays;
import java.util.Random;

public class Drunken extends Guard {
	private int backwards;
	private int sleep;
	private char sSymbol;

	public Drunken(int[] pos, char[] path) {
		super(pos, path);
		this.backwards = 0;
		this.sleep = 0;
		this.sSymbol = 'g';
	}

	@Override
	public String toString() {
		return "Drunken [path=" + Arrays.toString(path) + ", pos=" + Arrays.toString(pos) + "]";
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
			int oldBackwards = this.backwards;
			this.backwards = generator.nextInt(2);
			if (oldBackwards != this.backwards) {
				if (this.backwards == 1)
					this.setCounter((this.getCounter() - 1 + this.getPath().length) % this.getPath().length);
				else
					this.setCounter((this.getCounter() + 1) % this.getPath().length);
			}
		}

		char command = this.getPath()[this.getCounter()];
		if (backwards == 1) {
			switch (command) {
			case 's':
				if (map[this.getPos()[0] - 1][this.getPos()[1]] == ' ') {
					this.getPos()[0]--;
					this.setCounter(this.getCounter() - 1);
				}
				break;

			case 'd':
				if (map[this.getPos()[0]][this.getPos()[1] - 1] == ' ') {
					this.getPos()[1]--;
					this.setCounter(this.getCounter() - 1);
				}
				break;

			case 'w':
				if (map[this.getPos()[0] + 1][this.getPos()[1]] == ' ') {
					this.getPos()[0]++;
					this.setCounter(this.getCounter() - 1);
				}
				break;

			case 'a':
				if (map[this.getPos()[0]][this.getPos()[1] + 1] == ' ') {
					this.getPos()[1]++;
					this.setCounter(this.getCounter() - 1);
				}
				break;

			default:
				break;
			}

		} else
			super.move(map);
		if (this.getCounter() < 0)
			this.setCounter(this.getCounter() + this.getPath().length);
		this.setCounter(this.getCounter() % this.getPath().length);
	}

	@Override
	public boolean killedHero(Hero hero) {
		if (sleep == 1)
			return false;
		return super.killedHero(hero);
	}

}
