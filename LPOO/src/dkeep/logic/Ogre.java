package dkeep.logic;

import java.util.Random;

public class Ogre extends Character {
	private int clubPos[];
	
	public int[] getClubPos() {
		return clubPos;
	}

	public void setClubPos(int[] clubPos) {
		this.clubPos = clubPos;
	}

	Ogre(int[] pos, char symbol) {
		super(pos, symbol);
	}
	
	public void swing()
	{
		Random generator = new Random();
		int command = generator.nextInt(4);
		switch (command) {
		case 0:
			this.getClubPos()[0] = this.getPos()[0] + 1;
			this.getClubPos()[1] = this.getPos()[1];
			break;

		case 1:
			this.getClubPos()[0] = this.getPos()[0];
			this.getClubPos()[1] = this.getPos()[1]+ 1;
			break;

		case 2:
			this.getClubPos()[0] = this.getPos()[0]-1;
			this.getClubPos()[1] = this.getPos()[1];
			break;

		case 3:
			this.getClubPos()[0] = this.getPos()[0];
			this.getClubPos()[1] = this.getPos()[1]-1;
			break;
		default:
			break;
		}
	}
	
	public void move(char map[][])
	{
		Random generator = new Random();
		int command = generator.nextInt(4); 
		switch (command) {
		case 0:
			if(map[this.getPos()[0] - 1][this.getPos()[1]] == ' ' || map[this.getPos()[0] - 1][this.getPos()[1]] == 'S')
				this.getPos()[0]--;
			

		case 1:
			if(map[this.getPos()[0]][this.getPos()[1] - 1] == ' '|| map[this.getPos()[0]][this.getPos()[1] - 1] == 'S')
				this.getPos()[1]--;
			

		case 2:
			if(map[this.getPos()[0] + 1][this.getPos()[1]] == ' '|| map[this.getPos()[0] - 1][this.getPos()[1]] == 'S')
				this.getPos()[0]++;
			

		case 3:
			if(map[this.getPos()[0]][this.getPos()[1] + 1] == ' '|| map[this.getPos()[0]][this.getPos()[1] + 1] == 'S')
				this.getPos()[1]++;
			
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
		else if((Math.abs(hero.getPos()[0] - this.getClubPos()[0]) <= 1 && hero.getPos()[1] == this.getClubPos()[1]) ||
				(Math.abs(hero.getPos()[1] - this.getClubPos()[1]) <= 1 && hero.getPos()[0] == this.getClubPos()[0]))
		{
			return true;
		}
		return false;
	}

}
