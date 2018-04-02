package dkeep.logic;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Game extends GameReader implements Serializable {

	private static final long serialVersionUID = 8322748650867056100L;
	private ArrayList<Enemy> enemy;

	public Game(Hero hero, char map[][]) {
		super();
		this.hero = hero;
		this.map = deepCopyCharMatrix(map);
		enemy = new ArrayList<Enemy>();
	}

	public Game() throws IOException {
		enemy = new ArrayList<Enemy>();
		this.readLevelNames();
	}

	public void addEnemy(Guard guard) {
		enemy.add(guard);
	}

	public void addEnemy(Ogre guard) {
		enemy.add(guard);
	}

	public int endLevel() {
		if (this.getMap()[this.getHero().getPos()[0]][this.getHero().getPos()[1]] == 'S') {
			return 0;
		}

		for (int i = 0; i < enemy.size(); i++) {
			if (enemy.get(i).killedHero(hero)) {
				return 2;
			}
		}

		return 1;
	}

	@Override
	public char[][] getMapWCharacter() {
		char mapWChar[][] = deepCopyCharMatrix(map);
		
		for (DoorMechanism dMecha : dMechanism) {
			mapWChar[dMecha.getPos()[0]][dMecha.getPos()[1]] = dMecha.getSymbol();
		}

		if (hero != null)
			mapWChar[this.getHero().getPos()[0]][this.getHero().getPos()[1]] = this.getHero().getSymbol();

		for (int i = 0; i < enemy.size(); i++) {
			if (enemy.get(i) != null)
				enemy.get(i).print(mapWChar);
		}

		return mapWChar;
	}

	public void movement(char command) {
		if (!hero.move(command, map))
			return;
		for (int i = 0; i < enemy.size(); i++) {
			enemy.get(i).move(map);
		}
		
		for (Iterator<DoorMechanism> iterator = dMechanism.iterator(); iterator.hasNext();) {
			DoorMechanism dMecha = (DoorMechanism) iterator.next();
			if(dMecha != null)
				if(dMecha.activateMechanism(hero, map) && dMecha instanceof KeyDoor)
					iterator.remove();
		}
	}

	public ArrayList<Enemy> getEnemy() {
		return enemy;
	}

	public void setEnemy(ArrayList<Enemy> enemy) {
		this.enemy = enemy;
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
			this.enemy.clear();
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
		char path[] = new char[0];
		int guardPos[] = new int[2];
		if (guardScanner.hasNextInt())
			guardPos[0] = guardScanner.nextInt();
		if (guardScanner.hasNextInt())
			guardPos[1] = guardScanner.nextInt();
		if (guardScanner.hasNext())
			path = guardScanner.nextLine().toCharArray();
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
			this.enemy.add(new Ogre(ogrePos.clone()));
		ogreScanner.close();
	}

}