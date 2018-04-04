package dkeep.gameManipulator;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import dkeep.logic.Door;
import dkeep.logic.DoorMechanism;
import dkeep.logic.KeyDoor;
import dkeep.logic.LeverDoor;

/**
 * Reads all mechanisms and their doors from a file.
 *
 */
public abstract class ReadMechanisms implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4413916751495525296L;
	
	/**
	 * Stores all door mechanisms.
	 */
	protected ArrayList<DoorMechanism> dMechanism = new ArrayList<DoorMechanism>();

	/**
	 * Empty Constructor.
	 */
	public ReadMechanisms() {
		super();
	}

	/**
	 * Adds a mechanism to the array.
	 * 
	 * @param mecha
	 *            Mechanism to add.
	 */
	public void addMechanism(DoorMechanism mecha) {
		if (dMechanism != null)
			dMechanism.add(mecha);
	}

	/**
	 * Sets the mechanism array.
	 * 
	 * @param dMechanism
	 *            New mechanism array.
	 */
	public void setdMechanism(ArrayList<DoorMechanism> dMechanism) {
		this.dMechanism = dMechanism;
	}

	/**
	 * Returns the mechanism in some given coordinates.
	 * 
	 * @param coords
	 *            Coordinates to search.
	 * @return Mechanism in the given coordinates, if no mechanism is in those
	 *         coordinates, it returns null
	 */
	public DoorMechanism getMechanism(int coords[]) {
		if (coords.length < 2) {
			return null;
		}
		for (DoorMechanism dMecha : dMechanism) {
			if (Arrays.equals(coords, dMecha.getPos())) {
				return dMecha;
			}
		}
		return null;
	}

	/**
	 * Returns the mechanism array.
	 * 
	 * @return Mechanism array.
	 */
	public ArrayList<DoorMechanism> getdMechanism() {
		return dMechanism;
	}

	/**
	 * Reads a mechanism from a string.
	 * 
	 * @param line
	 *            Type of mechanism.
	 * @param charInfo
	 *            String to read from.
	 */
	protected void readKeyOrLever(String line, String charInfo) {
		Scanner keyScanner = new Scanner(charInfo);
		int keyPos[] = new int[2];
		if (keyScanner.hasNextInt())
			keyPos[0] = keyScanner.nextInt();
		if (keyScanner.hasNextInt())
			keyPos[1] = keyScanner.nextInt();

		ArrayList<Door> doors = readDoors(keyScanner);

		if (line.equals("Lever")) {
			this.dMechanism.add(new LeverDoor(keyPos, doors));
		} else {
			this.dMechanism.add(new KeyDoor(keyPos, doors));
		}
		keyScanner.close();
	}

	/**
	 * Reads the doors of mechanism.
	 * 
	 * @param keyScanner
	 *            Scanner to read from.
	 * @return Array of the doors read.
	 */
	protected ArrayList<Door> readDoors(Scanner keyScanner) {
		ArrayList<Door> doors = new ArrayList<Door>();
		while (keyScanner.hasNextInt()) {
			int door[] = new int[2];
			if (keyScanner.hasNextInt()) {
				door[0] = keyScanner.nextInt();
				if (keyScanner.hasNextInt()) {
					door[1] = keyScanner.nextInt();
					if (keyScanner.hasNextInt()) {
						int exit = keyScanner.nextInt();
						doors.add(new Door(door, exit != 0 ? 'S' : ' '));
					}
				}
			}
		}
		return doors;
	}

	/**
	 * Saves a Lever to a file.
	 * @param writer Place to write to.
	 */
	protected void saveLever(PrintWriter writer) {
		if (this.dMechanism != null) {
			for (DoorMechanism lever : dMechanism) {
				if (lever instanceof LeverDoor) {
					writer.println("Lever");
					writer.print(lever.getPos()[0] + " " + lever.getPos()[1]);
					for (Door door : lever.getDoors()) {
						writer.print(" " + door.getPos()[0] + " " + door.getPos()[1] + " "
								+ (door.getOpenS() == 'S' ? 1 : 0));
					}
					writer.println();
				}
			}

		}
	}

	/**
	 * Saves a Key to a file.
	 * @param writer Place to write to.
	 */
	protected void saveKey(PrintWriter writer) {
		if (this.dMechanism != null) {
			for (DoorMechanism key : dMechanism) {
				if (key instanceof KeyDoor) {
					writer.println("Key");
					writer.print(key.getPos()[0] + " " + key.getPos()[1]);
					for (Door door : key.getDoors()) {
						writer.print(" " + door.getPos()[0] + " " + door.getPos()[1] + " "
								+ (door.getOpenS() == 'S' ? 1 : 0));
					}
					writer.println();
				}
			}
		}
	}

}