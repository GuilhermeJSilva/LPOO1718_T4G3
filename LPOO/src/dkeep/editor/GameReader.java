package dkeep.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import dkeep.logic.Door;
import dkeep.logic.DoorMechanism;
import dkeep.logic.Enemy;
import dkeep.logic.Guard;
import dkeep.logic.Hero;
import dkeep.logic.KeyDoor;
import dkeep.logic.LeverDoor;
import dkeep.logic.Ogre;

public class GameReader implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3952642826484349830L;
	protected ArrayList<String> levels = new ArrayList<String>();
	protected int curr_level = 0;
	protected Hero hero;
	protected ArrayList<DoorMechanism> dMechanism = new ArrayList<DoorMechanism>();;
	protected char map[][];
	protected ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	public Hero getHero() {
		return hero;
	}

	public char[][] getMap() {
		return map;
	}

	public GameReader() {
		super();
	}

	public void addMechanism(DoorMechanism mecha) {
		if (dMechanism != null)
			dMechanism.add(mecha);
	}

	public DoorMechanism getMechanism(int coords[]) {
		if (coords.length < 2) {
			return null;
		}
		for (DoorMechanism dMecha : dMechanism) {
			if (Arrays.equals(coords, dMecha.getPos())) {
				return dMecha;
			}
		}
		return null;
	}

	public ArrayList<DoorMechanism> getdMechanism() {
		return dMechanism;
	}

	public void setdMechanism(ArrayList<DoorMechanism> dMechanism) {
		this.dMechanism = dMechanism;
	}

	public boolean nextLevel() {
		if (curr_level < levels.size()) {
			try {
				this.readLevel(levels.get(curr_level));
			} catch (IOException e) {
				System.out.println("File " + levels.get(curr_level) + " missing: passing to the next level.");
				curr_level++;
				return this.nextLevel();
			}
			curr_level++;
			return true;
		}
		return false;
	}

	public void readLevel(String fileName) throws IOException {
		Scanner sc = new Scanner(new File(fileName));
		try {
			this.enemies.clear();
			this.dMechanism.clear();
			String line = new String();
			this.map = readMap(sc, line);

			readCharacters(sc);

		} finally {
			sc.close();
		}

	}

	public ArrayList<String> getLevels() {
		return levels;
	}


	public int getCurr_level() {
		return curr_level;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}


	protected void readCharacters(Scanner sc) {
		String line;

		while (sc.hasNextLine()) {
			line = sc.nextLine();
			String charInfo = sc.nextLine();
			switch (line) {
			case "Ogre":
				readOgre(charInfo);
				break;
			case "Key":
			case "Lever":
				readKeyOrLever(line, charInfo);
				break;

			case "Guard":
				readGuard(charInfo);
				break;

			case "Hero":
				readHero(charInfo);
				break;

			default:
				break;
			}

		}
	}

	protected void readGuard(String charInfo) {
		Scanner guardScanner = new Scanner(charInfo);
		char path[] = new char[0];
		int guardPos[] = new int[2];
		if (guardScanner.hasNextInt())
			guardPos[0] = guardScanner.nextInt();
		if (guardScanner.hasNextInt())
			guardPos[1] = guardScanner.nextInt();
		if (guardScanner.hasNext())
			path = guardScanner.nextLine().toCharArray();

		this.enemies.add(new Guard(guardPos, Arrays.copyOfRange(path, 1, path.length)));

		guardScanner.close();
	}

	protected void readOgre(String charInfo) {
		Scanner ogreScanner = new Scanner(charInfo);
		int ogrePos[] = new int[2];
		if (ogreScanner.hasNextInt())
			ogrePos[0] = ogreScanner.nextInt();
		if (ogreScanner.hasNextInt())
			ogrePos[1] = ogreScanner.nextInt();
		this.enemies.add(new Ogre(ogrePos.clone()));
		ogreScanner.close();
	}

	protected void readKeyOrLever(String line, String charInfo) {
		Scanner keyScanner = new Scanner(charInfo);
		int keyPos[] = new int[2];
		if (keyScanner.hasNextInt())
			keyPos[0] = keyScanner.nextInt();
		if (keyScanner.hasNextInt())
			keyPos[1] = keyScanner.nextInt();

		ArrayList<Door> doors = new ArrayList<Door>();
		while (keyScanner.hasNextInt()) {
			int door[] = new int[2];
			if (keyScanner.hasNextInt()) {
				door[0] = keyScanner.nextInt();
				if (keyScanner.hasNextInt()) {
					door[1] = keyScanner.nextInt();
					if (keyScanner.hasNextInt()) {
						int exit = keyScanner.nextInt();
						doors.add(new Door(door, exit != 0 ? 'S' : ' '));
					}
				}
			}
		}

		if (line.equals("Lever")) {
			this.dMechanism.add(new LeverDoor(keyPos, doors));
		} else {
			this.dMechanism.add(new KeyDoor(keyPos, doors));
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

	protected char[][] readMap(Scanner sc, String line) {
		int x, y;
		x = sc.nextInt();
		y = sc.nextInt();
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

	public void readLevelNames() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("levels.txt"));
		try {
			String line = br.readLine();

			while (line != null) {
				this.levels.add(line);
				line = br.readLine();
			}
		} finally {
			br.close();
		}
	}

	public void saveLevelFiles() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("levels.txt", "UTF-8");
		for (String file : levels) {
			writer.println(file);
		}
		writer.close();

	}

	public void saveLevel(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		writer.println(map.length + " " + map[0].length);
		for (char[] line : map) {
			for (char c : line) {
				writer.print(c);
			}
			writer.println();
		}
		saveEnemies(writer);
		saveKey(writer);
		saveLever(writer);
		saveHero(writer);

		writer.close();

	}

	protected void saveHero(PrintWriter writer) {
		if (this.hero != null) {
			writer.println("Hero");
			writer.println(hero.getPos()[0] + " " + hero.getPos()[1] + " " + (hero.isArmed() ? 1 : 0));
		}
	}

	protected void saveLever(PrintWriter writer) {
		if (this.dMechanism != null) {
			for (DoorMechanism lever : dMechanism) {
				if (lever instanceof LeverDoor) {
					writer.println("Lever");
					writer.print(lever.getPos()[0] + " " + lever.getPos()[1]);
					for (Door door : lever.getDoors()) {
						writer.print(" " + door.getPos()[0] + " " + door.getPos()[1] + " "
								+ (door.getOpenS() == 'S' ? 1 : 0));
					}
					writer.println();
				}
			}

		}
	}

	protected void saveKey(PrintWriter writer) {
		if (this.dMechanism != null) {
			for (DoorMechanism key : dMechanism) {
				if (key instanceof KeyDoor) {
					writer.println("Key");
					writer.print(key.getPos()[0] + " " + key.getPos()[1]);
					for (Door door : key.getDoors()) {
						writer.print(" " + door.getPos()[0] + " " + door.getPos()[1] + " "
								+ (door.getOpenS() == 'S' ? 1 : 0));
					}
					writer.println();
				}
			}
		}
	}

	protected void saveEnemies(PrintWriter writer) {
		for (Enemy enemy : enemies) {
			if (enemy instanceof Guard) {
				saveGuard((Guard) enemy, writer);
			} else if (enemy instanceof Ogre) {
				saveOgre((Ogre) enemy, writer);
			}
		}
	}

	protected void saveOgre(Ogre ogre, PrintWriter writer) {
		if (ogre != null) {
			writer.println("Ogre");
			writer.println(ogre.getPos()[0] + " " + ogre.getPos()[1]);
		}
	}

	protected void saveGuard(Guard guard, PrintWriter writer) {
		if (guard != null) {
			writer.println("Guard");
			writer.print(guard.getPos()[0] + " " + guard.getPos()[1] + " ");
			for (char c : guard.getPath())
				writer.print(c);
			writer.println();
		}
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

	public char[][] getMapWCharacter() {
		char mapWChar[][] = deepCopyCharMatrix(map);

		for (DoorMechanism dMecha : dMechanism) {
			mapWChar[dMecha.getPos()[0]][dMecha.getPos()[1]] = dMecha.getSymbol();
		}
		if (hero != null)
			mapWChar[this.getHero().getPos()[0]][this.getHero().getPos()[1]] = this.getHero().getSymbol();

		for (Enemy enemy : enemies) {
			enemy.print(mapWChar);
		}
		return mapWChar;
	}
}