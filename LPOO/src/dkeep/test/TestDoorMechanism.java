package dkeep.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dkeep.logic.Door;
import dkeep.logic.KeyDoor;

public class TestDoorMechanism {

	@Test
	public void testAddandRmDoor() {
		KeyDoor mechanism = new KeyDoor(new int[] { 1, 1 });
		assertEquals(mechanism.getDoors().size(), 0);
		mechanism.addDoor(new Door(new int[] { 0, 0 }, ' '));
		assertEquals(mechanism.getDoors().size(), 1);
		assertArrayEquals(mechanism.getDoors().get(0).getPos(), new int[] { 0, 0 });
		mechanism.rmDoor(new int[] { 0, 0 });
		assertEquals(mechanism.getDoors().size(), 0);

		mechanism.addDoor(new int[] { 0, 0 }, ' ');
		assertEquals(mechanism.getDoors().size(), 1);
		mechanism.rmDoor(new int[] { 0 });
		assertEquals(mechanism.getDoors().size(), 1);
	}
}
