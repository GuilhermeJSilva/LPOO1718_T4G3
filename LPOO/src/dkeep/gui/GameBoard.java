package dkeep.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GameBoard extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char[][] map;

	public GameBoard() {
		map = null;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(map == null)
			return;
		int x = 0, y = 0;
		int deltaY = super.getHeight() / map.length;
		int deltaX = super.getWidth() / map[0].length;

		for (char[] cs : map) {
			x = 0;
			for (char c : cs) {
				g.setColor(this.getImage(c));
				g.fillRect(x, y, deltaX, deltaY);
				//System.out.print(c);
				x += deltaX;
			}
			//System.out.println("");
			y += deltaY;
		}
	}

	public char[][] getMap() {
		return map;
	}

	public void setMap(char[][] map) {
		this.map = map;
	}

	public Color getImage(char ch) {
		switch (ch) {
		case 'X':
			return Color.black;

		case 'I':
			return Color.black;

		case 'S':
			return Color.white;

		case 'H':
			return Color.yellow;

		case 'K':
			return Color.black;

		case 'A':
			return Color.magenta;

		case 'G':
			return Color.red;

		case 'g':
			return Color.black;

		case 'O':
			return Color.CYAN;

		case '*':
			return Color.BLUE;

		case ' ':
			return Color.WHITE;
		
		case 'k':
			return Color.DARK_GRAY;

		default:
			return Color.PINK;

		}
	}
}
