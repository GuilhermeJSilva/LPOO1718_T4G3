package dkeep.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameReader {
	protected ArrayList<String> levels = new ArrayList<String>();
	protected int curr_level = 0;
	protected Hero hero;
	protected LeverDoor lever;
	protected KeyDoor key;
	protected char map[][];
	protected Guard guard;
	protected Ogre ogre;

	public Ogre getOgre() {
		return ogre;
	}

	public void setOgre(Ogre ogre) {
		this.ogre = ogre;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
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

	public Guard getGuard() {
		return guard;
	}

	public void setGuard(Guard guard) {
		this.guard = guard;
	}

	public char[][] getMap() {
		return map;
	}

	public GameReader() {
		super();
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
			this.guard = null;
			this.ogre = null;
			this.key  = null;
			this.lever = null;
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

	public void setLevels(ArrayList<String> levels) {
		this.levels = levels;
	}

	public int getCurr_level() {
		return curr_level;
	}

	public void setCurr_level(int curr_level) {
		this.curr_level = curr_level;
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

		this.guard = new Guard(guardPos, Arrays.copyOfRange(path, 1, path.length));

		guardScanner.close();
	}

	protected void readOgre(String charInfo) {
		Scanner ogreScanner = new Scanner(charInfo);
		int ogrePos[] = new int[2];
		if (ogreScanner.hasNextInt())
			ogrePos[0] = ogreScanner.nextInt();
		if (ogreScanner.hasNextInt())
			ogrePos[1] = ogreScanner.nextInt();
		this.ogre = new Ogre(ogrePos.clone());
		ogreScanner.close();
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
		// System.out.println(Arrays.toString(line.toCharArray()));
		if (line.equals("Lever")) {
			this.lever = new LeverDoor(keyPos, aDoors);
			this.key = null;
		} else {
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

	protected char[][] readMap(Scanner sc, String line) {
		int x, y;
		x = sc.nextInt();
		// System.out.println(x);
		y = sc.nextInt();
		// System.out.println(y);
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
		saveGuard(writer);
		
		saveOgre(writer);
		
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
		if(this.lever != null) {
			writer.println("Lever");
			writer.print(lever.getPos()[0] + " " + lever.getPos()[1]);
			for(ArrayList<Integer> intA: this.lever.getDoors()) {
				writer.print(" " + intA.get(0) + " " + intA.get(1));
			}
			writer.println();
		}
	}

	protected void saveKey(PrintWriter writer) {
		if(this.key != null) {
			writer.println("Key");
			writer.print(key.getPos()[0] + " " + key.getPos()[1]);
			for(ArrayList<Integer> intA: this.key.getDoors()) {
				writer.print(" " + intA.get(0) + " " + intA.get(1));
			}
			writer.println();
		}
	}

	protected void saveOgre(PrintWriter writer) {
		if(this.ogre != null) {
			writer.println("Ogre");
			writer.println(ogre.getPos()[0] + " " + ogre.getPos()[1]);
		}
	}

	protected void saveGuard(PrintWriter writer) {
		if(this.guard != null) {
			writer.println("Guard");
			writer.print(guard.getPos()[0] + " " + guard.getPos()[1] + " ");
			for(char c: guard.getPath())
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
		if (lever != null)
			mapWChar[lever.getPos()[0]][lever.getPos()[1]] = lever.getSymbol();

		if (key != null && !key.isPicked())
			mapWChar[key.getPos()[0]][key.getPos()[1]] = key.getSymbol();

		if (hero != null)
			mapWChar[this.getHero().getPos()[0]][this.getHero().getPos()[1]] = this.getHero().getSymbol();

		if (this.guard != null)
			this.guard.print(mapWChar);
		if (this.ogre != null)
			this.ogre.print(mapWChar);
		return mapWChar;
	}
}