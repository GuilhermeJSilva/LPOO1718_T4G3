package dkeep.logic;

import java.util.ArrayList;

public class LevelState {

	private char[][] map;
	private ArrayList<Guard> guards;
	private ArrayList<Ogre> ogres;
	private Hero hero;

	public LevelState(char map[][], Hero hero) {
		this.map = map;
		this.hero = hero;
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
}