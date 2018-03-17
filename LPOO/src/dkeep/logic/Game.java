package dkeep.logic;

import java.util.ArrayList;

public class Game {

	private ArrayList<Enemy> enemy;
	private Hero hero;
	private LeverDoor lever;
	private KeyDoor key;
	private char map[][];
	private boolean levelAvailable;

	public Game(Hero hero, char map[][]) {
		this.hero = hero;
		this.map = deepCopyCharMatrix(map);
		enemy = new ArrayList<Enemy>();
		key = null;
		lever = null;
		levelAvailable = true;

		
	}

	public void addEnemy(Guard guard) {
		enemy.add(guard);
	}

	public void addEnemy(Ogre guard) {
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

	public int endLevel() {
		if (this.getMap()[this.getHero().getPos()[0]][this.getHero().getPos()[1]] == 'S') {
			return 0;
		}

		for (int i = 0; i < enemy.size(); i++) {
			if (enemy.get(i).killedHero(hero)) {
				return 2;
			}
		}

		return 1;
	}

	public char[][] getMapWCharacter() {
		char mapWChar[][] = deepCopyCharMatrix(map);
		if (lever != null)
			mapWChar[lever.getPos()[0]][lever.getPos()[1]] = lever.getSymbol();

		if (key != null && !key.isPicked())
			mapWChar[key.getPos()[0]][key.getPos()[1]] = key.getSymbol();

		mapWChar[this.getHero().getPos()[0]][this.getHero().getPos()[1]] = this.getHero().getSymbol();

		for (int i = 0; i < enemy.size(); i++) {
			enemy.get(i).print(mapWChar);
		}

		return mapWChar;
	}

	public void movement(char command) {
		if (!hero.move(command, map))
			return;
		for (int i = 0; i < enemy.size(); i++) {
			enemy.get(i).move(map);
		}

		if (lever != null)
			lever.pullLever(hero, map);

		if (key != null)
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

	public boolean nextLevel(int numberOfOgres) {
		if (levelAvailable) {
			char[][] map2 = new char[][] { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
					{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
					{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
					{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
					{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
					{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
					{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
					{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
					{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
					{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, };

			hero = new Hero(new int[] { 8, 1 }, 'H', true);
			map = map2;
			this.getEnemy().clear();
			for (int i = 0; i < numberOfOgres; i++)
				this.getEnemy().add(new Ogre(new int[] { 1, 4 }, 'O', '*', '8'));
			this.setLever(null);
			this.setKey(new KeyDoor(new int[] { 1, 8 }, new int[][] { { 1, 0 } }, 'k', 'S'));
			levelAvailable = false;
			return true;
		}
		return false;
	}

}