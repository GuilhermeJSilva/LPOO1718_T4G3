package dkeep.test;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import dkeep.logic.Door;
import dkeep.logic.Drunken;
import dkeep.logic.Game;
import dkeep.logic.Hero;
import dkeep.logic.LeverDoor;
import dkeep.logic.Suspicious;

public class TestGuards {

	char map[][] = {
			{'X', 'X', 'X', 'X', 'X'},
			{'X', ' ', ' ', ' ', 'X'},
			{'I', ' ', ' ', ' ', 'X'},
			{'I', ' ', ' ', ' ', 'X'},
			{'X', 'X', 'X', 'X', 'X'}};
	
	@Test(timeout=1000)
	public void testDrunkenSleep() {
		Game level = new Game(new Hero(new int[] {1,1}, false), map);
		level.addEnemy(new Drunken(new int[] {1,3}, new char[] {'s','w','a','d'}) );
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] {2, 0}, 'S'));
		doors.add(new Door(new int[] {3, 0}, 'S'));
		level.addMechanism(new LeverDoor(new int[] {3,1},doors));
		int sleepTimes = 0;
		while (sleepTimes < 10) {
			level.movement('s');
			if(level.getEnemies().get(0).getSymbol() == 'g')
				sleepTimes++;
			level.movement('w');
			if(level.getEnemies().get(0).getSymbol() == 'g')
				sleepTimes++;
		}

	}
	
	@Test(timeout=1000)
	public void testSuspiciousBackwards() {
		Game level = new Game(new Hero(new int[] {1,1}, false), map);
		level.addEnemy(new Suspicious(new int[] {1,3}, new char[] {'s','w','a','d'}) );
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] {2, 0}, 'S'));
		doors.add(new Door(new int[] {3, 0}, 'S'));
		level.addMechanism(new LeverDoor(new int[] {3,1},doors));
		int backwards = 0;
		while (backwards < 10) {
			int oldPos[] = level.getEnemies().get(0).getPos();
			level.movement('s');
			if(Arrays.equals(level.getEnemies().get(0).getPos(), oldPos))
				backwards++;
			level.movement('w');
			if(Arrays.equals(level.getEnemies().get(0).getPos(), oldPos))
				backwards++;
		}

	}
}
