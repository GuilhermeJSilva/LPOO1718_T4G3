package dkeep.logic;

public class LeverDoor extends DoorMechanism{
	
	public LeverDoor(int[] pos, int[][] is) {
		super();
		this.pos = pos;
		this.doors = is;
		this.symbol = 'k';
		this.openSymbol ='S';
	}

	public void pullLever(Hero hero, char map[][])
	{
		if(hero.getPos()[0] ==  pos[0] && hero.getPos()[1] == pos[1])
		{
			for (int i = 0; i < doors.length; i++) {
				map[doors[i][0]][doors[i][1]] = this.openSymbol;
			}
		}
	}
	
}
