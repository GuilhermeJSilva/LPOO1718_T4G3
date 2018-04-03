package dkeep.gameManipulator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import dkeep.logic.Drunken;
import dkeep.logic.Guard;
import dkeep.logic.Ogre;
import dkeep.logic.Suspicious;

public abstract class GameReader extends SimpleReader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7672596544896777696L;
	
	public void addEnemy(Guard guard) {
		enemies.add(guard);
	}

	public void addEnemy(Ogre guard) {
		enemies.add(guard);
	}

	
	public boolean nextLevel(int numberOfOgres, String guardType) {
		if (curr_level < levels.size()) {
			try {
				this.readLevel(levels.get(curr_level), numberOfOgres, guardType);
			} catch (IOException e) {
				System.out.println("File " + levels.get(curr_level) + " passing to the next level.");
				curr_level++;
				return this.nextLevel(numberOfOgres, guardType);
			}
			curr_level++;
			return true;
		}
		return false;
	}

	public void readLevel(String fileName, int numberOfOgres, String guardType) throws IOException {
		Scanner sc = new Scanner(new File(fileName));
		try {
			this.enemies.clear();
			this.dMechanism.clear();
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

	protected void readGuard(String guardType, String charInfo) {
		Scanner guardScanner = new Scanner(charInfo);
		int[] guardPos = readGuardPos(guardScanner);
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

	protected void readOgre(int numberOfOgres, String charInfo) {
		Scanner ogreScanner = new Scanner(charInfo);
		int ogrePos[] = new int[2];
		if (ogreScanner.hasNextInt())
			ogrePos[0] = ogreScanner.nextInt();
		if (ogreScanner.hasNextInt())
			ogrePos[1] = ogreScanner.nextInt();
		for (int i = 0; i < numberOfOgres; i++)
			this.enemies.add(new Ogre(ogrePos.clone()));
		ogreScanner.close();
	}
}
