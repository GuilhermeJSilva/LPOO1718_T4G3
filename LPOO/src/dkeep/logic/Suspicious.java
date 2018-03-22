package dkeep.logic;

import java.util.Arrays;
import java.util.Random;

public class Suspicious extends Guard {
	private int backwards;
	public Suspicious(int[] pos, char[] path) {
		super(pos, path);
		this.backwards = 0;
	}

	@Override
	public String toString() {
		return "Suspicious [path=" + Arrays.toString(path) + ", pos=" + Arrays.toString(pos) + "]";
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
