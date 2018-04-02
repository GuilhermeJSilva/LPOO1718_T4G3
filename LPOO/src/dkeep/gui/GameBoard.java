package dkeep.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameBoard extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8172821655077352557L;
	private char[][] map;
	private String guardType = "";
	private int gameSize = 0;
	private int maxSize = 0;
	private int deltaX = 0;
	private int deltaY = 0;
	private int deltaD = 0;

	public void setGuardType(String guardType) {
		this.guardType = guardType;
	}

	Image door;
	Image drukenawake;
	Image drukensleep;
	Image floor;
	Image hero;
	Image key;
	Image maul;
	Image ogre;
	Image rookie;
	Image suspicious;
	Image swordedHero;
	Image wall;
	Image stairs;
	Image herowithkey;
	Image ogrekey;
	Image ogrestunned;

	public GameBoard() throws IOException {
		super();
		map = null;
		door = ImageIO.read(new File("images/door.png"));
		drukenawake = ImageIO.read(new File("images/drunkenawake.png"));
		drukensleep = ImageIO.read(new File("images/drunkensleep.png"));
		floor = ImageIO.read(new File("images/floor.png"));
		hero = ImageIO.read(new File("images/hero2.png"));
		key = ImageIO.read(new File("images/key.png"));
		maul = ImageIO.read(new File("images/maul.png"));
		ogre = ImageIO.read(new File("images/ogre2.png"));
		rookie = ImageIO.read(new File("images/rookie.png"));
		suspicious = ImageIO.read(new File("images/suspicious2.png"));
		swordedHero = ImageIO.read(new File("images/swordedhero.png"));
		wall = ImageIO.read(new File("images/wall.png"));
		stairs = ImageIO.read(new File("images/stairs1.png"));
		herowithkey = ImageIO.read(new File("images/herowithkey.png"));
		ogrekey = ImageIO.read(new File("images/ogre2key.png"));
		ogrestunned = ImageIO.read(new File("images/ogre2stunned.png"));

	}

	public void paintComponent1(Graphics g) {
		super.paintComponent(g);
		if (map == null)
			return;
		int x = 0, y = 0;

		gameSize = super.getHeight();
		maxSize = super.getWidth();

		if (gameSize > super.getWidth()) {
			gameSize = super.getWidth();
			maxSize = super.getHeight();
		}

		x = (maxSize - super.getHeight()) / 2;
		y = (maxSize - super.getWidth()) / 2;

		deltaY = gameSize / map.length;
		deltaX = gameSize / map.length;

		for (char[] cs : map) {
			x = (maxSize - super.getHeight()) / 2;
			for (char c : cs) {

				g.drawImage(getImage(c), x, y, deltaX, deltaY, null);
				x += deltaX;
			}
			// System.out.println("");
			y += deltaY;
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (map == null)
			return;
		int x = 0, y = 0;

		gameSize = super.getHeight();
		maxSize = super.getWidth();

		if (gameSize > super.getWidth()) {
			gameSize = super.getWidth();
			maxSize = super.getHeight();
		}

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
		switch (ch) {
		case 'X':
			return wall;

		case 'I':
			return door;

		case 'S':
			return stairs;

		case 'H':
			return hero;

		case 'K':
			return herowithkey;

		case 'A':
			return swordedHero;

		case 'G':
			switch (guardType) {
			case "Drunken":
				return drukenawake;

			case "Suspicious":
				return suspicious;

			default:
				return rookie;
			}

		case 'g':
			return drukensleep;

		case 'O':
			return ogre;

		case '*':
			return maul;

		case ' ':
			return floor;

		case 'k':
			return key;

		case '$':
			return ogrekey;

		case '8':
			return ogrestunned;

		default:
			return null;

		}
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
