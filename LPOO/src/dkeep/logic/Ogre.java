package dkeep.logic;

import java.util.Random;

public class Ogre extends Enemy {
	private int clubPos[];
	private char club;
	private int stunnedTurns;
	private char sSymbol;

	public char getClub() {
		return club;
	}

	public void setClub(char club) {
		this.club = club;
	}

	public int[] getClubPos() {
		return clubPos;
	}

	public void setClubPos(int[] clubPos) {
		this.clubPos = clubPos;
	}

	public Ogre(int[] pos) {
		super(pos, 'O');
		clubPos = new int[2];
		this.swing();
		this.club = '*';
		this.sSymbol = '8';
		this.stunnedTurns = 0;
	}

	@Override
	public char getSymbol() {
		if(this.stunnedTurns == 0)
			return super.getSymbol();
		else 
			return this.sSymbol;
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

	@Override
	public void move(char map[][])
	{
		if(this.stunnedTurns != 0)
		{
			this.stunnedTurns--;
			return;
		}
		
		Random generator = new Random();
		int command = generator.nextInt(4); 
		switch (command) {
		case 0:
			if(map[this.getPos()[0] - 1][this.getPos()[1]] == ' ' )
				this.getPos()[0]--;

			break;
		case 1:
			if(map[this.getPos()[0]][this.getPos()[1] - 1] == ' ')
				this.getPos()[1]--;

			break;
		case 2:
			if(map[this.getPos()[0] + 1][this.getPos()[1]] == ' ')
				this.getPos()[0]++;

			break;
		case 3:
			if(map[this.getPos()[0]][this.getPos()[1] + 1] == ' ')
				this.getPos()[1]++;

			break;

		default:
			break;
		}
		this.swing();
	}

	@Override
	public boolean killedHero(Hero hero)
	{

		if ((Math.abs(hero.getPos()[0] - this.getPos()[0]) <= 1 && hero.getPos()[1] == this.getPos()[1]) ||
				(Math.abs(hero.getPos()[1] - this.getPos()[1]) <= 1 && hero.getPos()[0] == this.getPos()[0]))
		{
			if(hero.isArmed())
			{
				this.stun(2);
				return false;

			}
			else 
				return true;
		}
		else if(((Math.abs(hero.getPos()[0] - this.getClubPos()[0]) <= 1 && hero.getPos()[1] == this.getClubPos()[1]) ||
				(Math.abs(hero.getPos()[1] - this.getClubPos()[1]) <= 1 && hero.getPos()[0] == this.getClubPos()[0])) 
				&& this.stunnedTurns == 0)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public void print(char[][] map) {
		// TODO Auto-generated method stub
		if(map[this.getPos()[0]][this.getPos()[1]] == 'k')
			map[this.getPos()[0]][this.getPos()[1]] = '$';
		else
			map[this.getPos()[0]][this.getPos()[1]] = this.getSymbol();
		if(this.stunnedTurns == 0)
		{
			if(map[this.getClubPos()[0]][this.getClubPos()[1]] == 'k')
				map[this.getClubPos()[0]][this.getClubPos()[1]] ='$';
			else
				map[this.getClubPos()[0]][this.getClubPos()[1]] = this.getClub();
		}
	}

	public void stun(int turns)
	{
		this.stunnedTurns = turns;
	}
}
