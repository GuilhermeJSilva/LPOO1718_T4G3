package dkeep.logic;

import java.util.ArrayList;

public class LevelState {

	private char[][] map;
	private static char[][] map1;
	private static char[][] map2;
	private ArrayList<Enemy> enemy;
	private Hero hero;
	private LeverDoor lever;
	private KeyDoor key;

	public LevelState(Hero hero) {
		map1 = new char[][] {
			{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
			{'X', ' ', ' ', ' ', 'I', ' ', 'X', ' ', ' ', 'X'},
			{'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X'},
			{'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X'},
			{'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X'},
			{'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
			{'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
			{'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X'},
			{'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X'},
			{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
		};

		map2 =  new char[][] {
			{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
			{'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
			{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
			{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
			{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
			{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
			{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
			{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
			{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
			{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
		};

		map = deepCopyCharMatrix(map1);
		enemy = new ArrayList<Enemy>();
		this.enemy.add(new Drunken(new int[] {1,8}, new char[] {'a', 's', 's' , 's' , 's', 'a', 'a', 'a', 'a', 'a', 'a','s','d','d','d','d','d','d','d','w','w','w','w','w'}, 'G','g') );
		this.hero = hero;
		lever = new LeverDoor(new int[] {8,7}, new int[][] {{5,0}, {6,0}}, 'k','S');

	}

	public void addGuard(Guard guard)
	{
		enemy.add(guard);
	}

	public char[][] getMap() {

		return map;
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

	public void nextMap()
	{
		map = deepCopyCharMatrix(map2);
		enemy.clear();
		enemy.add(new Ogre(new int[]{1,4},'O', '*'));
		enemy.add(new Ogre(new int[]{5,4},'O', '*'));
		lever = null;
		key = new KeyDoor(new int[] {1,8}, new int[][] {{1,0}}, 'k', 'S');
		hero = new Hero(new int[] {8,1}, 'H');
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