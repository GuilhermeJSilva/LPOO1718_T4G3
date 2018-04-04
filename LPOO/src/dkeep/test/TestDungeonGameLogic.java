package dkeep.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import dkeep.logic.Door;
import dkeep.logic.Game;
import dkeep.logic.Guard;
import dkeep.logic.Hero;
import dkeep.logic.KeyDoor;
import dkeep.logic.LeverDoor;

public class TestDungeonGameLogic {
	
	char map[][] = {
			{'X', 'X', 'X', 'X', 'X'},
			{'X', ' ', ' ', ' ', 'X'},
			{'I', ' ', ' ', ' ', 'X'},
			{'I', ' ', ' ', ' ', 'X'},
			{'X', 'X', 'X', 'X', 'X'}};
	
	@Test
	public void testMoveHeroIntoFreeCell() {
		Game level = new Game(new Hero(new int[] {1,1}, false), map);
		level.addEnemy(new Guard(new int[] {1,3}, new char[0]) ); 
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] {2, 0}, 'S'));
		doors.add(new Door(new int[] {3, 0}, 'S'));
		level.addMechanism(new LeverDoor(new int[] {3,1},doors));
		assertTrue(Arrays.equals(new int[] {1,1}, level.getHero().getPos()));
		level.movement('s');
		assertTrue(Arrays.equals(new int[] {2,1}, level.getHero().getPos()));
	}
	
	@Test
	public void testMoveHeroIntoWall() {
		Game level = new Game(new Hero(new int[] {1,1}, false), map);
		level.addEnemy(new Guard(new int[] {1,3}, new char[0]) ); 
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] {2, 0}, 'S'));
		doors.add(new Door(new int[] {3, 0}, 'S'));
		level.addMechanism(new LeverDoor(new int[] {3,1},doors));
		assertTrue(Arrays.equals(new int[] {1,1}, level.getHero().getPos()));
		level.movement('w');
		assertTrue(Arrays.equals(new int[] {1,1}, level.getHero().getPos()));
	}
	
	@Test
	public void testMoveHeroIntoDoor() {
		Game level = new Game(new Hero(new int[] {1,1}, false), map);
		level.addEnemy(new Guard(new int[] {1,3}, new char[0]) ); 
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] {2, 0}, 'S'));
		doors.add(new Door(new int[] {3, 0}, 'S'));
		level.addMechanism(new LeverDoor(new int[] {3,1},doors));
		assertTrue(Arrays.equals(new int[] {1,1}, level.getHero().getPos()));
		level.movement('s');
		level.movement('a');
		assertTrue(Arrays.equals(new int[] {2,1}, level.getHero().getPos()));
	}
	
	@Test
	public void testMoveHeroOpensDoor() {
		Game level = new Game(new Hero(new int[] {1,1}, false), map);
		level.addEnemy(new Guard(new int[] {1,3}, new char[0]) ); 
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] {2, 0}, 'S'));
		doors.add(new Door(new int[] {3, 0}, 'S'));
		level.addMechanism(new LeverDoor(new int[] {3,1},doors));
		assertTrue(Arrays.equals(new int[] {1,1}, level.getHero().getPos()));
		level.movement('s');
		level.movement('s');
		assertTrue(Arrays.equals(new int[] {3,1}, level.getHero().getPos()));
		assertEquals(level.getMapWCharacter()[2][0], 'S');
		assertEquals(level.getMapWCharacter()[3][0], 'S');
	}
	
	@Test
	public void testMoveHeroMovesToStairs() {
		Game level = new Game(new Hero(new int[] {1,1}, false), map);
		level.addEnemy(new Guard(new int[] {1,3}, new char[0]) ); 
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] {2, 0}, 'S'));
		doors.add(new Door(new int[] {3, 0}, 'S'));
		level.addMechanism(new LeverDoor(new int[] {3,1},doors));
		level.movement('s');
		level.movement('s');
		level.movement('a');
		assertTrue(Arrays.equals(new int[] {3,0}, level.getHero().getPos()));
		assertEquals(level.endLevel(), 0);
	}
	
	@Test
	public void testMoveHeroIsCapturedByGuard() {
		Game level = new Game(new Hero(new int[] {1,1}, false), map);
		level.addEnemy(new Guard(new int[] {1,3}, new char[0]) ); 
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] {2, 0}, 'S'));
		doors.add(new Door(new int[] {3, 0}, 'S'));
		level.addMechanism(new LeverDoor(new int[] {3,1},doors));
		assertTrue(Arrays.equals(new int[] {1,1}, level.getHero().getPos()));
		level.movement('d');
		assertTrue(Arrays.equals(new int[] {1,2}, level.getHero().getPos()));
		assertEquals(level.endLevel(), 2);
	}
	
	@Test
	public void testMoveHeroOpensDoorKey() {
		Game level = new Game(new Hero(new int[] {1,1}, false), map);
		level.addEnemy(new Guard(new int[] {1,3}, new char[0]) ); 
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] {2, 0}, 'S'));
		doors.add(new Door(new int[] {3, 0}, 'S'));
		level.addMechanism(new KeyDoor(new int[] {3,1},doors));
		assertTrue(Arrays.equals(new int[] {1,1}, level.getHero().getPos()));
		level.movement('s');
		level.movement('s');
		level.movement('a');
		assertTrue(Arrays.equals(new int[] {3,1}, level.getHero().getPos()));
		assertEquals(level.getMapWCharacter()[3][0], 'S');
	}
	
	@Test
	public void testMoveHeroMovesToStairsKey() {
		Game level = new Game(new Hero(new int[] {1,1}, false), map);
		level.addEnemy(new Guard(new int[] {1,3}, new char[0]) ); 
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] {2, 0}, 'S'));
		doors.add(new Door(new int[] {3, 0}, 'S'));
		level.addMechanism(new KeyDoor(new int[] {3,1},doors));
		assertTrue(Arrays.equals(new int[] {1,1}, level.getHero().getPos()));
		level.movement('s');
		level.movement('s');
		level.movement('a');
		assertTrue(Arrays.equals(new int[] {3,1}, level.getHero().getPos()));
		assertEquals(level.getMapWCharacter()[3][0], 'S');
		level.movement('a');
		assertTrue(Arrays.equals(new int[] {3,0}, level.getHero().getPos()));
		
	}
	
	@Test
	public void testHeroMovement() {
		Hero h = new Hero(new int[] {2,2}, false);
		h.move('w', map);
		assertTrue(Arrays.equals(new int[] {1,2}, h.getPos()));
		h.move('s', map);
		assertTrue(Arrays.equals(new int[] {2,2}, h.getPos()));
		h.move('a', map);
		assertTrue(Arrays.equals(new int[] {2,1}, h.getPos()));
		h.move('d', map);
		assertTrue(Arrays.equals(new int[] {2,2}, h.getPos()));
	}
	
}
