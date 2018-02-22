package dkeep.logic;

public class GameState {
	
	public LevelState level;
	public int counter;
	public int nLevels;

	public GameState() {
		counter = 0;
		nLevels = 2;
		level = new LevelState(new Hero(new int[] {1,1}, 'H'));
	}
	
	public char[][] getGameLayer()
	{
		return this.level.getMapWCharacter();
	}
	
	public void updateLevel(char command)
	{
		level.movement(command);
	}
	
	public boolean endGame()
	{
		return counter >= nLevels;
	}
	
	public LevelState getLevel()
	{
		return level;
	}
	
	public boolean nextMap()
	{
		counter++;
		level.nextMap();
		return counter < nLevels;
	}
}