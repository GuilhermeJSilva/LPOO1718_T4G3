package dkeep.logic;

import java.util.Random;

public class Drunken extends Guard {
	private int backwards;
	private int sleep;
	private char sSymbol;
	
	public Drunken(int[] pos, char[] path, char symbol, char sSymbol) {
		super(pos, path, symbol);
		this.backwards = 0;
		this.sleep = 0;
		this.sSymbol = sSymbol;
	}

	@Override
	public char getSymbol() {
		if(sleep == 1)
			return sSymbol;
		else 
			return super.getSymbol();
	}

	@Override
	public void move(char[][] map) {
		Random generator = new Random();
		int newSleep = generator.nextInt(2);

		if(newSleep == 1)
		{
			sleep = 1;
			return;
		}

		if(sleep == 1 && newSleep == 0)
		{
			sleep = 0;
			this.backwards = generator.nextInt(2);
		}

		char command = this.getPath()[this.getCounter()];
		if(backwards == 1)
		{
			switch (command) {
			case 's':
				if(map[this.getPos()[0] - 1][this.getPos()[1]] == ' ' )
				{
					this.getPos()[0]--;
					this.setCounter(this.getCounter() - 1);
				}
				break;

			case 'd':
				if(map[this.getPos()[0]][this.getPos()[1] - 1] == ' ')
				{
					this.getPos()[1]--;
					this.setCounter(this.getCounter() - 1);
				}
				break;
					
			case 'w':
				if(map[this.getPos()[0] + 1][this.getPos()[1]] == ' ')
				{
					this.getPos()[0]++;
					this.setCounter(this.getCounter() - 1);
				}
				break;

			case 'a':
				if(map[this.getPos()[0]][this.getPos()[1] + 1] == ' ')
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
