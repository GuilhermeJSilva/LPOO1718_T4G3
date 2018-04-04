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

/**
 * Reads all elements to form an editor.
 *
 */
public abstract class SimpleReader extends ReadElements implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3952642826484349830L;
	/**
	 * Array of filenames that contain levels.
	 */
	protected ArrayList<String> levels = new ArrayList<String>();

	/**
	 * Index of the next level.
	 */
	protected int next_level = 0;

	/**
	 * Map of the level.
	 */
	protected char map[][];

	/**
	 * Returns the current Map.
	 * 
	 * @return Current map.
	 */
	public char[][] getMap() {
		return map;
	}

	/**
	 * Empty constructor.
	 */
	public SimpleReader() {
		super();
	}

	/**
	 * Advances to the next level.
	 * 
	 * @return True if there is a next level.
	 */
	public boolean nextLevel() {
		if (next_level < levels.size()) {
			try {
				this.readLevel(levels.get(next_level));
			} catch (IOException e) {
				System.err.println("File " + levels.get(next_level) + " missing: passing to the next level.");
				next_level++;
				return this.nextLevel();
			}
			next_level++;
			return true;
		}
		return false;
	}

	/**
	 * Reads a level from a file.
	 * 
	 * @param fileName
	 *            Level filename.
	 * @throws IOException
	 *             The file does not exist.
	 */
	public void readLevel(String fileName) throws IOException {
		Scanner sc = new Scanner(new File(fileName));
		try {
			this.enemies.clear();
			this.dMechanism.clear();
			this.map = readMap(sc);

			readCharacters(sc);

		} finally {
			sc.close();
		}

	}

	/**
	 * Returns the array of levels.
	 * @return Array of levels.
	 */
	public ArrayList<String> getLevels() {
		return levels;
	}

	/**
	 * Returns the index of the next level.
	 * @return Index of the next level.
	 */
	public int getNext_level() {
		return next_level;
	}

	/**
	 * Reads a map from a given scanner.
	 * @param sc Source of the map.
	 * @return Map.
	 */
	protected char[][] readMap(Scanner sc) {
		String line =  new String();
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

	/**
	 * Reads the level filenames from
	 * @throws IOException
	 */
	public void readLevelNames() throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("levels.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("File levels.txt not found.");
			System.exit(-4);
		}
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

	/**
	 * Saves file names to the levels.txt file.
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void saveLevelFiles() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("levels.txt", "UTF-8");
		for (String file : levels) {
			writer.println(file);
		}
		writer.close();

	}

	/**
	 * Saves a level to a file.
	 * @param fileName Filename of the save destination.
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
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

	/**
	 * Clones a two dimensional array.
	 * @param input Matrix to clone.
	 * @return Cloned matrix.
	 */
	public static char[][] deepCopyCharMatrix(char[][] input) {
		if (input == null)
			return null;
		char[][] result = new char[input.length][];
		for (char r = 0; r < input.length; r++) {
			result[r] = input[r].clone();
		}
		return result;
	}

	/**
	 * Returns the map with the characters inserted.
	 * @return Map with the characters.
	 */
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