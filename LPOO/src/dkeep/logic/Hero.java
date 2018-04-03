package dkeep.logic;

import java.util.ArrayList;

public class Hero extends Character {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2037315788131079148L;
	private ArrayList<KeyDoor> keys = new ArrayList<KeyDoor>();
	private boolean armed;

	@Override
	public char getSymbol() {

		if (keys.size() == 0)
			if (armed)
				return 'A';
			else
				return super.getSymbol();
		else
			return 'K';
	}

	public boolean isArmed() {
		return armed;
	}

	public void setArmed(boolean armed) {
		this.armed = armed;
	}

	public Hero(int[] pos, boolean armed) {
		super(pos, 'H');
		this.armed = armed;
	}

	public boolean move(char command, char[][] map) {
		int newPos[] =  null;
		switch (command) {
		case 'w':
			newPos =  new int[] {this.getPos()[0] - 1 ,this.getPos()[1]};
			if(moveInto(newPos, map))
				return true;

			return openDoor(map, newPos);

		case 'a':
			newPos = new int[] { this.getPos()[0], this.getPos()[1] - 1 };
			if( moveInto(newPos, map))
				return true;

			return openDoor(map, newPos);

		case 's':
			newPos = new int[] { this.getPos()[0] + 1, this.getPos()[1] };
			if( moveInto(newPos, map))
				return true;

			return openDoor(map, newPos);
		case 'd':
			newPos = new int[] { this.getPos()[0], this.getPos()[1] + 1 };
			if( moveInto(newPos, map))
				return true;

			return openDoor(map, newPos);

		default:
			break;
		}
		return false;
	}

	protected boolean openDoor(char map[][], int pos[]) {
		if (map[pos[0]][pos[1]] == 'I') {
			if (keys.size() != 0) {
				for (KeyDoor key : keys) {
					Door dToOpen = null;
					if ((dToOpen = key.isADoor(pos)) != null) {
						map[pos[0]][pos[1]] = dToOpen.getOpenS();
						return true;
					}
				}

			}
		}
		return false;
	}

	public void addKey(KeyDoor key) {
		this.keys.add(key);
	}

}
