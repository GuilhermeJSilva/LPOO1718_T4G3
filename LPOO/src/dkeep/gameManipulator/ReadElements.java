package dkeep.gameManipulator;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

import dkeep.logic.Hero;

public abstract class ReadElements extends ReadNonPlayerCharecters implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6857892433134458106L;
	protected Hero hero;
	public ReadElements() {
		super();
	}

	public Hero getHero() {
		return hero;
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

	protected void saveHero(PrintWriter writer) {
		if (this.hero != null) {
			writer.println("Hero");
			writer.println(hero.getPos()[0] + " " + hero.getPos()[1] + " " + (hero.isArmed() ? 1 : 0));
		}
	}

}