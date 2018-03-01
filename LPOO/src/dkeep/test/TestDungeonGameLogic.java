package dkeep.test;

import static org.junit.Assert.assertEquals;

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
		System.out.println(level.getMapWCharacter());
		assertEquals(new int[] {1,1}, level.getHero().getPos());
		level.movement('s');
		System.out.println(level.getHero().getPos());
		assertEquals(new int[] {2,1}, level.getHero().getPos());
	}
}
