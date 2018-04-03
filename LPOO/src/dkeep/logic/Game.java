package dkeep.logic;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import dkeep.gameManipulator.GameReader;

public class Game extends GameReader implements Serializable {

	private static final long serialVersionUID = 8322748650867056100L;


	public Game(Hero hero, char map[][]) {
		super();
		this.hero = hero;
		this.map = deepCopyCharMatrix(map);
	}

	public Game() {
		enemies = new ArrayList<Enemy>();
		try {
			this.readLevelNames();
		} catch (IOException e) {
			System.err.println("Error accessing levels.txt");
			System.exit(-3);
		}
	}


	public int endLevel() {
		if (this.getMap()[this.getHero().getPos()[0]][this.getHero().getPos()[1]] == 'S') {
			return 0;
		}

		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).killedHero(hero)) {
				return 2;
			}
		}

		return 1;
	}

	public void movement(char command) {
		if (!hero.move(command, map))
			return;
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).move(map);
		}
		
		for (Iterator<DoorMechanism> iterator = dMechanism.iterator(); iterator.hasNext();) {
			DoorMechanism dMecha = (DoorMechanism) iterator.next();
			if(dMecha != null)
				if(dMecha.activateMechanism(hero, map) && dMecha instanceof KeyDoor)
					iterator.remove();
		}
	}

	public String getGuardType() {
		for (Enemy enemy : enemies) {
			if(enemy instanceof Drunken) {
				return "Drunken";
			}
			if(enemy instanceof Suspicious) {
				return "Suspicious";
			}
			if(enemy instanceof Guard) {
				return "Rookie";
			}
		}
		return "Rookie";
	}

	

}