package dkeep.cli;

import java.util.Scanner;

import dkeep.logic.GameState;

public class Cli {
	public static void main(String[] args) {
		GameState g = new GameState();
		mainLoop(g);
	}

	private static void mainLoop(GameState g) {
		printChar(g.getGameLayer());
		while(!g.endGame())
		{
			while (!g.getLevel().endLevel()) {
				char command = getChar();
				g.updateLevel(command);
				printChar(g.getGameLayer());
			}
			if(g.nextMap())
				printChar(g.getGameLayer());
		}
	}

	public static char getChar()
	{
		char c;
		Scanner sc =  new Scanner(System.in);
		c = sc.next().charAt(0);
		return c;

	}

	public static void printChar(char map[][])
	{
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
	}

}
