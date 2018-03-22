package dkeep.cli;

import java.util.Scanner;

import dkeep.logic.Drunken;
import dkeep.logic.Game;
import dkeep.logic.Guard;
import dkeep.logic.Hero;
import dkeep.logic.LeverDoor;
import dkeep.logic.Suspicious;

public class Cli {
	private static char[][] map1 = new char[][] { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
			{ 'X', ' ', ' ', ' ', 'I', ' ', 'X', ' ', ' ', 'X' }, { 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
			{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' }, { 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
			{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' }, { 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' }, { 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, };

	public static void main(String[] args) {
		Game game = new Game(new Hero(new int[] { 1, 1 }, 'H', false), map1);
		switch ("Rookie") {
		case "Drunken":
			game.addEnemy(new Drunken(new int[] { 1, 8 }, new char[] { 'a', 's', 's', 's', 's', 'a', 'a', 'a', 'a', 'a',
					'a', 's', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'w', 'w', 'w', 'w', 'w' }));
			break;
		case "Rookie":
			game.addEnemy(new Guard(new int[] { 1, 8 }, new char[] { 'a', 's', 's', 's', 's', 'a', 'a', 'a', 'a', 'a',
					'a', 's', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'w', 'w', 'w', 'w', 'w' }));
			break;
		case "Suspicious":
			game.addEnemy(new Suspicious(new int[] { 1, 8 }, new char[] { 'a', 's', 's', 's', 's', 'a', 'a', 'a', 'a', 'a',
					'a', 's', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'w', 'w', 'w', 'w', 'w' }));
			break;
		default:
			break;
		}

		game.setLever(new LeverDoor(new int[] { 8, 7 }, new int[][] { { 5, 0 }, { 6, 0 } }, 'k', 'S'));
		mainLoop(game);
	}

	private static void mainLoop(Game g) {
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
			if (!g.nextLevel(1))
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
