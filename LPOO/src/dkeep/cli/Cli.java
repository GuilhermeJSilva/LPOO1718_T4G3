package dkeep.cli;

import java.io.IOException;
import java.util.Scanner;

import dkeep.logic.Game;

/**
 * Console line interface.
 *
 */
public class Cli {

	/**
	 * Main function for a console game
	 * 
	 * @param args
	 *            Comand line arguments
	 * @throws IOException
	 *             File reading errors.
	 */
	public static void main(String[] args) throws IOException {
		Game game = new Game();
		game.nextLevel(1, "Rookie");
		mainLoop(game);
	}

	/**
	 * Retrieves command from the user and updates the game
	 * 
	 * @param g
	 *            Game to interact over
	 * @throws IOException
	 */
	private static void mainLoop(Game g) throws IOException {
		while (true) {
			while (g.endLevel() == 1) {
				printChar(g.getMapWCharacter());
				char command = getChar();
				g.movement(command);
			}
			printChar(g.getMapWCharacter());
			if (g.endLevel() == 2) {
				System.out.println("Defeat");
				return;
			}
			if (!g.nextLevel(1, "Rookie"))
				return;
		}
	}

	/**
	 * Retrieves a character from System.in
	 * 
	 * @return Retrieved character
	 */
	public static char getChar() {
		char c;
		Scanner sc = new Scanner(System.in);
		c = sc.next().charAt(0);
		return c;

	}

	/**
	 * Prints a matrix of char to the string
	 * 
	 * @param map
	 *            Matrix
	 */
	public static void printChar(char map[][]) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
	}

}
