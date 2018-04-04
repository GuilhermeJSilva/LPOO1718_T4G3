package dkeep.logic;

import java.util.Random;

public class Ogre extends Enemy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6255598912534679523L;
	
	/**
	 * Position of the ogres club.
	 */
	private int clubPos[];
	
	/**
	 * Symbol of the club.
	 */
	private char club;
	
	/**
	 * Turns that the ogre is still stunned for.
	 */
	private int stunnedTurns;
	
	/**
	 * Symbol of a stunned ogre.
	 */
	private char sSymbol;
	
	/**
	 * Creates an ogre in a certain position.
	 * @param pos Position of the ogre.
	 */
	public Ogre(int[] pos) {
		super(pos, 'O');
		clubPos = new int[2];
		this.swing();
		this.club = '*';
		this.sSymbol = '8';
		this.stunnedTurns = 0;
	}
	
	/**
	 * Retuns the number of stunned turns left.
	 * @return Number of stunned turns left.
	 */
	public int getStunnedTurns() {
		return stunnedTurns;
	}

	
	/**
	 * Returns the symbol of the club.
	 * @return Club symbol.
	 */
	public char getClub() {
		return club;
	}

	/**
	 * Changes the club symbol.
	 * @param club New club symbol.
	 */
	public void setClub(char club) {
		this.club = club;
	}

	/**
	 * Returns the club position.
	 * @return Club position.
	 */
	public int[] getClubPos() {
		return clubPos;
	}

	/**
	 * Changes the club position.
	 * @param clubPos
	 */
	public void setClubPos(int[] clubPos) {
		this.clubPos = clubPos;
	}

	
	/**
	 * Returns the active symbol.
	 * @return Active symbol.
	 */
	@Override
	public char getSymbol() {
		if(this.stunnedTurns == 0)
			return super.getSymbol();
		else 
			return this.sSymbol;
	}

	/**
	 * Changes randomly the position of the club in accordance with the position ogre.
	 */
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

	/**
	 * Move the ogre in relation with a given map.
	 * @param map Map.
	 */
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
		
		movement(map, command);
		this.swing();
	}

	/**
	 * Moves the ogre randomly in accordance with a map and a command.
	 * @param map Map.
	 * @param command Command.
	 */
	public void movement(char[][] map, int command) {
		int[] newPos = getNewPosOgre(command);
		if(newPos != null)
			moveInto(newPos, map);
	}

	/**
	 * Gets a new position for the ogre.
	 * @param command Command that determines the new position.
	 * @return New position.
	 */
	protected int[] getNewPosOgre(int command) {
		int newPos[] = null;
		switch (command) {
		case 0:
			newPos =  new int[] {this.getPos()[0] - 1 ,this.getPos()[1]};
			break;
		case 1:
			newPos =  new int[] {this.getPos()[0] ,this.getPos()[1] -1};
			break;
		case 2:
			newPos =  new int[] {this.getPos()[0] + 1 ,this.getPos()[1]};
			break;
		case 3:
			newPos =  new int[] {this.getPos()[0] ,this.getPos()[1] + 1};
			break;

		default:
			break;
		}
		return newPos;
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
		if(map == null || this.getPos()[0] >=  map.length || (this.getPos()[1] >= map[0].length))
			return;
		
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

	/**
	 * Changes the number of stunned turns left.
	 * @param turns New number of stunned turns left.
	 */
	public void stun(int turns)
	{
		this.stunnedTurns = turns;
	}
}
