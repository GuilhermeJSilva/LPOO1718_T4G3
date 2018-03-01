package dkeep.logic;

public class GameState {
	
	public LevelState level;
	
	public int counter;
	public int nLevels;

	public GameState(LevelState level, int nLevels) {
		this.counter = 0;
		this.nLevels = nLevels;
		this.level = level;
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
	
	public boolean nextMap(LevelState level)
	{
		counter++;
		this.level = level;
		return counter < nLevels;
	}
}