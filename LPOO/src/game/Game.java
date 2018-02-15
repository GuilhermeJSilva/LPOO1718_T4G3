package game;

import java.util.Random;
import java.util.Scanner;

public class Game {

	private char map[][] = {
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

	private char map2[][] = {
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

	public int[] getHeroPos() {
		return heroPos;
	}

	public void setHeroPos(int[] heroPos) {
		this.heroPos = heroPos;
	}

	public int[] getGuardPos() {
		return guardPos;
	}

	public void setGuardPos(int[] guardPos) {
		this.guardPos = guardPos;
	}

	private int heroPos[] = {1,1};
	private int guardPos[] = {1,8};
	private int key[] = {8,7};
	private boolean keyPicked = false;

	private char guardPath[] = {'a', 's', 's' , 's' , 's', 'a', 'a', 'a', 'a', 'a', 'a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
	private int counter = 0;


	private int ogrePos[] = {1,4};
	private int ogreClub[] = {2,4};

	public static void main(String[] args) {
		Game g = new Game();

		g.printMap();
		while(!g.endGame())
		{
			if(g.move(g.getChar(),g.getHeroPos()))
			{
				g.move(g.guardPath[g.counter], g.guardPos);
				g.counter++;
				g.counter %= g.guardPath.length;
			}
			if(!g.keyPicked)
			{
				g.pickKey();
			}
			g.printMap();
		}

		g.heroPos[0] = 8;
		g.heroPos[1] = 1;
		g.keyPicked = false;
		g.key[0] = 1;
		g.key[1] = 8;
		g.printMap2();

		while(!g.endGame2())
		{
			if(g.move2(g.getChar(),g.getHeroPos()))
			{
				g.moveRand(g.ogrePos);
				g.clubRand(g.ogreClub,g.ogrePos);
			}

			if(!g.keyPicked)
			{
				g.pickKey2();
			}
			g.printMap2();
		}
	}

	private void clubRand(int[] ogreClub2, int[] ogrePos2) {
		
		Random generator = new Random();
		int command = generator.nextInt(4);
		switch (command) {
		case 0:
			ogreClub2[0] = ogrePos2[0] + 1;
			ogreClub2[1] = ogrePos2[1];
			break;

		case 1:
			ogreClub2[0] = ogrePos2[0];
			ogreClub2[1] = ogrePos2[1]+ 1;
			break;

		case 2:
			ogreClub2[0] = ogrePos2[0]-1;
			ogreClub2[1] = ogrePos2[1];
			break;

		case 3:
			ogreClub2[0] = ogrePos2[0];
			ogreClub2[1] = ogrePos2[1]-1;
			break;
		default:
			break;
		}
	}

	public void printMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if(i == guardPos[0] && j ==  guardPos[1])
				{
					System.out.print('G');
					System.out.print(' ');
				}
				else if(i == heroPos[0] && j ==  heroPos[1])
				{
					System.out.print('H');
					System.out.print(' ');
				}
				else if(i == key[0] && j ==  key[1])
				{
					System.out.print('k');
					System.out.print(' ');
				}
				else
				{
					System.out.print(map[i][j]);
					System.out.print(' ');
				}
			}
			System.out.println();
		}
	}

	public void printMap2() {
		for (int i = 0; i < map2.length; i++) {
			for (int j = 0; j < map2[i].length; j++) {
				if(i == ogrePos[0] && j ==  ogrePos[1] && i == key[0] && j ==  key[1])
				{
					System.out.print('$');
					System.out.print(' ');
				}
				else if(i == ogreClub[0] && j ==  ogreClub[1] && i == key[0] && j ==  key[1])
				{
					System.out.print('$');
					System.out.print(' ');
				}
				else if(i == ogrePos[0] && j ==  ogrePos[1])
				{
					System.out.print('O');
					System.out.print(' ');
				}
				else if(i == ogreClub[0] && j ==  ogreClub[1])
				{
					System.out.print('*');
					System.out.print(' ');
				}
				else if(i == heroPos[0] && j ==  heroPos[1] && !keyPicked)
				{
					System.out.print('H');
					System.out.print(' ');
				}
				else if(i == heroPos[0] && j ==  heroPos[1])
				{
					System.out.print('K');
					System.out.print(' ');
				}
				else if(i == key[0] && j ==  key[1] && !keyPicked)
				{
					System.out.print('k');
					System.out.print(' ');
				}
				else
				{
					System.out.print(map2[i][j]);
					System.out.print(' ');
				}
			}
			System.out.println();
		}
	}

	public char getChar()
	{
		char c;
		Scanner sc =  new Scanner(System.in);
		c = sc.next().charAt(0);
		return c;

	}

