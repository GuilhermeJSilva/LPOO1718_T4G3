package dkeep.logic;

public class Guard extends Character {
	private char path[];
	private int counter;
	
	Guard(int[] pos, char path[], char symbol) {
		super(pos, symbol);
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

	public void move(char[][] map) {
		char command = path[counter];
		switch (command) {
		case 'w':
			if(map[this.getPos()[0] - 1][this.getPos()[1]] == ' ' || map[this.getPos()[0] - 1][this.getPos()[1]] == 'S')
			{
				this.getPos()[0]--;
				counter++;
			}

		case 'a':
			if(map[this.getPos()[0]][this.getPos()[1] - 1] == ' '|| map[this.getPos()[0]][this.getPos()[1] - 1] == 'S')
			{
				this.getPos()[1]--;
				counter++;
			}
				
		case 's':
			if(map[this.getPos()[0] + 1][this.getPos()[1]] == ' '|| map[this.getPos()[0] - 1][this.getPos()[1]] == 'S')
			{
				this.getPos()[0]++;
				counter++;
			}

		case 'd':
			if(map[this.getPos()[0]][this.getPos()[1] + 1] == ' '|| map[this.getPos()[0]][this.getPos()[1] + 1] == 'S')
			{
				this.getPos()[1]++;
				counter++;
			}
			
		default:
			break;
		}
		
	}
	
	public boolean killedHero(Hero hero)
	{
		if ((Math.abs(hero.getPos()[0] - this.getPos()[0]) <= 1 && hero.getPos()[1] == this.getPos()[1]) ||
				(Math.abs(hero.getPos()[1] - this.getPos()[1]) <= 1 && hero.getPos()[0] == this.getPos()[0]))
		{
			return true;
		}
		return false;
	}

}
