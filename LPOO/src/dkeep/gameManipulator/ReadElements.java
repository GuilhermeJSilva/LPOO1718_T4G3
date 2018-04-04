package dkeep.gameManipulator;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

import dkeep.logic.Hero;

/**
 * Reads all game elements from files.
 *
 */
public abstract class ReadElements extends ReadNonPlayerCharecters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6857892433134458106L;
	/**
	 * Saves the game/editor hero.
	 */
	protected Hero hero;

	/**
	 * Empty constructor.
	 */
	public ReadElements() {
		super();
	}

	/**
	 * Returns the hero.
	 * 
	 * @return Hero.
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * Reads all game characters from a scanner.
	 * 
	 * @param sc
	 *            Scanner to read from.
	 */
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

	/**
	 * Reads a hero from a string.
	 * 
	 * @param charInfo
	 *            String to read from.
	 */
	protected void readHero(String charInfo) {
		Scanner heroScanner = new Scanner(charInfo);
		int heroPos[] = readPos(heroScanner);
		int armed = 0;
		if (heroScanner.hasNextInt())
			armed = heroScanner.nextInt();
		this.hero = new Hero(heroPos, armed == 1);
		heroScanner.close();
	}

	/**
	 * Saves an hero to a file.
	 * 
	 * @param writer
	 *            Place to write to.
	 */
	protected void saveHero(PrintWriter writer) {
		if (this.hero != null) {
			writer.println("Hero");
			writer.println(hero.getPos()[0] + " " + hero.getPos()[1] + " " + (hero.isArmed() ? 1 : 0));
		}
	}

}