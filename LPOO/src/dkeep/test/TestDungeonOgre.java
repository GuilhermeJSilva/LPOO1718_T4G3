package dkeep.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import dkeep.cli.Cli;
import dkeep.logic.Door;
import dkeep.logic.Game;
import dkeep.logic.Hero;
import dkeep.logic.KeyDoor;
import dkeep.logic.Ogre;

public class TestDungeonOgre {

	char map[][] = { { 'X', 'X', 'X', 'X', 'X' }, { 'X', ' ', ' ', ' ', 'X' }, { 'I', ' ', ' ', ' ', 'X' },
			{ 'I', ' ', ' ', ' ', 'X' }, { 'X', 'X', 'X', 'X', 'X' } };

	@Test
	public void testMoveHeroCapturedByOgre() {
		Game level = new Game(new Hero(new int[] { 1, 1 }, false), map);
		level.addEnemy(new Ogre(new int[] { 1, 3 }));
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] { 2, 0 }, 'S'));
		doors.add(new Door(new int[] { 3, 0 }, 'S'));
		level.addMechanism(new KeyDoor(new int[] { 3, 1 }, doors));
		assertTrue(Arrays.equals(new int[] { 1, 1 }, level.getHero().getPos()));
		level.getHero().move('d', level.getMap());
		assertTrue(Arrays.equals(new int[] { 1, 2 }, level.getHero().getPos()));
		assertEquals(level.endLevel(), 2);
	}

	@Test
	public void testMoveHeroPicksUpKey() {
		Game level = new Game(new Hero(new int[] { 1, 1 }, false), map);
		level.addEnemy(new Ogre(new int[] { 1, 3 }));
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] { 2, 0 }, 'S'));
		doors.add(new Door(new int[] { 3, 0 }, 'S'));
		level.addMechanism(new KeyDoor(new int[] { 3, 1 }, doors));
		level.getHero().move('s', level.getMap());
		level.getHero().move('s', level.getMap());
		level.getMechanism(level.getHero().getPos()).activateMechanism(level.getHero(), level.getMap());
		assertEquals(level.getHero().getSymbol(), 'K');
	}

	@Test
	public void testMoveHeroRunsIntoDoor() {
		Game level = new Game(new Hero(new int[] { 1, 1 }, false), map);
		level.addEnemy(new Ogre(new int[] { 1, 3 }));
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] { 2, 0 }, 'S'));
		doors.add(new Door(new int[] { 3, 0 }, 'S'));
		level.addMechanism(new KeyDoor(new int[] { 3, 1 }, doors));
		level.getHero().move('s', level.getMap());
		if (level.getMechanism(level.getHero().getPos()) != null)
			level.getMechanism(level.getHero().getPos()).activateMechanism(level.getHero(), level.getMap());
		assertTrue(Arrays.equals(new int[] { 2, 1 }, level.getHero().getPos()));
		level.getHero().move('a', level.getMap());
		if (level.getMechanism(level.getHero().getPos()) != null)
			level.getMechanism(level.getHero().getPos()).activateMechanism(level.getHero(), level.getMap());
		assertTrue(Arrays.equals(new int[] { 2, 1 }, level.getHero().getPos()));
		assertEquals(level.getMapWCharacter()[2][0], 'I');
	}

	@Test
	public void testMoveHeroOpensDoor() {
		Game level = new Game(new Hero(new int[] { 1, 1 }, false), map);
		level.addEnemy(new Ogre(new int[] { 1, 3 }));
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] { 2, 0 }, 'S'));
		doors.add(new Door(new int[] { 3, 0 }, 'S'));
		level.addMechanism(new KeyDoor(new int[] { 3, 1 }, doors));
		level.getHero().move('s', level.getMap());
		if (level.getMechanism(level.getHero().getPos()) != null)
			level.getMechanism(level.getHero().getPos()).activateMechanism(level.getHero(), level.getMap());
		level.getHero().move('s', level.getMap());
		if (level.getMechanism(level.getHero().getPos()) != null)
			level.getMechanism(level.getHero().getPos()).activateMechanism(level.getHero(), level.getMap());
		assertTrue(Arrays.equals(new int[] { 3, 1 }, level.getHero().getPos()));
		level.getHero().move('a', level.getMap());
		if (level.getMechanism(level.getHero().getPos()) != null)
			level.getMechanism(level.getHero().getPos()).activateMechanism(level.getHero(), level.getMap());
		assertTrue(Arrays.equals(new int[] { 3, 1 }, level.getHero().getPos()));
		assertEquals(level.getMapWCharacter()[3][0], 'S');
	}

	@Test
	public void testMoveHeroWins() {
		Game level = new Game(new Hero(new int[] { 1, 1 }, false), map);
		level.addEnemy(new Ogre(new int[] { 1, 3 }));
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] { 2, 0 }, 'S'));
		doors.add(new Door(new int[] { 3, 0 }, 'S'));
		level.addMechanism(new KeyDoor(new int[] { 3, 1 }, doors));
		level.getHero().move('s', level.getMap());
		level.getHero().move('s', level.getMap());
		level.getMechanism(level.getHero().getPos()).activateMechanism(level.getHero(), level.getMap());
		level.getHero().move('a', level.getMap());
		level.getHero().move('a', level.getMap());
		assertEquals(level.endLevel(), 0);

	}

	@Test(timeout = 1000)
	public void testMoveOgre() {

		Game level = new Game(new Hero(new int[] { 1, 1 }, false), map);
		level.addEnemy(new Ogre(new int[] { 1, 3 }));
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(new int[] { 2, 0 }, 'S'));
		doors.add(new Door(new int[] { 3, 0 }, 'S'));
		level.addMechanism(new KeyDoor(new int[] { 3, 1 }, doors));
		boolean up = false, down = false, left = false, right = false;
		boolean upC = false, downC = false, leftC = false, rightC = false;
		char path[] = new char[] { 's', 's', 'd', 'd', 'w', 'w', 'a', 'a' };
		int counter = 0;
		while (!up || !down || !left || !right || !upC || !downC || !leftC || !rightC) {
			Cli.printChar(level.getMapWCharacter());
			Ogre ogre = (Ogre) level.getEnemies().get(0);
			int prevPos[] = ogre.getPos().clone();
			level.endLevel(); // System.out.println(Arrays.toString(prevPos));
			level.movement(path[counter]);

			ogre = (Ogre) level.getEnemies().get(0);
			int newPos[] = level.getEnemies().get(0).getPos();
			// System.out.println(Arrays.toString(newPos));
			int newCPos[] = ogre.getClubPos();

			if (Arrays.equals(newPos, new int[] { prevPos[0] + 1, prevPos[1] }))
				down = true;
			else if (Arrays.equals(newPos, new int[] { prevPos[0] - 1, prevPos[1] }))
				up = true;
			else if (Arrays.equals(newPos, new int[] { prevPos[0], prevPos[1] + 1 }))
				right = true;
			else if (Arrays.equals(newPos, new int[] { prevPos[0], prevPos[1] - 1 }))
				left = true;
			else if (Arrays.equals(newPos, prevPos))
				;
			else
				fail("Wrong Movement");

			if (Arrays.equals(newCPos, new int[] { newPos[0] + 1, newPos[1] }))
				downC = true;
			else if (Arrays.equals(newCPos, new int[] { newPos[0] - 1, newPos[1] }))
				upC = true;
			else if (Arrays.equals(newCPos, new int[] { newPos[0], newPos[1] + 1 }))
				rightC = true;
			else if (Arrays.equals(newCPos, new int[] { newPos[0], newPos[1] - 1 }))
				leftC = true;
			else
				fail("Wrong Swing");
			counter = (counter + 1) % path.length;
		}

	}

	@Test
	public void testReduceStunTurns() {
		Ogre o = new Ogre(new int[] { 1, 3 });
		o.stun(3);

		for (int i = 0; i < 3; i++) {
			o.move(map);
			assertEquals(o.getStunnedTurns(), 2 - i);
		}
	}

	@Test(timeout = 1000)
	public void testKillHero() {
		Hero h = new Hero(new int[] { 1, 1 }, false);
		Ogre o = new Ogre(new int[] { 2, 3 });

		assertFalse(o.killedHero(h));
		o.movement(map, 1);

		boolean left = false, bottom = false, right = false, top = false;
		
		while(!left | !right | !top | !bottom) {
			o.swing();
			int clubPos[] =  o.getClubPos();
			
			if(Arrays.equals(clubPos, new int[] {o.getPos()[0] - 1, o.getPos()[1]})) {
				top = true;
				assertTrue(o.killedHero(h));
			}
			
			if(Arrays.equals(clubPos, new int[] {o.getPos()[0], o.getPos()[1] + 1})) {
				right = true;
				assertTrue(!o.killedHero(h));
			}
			
			if(Arrays.equals(clubPos, new int[] {o.getPos()[0] + 1, o.getPos()[1]})) {
				bottom = true;
				assertTrue(!o.killedHero(h));
			}
			
			if(Arrays.equals(clubPos, new int[] {o.getPos()[0], o.getPos()[1] - 1})) {
				left = true;
				assertTrue(o.killedHero(h));
			}
		}
		 h = new Hero(new int[] { 2, 2 }, false);
		 o = new Ogre(new int[] { 2, 3 });
		 int path[] = new int[] {2,1,1,0,0,3,3,2};
		 o.stun(100);
		 boolean kill = true;
		 for (int i : path) {
			assertEquals(kill, o.killedHero(h));
			kill = !kill;
			o.movement(map, i);
		}
	}
}
