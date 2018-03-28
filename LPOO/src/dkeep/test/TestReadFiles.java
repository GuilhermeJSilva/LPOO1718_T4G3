package dkeep.test;

import java.io.IOException;

import org.junit.Test;

import dkeep.cli.Cli;
import dkeep.logic.Game;

public class TestReadFiles {
	
	@Test
	public void testReadFile() throws IOException {
		Game game = new Game();
		game.readLevel("lvl1.txt", 1 , "Rookie");
		Cli.printChar(game.getMapWCharacter());
	}
}
