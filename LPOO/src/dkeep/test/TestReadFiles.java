package dkeep.test;

import java.io.IOException;

import org.junit.Test;

import dkeep.cli.Cli;
import dkeep.logic.Game;

public class TestReadFiles {
	
	@Test
	public void testReadFile() throws IOException {
		Game game = new Game();
		while(game.nextLevel(1, "Rookie"))
		Cli.printChar(game.getMapWCharacter());
		
		game = new Game();
		while(game.nextLevel(1, "Drunken"))
		Cli.printChar(game.getMapWCharacter());
		
		game = new Game();
		while(game.nextLevel(1, "Suspicious"))
		Cli.printChar(game.getMapWCharacter());
		
		game = new Game();
		while(game.nextLevel())
		Cli.printChar(game.getMapWCharacter());
	}
	
	public void testSaveFiles() {
		
	}
}