	public boolean move(char command, int pos[])
	{
		switch (command) {
		case 'w':
			if(map[pos[0] - 1][pos[1]] == ' ' || map[pos[0] - 1][pos[1]] == 'S')
				pos[0]--;
			return true;

		case 'a':
			if(map[pos[0]][pos[1] - 1] == ' '|| map[pos[0]][pos[1] - 1] == 'S')
				pos[1]--;
			return true;

		case 's':
			if(map[pos[0] + 1][pos[1]] == ' '|| map[pos[0] - 1][pos[1]] == 'S')
				pos[0]++;
			return true;

		case 'd':
			if(map[pos[0]][pos[1] + 1] == ' '|| map[pos[0]][pos[1] + 1] == 'S')
				pos[1]++;
			return true;
		default:
			break;
		}
		return false;
	}

	public boolean move2(char command, int pos[])
	{
		switch (command) {
		case 'w':
			if(map2[pos[0] - 1][pos[1]] == ' ' || map2[pos[0] - 1][pos[1]] == 'S')
				pos[0]--;
			else if(map2[pos[0] - 1][pos[1]] == 'I' && keyPicked)
				map2[pos[0] - 1][pos[1]] = 'S';
			return true;

		case 'a':
			if(map2[pos[0]][pos[1] - 1] == ' '|| map2[pos[0]][pos[1] - 1] == 'S')
				pos[1]--;
			else if(map2[pos[0]][pos[1] - 1] == 'I' && keyPicked)
				map2[pos[0]][pos[1] - 1] = 'S';
			return true;

		case 's':
			if(map2[pos[0] + 1][pos[1]] == ' '|| map2[pos[0] + 1][pos[1]] == 'S')
				pos[0]++;
			else if(map2[pos[0] + 1][pos[1]] == 'I' && keyPicked)
				map2[pos[0] + 1][pos[1]] = 'S';
			return true;

		case 'd': 
			if(map2[pos[0]][pos[1] + 1] == ' '|| map2[pos[0]][pos[1] + 1] == 'S')
				pos[1]++;
			else if(map2[pos[0]][pos[1] + 1] == 'I' && keyPicked)
				map2[pos[0]][pos[1] + 1] = 'S';
			return true;
		default:
			break;
		}
		return false;
	}

	public boolean moveRand(int pos[])
	{
		Random generator = new Random();
		int command = generator.nextInt(4); 
		switch (command) {
		case 0:
			if(map2[pos[0] - 1][pos[1]] == ' ' || map2[pos[0] - 1][pos[1]] == 'S')
				pos[0]--;
			return true;

		case 1:
			if(map2[pos[0]][pos[1] - 1] == ' '|| map2[pos[0]][pos[1] - 1] == 'S')
				pos[1]--;
			return true;

		case 2:
			if(map2[pos[0] + 1][pos[1]] == ' '|| map2[pos[0] - 1][pos[1]] == 'S')
				pos[0]++;
			return true;

		case 3:
			if(map2[pos[0]][pos[1] + 1] == ' '|| map2[pos[0]][pos[1] + 1] == 'S')
				pos[1]++;
			return true;
		default:
			break;
		}
		return false;
	}

	public void pickKey()
	{
		if(heroPos[0] ==  key[0] && heroPos[1] ==  key[1])
		{
			map[5][0] = 'S';
			map[6][0] = 'S';
			keyPicked = true;
		}
	}


	public void pickKey2()
	{
		if(heroPos[0] ==  key[0] && heroPos[1] ==  key[1])
		{
			keyPicked = true;
		}
	}
	public boolean endGame()
	{
		if(map[heroPos[0]][heroPos[1]] == 'S') {
			System.out.println("Victory");
			return true;
		}
		else if ((Math.abs(heroPos[0] - guardPos[0]) <= 1 && heroPos[1] == guardPos[1]) ||
				(Math.abs(heroPos[1] - guardPos[1]) <= 1 && heroPos[0] == guardPos[0]))
		{
			System.out.println("Defeat");
			return true;
		}
		return false;
	}

	public boolean endGame2()
	{
		if(map2[heroPos[0]][heroPos[1]] == 'S') {
			System.out.println("Victory");
			return true;
		}
		else if ((Math.abs(heroPos[0] - ogrePos[0]) <= 1 && heroPos[1] == ogrePos[1]) ||
				(Math.abs(heroPos[1] - ogrePos[1]) <= 1 && heroPos[0] == ogrePos[0]))
		{
			System.out.println("Defeat");
			return true;
		}
		
		else if ((Math.abs(heroPos[0] - ogreClub[0]) <= 1 && heroPos[1] == ogreClub[1]) ||
				(Math.abs(heroPos[1] - ogreClub[1]) <= 1 && heroPos[0] == ogreClub[0]))
		{
			System.out.println("Defeat");
			return true;
		}
		return false;
	}
}
