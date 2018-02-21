package dkeep.cli;

import java.util.Scanner;

import game.Game;

public class Cli {
	public static void main(String[] args) {
		Game g = new Game();

		g.printMap();
		mainLoop(g);

		g.data.heroPos[0] = 8;
		g.data.heroPos[1] = 1;
		g.data.data.data.keyPicked = false;
		g.data.data.data.key[0] = 1;
		g.data.data.data.key[1] = 8;
		g.printMap2();

		while(!g.endGame2())
		{
			if(g.move2(g.getChar(),g.getHeroPos()))
			{
				g.moveRand(g.data.ogrePos);
				g.clubRand(g.data.ogreClub,g.data.ogrePos);
			}

			if(!g.data.data.data.keyPicked)
			{
				g.pickKey2();
			}
			g.printMap2();
		}
	}

	private static void mainLoop(Game g) {
		while(!g.endGame())
		{
			if(g.move(g.getChar(),g.getHeroPos()))
			{
				g.move(g.data.guardPath[g.data.counter], g.data.guardPos);
				g.data.counter++;
				g.data.counter %= g.data.guardPath.length;
			}
			if(!g.data.data.data.keyPicked)
			{
				g.pickKey();
			}
			g.printMap();
		}
	}
	
	public char getChar()
	{
		char c;
		Scanner sc =  new Scanner(System.in);
		c = sc.next().charAt(0);
		return c;

	}
	
}
