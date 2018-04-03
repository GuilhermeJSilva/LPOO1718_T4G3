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

public class ReadMechanisms implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4413916751495525296L;
	protected ArrayList<DoorMechanism> dMechanism = new ArrayList<DoorMechanism>();

	public ReadMechanisms() {
		super();
	}

	public void addMechanism(DoorMechanism mecha) {
		if (dMechanism != null)
			dMechanism.add(mecha);
	}

	public void setdMechanism(ArrayList<DoorMechanism> dMechanism) {
		this.dMechanism = dMechanism;
	}
	
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

	public ArrayList<DoorMechanism> getdMechanism() {
		return dMechanism;
	}

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