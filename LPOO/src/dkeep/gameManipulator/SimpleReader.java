package dkeep.gameManipulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import dkeep.logic.DoorMechanism;
import dkeep.logic.Enemy;

public abstract class SimpleReader extends ReadElements implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3952642826484349830L;
	protected ArrayList<String> levels = new ArrayList<String>();
	protected int curr_level = 0;
	protected char map[][];
	public char[][] getMap() {
		return map;
	}

	public SimpleReader() {
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