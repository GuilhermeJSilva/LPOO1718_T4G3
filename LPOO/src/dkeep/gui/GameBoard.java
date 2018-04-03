package dkeep.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameBoard extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8172821655077352557L;
	private char[][] map;
	private String guardType = "";
	private int deltaX = 0;
	private int deltaY = 0;
	private int deltaD = 0;

	public void setGuardType(String guardType) {
		this.guardType = guardType;
	}

	HashMap<String, Image> images = new HashMap<String, Image>();

	public GameBoard() throws IOException {
		super();
		map = null;
		images.put("I", ImageIO.read(new File("images/door.png")));
		images.put("GD", ImageIO.read(new File("images/drunkenawake.png")));
		images.put("g", ImageIO.read(new File("images/drunkensleep.png")));
		images.put(" ", ImageIO.read(new File("images/floor.png")));
		images.put("H", ImageIO.read(new File("images/hero2.png")));
		images.put("k", ImageIO.read(new File("images/key.png")));
		images.put("*", ImageIO.read(new File("images/maul.png")));
		images.put("O", ImageIO.read(new File("images/ogre2.png")));
		images.put("GR", ImageIO.read(new File("images/rookie.png")));
		images.put("GS", ImageIO.read(new File("images/suspicious2.png")));
		images.put("A", ImageIO.read(new File("images/swordedhero.png")));
		images.put("X", ImageIO.read(new File("images/wall.png")));
		images.put("S", ImageIO.read(new File("images/stairs1.png")));
		images.put("K", ImageIO.read(new File("images/herowithkey.png")));
		images.put("$", ImageIO.read(new File("images/ogre2key.png")));
		images.put("8", ImageIO.read(new File("images/ogre2stunned.png")));

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (map == null)
			return;
		int x = 0, y = 0;

		deltaD = Integer.min(super.getWidth() / map[0].length, super.getHeight() / map.length);

		deltaY = (super.getHeight() - deltaD * map.length) / 2;
		deltaX = (super.getWidth() - deltaD * map[0].length) / 2;

		y = deltaY;
		for (char[] cs : map) {
			x = deltaX;
			for (char c : cs) {

				g.drawImage(getImage(c), x, y, deltaD, deltaD, null);
				x += deltaD;
			}
			y += deltaD;
		}
	}

	public char[][] getMap() {
		return map;
	}

	public void setMap(char[][] map) {
		this.map = map;
	}

	public Image getImage(char ch) {
		String key = "" + ch;
		if (ch == 'G')
			switch (guardType) {
			case "Drunken":
				key += 'D';
				break;

			case "Suspicious":
				key += 'S';
				break;

			default:
				key += 'R';
				break;
			}
		if (images.containsKey(key))
			return images.get(key);
		else
			return null;

	}

	public int[] getMapCoords(int x, int y) throws InvalidClick {
		if (map == null)
			throw new InvalidClick();
		int coords[] = new int[2];
		x -= deltaX;
		y -= deltaY;

		coords[1] = x / deltaD;
		coords[0] = y / deltaD;

		if (coords[0] >= map.length || coords[1] >= map[0].length)
			throw new InvalidClick();

		return coords;

	}
}
