package dkeep.gameManipulator;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import dkeep.logic.Enemy;
import dkeep.logic.Guard;
import dkeep.logic.Ogre;

public class ReadNonPlayerCharecters extends ReadMechanisms implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2498792618306375240L;
	protected ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	public ReadNonPlayerCharecters() {
		super();
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	protected void readGuard(String charInfo) {
		Scanner guardScanner = new Scanner(charInfo);
		
		int[] guardPos = readGuardPos(guardScanner);
		char[] path = readPath(guardScanner);
	
		this.enemies.add(new Guard(guardPos, Arrays.copyOfRange(path, 1, path.length)));
	
		guardScanner.close();
	}

	protected char[] readPath(Scanner guardScanner) {
		char path[] = new char[0];
		if (guardScanner.hasNext())
			path = guardScanner.nextLine().toCharArray();
		return path;
	}

	protected int[] readGuardPos(Scanner guardScanner) {
		int guardPos[] = new int[2];
		if (guardScanner.hasNextInt())
			guardPos[0] = guardScanner.nextInt();
		if (guardScanner.hasNextInt())
			guardPos[1] = guardScanner.nextInt();
		return guardPos;
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

}