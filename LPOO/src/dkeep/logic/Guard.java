package dkeep.logic;

import java.util.Arrays;

public class Guard extends Enemy {
	protected char path[];
	private int counter;
	
	

	@Override
	public String toString() {
		return "Guard [path=" + Arrays.toString(path) + ", pos=" + Arrays.toString(pos) + "]";
	}

	public Guard(int[] pos, char path[]) {
		super(pos, 'G');
		this.path = path;
		this.counter = 0;
	}

	public char[] getPath() {
		return path;
	}

	public void setPath(char[] path) {
		this.path = path;
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
