package dkeep.logic;

import java.util.Arrays;

public class Hero extends Character {
	private DoorMechanism key;
	private boolean armed;

	@Override
	public char getSymbol() {

		if(key == null)
			if(armed)
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

	public DoorMechanism getKey() {
		return key;
	}

	@Override
	public String toString() {
		return "Hero [key=" + key + ", armed=" + armed + ", pos=" + Arrays.toString(pos) + "]";
	}

	public boolean move(char command, char[][] map) {
		switch (command) {
		case 'w':
			if(map[this.getPos()[0] - 1][this.getPos()[1]] == ' ' || map[this.getPos()[0] - 1][this.getPos()[1]] == 'S')
			{
				this.getPos()[0]--;
				return true;
			}

			if(map[this.getPos()[0] - 1][this.getPos()[1]] == 'I' && key != null)
			{
				map[this.getPos()[0] - 1][this.getPos()[1]] = 'S';
				return true;
			}
			break;

		case 'a':
			if(map[this.getPos()[0]][this.getPos()[1] - 1] == ' '|| map[this.getPos()[0]][this.getPos()[1] - 1] == 'S')
			{
				this.getPos()[1]--;
				return true;
			}

			if(map[this.getPos()[0]][this.getPos()[1] - 1] == 'I' && key != null)
			{
				map[this.getPos()[0]][this.getPos()[1] - 1] = 'S';
				return true;
			}
			break;

		case 's':
			if(map[this.getPos()[0] + 1][this.getPos()[1]] == ' '|| map[this.getPos()[0] - 1][this.getPos()[1]] == 'S')
			{
				this.getPos()[0]++;
				return true;
			}

			if(map[this.getPos()[0] + 1][this.getPos()[1]] == 'I' && key != null)
			{
				map[this.getPos()[0] + 1][this.getPos()[1]] = 'S';
				return true;
			}
			break;
		case 'd':
			if(map[this.getPos()[0]][this.getPos()[1] + 1] == ' '|| map[this.getPos()[0]][this.getPos()[1] + 1] == 'S')
			{
				this.getPos()[1]++;
				return true;
			}

			if(map[this.getPos()[0]][this.getPos()[1] + 1] == 'I' && key != null)
			{
				map[this.getPos()[0]][this.getPos()[1] + 1] = 'S';
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}

	public void setKey(DoorMechanism key) {
		this.key = key;
	}

}
