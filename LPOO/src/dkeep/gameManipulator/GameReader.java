package dkeep.gameManipulator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import dkeep.logic.Drunken;
import dkeep.logic.Guard;
import dkeep.logic.Ogre;
import dkeep.logic.Suspicious;

/**
 * Contains all functions to read an entire game from files.
 */
public abstract class GameReader extends SimpleReader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7672596544896777696L;

	/**
	 * Adds a guard to the enemy array.
	 * 
	 * @param guard
	 *            Guard to add.
	 */
	public void addEnemy(Guard guard) {
		enemies.add(guard);
	}

	/**
	 * Adds a ogre to the enemy array.
	 * 
	 * @param ogre
	 *            Ogre to add.
	 */
	public void addEnemy(Ogre ogre) {
		enemies.add(ogre);
	}

	/**
	 * Moves to the next Level in the filename array.
	 * 
	 * @param numberOfOgres
	 *            Number of ogres per spawn.
	 * @param guardType
	 *            Type of Guards.
	 * @return True if there is a next level.
	 */
	public boolean nextLevel(int numberOfOgres, String guardType) {
		if (next_level < levels.size()) {
			try {
				this.readLevel(levels.get(next_level), numberOfOgres, guardType);
			} catch (IOException e) {
				System.out.println("File " + levels.get(next_level) + " passing to the next level.");
				next_level++;
				return this.nextLevel(numberOfOgres, guardType);
			}
			next_level++;
			return true;
		}
		return false;
	}

	/**
	 * Loads a game from a file.
	 * 
	 * @param fileName
	 *            Filename containing the level.
	 * @param numberOfOgres
	 *            Number of ogres per spawn.
	 * @param guardType
	 *            Type of Guards.
	 * @throws IOException
	 */
	public void readLevel(String fileName, int numberOfOgres, String guardType) throws IOException {
		Scanner sc = new Scanner(new File(fileName));
		try {
			this.enemies.clear();
			this.dMechanism.clear();
			this.map = readMap(sc);

			readCharacters(numberOfOgres, guardType, sc);

		} finally {
			sc.close();
		}

	}

	/**
	 * Reads all characters from a String.
	 * 
	 * @param numberOfOgres
	 *            Number of ogres per spawn.
	 * @param guardType
	 *            Type of Guards.
	 * @param sc
	 *            String to read from.
	 */
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

	/**
	 * Reads a guard from a string.
	 * 
	 * @param guardType
	 *            Type of Guard.
	 * @param sc
	 *            String to read from.
	 */
	protected void readGuard(String guardType, String charInfo) {
		Scanner guardScanner = new Scanner(charInfo);
		int[] guardPos = readPos(guardScanner);
		char[] path = readPath(guardScanner);
		switch (guardType) {
		case "Rookie":
			// System.out.println(guardType);
			this.addEnemy(new Guard(guardPos, Arrays.copyOfRange(path, 1, path.length)));
			break;
		case "Drunken":
			// System.out.println(guardType);
			this.addEnemy(new Drunken(guardPos, Arrays.copyOfRange(path, 1, path.length)));
			break;
		case "Suspicious":
			// System.out.println(guardType);
			this.addEnemy(new Suspicious(guardPos, Arrays.copyOfRange(path, 1, path.length)));
			break;
		}
		guardScanner.close();
	}

	/**
	 * Reads an ogre spawner from a string.
	 * 
	 * @param numberOfOgres
	 *            Number of ogres per spawn.
	 * @param sc
	 *            String to read from.
	 */
	protected void readOgre(int numberOfOgres, String charInfo) {
		Scanner ogreScanner = new Scanner(charInfo);
		int ogrePos[] = readPos(ogreScanner);
		for (int i = 0; i < numberOfOgres; i++)
			this.enemies.add(new Ogre(ogrePos.clone()));
		ogreScanner.close();
	}
}
