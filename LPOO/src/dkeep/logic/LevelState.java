package dkeep.logic;

import java.util.ArrayList;

public class LevelState {

	private char[][] map;
	private static char[][] map1;
	private static char[][] map2;
	private ArrayList<Guard> guards;
	private ArrayList<Ogre> ogres;
	private Hero hero;
	private LeverDoor lever;
	private KeyDoor key;

	public LevelState(Hero hero) {
		map1 = new char[][] {
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

		map2 =  new char[][] {
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

		map = deepCopyCharMatrix(map1);
		guards = new ArrayList<Guard>();
		ogres = new ArrayList<Ogre>();
		this.addGuard(new Drunken(new int[] {1,8}, new char[] {'a', 's', 's' , 's' , 's', 'a', 'a', 'a', 'a', 'a', 'a','s','d','d','d','d','d','d','d','w','w','w','w','w'}, 'G','g') );
		this.hero = hero;
		lever = new LeverDoor(new int[] {8,7}, new int[][] {{5,0}, {6,0}}, 'k','S');

	}

	public void addGuard(Guard guard)
	{
		guards.add(guard);
	}

	public void addOgre(Ogre ogre)
	{
		ogres.add(ogre);
	}

	public char[][] getMap() {

		return map;
	}

	public void setMap(char[][] map) {
		this.map = map;
	}

	public ArrayList<Guard> getGuards() {
		return guards;
	}

	public void setGuards(ArrayList<Guard> guards) {
		this.guards = guards;
	}

	public ArrayList<Ogre> getOgres() {
		return ogres;
	}

	public void setOgres(ArrayList<Ogre> ogres) {
		this.ogres = ogres;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public boolean endLevel()
	{
		if(this.getMap()[this.getHero().getPos()[0]][this.getHero().getPos()[1]] == 'S') {
			System.out.println("Victory");
			return true;
		}

		for (int i = 0; i < guards.size(); i++) {
			if(guards.get(i).killedHero(hero))
			{
				System.out.println("Defeat");
				return true;
			}
		}

		for (int i = 0; i < ogres.size(); i++) {
			if(ogres.get(i).killedHero(hero))
			{
				System.out.println("Defeat");
				return true;
			}
		}



		return false;
	}

	public char[][] getMapWCharacter() {
		char mapWChar[][] =  deepCopyCharMatrix(map);
		if(lever != null)
			mapWChar[lever.getPos()[0]][lever.getPos()[1]] = lever.getSymbol();

		if(key != null && !key.isPicked())
			mapWChar[key.getPos()[0]][key.getPos()[1]] = key.getSymbol();

		mapWChar[this.getHero().getPos()[0]][this.getHero().getPos()[1]] = this.getHero().getSymbol();

		for (int i = 0; i < guards.size(); i++) {
			mapWChar[guards.get(i).getPos()[0]][guards.get(i).getPos()[1]] = guards.get(i).getSymbol();
		}

		for (int i = 0; i < ogres.size(); i++) {
			if(mapWChar[ogres.get(i).getPos()[0]][ogres.get(i).getPos()[1]] == 'k')
				mapWChar[ogres.get(i).getPos()[0]][ogres.get(i).getPos()[1]] = '$';
			else
				mapWChar[ogres.get(i).getPos()[0]][ogres.get(i).getPos()[1]] = ogres.get(i).getSymbol();
			if(mapWChar[ogres.get(i).getClubPos()[0]][ogres.get(i).getClubPos()[1]] == 'k')
				mapWChar[ogres.get(i).getClubPos()[0]][ogres.get(i).getClubPos()[1]] ='$';
			else
				mapWChar[ogres.get(i).getClubPos()[0]][ogres.get(i).getClubPos()[1]] = ogres.get(i).getClub();
		}


		return mapWChar;
	}

	public void movement(char command) 
	{
		if(!hero.move(command, map))
			return;
		for (int i = 0; i < guards.size(); i++) {
			guards.get(i).move(map);
		}

		for (int i = 0; i < ogres.size(); i++) {
			ogres.get(i).move(map);
			ogres.get(i).swing();
		}
		
		if(lever != null)
			lever.pullLever(hero, map);
		
		if(key != null)
			key.pickKey(hero, map);
	}

	public void nextMap()
	{
		map = deepCopyCharMatrix(map2);
		guards.clear();
		ogres.add(new Ogre(new int[]{1,4},'O', '*'));
		lever = null;
		key = new KeyDoor(new int[] {1,8}, new int[][] {{1,0}}, 'k', 'S');
		hero = new Hero(new int[] {8,1}, 'H');
	}

	public static char[][] deepCopyCharMatrix(char[][] input) {
		if (input == null)
			return null;
		char[][] result = new char[input.length][];
		for (char r = 0; r < input.length; r++) {
			result[r] = input[r].clone();
		}
		return result;
	}

}