package dkeep.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import dkeep.logic.Door;
import dkeep.logic.Drunken;
import dkeep.logic.Game;
import dkeep.logic.Guard;
import dkeep.logic.Hero;
import dkeep.logic.LeverDoor;
import dkeep.logic.Suspicious;

public class TestGuards {

	char map[][] = { { 'X', 'X', 'X', 'X', 'X' }, { 'X', ' ', ' ', ' ', 'X' }, { 'I', ' ', ' ', ' ', 'X' },
			{ 'I', ' ', ' ', ' ', 'X' }, { 'X', 'X', 'X', 'X', 'X' } };

	@Test(timeout = 1000)
	public void testDrunkenSleep() {
		Game level = new Game(new Hero(new int[] { 1, 1 }, false), map);
		level.addEnemy(new Drunken(new int[] { 1, 3 }, new char[] { 's', 'w', 'a', 'd' }));
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] { 2, 0 }, 'S'));
		doors.add(new Door(new int[] { 3, 0 }, 'S'));
		level.addMechanism(new LeverDoor(new int[] { 3, 1 }, doors));
		int sleepTimes = 0;
		while (sleepTimes < 2) {
			level.movement('s');
			if (level.getEnemies().get(0).getSymbol() == 'g')
				sleepTimes++;
			level.movement('w');
			if (level.getEnemies().get(0).getSymbol() == 'g')
				sleepTimes++;
		}

	}

	@Test
	public void testDrunkenBackwards() {
		char path[] = new char[] { 's', 'w', 'a', 'd' };
		Drunken g = new Drunken(new int[] { 2, 2 }, path);

		for (char c : path) {
			int oldPos[] = g.getPos();
			g.movement(map, c);
			assertTrue(!Arrays.equals(g.getPos(), oldPos));
			g.movementBack(map, c);
			assertArrayEquals(g.getPos(), oldPos);
		}

	}
	
	@Test
	public void testSuspiciousBackwards() {
		char path[] = new char[] { 's', 'w', 'a', 'd' };
		Suspicious g = new Suspicious(new int[] { 2,2 }, path);

		for (char c : path) {
			int oldPos[] = g.getPos();
			g.movement(map, c);
			assertTrue(!Arrays.equals(g.getPos(), oldPos));
			g.movementBack(map, c);
			assertArrayEquals(g.getPos(), oldPos);
		}

	}
	
	@Test(timeout=1000)
	public void testRandomBackwardsDrunken() {
		char path[] = new char[] { 's', 'a', 'w', 'd' };
		Drunken g = new Drunken(new int[] { 2,2 }, path);
		int changeTime = 0;
		int backwards =  g.getBackwards();
		while(changeTime < 2) {
			g.move(map);
			if(g.getBackwards() != backwards)
				changeTime++;
			backwards =  g.getBackwards();
		}
	}
	
	@Test(timeout=1000)
	public void testRandomBackwardsSuspicious() {
		char path[] = new char[] { 's', 'a', 'w', 'd' };
		Suspicious g = new Suspicious(new int[] { 2,2 }, path);
		int changeTime = 0;
		int backwards =  g.getBackwards();
		while(changeTime < 2) {
			g.move(map);
			if(g.getBackwards() != backwards)
				changeTime++;
			backwards =  g.getBackwards();
		}
	}
	
	@Test
	public void testSleepKill() {
		char path[] = new char[] { 's', 'w', 'a', 'd' };
		Drunken g = new Drunken(new int[] { 2, 2 }, path);
		Hero h = new Hero(new int[] { 1, 1 }, false);
		
		g.movement(map, 'w');
		assertTrue(g.killedHero(h));
		g.setSleep(1);
		assertTrue(!g.killedHero(h));

	}
	
	@Test
	public void testPath() {
		char path[] = new char[] { 's', 'w', 'a', 'd' };
		Drunken g = new Drunken(new int[] { 2, 2 }, path);
		
		assertTrue(g.setPath(path));
		assertTrue(!g.setPath(new char[] { 's', 'w', 'a', 'd', 'g' }));
	}
	
	@Test
	public void testGuardType() {
		Game level = new Game(new Hero(new int[] { 1, 1 }, false), map);
		level.addEnemy(new Drunken(new int[] { 1, 3 }, new char[] { 's', 'w', 'a', 'd' }));
		assertEquals("Drunken", level.getGuardType());
		level.getEnemies().clear();
		level.addEnemy(new Suspicious(new int[] { 1, 3 }, new char[] { 's', 'w', 'a', 'd' }));
		assertEquals("Suspicious", level.getGuardType());
		level.getEnemies().clear();
		level.addEnemy(new Guard(new int[] { 1, 3 }, new char[] { 's', 'w', 'a', 'd' }));
		assertEquals("Rookie", level.getGuardType());
	}
}
