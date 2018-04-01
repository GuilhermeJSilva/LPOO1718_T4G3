package dkeep.logic;

import java.util.Arrays;

public class Guard extends Enemy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9167529585915531192L;
	protected char path[];
	private int counter;
	
	public Guard(int[] pos, char path[]) {
		super(pos, 'G');
		this.path = path;
		this.counter = 0;
	}
	
	@Override
	public String toString() {
		return "Guard [path=" + Arrays.toString(path) + ", pos=" + Arrays.toString(pos) + "]";
	}

	public char[] getPath() {
		return path;
	}

	public boolean setPath(char[] path) {
		if(!checkPath(path))
			return false;
		this.path = path;
		return true;
	}
	
	public boolean checkPath(char[] path) {
		for (char c : path) {
			if(c != 'a' && c != 's' && c != 'd' && c != 'w')
				return false;
		}
		return true;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	@Override
	public void move(char[][] map) {
		if(path.length == 0)
			return;
		counter %= this.path.length;
		char command = path[counter];
		switch (command) {
		case 'w':
			if(map[this.getPos()[0] - 1][this.getPos()[1]] == ' ' )
			{
				this.getPos()[0]--;
				counter++;
			}
			break;

		case 'a':
			if(map[this.getPos()[0]][this.getPos()[1] - 1] == ' ')
			{
				this.getPos()[1]--;
				counter++;
			}
			break;
				
		case 's':
			if(map[this.getPos()[0] + 1][this.getPos()[1]] == ' ')
			{
				this.getPos()[0]++;
				counter++;
			}
			break;

		case 'd':
			if(map[this.getPos()[0]][this.getPos()[1] + 1] == ' ')
			{
				this.getPos()[1]++;
				counter++;
			}
			break;
			
		default:
			counter++;
			this.move(map);
			break;
		}
		counter %= this.path.length;
	}
	
	@Override
	public boolean killedHero(Hero hero)
	{
		if ((Math.abs(hero.getPos()[0] - this.getPos()[0]) <= 1 && hero.getPos()[1] == this.getPos()[1]) ||
				(Math.abs(hero.getPos()[1] - this.getPos()[1]) <= 1 && hero.getPos()[0] == this.getPos()[0]))
		{
			return true;
		}
		return false;
	}

	@Override
	public void print(char[][] map) {
		map[this.getPos()[0]][this.getPos()[1]] = this.getSymbol();
	}


}
