package dkeep.editor;

import dkeep.logic.GameReader;

public class Editor extends GameReader {

	public Editor(int sizeX, int sizeY) {
		super();
		this.map = new char[sizeX][sizeY];
		this.resetMap();
		this.hero = null;
		this.guard = null;
		this.ogre = null;
		this.key = null;
		this.lever = null;
	}

	public void resetMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (i == map.length - 1 || i == 0 || j == map.length - 1 || j == 0) {
					map[i][j] = 'X';
				} else
					map[i][j] = ' ';
			}
		}
	}

	public void setTile(char tile, int x, int y) {
		if (x < map.length && y < map[x].length)
			return;

		map[x][y] = tile;
	}

	public void insertLvl(int index, String fileName) {
		if (index >= this.levels.size())
			this.levels.add(fileName);
		else
			this.levels.add(index, fileName);
	}
}
