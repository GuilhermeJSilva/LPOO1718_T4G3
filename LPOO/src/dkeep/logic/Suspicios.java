package dkeep.logic;

import java.util.Random;

public class Suspicios extends Guard {
	private int backwards;
	Suspicios(int[] pos, char[] path, char symbol) {
		super(pos, path, symbol);
		this.backwards = 0;
	}

	@Override
	public void move(char[][] map) {
		Random generator = new Random();	
		this.backwards = generator.nextInt(2);

		char command = this.getPath()[this.getCounter()];
		if(backwards == 1)
		{
			switch (command) {
			case 's':
				if(map[this.getPos()[0] - 1][this.getPos()[1]] == ' ' || map[this.getPos()[0] - 1][this.getPos()[1]] == 'S')
				{
					this.getPos()[0]--;
					this.setCounter(this.getCounter() - 1);
				}
				break;

			case 'd':
				if(map[this.getPos()[0]][this.getPos()[1] - 1] == ' '|| map[this.getPos()[0]][this.getPos()[1] - 1] == 'S')
				{
					this.getPos()[1]--;
					this.setCounter(this.getCounter() - 1);
				}
				break;

			case 'w':
				if(map[this.getPos()[0] + 1][this.getPos()[1]] == ' '|| map[this.getPos()[0] - 1][this.getPos()[1]] == 'S')
				{
					this.getPos()[0]++;
					this.setCounter(this.getCounter() - 1);
				}
				break;

			case 'a':
				if(map[this.getPos()[0]][this.getPos()[1] + 1] == ' '|| map[this.getPos()[0]][this.getPos()[1] + 1] == 'S')
				{
					this.getPos()[1]++;
					this.setCounter(this.getCounter() - 1);
				}
				break;

			default:
				break;
			}

		}
		else
			super.move(map);
		if(this.getCounter() < 0)
			this.setCounter(this.getCounter() + this.getPath().length);
		this.setCounter(this.getCounter() % this.getPath().length);

	}
}
