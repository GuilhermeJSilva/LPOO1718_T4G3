package dkeep.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dkeep.logic.Drunken;
import dkeep.logic.Game;
import dkeep.logic.Guard;
import dkeep.logic.Hero;
import dkeep.logic.LeverDoor;
import dkeep.logic.Suspicious;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow {

	private static char[][] map1 = new char[][] { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
			{ 'X', ' ', ' ', ' ', 'I', ' ', 'X', ' ', ' ', 'X' }, { 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
			{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' }, { 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
			{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' }, { 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' }, { 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, };

	private JFrame frame;
	private JTextField textField;
	private Game game;

	private JPanel options;
	private JPanel leftSide;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JComboBox<String> comboBox;
	private JLabel gameInfo;
	private GameBoard gameArea;
	private JPanel rightSide;
	private JButton newGameBt;
	private JPanel controls;
	private JButton upBt;
	private JButton leftBt;
	private JButton rightBt;
	private JButton downBt;
	private JButton quitBt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(10, 10, 1024, 512);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] { 256 };
		gridBagLayout.columnWidths = new int[] { 256, 64 };
		gridBagLayout.columnWeights = new double[] { 10.0, 1.0 };
		gridBagLayout.rowWeights = new double[] { 1.0 };

		frame.getContentPane().setLayout(gridBagLayout);

		leftSide = new JPanel();
		GridBagConstraints gbc_leftSide = new GridBagConstraints();
		gbc_leftSide.insets = new Insets(0, 0, 0, 0);
		gbc_leftSide.fill = GridBagConstraints.BOTH;
		gbc_leftSide.gridx = 0;
		gbc_leftSide.gridy = 0;
		frame.getContentPane().add(leftSide, gbc_leftSide);
		GridBagLayout gbl_leftSide = new GridBagLayout();
		gbl_leftSide.columnWeights = new double[] { 1.0 };
		gbl_leftSide.rowWeights = new double[] { 0.5, 12.0, 0.5 };
		leftSide.setLayout(gbl_leftSide);

		options = new JPanel();
		GridBagConstraints gbc_options = new GridBagConstraints();
		gbc_options.weighty = 1.0;
		gbc_options.insets = new Insets(0, 0, 0, 0);
		gbc_options.fill = GridBagConstraints.BOTH;
		gbc_options.gridx = 0;
		gbc_options.gridy = 0;
		leftSide.add(options, gbc_options);
		GridBagLayout gbl_options = new GridBagLayout();
		gbl_options.columnWeights = new double[] { 1.0, 1.0 };
		gbl_options.rowWeights = new double[] { 1.0, 1.0 };
		options.setLayout(gbl_options);

		lblNewLabel_1 = new JLabel("Number of Ogres");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.weighty = 1.0;
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		options.add(lblNewLabel_1, gbc_lblNewLabel_1);

		textField = new JTextField();
		textField.setText("1");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.weighty = 1.0;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		options.add(textField, gbc_textField);
		textField.setColumns(10);

		lblNewLabel_2 = new JLabel("Guard Type");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.weighty = 1.0;
		gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		options.add(lblNewLabel_2, gbc_lblNewLabel_2);

		comboBox = new JComboBox<String>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.weighty = 1.0;
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		options.add(comboBox, gbc_comboBox);
		comboBox.addItem("Drunken");
		comboBox.addItem("Rookie");
		comboBox.addItem("Suspicious");

		gameInfo = new JLabel("Press New Game to start the game!");
		GridBagConstraints gbc_gameInfo = new GridBagConstraints();
		gbc_gameInfo.weighty = 1.0;
		gbc_gameInfo.weightx = 1.0;
		gbc_gameInfo.insets = new Insets(0, 0, 5, 0);
		gbc_gameInfo.gridx = 0;
		gbc_gameInfo.gridy = 16;
		leftSide.add(gameInfo, gbc_gameInfo);

		gameArea = new GameBoard();
		gameArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (KeyEvent.VK_UP == arg0.getKeyCode()) {
					if (game == null)
						return;
					game.movement('w');

					updateScreen();
				}
				if (KeyEvent.VK_DOWN == arg0.getKeyCode()) {
					if (game == null)
						return;
					game.movement('s');

					updateScreen();
				}
				if (KeyEvent.VK_LEFT == arg0.getKeyCode()) {
					if (game == null)
						return;
					game.movement('a');

					updateScreen();
				}
				if (KeyEvent.VK_RIGHT == arg0.getKeyCode()) {
					if (game == null)
						return;
					game.movement('d');

					updateScreen();
				}
			}
		});
		gameArea.setFont(new Font("Consolas", Font.PLAIN, 12));
		GridBagConstraints gbc_gameArea = new GridBagConstraints();
		gbc_gameArea.weighty = 40.0;
		gbc_gameArea.weightx = 1.0;
		gbc_gameArea.gridheight = 15;
		gbc_gameArea.insets = new Insets(0, 0, 5, 0);
		gbc_gameArea.fill = GridBagConstraints.BOTH;
		gbc_gameArea.gridx = 0;
		gbc_gameArea.gridy = 1;
		leftSide.add(gameArea, gbc_gameArea);

		rightSide = new JPanel();
		GridBagConstraints gbc_rightSide = new GridBagConstraints();
		gbc_rightSide.insets = new Insets(0, 0, 0, 5);
		gbc_rightSide.fill = GridBagConstraints.BOTH;
		gbc_rightSide.gridx = 1;
		gbc_rightSide.gridy = 0;
		frame.getContentPane().add(rightSide, gbc_rightSide);
		GridBagLayout gbl_rightSide = new GridBagLayout();
		gbl_rightSide.columnWeights = new double[] { 0.0 };
		gbl_rightSide.rowWeights = new double[] { 0.0, 1.0, 0.0 };
		rightSide.setLayout(gbl_rightSide);

		newGameBt = new JButton("New Game");
		newGameBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (newGameBt.isEnabled()) {

					try {
						int nOgres = Integer.parseInt(textField.getText());
						if (nOgres <= 0 || nOgres > 5) {
							gameInfo.setText("Invalid Number of ogres");
							return;
						}

					} catch (Exception e) {
						gameInfo.setText("Invalid Number of ogres");
						return;
					}

					gameInfo.setText("Playing");
					game = new Game(new Hero(new int[] { 1, 1 }, 'H', false), map1);

					switch ((String) comboBox.getSelectedItem()) {
					case "Drunken":
						game.addEnemy(new Drunken(new int[] { 1, 8 }, new char[] { 'a', 's', 's', 's', 's', 'a', 'a',
								'a', 'a', 'a', 'a', 's', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'w', 'w', 'w', 'w', 'w' },
								'G', 'g'));
						break;
					case "Rookie":
						game.addEnemy(new Guard(new int[] { 1, 8 }, new char[] { 'a', 's', 's', 's', 's', 'a', 'a', 'a',
								'a', 'a', 'a', 's', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'w', 'w', 'w', 'w', 'w' }, 'G'));
						break;
					case "Suspicious":
						game.addEnemy(new Suspicious(new int[] { 1, 8 }, new char[] { 'a', 's', 's', 's', 's', 'a', 'a',
								'a', 'a', 'a', 'a', 's', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'w', 'w', 'w', 'w', 'w' },
								'G'));
						break;
					default:
						break;
					}

					game.setLever(new LeverDoor(new int[] { 8, 7 }, new int[][] { { 5, 0 }, { 6, 0 } }, 'k', 'S'));

					updateScreen();
					newGameBt.setEnabled(false);
					textField.setEditable(false);
					comboBox.setEnabled(false);
					upBt.setEnabled(true);
					downBt.setEnabled(true);
					leftBt.setEnabled(true);
					rightBt.setEnabled(true);
					gameArea.requestFocusInWindow();

				}

			}
		});
		GridBagConstraints gbc_newGameBt = new GridBagConstraints();
		gbc_newGameBt.insets = new Insets(0, 0, 5, 0);
		gbc_newGameBt.gridx = 0;
		gbc_newGameBt.gridy = 0;
		rightSide.add(newGameBt, gbc_newGameBt);

		controls = new JPanel();
		GridBagConstraints gbc_controls = new GridBagConstraints();
		gbc_controls.fill = GridBagConstraints.VERTICAL;
		gbc_controls.insets = new Insets(0, 0, 5, 0);
		gbc_controls.gridx = 0;
		gbc_controls.gridy = 1;
		rightSide.add(controls, gbc_controls);
		GridBagLayout gbl_controls = new GridBagLayout();
		gbl_controls.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_controls.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		controls.setLayout(gbl_controls);

		upBt = new JButton("UP");
		upBt.setEnabled(false);
		upBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!upBt.isEnabled())
					return;
				if (game == null)
					return;
				game.movement('w');

				updateScreen();
				gameArea.requestFocusInWindow();
			}
		});
		GridBagConstraints gbc_upBt = new GridBagConstraints();
		gbc_upBt.fill = GridBagConstraints.BOTH;
		gbc_upBt.anchor = GridBagConstraints.NORTHWEST;
		gbc_upBt.insets = new Insets(0, 0, 5, 5);
		gbc_upBt.gridx = 1;
		gbc_upBt.gridy = 0;
		controls.add(upBt, gbc_upBt);

		leftBt = new JButton("LEFT");
		leftBt.setEnabled(false);
		leftBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!leftBt.isEnabled())
					return;
				if (game == null)
					return;
				game.movement('a');

				updateScreen();
				gameArea.requestFocusInWindow();
			}
		});
		GridBagConstraints gbc_leftBt = new GridBagConstraints();
		gbc_leftBt.anchor = GridBagConstraints.NORTHWEST;
		gbc_leftBt.insets = new Insets(0, 0, 5, 5);
		gbc_leftBt.gridx = 0;
		gbc_leftBt.gridy = 1;
		controls.add(leftBt, gbc_leftBt);

		rightBt = new JButton("RIGHT");
		rightBt.setEnabled(false);
		rightBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!rightBt.isEnabled())
					return;
				if (game == null)
					return;
				game.movement('d');

				updateScreen();
				gameArea.requestFocusInWindow();
			}
		});
		GridBagConstraints gbc_rightBt = new GridBagConstraints();
		gbc_rightBt.anchor = GridBagConstraints.NORTHWEST;
		gbc_rightBt.insets = new Insets(0, 0, 5, 5);
		gbc_rightBt.gridx = 2;
		gbc_rightBt.gridy = 1;
		controls.add(rightBt, gbc_rightBt);

		downBt = new JButton("DOWN");
		downBt.setEnabled(false);
		downBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!downBt.isEnabled())
					return;
				if (game == null)
					return;
				game.movement('s');

				updateScreen();
				gameArea.requestFocusInWindow();
			}
		});
		GridBagConstraints gbc_downBt = new GridBagConstraints();
		gbc_downBt.insets = new Insets(0, 0, 0, 5);
		gbc_downBt.anchor = GridBagConstraints.NORTHWEST;
		gbc_downBt.gridx = 1;
		gbc_downBt.gridy = 2;
		controls.add(downBt, gbc_downBt);

		quitBt = new JButton("Exit");
		quitBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		GridBagConstraints gbc_quitBt = new GridBagConstraints();
		gbc_quitBt.insets = new Insets(0, 0, 5, 0);
		gbc_quitBt.gridx = 0;
		gbc_quitBt.gridy = 2;
		rightSide.add(quitBt, gbc_quitBt);
	}

	public void updateScreen() {
		char[][] gameText = game.getMapWCharacter();
		gameArea.setMap(gameText);
		gameArea.repaint();

		if (game != null)
			switch (game.endLevel()) {
			case 0:
				if (!game.nextLevel(Integer.parseInt(textField.getText()))) {
					gameInfo.setText("YOU WIN");
					game = null;
					newGameBt.setEnabled(true);
					textField.setEditable(true);
					comboBox.setEnabled(true);
					upBt.setEnabled(false);
					downBt.setEnabled(false);
					leftBt.setEnabled(false);
					rightBt.setEnabled(false);
				} else {
					gameInfo.setText("NEW LEVEL");
					updateScreen();
				}
				break;
			case 2:
				gameInfo.setText("YOU LOST");
				game = null;
				newGameBt.setEnabled(true);
				textField.setEditable(true);
				comboBox.setEnabled(true);
				upBt.setEnabled(false);
				downBt.setEnabled(false);
				leftBt.setEnabled(false);
				rightBt.setEnabled(false);
				break;
			default:
				break;
			}
		gameArea.requestFocusInWindow();
	}
}
