package dkeep.cli;

import java.util.Scanner;

import dkeep.logic.Drunken;
import dkeep.logic.GameState;
import dkeep.logic.Hero;
import dkeep.logic.KeyDoor;
import dkeep.logic.LevelState;
import dkeep.logic.LeverDoor;
import dkeep.logic.Ogre;

public class Cli {
	private static char[][] map1 = new char[][] {
		{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
		{'X', ' ', ' ', ' ', 'I', ' ', 'X', ' ', ' ', 'X'},
		{'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X'},
		{'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X'},
		{'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X'},
		{'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X'},
		{'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X'},
		{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
	};
	
	private static char[][] map2 = new char[][] {
		{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
		{'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
	};
	
	public static void main(String[] args) {
		LevelState level = new LevelState(new Hero(new int[] {1,1}, 'H', false), map1);
		level.addEnemy(new Drunken(new int[] {1,8}, new char[] {'a', 's', 's' , 's' , 's', 'a', 'a', 'a', 'a', 'a', 'a','s','d','d','d','d','d','d','d','w','w','w','w','w'}, 'G','g') ); 
	   level.setLever(new LeverDoor(new int[] {8,7}, new int[][] {{5,0}, {6,0}}, 'k','S'));
	   GameState g = new GameState(level, 2);
		mainLoop(g);
	}

	private static void mainLoop(GameState g) {
		//printChar(g.getGameLayer());
		while(!g.endGame())
		{
			while (g.getLevel().endLevel() == 1) {
				printChar(g.getGameLayer());
				char command = getChar();
				g.updateLevel(command);	
			}
			printChar(g.getGameLayer());
			if(g.getLevel().endLevel() == 2)
				return;
			
			LevelState level = new LevelState(new Hero(new int[] {8,1}, 'H', true), map2);
			level.getEnemy().clear();
			level.getEnemy().add(new Ogre(new int[]{1,4},'O', '*', '8'));
			level.getEnemy().add(new Ogre(new int[]{5,4},'O', '*', '8'));
			level.setLever(null);
			level.setKey(new KeyDoor(new int[] {1,8}, new int[][] {{1,0}}, 'k', 'S'));
			if(g.nextMap(level))
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
