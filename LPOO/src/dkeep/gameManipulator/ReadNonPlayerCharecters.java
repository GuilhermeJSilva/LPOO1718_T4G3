package dkeep.gameManipulator;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import dkeep.logic.Enemy;
import dkeep.logic.Guard;
import dkeep.logic.Ogre;

/**
 * Reads all non player characters from a file.
 *
 */
public class ReadNonPlayerCharecters extends ReadMechanisms implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2498792618306375240L;

	/**
	 * Array of enemies (Guards/Ogres)
	 */
	protected ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	/**
	 * Empty constructor.
	 */
	public ReadNonPlayerCharecters() {
		super();
	}

	/**
	 * Returns the enemy array.
	 * 
	 * @return Enemy array.
	 */
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	/**
	 * Reads a guard from a string.
	 * @param charInfo String to read from.
	 */
	protected void readGuard(String charInfo) {
		Scanner guardScanner = new Scanner(charInfo);

		int[] guardPos = readPos(guardScanner);
		char[] path = readPath(guardScanner);

		this.enemies.add(new Guard(guardPos, Arrays.copyOfRange(path, 1, path.length)));

		guardScanner.close();
	}

	/**
	 * Reads a guard path.
	 * @param guardScanner Scanner to read from.
	 * @return Read path.
	 */
	protected char[] readPath(Scanner guardScanner) {
		char path[] = new char[0];
		if (guardScanner.hasNext())
			path = guardScanner.nextLine().toCharArray();
		return path;
	}


	/**
	 * Reads the coordinates from a scanner.
	 * @param guardScanner Scanner to read from.
	 * @return Coordinates read.
	 */
	protected int[] readPos(Scanner guardScanner) {
		int pos[] = new int[2];
		if (guardScanner.hasNextInt())
			pos[0] = guardScanner.nextInt();
		if (guardScanner.hasNextInt())
			pos[1] = guardScanner.nextInt();
		return pos;
	}

	/**
	 * Reads an ogre from a string.
	 * @param charInfo String to read from.
	 */
	protected void readOgre(String charInfo) {
		Scanner ogreScanner = new Scanner(charInfo);
		int ogrePos[] = readPos(ogreScanner);
		this.enemies.add(new Ogre(ogrePos.clone()));
		ogreScanner.close();
	}

	/**
	 * Saves all enemies to a file.
	 * @param writer Save destination.
	 */
	protected void saveEnemies(PrintWriter writer) {
		for (Enemy enemy : enemies) {
			if (enemy instanceof Guard) {
				saveGuard((Guard) enemy, writer);
			} else if (enemy instanceof Ogre) {
				saveOgre((Ogre) enemy, writer);
			}
		}
	}

	/**
	 * Saves an ogre to a file.
	 * @param ogre Ogre to save.
	 * @param writer Save destination.
	 */
	protected void saveOgre(Ogre ogre, PrintWriter writer) {
		if (ogre != null) {
			writer.println("Ogre");
			writer.println(ogre.getPos()[0] + " " + ogre.getPos()[1]);
		}
	}

	/**
	 * Saves an guard to a file.
	 * @param guard Guard to save.
	 * @param writer Save destination.
	 */
	protected void saveGuard(Guard guard, PrintWriter writer) {
		if (guard != null) {
			writer.println("Guard");
			writer.print(guard.getPos()[0] + " " + guard.getPos()[1] + " ");
			for (char c : guard.getPath())
				writer.print(c);
			writer.println();
		}
	}

}