package dkeep.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {

	private ArrayList<String> levels = new ArrayList<String>();
	private int curr_level = 0;
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

	public Game() {
		enemy = new ArrayList<Enemy>();
		levelAvailable = true;
		levels.add("lvl1.txt");
		levels.add("lvl2.txt");
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

	public boolean nextLevel(int numberOfOgres, String guardType) throws IOException {
		if (curr_level < levels.size()) {
			this.readLevel(levels.get(curr_level),numberOfOgres, guardType);
			curr_level++;
			return true;
		}
		return false;
	}

	public void readLevel(String fileName, int numberOfOgres, String guardType) throws IOException {
		Scanner sc = new Scanner(new File(fileName));
		try {
			this.enemy.clear();
			String line = new String();
			this.map = readMap(sc, line);

			readCharacters(numberOfOgres, guardType, sc);

		} finally {
			sc.close();
		}

	}

	protected void readCharacters(int numberOfOgres, String guardType, Scanner sc) {
		String line;

		while (sc.hasNextLine()) {
			line = sc.nextLine();
			String charInfo = sc.nextLine();
			switch (line) {
			case "Ogre":
				readOgre(numberOfOgres, charInfo);
				break;
			case "Key":
			case "Lever":
				readKeyOrLever(line, charInfo);
				break;

			case "Guard":
				readGuard(guardType, charInfo);
				break;

			case "Hero":
				readHero(charInfo);
				break;

			default:
				break;
			}

		}
	}

	protected void readKeyOrLever(String line, String charInfo) {
		Scanner keyScanner = new Scanner(charInfo);
		int keyPos[] = new int[2];
		if (keyScanner.hasNextInt())
			keyPos[0] = keyScanner.nextInt();
		if (keyScanner.hasNextInt())
			keyPos[1] = keyScanner.nextInt();

		ArrayList<int[]> doors = new ArrayList<int[]>();
		while (keyScanner.hasNextInt()) {
			int door[] = new int[2];
			if (keyScanner.hasNextInt()) {
				door[0] = keyScanner.nextInt();
				if (keyScanner.hasNextInt()) {
					door[1] = keyScanner.nextInt();
					doors.add(door);
				}
			}
		}

		int aDoors[][] = new int[doors.size()][2];
		aDoors = doors.toArray(aDoors);
		//System.out.println(Arrays.toString(line.toCharArray()));
		if(line.equals("Lever")) {
			this.lever = new LeverDoor(keyPos, aDoors);
			this.key = null;
		}
		else {
			this.key = new KeyDoor(keyPos, aDoors);
			this.lever = null;
		}
		keyScanner.close();
	}

	protected void readHero(String charInfo) {
		Scanner heroScanner = new Scanner(charInfo);
		int heroPos[] = new int[2];
		int armed = 0;
		if (heroScanner.hasNextInt())
			heroPos[0] = heroScanner.nextInt();
		if (heroScanner.hasNextInt())
			heroPos[1] = heroScanner.nextInt();
		if (heroScanner.hasNextInt())
			armed = heroScanner.nextInt();
		this.hero = new Hero(heroPos, armed == 1);
		heroScanner.close();
	}

	protected void readGuard(String guardType, String charInfo) {
		Scanner guardScanner = new Scanner(charInfo);
		char path[] = new char[0];
		int guardPos[] = new int[2];
		if (guardScanner.hasNextInt())
			guardPos[0] = guardScanner.nextInt();
		if (guardScanner.hasNextInt())
			guardPos[1] = guardScanner.nextInt();
		if (guardScanner.hasNext())
			path = guardScanner.nextLine().toCharArray();
		switch (guardType) {
		case "Rookie":
			//System.out.println(guardType);
			this.addEnemy(new Guard(guardPos, Arrays.copyOfRange(path, 1, path.length)));
			break;
		case "Drunken":
			//System.out.println(guardType);
			this.addEnemy(new Drunken(guardPos,  Arrays.copyOfRange(path, 1, path.length)));
			break;
		case "Suspicious":
			//System.out.println(guardType);
			this.addEnemy(new Suspicious(guardPos,  Arrays.copyOfRange(path, 1, path.length)));
			break;
		}
		guardScanner.close();
	}

	protected void readOgre(int numberOfOgres, String charInfo) {
		Scanner ogreScanner = new Scanner(charInfo);
		int ogrePos[] = new int[2];
		if (ogreScanner.hasNextInt())
			ogrePos[0] = ogreScanner.nextInt();
		if (ogreScanner.hasNextInt())
			ogrePos[1] = ogreScanner.nextInt();
		for (int i = 0; i < numberOfOgres; i++)
			this.enemy.add(new Ogre(ogrePos.clone()));
		ogreScanner.close();
	}

	protected char[][] readMap(Scanner sc, String line) {
		int x, y;
		x = sc.nextInt();
		//System.out.println(x);
		y = sc.nextInt();
		//System.out.println(y);
		if (sc.hasNext())
			line = sc.nextLine();
		char map[][] = new char[x][y];
		for (int i = 0; i < x; i++) {
			if (sc.hasNext())
				line = sc.nextLine();
			map[i] = line.toCharArray();
		}
		return map;
	}

}