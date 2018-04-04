package dkeep.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Component to show the game.
 *
 */
public class GameBoard extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8172821655077352557L;
	/**
	 * Map to display.
	 */
	private char[][] map;

	/**
	 * Guard type to display.
	 */
	private String guardType = "";

	/**
	 * Horizontal offset of the game.
	 */
	private int dY = 0;

	/**
	 * Vertical offset of the game.
	 */
	private int dX = 0;

	/**
	 * Size of each tile.
	 */
	private int deltaD = 0;

	/**
	 * Image container.
	 */
	HashMap<String, Image> images = new HashMap<String, Image>();

	/**
	 * Change the guard type.
	 * 
	 * @param guardType
	 *            New guard type.
	 */
	public void setGuardType(String guardType) {
		this.guardType = guardType;
	}

	/**
	 * Initializes the image container.
	 * 
	 * @throws IOException File not found.
	 */
	public GameBoard() throws IOException {
		super();
		map = null;
		images.put("I", ImageIO.read(new File("images/door.png")));
		images.put(" ", ImageIO.read(new File("images/floor.png")));
		images.put("H", ImageIO.read(new File("images/hero2.png")));
		images.put("k", ImageIO.read(new File("images/key.png")));
		images.put("A", ImageIO.read(new File("images/swordedhero.png")));
		images.put("X", ImageIO.read(new File("images/wall.png")));
		images.put("S", ImageIO.read(new File("images/stairs1.png")));
		images.put("K", ImageIO.read(new File("images/herowithkey.png")));

		readOgreImages();
		readGuardImages();

	}

	/**
	 * Reads all ogre images.
	 * @throws IOException
	 */
	protected void readOgreImages() throws IOException {
		images.put("$", ImageIO.read(new File("images/ogre2key.png")));
		images.put("8", ImageIO.read(new File("images/ogre2stunned.png")));
		images.put("*", ImageIO.read(new File("images/maul.png")));
		images.put("O", ImageIO.read(new File("images/ogre2.png")));
	}

	/**
	 * Reads all guard images.
	 * @throws IOException
	 */
	protected void readGuardImages() throws IOException {
		images.put("GD", ImageIO.read(new File("images/drunkenawake.png")));
		images.put("g", ImageIO.read(new File("images/drunkensleep.png")));
		images.put("GR", ImageIO.read(new File("images/rookie.png")));
		images.put("GS", ImageIO.read(new File("images/suspicious2.png")));
	}

	/**
	 * Function called by the Main Window to print an object of Game Board.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (map == null)
			return;
		int x = 0, y = 0;

		deltaD = Integer.min(super.getWidth() / map[0].length, super.getHeight() / map.length);

		dX = (super.getHeight() - deltaD * map.length) / 2;
		dY = (super.getWidth() - deltaD * map[0].length) / 2;

		y = dX;
		for (char[] cs : map) {
			x = dY;
			for (char c : cs) {

				g.drawImage(getImage(c), x, y, deltaD, deltaD, null);
				x += deltaD;
			}
			y += deltaD;
		}
	}

	/**
	 * Returns the map.
	 * @return Map.
	 */
	public char[][] getMap() {
		return map;
	}

	/**
	 * Changes the map.
	 * @param map New map.
	 */
	public void setMap(char[][] map) {
		this.map = map;
	}

	/**
	 * Gets an image.
	 * @param ch Key of the image.
	 * @return Image if key exists, null otherwise.
	 */
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

	/**
	 * Get map coordinates of the click.
	 * @param x X coordinate of the click.
	 * @param y Y coordinate of the click.
	 * @return Coordinates on the map.
	 * @throws InvalidClick Click not on the game board.
	 */
	public int[] getMapCoords(int x, int y) throws InvalidClick {
		if (map == null)
			throw new InvalidClick();
		int coords[] = new int[2];
		x -= dY;
		y -= dX;

		coords[1] = x / deltaD;
		coords[0] = y / deltaD;

		if (coords[0] >= map.length || coords[1] >= map[0].length)
			throw new InvalidClick();

		return coords;

	}
}
