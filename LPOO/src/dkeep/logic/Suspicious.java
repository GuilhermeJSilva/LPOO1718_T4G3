package dkeep.logic;

import java.util.Random;

public class Suspicious extends Guard {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4734790203016569031L;
	protected int backwards;
	protected int oldBackwards;
	
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
		movement(map, command);
		fixCounter();

	}

	protected void fixCounter() {
		if(this.getCounter() < 0)
			this.setCounter(this.getCounter() + this.getPath().length);
		this.setCounter(this.getCounter() % this.getPath().length);
	}

	protected void movement(char[][] map, char command) {
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
	}

	public int getBackwards() {
		return backwards;
	}

	public void setBackwards(int backwards) {
		oldBackwards = this.backwards;
		this.backwards = backwards;
		if(oldBackwards != this.backwards) {
			if(this.backwards != 0)
				this.setCounter((this.getCounter() - 1 + this.getPath().length) % this.getPath().length);
			else 
				this.setCounter((this.getCounter() + 1) % this.getPath().length);
		}
	}
}
