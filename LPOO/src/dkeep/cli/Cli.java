package dkeep.cli;

import java.io.IOException;
import java.util.Scanner;

import dkeep.logic.Game;

public class Cli {

	public static void main(String[] args) throws IOException {
		Game game = new Game();
		//game.readLevel("lvl1.txt", 1, "Rookie");
		game.nextLevel(1, "Rookie");
		mainLoop(game);
	}

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

	public static char getChar() {
		char c;
		Scanner sc = new Scanner(System.in);
		c = sc.next().charAt(0);
		return c;

	}

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
