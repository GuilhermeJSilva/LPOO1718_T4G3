package dkeep.logic;

import java.util.ArrayList;

public class LevelState {

	private ArrayList<Enemy> enemy;
	private Hero hero;
	private LeverDoor lever;
	private KeyDoor key;
	private char map[][];

	public LevelState(Hero hero, char map[][]) {		
		this.hero = hero;
		this.map = deepCopyCharMatrix(map);
		enemy = new ArrayList<Enemy>();
		key = null;
		lever = null;
	}

	public void addEnemy(Guard guard)
	{
		enemy.add(guard);
	}
	
	public void addEnemy(Ogre guard)
	{
		enemy.add(guard);
	}

	public LeverDoor getLever() {
		return lever;
	}

	public void setLever(LeverDoor lever) {
		this.lever = lever;
	}

	public KeyDoor getKey() {
		return key;
	}

	public void setKey(KeyDoor key) {
		this.key = key;
	}

	public char[][] getMap() {

		return this.map;
	}

	public void setMap(char[][] map) {
		this.map = map;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public int endLevel()
	{
		if(this.getMap()[this.getHero().getPos()[0]][this.getHero().getPos()[1]] == 'S') {
			System.out.println("Victory");
			return 0;
		}

		for (int i = 0; i < enemy.size(); i++) {
			if(enemy.get(i).killedHero(hero))
			{
				System.out.println("Defeat");
				return 2;
			}
		}

		return 1;
	}

	public char[][] getMapWCharacter() {
		char mapWChar[][] =  deepCopyCharMatrix(map);
		if(lever != null)
			mapWChar[lever.getPos()[0]][lever.getPos()[1]] = lever.getSymbol();

		if(key != null && !key.isPicked())
			mapWChar[key.getPos()[0]][key.getPos()[1]] = key.getSymbol();

		mapWChar[this.getHero().getPos()[0]][this.getHero().getPos()[1]] = this.getHero().getSymbol();

		for (int i = 0; i < enemy.size(); i++) {
			enemy.get(i).print(mapWChar);
		}

		return mapWChar;
	}

	public void movement(char command) 
	{
		if(!hero.move(command, map))
			return;
		for (int i = 0; i < enemy.size(); i++) {
			enemy.get(i).move(map);
		}
		
		if(lever != null)
			lever.pullLever(hero, map);
		
		if(key != null)
			key.pickKey(hero, map);
	}


	public ArrayList<Enemy> getEnemy() {
		return enemy;
	}

	public void setEnemy(ArrayList<Enemy> enemy) {
		this.enemy = enemy;
	}

	public static char[][] deepCopyCharMatrix(char[][] input) {
		if (input == null)
			return null;
		char[][] result = new char[input.length][];
		for (char r = 0; r < input.length; r++) {
			result[r] = input[r].clone();
		}
		return result;
	}

}