package dkeep.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.junit.Test;

import dkeep.logic.Guard;
import dkeep.logic.Hero;
import dkeep.logic.LevelState;
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
		LevelState level = new LevelState(new Hero(new int[] {1,1}, 'H', false), map);
		level.addEnemy(new Guard(new int[] {1,3}, new char[0], 'G') ); 
		level.setLever(new LeverDoor(new int[] {3,1}, new int[][] {{2,0}, {3,0}}, 'k','S'));
		assertTrue(Arrays.equals(new int[] {1,1}, level.getHero().getPos()));
		level.movement('s');
		assertTrue(Arrays.equals(new int[] {2,1}, level.getHero().getPos()));
	}
	
	@Test
	public void testMoveHeroIntoWall() {
		LevelState level = new LevelState(new Hero(new int[] {1,1}, 'H', false), map);
		level.addEnemy(new Guard(new int[] {1,3}, new char[0], 'G') ); 
		level.setLever(new LeverDoor(new int[] {3,1}, new int[][] {{2,0}, {3,0}}, 'k','S'));
		assertTrue(Arrays.equals(new int[] {1,1}, level.getHero().getPos()));
		level.movement('w');
		assertTrue(Arrays.equals(new int[] {1,1}, level.getHero().getPos()));
	}
	
	@Test
	public void testMoveHeroIntoDoor() {
		LevelState level = new LevelState(new Hero(new int[] {1,1}, 'H', false), map);
		level.addEnemy(new Guard(new int[] {1,3}, new char[0], 'G') ); 
		level.setLever(new LeverDoor(new int[] {3,1}, new int[][] {{2,0}, {3,0}}, 'k','S'));
		assertTrue(Arrays.equals(new int[] {1,1}, level.getHero().getPos()));
		level.movement('s');
		level.movement('a');
		assertTrue(Arrays.equals(new int[] {2,1}, level.getHero().getPos()));
	}
	
	@Test
	public void testMoveHeroOpensDoor() {
		LevelState level = new LevelState(new Hero(new int[] {1,1}, 'H', false), map);
		level.addEnemy(new Guard(new int[] {1,3}, new char[0], 'G') ); 
		level.setLever(new LeverDoor(new int[] {3,1}, new int[][] {{2,0}, {3,0}}, 'k','S'));
		assertTrue(Arrays.equals(new int[] {1,1}, level.getHero().getPos()));
		level.movement('s');
		level.movement('s');
		assertTrue(Arrays.equals(new int[] {3,1}, level.getHero().getPos()));
		assertEquals(level.getMapWCharacter()[2][0], 'S');
		assertEquals(level.getMapWCharacter()[3][0], 'S');
	}
	
	@Test
	public void testMoveHeroMovesToStairs() {
		LevelState level = new LevelState(new Hero(new int[] {1,1}, 'H', false), map);
		level.addEnemy(new Guard(new int[] {1,3}, new char[0], 'G') ); 
		level.setLever(new LeverDoor(new int[] {3,1}, new int[][] {{2,0}, {3,0}}, 'k','S'));
		level.movement('s');
		level.movement('s');
		level.movement('a');
		assertTrue(Arrays.equals(new int[] {3,0}, level.getHero().getPos()));
		assertEquals(level.endLevel(), 0);
	}
	
	@Test
	public void testMoveHeroIsCapturedByGuard() {
		LevelState level = new LevelState(new Hero(new int[] {1,1}, 'H', false), map);
		level.addEnemy(new Guard(new int[] {1,3}, new char[0], 'G') ); 
		level.setLever(new LeverDoor(new int[] {3,1}, new int[][] {{2,0}, {3,0}}, 'k','S'));
		assertTrue(Arrays.equals(new int[] {1,1}, level.getHero().getPos()));
		level.movement('d');
		assertTrue(Arrays.equals(new int[] {1,2}, level.getHero().getPos()));
		assertEquals(level.endLevel(), 2);
	}
	
}