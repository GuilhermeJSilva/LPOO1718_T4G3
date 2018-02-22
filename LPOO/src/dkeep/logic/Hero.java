package dkeep.logic;

public class Hero extends Character {
	private KeyDoor key;

	@Override
	public char getSymbol() {
		// TODO Auto-generated method stub
		if(key == null)
			return super.getSymbol();
		else 
			return 'K';
	}

	Hero(int[] pos, char symbol) {
		super(pos, symbol);
	}

	public KeyDoor getKey() {
		return key;
	}

	@Override
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

	public void setKey(KeyDoor key) {
		this.key = key;
	}

}
