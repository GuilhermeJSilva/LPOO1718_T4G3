package dkeep.gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dkeep.editor.Editor;
import dkeep.logic.DoorMechanism;
import dkeep.logic.Game;

public class MainWindow {

	private JFrame frame;
	private JTextField textField;
	private Game game;
	private Editor editor = null;

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
	private JTabbedPane tabbedPane;
	private JPanel settings_panel;
	private JPanel editing;
	private JLabel tileChoser;
	private JComboBox<String> tileChoser_CB;
	private JLabel guardPath;
	private JTextField textField_Path;
	private JButton btnStartEdit;
	private JButton btnNxtLevel;
	private JButton btnNewLevel;
	private JTextField txtLevelN;
	private JButton btnAddLevel;
	private JTextField txtFileName;
	private JLabel lblInfo;
	private JLabel lblKeyToOpen;
	private JComboBox<DoorMechanism> keysCB;
	private JButton btnReplaceLevel;
	private JPanel panel;
	private JButton btnLoadGame;
	private JButton btnSaveGame;

	final JFileChooser fc = new JFileChooser();

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
	 * 
	 * @throws IOException
	 */
	public MainWindow() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(10, 10, 1 * (1024 - (256 + 32)), 1 * (512));
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		GridBagLayout gridBagLayout = new GridBagLayout();
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
		gbl_options.columnWeights = new double[] {};
		gbl_options.rowWeights = new double[] {};
		options.setLayout(gbl_options);

		gameInfo = new JLabel("Press New Game to start the game!");
		GridBagConstraints gbc_gameInfo = new GridBagConstraints();
		gbc_gameInfo.weighty = 1.0;
		gbc_gameInfo.weightx = 1.0;
		gbc_gameInfo.insets = new Insets(0, 0, 5, 0);
		gbc_gameInfo.gridx = 0;
		gbc_gameInfo.gridy = 16;
		leftSide.add(gameInfo, gbc_gameInfo);

		gameArea = new GameBoard();
		gameArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(game != null)
					return;
				try {
					int coords[] = gameArea.getMapCoords(e.getX(), e.getY());
					String tile = (String) tileChoser_CB.getSelectedItem();
					String path = (String) textField_Path.getText();
					System.out.println(Arrays.toString(coords));
					editor.editCoords(coords, tile, path);
					if (editor != null) {
						switch (tile) {
						case "Door":
							DoorMechanism dm = (DoorMechanism) keysCB.getSelectedItem();
							if (dm != null) {
								dm.addDoor(coords.clone());
							}
							break;
						case "Key":
							keysCB.addItem(editor.getKey());
							break;
						case "Lever":
							keysCB.addItem(editor.getLever());
							break;
						default:
							break;
						}

						updateKeysCB();
					}
					gameArea.setMap(editor.getMapWCharacter());
					gameArea.repaint();
				} catch (InvalidClick ex) {
					System.err.println("Invalid click");
				}
			}
		});
		gameArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (KeyEvent.VK_W == arg0.getKeyCode()) {
					if (game == null)
						return;
					game.movement('w');

				} else if (KeyEvent.VK_S == arg0.getKeyCode()) {
					if (game == null)
						return;
					game.movement('s');

				} else if (KeyEvent.VK_A == arg0.getKeyCode()) {
					if (game == null)
						return;
					game.movement('a');

				} else if (KeyEvent.VK_D == arg0.getKeyCode()) {
					if (game == null)
						return;
					game.movement('d');

				} else {
					return;
				}

				try {
					updateScreen();
				} catch (NumberFormatException e1) {

					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				gameArea.requestFocusInWindow();
			}
		});
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
		gbl_rightSide.columnWeights = new double[] { 1.0 };
		gbl_rightSide.rowWeights = new double[] { 0.0, 1.0, 0.0 };
		rightSide.setLayout(gbl_rightSide);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		rightSide.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[] { 1.0, 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0 };
		panel.setLayout(gbl_panel);

		btnLoadGame = new JButton("Load Game");
		btnLoadGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = fc.showOpenDialog(btnSaveGame);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the file.
					System.out.println("Opening: " + file.getAbsolutePath() + ".\n");
					ObjectInputStream oos = null;
					FileInputStream fout = null;
					try {
						fout = new FileInputStream(file);
						oos = new ObjectInputStream(fout);
						game = (Game) oos.readObject();
						oos.close();
						gameArea.setMap(game.getMapWCharacter());
						gameArea.repaint();
						gameArea.requestFocusInWindow();
						enableChanges(false);
					} catch (IOException ex) {
						gameInfo.setText("Load failed (IO)");
					} catch (ClassNotFoundException e1) {
						gameInfo.setText("Load failed (Class)");
					} finally {
					}

				} else {
					System.out.println("Open command cancelled by user.\n");
				}
				gameArea.requestFocusInWindow();

			}
		});
		GridBagConstraints gbc_btnLoadGame = new GridBagConstraints();
		gbc_btnLoadGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoadGame.gridx = 0;
		gbc_btnLoadGame.gridy = 0;
		panel.add(btnLoadGame, gbc_btnLoadGame);

		btnSaveGame = new JButton("Save Game");
		btnSaveGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showSaveDialog(btnSaveGame);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the file.
					//System.out.println("Opening: " + file.getAbsolutePath() + "\\t1.ser" + ".\n");
					ObjectOutputStream oos = null;
					FileOutputStream fout = null;
					try {
						fout = new FileOutputStream(file.getAbsolutePath(), true);
						oos = new ObjectOutputStream(fout);
						oos.writeObject(game);
						oos.close();
					} catch (IOException ex) {
						gameInfo.setText("Save failed");
					} finally {
						gameInfo.setText("Game state saved");
					}

				} else {
					System.out.println("Open command cancelled by user.\n");
				}
				gameArea.requestFocusInWindow();

			}
		});
		GridBagConstraints gbc_btnSaveGame = new GridBagConstraints();
		gbc_btnSaveGame.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveGame.gridx = 1;
		gbc_btnSaveGame.gridy = 0;
		panel.add(btnSaveGame, gbc_btnSaveGame);

		newGameBt = new JButton("New Game");
		GridBagConstraints gbc_newGameBt = new GridBagConstraints();
		gbc_newGameBt.gridwidth = 2;
		gbc_newGameBt.gridx = 0;
		gbc_newGameBt.gridy = 1;
		panel.add(newGameBt, gbc_newGameBt);
		newGameBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (newGameBt.isEnabled()) {
					int nOgres = 1;
					try {
						nOgres = Integer.parseInt(textField.getText());
						if (nOgres <= 0 || nOgres > 5) {
							gameInfo.setText("Invalid Number of ogres");
							return;
						}

					} catch (NumberFormatException e) {
						gameInfo.setText("Invalid Number of ogres");
						return;
					}

					gameInfo.setText("Playing");

					try {
						game = new Game();
						editor = null;
					} catch (IOException e2) {
						e2.printStackTrace();
						System.exit(-1);
					}
					gameArea.setGuardType((String) comboBox.getSelectedItem());
					try {
						game.nextLevel(nOgres, (String) comboBox.getSelectedItem());
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					try {
						updateScreen();
					} catch (NumberFormatException e1) {

						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					gameArea.requestFocusInWindow();
					enableChanges(false);

				}
				gameArea.requestFocusInWindow();

			}
		});

		quitBt = new JButton("Exit");
		quitBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				gameArea.requestFocusInWindow();
			}
		});
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		rightSide.add(tabbedPane, gbc_tabbedPane);

		controls = new JPanel();
		tabbedPane.addTab("Controls", null, controls, null);
		GridBagLayout gbl_controls = new GridBagLayout();
		gbl_controls.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_controls.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		controls.setLayout(gbl_controls);

		upBt = new JButton("UP");
		upBt.setEnabled(false);
		upBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!upBt.isEnabled()) {
					gameArea.requestFocusInWindow();
					return;
				}
				if (game == null)
					return;
				game.movement('w');

				try {
					updateScreen();
				} catch (NumberFormatException e1) {

					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
				if (!leftBt.isEnabled()) {
					gameArea.requestFocusInWindow();
					return;
				}
				if (game == null)
					return;
				game.movement('a');

				try {
					updateScreen();
				} catch (NumberFormatException e1) {

					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
				if (!rightBt.isEnabled()) {
					gameArea.requestFocusInWindow();
					return;
				}
				if (game == null)
					return;
				game.movement('d');

				try {
					updateScreen();
				} catch (NumberFormatException e1) {

					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
				if (!downBt.isEnabled()) {
					gameArea.requestFocusInWindow();
					return;
				}
				if (game == null)
					return;
				game.movement('s');

				try {
					updateScreen();
				} catch (NumberFormatException e1) {

					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				gameArea.requestFocusInWindow();
			}
		});
		GridBagConstraints gbc_downBt = new GridBagConstraints();
		gbc_downBt.insets = new Insets(0, 0, 0, 5);
		gbc_downBt.anchor = GridBagConstraints.NORTHWEST;
		gbc_downBt.gridx = 1;
		gbc_downBt.gridy = 2;
		controls.add(downBt, gbc_downBt);

		settings_panel = new JPanel();
		tabbedPane.addTab("Settings", null, settings_panel, null);
		GridBagLayout gbl_settings_panel = new GridBagLayout();
		gbl_settings_panel.columnWeights = new double[] { 0.0, 0.0 };
		gbl_settings_panel.rowWeights = new double[] { 0.0, 0.0 };
		settings_panel.setLayout(gbl_settings_panel);

		lblNewLabel_1 = new JLabel("Number of Ogres");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		settings_panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);

		textField = new JTextField();
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!textField.isEnabled() || !textField.isEditable())
					gameArea.requestFocusInWindow();
				else
					super.mouseClicked(e);
			}
		});
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		settings_panel.add(textField, gbc_textField);
		textField.setText("1");
		textField.setColumns(10);

		lblNewLabel_2 = new JLabel("Guard Type");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		settings_panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);

		comboBox = new JComboBox<String>();
		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!textField.isEnabled())
					gameArea.requestFocusInWindow();
				else
					super.mouseClicked(e);
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		settings_panel.add(comboBox, gbc_comboBox);

		editing = new JPanel();
		tabbedPane.addTab("Editing", null, editing, null);
		GridBagLayout gbl_editing = new GridBagLayout();
		gbl_editing.columnWeights = new double[] { 1.0, 1.0 };
		gbl_editing.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		editing.setLayout(gbl_editing);

		btnStartEdit = new JButton("Start Edit");
		btnStartEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnStartEdit.isEnabled()) {
					editor = new Editor(10, 10);

					try {
						editor.readLevelNames();
					} catch (IOException e) {
						System.err.println("List of level not found");
						System.exit(-1);
					}
					updateKeysCB();
					editor.nextLevel();
					updateEditFields();
					setLevelParam();
					gameArea.repaint();
				}
				gameArea.requestFocusInWindow();
			}

		});

		GridBagConstraints gbc_btnStartEdit = new GridBagConstraints();
		gbc_btnStartEdit.insets = new Insets(0, 0, 5, 5);
		gbc_btnStartEdit.gridx = 0;
		gbc_btnStartEdit.gridy = 0;
		editing.add(btnStartEdit, gbc_btnStartEdit);

		btnNxtLevel = new JButton("Next Level");
		GridBagConstraints gbc_btnNxtLevel = new GridBagConstraints();
		btnNxtLevel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnNxtLevel.isEnabled()) {
					if (editor != null) {
						editor.nextLevel();
						updateEditFields();
						updateKeysCB();
						setLevelParam();
						gameArea.repaint();
					}
				}
				gameArea.requestFocusInWindow();
			}
		});
		gbc_btnNxtLevel.insets = new Insets(0, 0, 5, 0);
		gbc_btnNxtLevel.gridx = 1;
		gbc_btnNxtLevel.gridy = 0;
		editing.add(btnNxtLevel, gbc_btnNxtLevel);

		btnNewLevel = new JButton("New Level");
		btnNewLevel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnNewLevel.isEnabled()) {
					editor = new Editor(10, 10);
					updateEditFields();
					updateKeysCB();
					txtLevelN.setText("Lvl order");
					txtFileName.setText("Filename");
					gameArea.repaint();
				}
				gameArea.requestFocusInWindow();
			}
		});
		GridBagConstraints gbc_btnNewLevel = new GridBagConstraints();
		gbc_btnNewLevel.gridwidth = 2;
		gbc_btnNewLevel.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewLevel.gridx = 0;
		gbc_btnNewLevel.gridy = 1;
		editing.add(btnNewLevel, gbc_btnNewLevel);

		lblKeyToOpen = new JLabel("Key To Open");
		lblKeyToOpen.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblKeyToOpen = new GridBagConstraints();
		gbc_lblKeyToOpen.insets = new Insets(0, 0, 5, 5);
		gbc_lblKeyToOpen.gridx = 0;
		gbc_lblKeyToOpen.gridy = 3;
		editing.add(lblKeyToOpen, gbc_lblKeyToOpen);

		keysCB = new JComboBox<DoorMechanism>();
		GridBagConstraints gbc_keysCB = new GridBagConstraints();
		gbc_keysCB.insets = new Insets(0, 0, 5, 0);
		gbc_keysCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_keysCB.gridx = 1;
		gbc_keysCB.gridy = 3;
		editing.add(keysCB, gbc_keysCB);

		txtLevelN = new JTextField();
		txtLevelN.setText("Level n\u00BA");
		GridBagConstraints gbc_txtLevelN = new GridBagConstraints();
		gbc_txtLevelN.insets = new Insets(0, 0, 5, 0);
		gbc_txtLevelN.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLevelN.gridx = 1;
		gbc_txtLevelN.gridy = 5;
		editing.add(txtLevelN, gbc_txtLevelN);
		txtLevelN.setColumns(10);

		btnReplaceLevel = new JButton("Replace Level");
		btnReplaceLevel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnAddLevel.isEnabled()) {
					int index = 0;
					if (editor.getHero() == null) {
						lblInfo.setText("No hero");
						return;
					}

					if (editor.getKey() == null && editor.getLever() == null) {
						lblInfo.setText("No way out");
						return;
					}

					try {
						index = Integer.parseInt(txtLevelN.getText());
					} catch (NumberFormatException e) {
						lblInfo.setText("Invalid level number");
						return;
					}

					if (txtFileName.getText().equals("Filename")) {
						lblInfo.setText("Invalid filename");
						return;
					}

					if (editor == null) {
						lblInfo.setText("Invalid editor");
						return;
					}
					String fileName;
					try {
						fileName = txtFileName.getText();
						editor.saveLevel(txtFileName.getText());
						editor.replaceLvl(index, fileName);
						editor.saveLevelFiles();
					} catch (FileNotFoundException e) {
						lblInfo.setText("Invalid filename");
						return;
					} catch (UnsupportedEncodingException e) {
						lblInfo.setText("Invalid ecoding");
						return;
					}

					lblInfo.setText("File Saved");
				}
			}
		});
		GridBagConstraints gbc_btnReplaceLevel = new GridBagConstraints();
		gbc_btnReplaceLevel.insets = new Insets(0, 0, 5, 5);
		gbc_btnReplaceLevel.gridx = 0;
		gbc_btnReplaceLevel.gridy = 6;
		editing.add(btnReplaceLevel, gbc_btnReplaceLevel);

		txtFileName = new JTextField();
		txtFileName.setText("File Name");
		GridBagConstraints gbc_txtFileName = new GridBagConstraints();
		gbc_txtFileName.insets = new Insets(0, 0, 5, 0);
		gbc_txtFileName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFileName.gridx = 1;
		gbc_txtFileName.gridy = 6;
		editing.add(txtFileName, gbc_txtFileName);
		txtFileName.setColumns(10);

		tileChoser = new JLabel("Tile");
		GridBagConstraints gbc_tileChoser = new GridBagConstraints();
		gbc_tileChoser.insets = new Insets(0, 0, 5, 5);
		gbc_tileChoser.gridx = 0;
		gbc_tileChoser.gridy = 2;
		editing.add(tileChoser, gbc_tileChoser);

		tileChoser_CB = new JComboBox<String>();
		GridBagConstraints gbc_tileChoser_CB = new GridBagConstraints();
		gbc_tileChoser_CB.insets = new Insets(0, 0, 5, 0);
		gbc_tileChoser_CB.fill = GridBagConstraints.HORIZONTAL;
		gbc_tileChoser_CB.gridx = 1;
		gbc_tileChoser_CB.gridy = 2;
		editing.add(tileChoser_CB, gbc_tileChoser_CB);
		tileChoser_CB.addItem("Hero Armed");
		tileChoser_CB.addItem("Hero");
		tileChoser_CB.addItem("Ogre Spawn");
		tileChoser_CB.addItem("Guard");
		tileChoser_CB.addItem("Key");
		tileChoser_CB.addItem("Lever");
		tileChoser_CB.addItem("Door");
		tileChoser_CB.addItem("Wall");
		tileChoser_CB.addItem("Floor");

		guardPath = new JLabel("Path");
		guardPath.setToolTipText("");
		GridBagConstraints gbc_guardPath = new GridBagConstraints();
		gbc_guardPath.insets = new Insets(0, 0, 5, 5);
		gbc_guardPath.gridx = 0;
		gbc_guardPath.gridy = 4;
		editing.add(guardPath, gbc_guardPath);

		textField_Path = new JTextField();
		textField_Path.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updatePath();
			}

			public void removeUpdate(DocumentEvent e) {
				updatePath();
			}

			public void insertUpdate(DocumentEvent e) {
				updatePath();
			}

			public void updatePath() {
				String path = textField_Path.getText();
				if (editor.getGuard() != null) {
					if (!editor.getGuard().setPath(path.toCharArray())) {
						lblInfo.setText("Wrong guard path");
					}
				}
			}
		});

		GridBagConstraints gbc_textField_Path = new GridBagConstraints();
		gbc_textField_Path.insets = new Insets(0, 0, 5, 0);
		gbc_textField_Path.fill = GridBagConstraints.BOTH;
		gbc_textField_Path.gridx = 1;
		gbc_textField_Path.gridy = 4;
		editing.add(textField_Path, gbc_textField_Path);
		textField_Path.setColumns(10);

		btnAddLevel = new JButton("Add Level");
		btnAddLevel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnAddLevel.isEnabled()) {
					int index = 0;
					if (editor.getHero() == null) {
						lblInfo.setText("No hero");
						return;
					}

					if (editor.getKey() == null && editor.getLever() == null) {
						lblInfo.setText("No way out");
						return;
					}

					try {
						index = Integer.parseInt(txtLevelN.getText());
					} catch (NumberFormatException e) {
						lblInfo.setText("Invalid level number");
						return;
					}

					if (txtFileName.getText().equals("Filename")) {
						lblInfo.setText("Invalid filename");
						return;
					}

					if (editor == null) {
						lblInfo.setText("Invalid editor");
						return;
					}
					String fileName;
					try {
						fileName = txtFileName.getText();
						editor.saveLevel(txtFileName.getText());
						editor.insertLvl(index, fileName);
						editor.saveLevelFiles();
					} catch (FileNotFoundException e) {
						lblInfo.setText("Invalid filename");
						return;
					} catch (UnsupportedEncodingException e) {
						lblInfo.setText("Invalid ecoding");
						return;
					}

					lblInfo.setText("File Saved");
				}
			}
		});
		GridBagConstraints gbc_btnAddLevel = new GridBagConstraints();
		gbc_btnAddLevel.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddLevel.gridx = 0;
		gbc_btnAddLevel.gridy = 5;
		editing.add(btnAddLevel, gbc_btnAddLevel);

		lblInfo = new JLabel("Info");
		GridBagConstraints gbc_lblInfo = new GridBagConstraints();
		gbc_lblInfo.gridwidth = 2;
		gbc_lblInfo.gridx = 0;
		gbc_lblInfo.gridy = 7;
		editing.add(lblInfo, gbc_lblInfo);
		comboBox.addItem("Drunken");
		comboBox.addItem("Rookie");
		comboBox.addItem("Suspicious");
		GridBagConstraints gbc_quitBt = new GridBagConstraints();
		gbc_quitBt.gridx = 0;
		gbc_quitBt.gridy = 2;
		rightSide.add(quitBt, gbc_quitBt);
	}

	public void updateScreen() throws NumberFormatException, IOException {
		char[][] gameText = game.getMapWCharacter();
		gameArea.setMap(gameText);
		gameArea.repaint();

		if (game != null)
			switch (game.endLevel()) {
			case 0:
				if (!game.nextLevel(Integer.parseInt(textField.getText()), (String) comboBox.getSelectedItem())) {
					gameInfo.setText("YOU WIN");
					game = null;
					enableChanges(true);
				} else {
					gameInfo.setText("NEW LEVEL");
					updateScreen();
				}
				break;
			case 2:
				gameInfo.setText("YOU LOST");
				game = null;
				enableChanges(true);
				break;
			default:
				break;
			}
		gameArea.requestFocusInWindow();
	}

	public void enableChanges(boolean value) {
		newGameBt.setEnabled(value);
		textField.setEditable(value);
		comboBox.setEnabled(value);

		tileChoser.setEnabled(value);
		tileChoser_CB.setEnabled(value);
		guardPath.setEnabled(value);
		textField_Path.setEnabled(value);
		btnStartEdit.setEnabled(value);
		btnNxtLevel.setEnabled(value);

		btnNewLevel.setEnabled(value);
		txtLevelN.setEnabled(value);
		txtFileName.setEnabled(value);
		btnAddLevel.setEnabled(value);
		btnReplaceLevel.setEnabled(value);
		lblKeyToOpen.setEnabled(value);
		keysCB.setEnabled(value);

		btnLoadGame.setEnabled(value);
		btnSaveGame.setEnabled(!value);

		upBt.setEnabled(!value);
		downBt.setEnabled(!value);
		leftBt.setEnabled(!value);
		rightBt.setEnabled(!value);
	}

	protected void setLevelParam() {
		txtLevelN.setText(Integer.toString(editor.getCurr_level() - 1));
		txtFileName.setText(editor.getLevels().get(editor.getCurr_level() - 1));
	}

	protected void updateEditFields() {
		gameArea.setMap(editor.getMapWCharacter());
		if (editor.getGuard() != null) {
			String path = new String();
			for (char c : editor.getGuard().getPath()) {
				path += c;
			}
			textField_Path.setText(path);
		} else
			textField_Path.setText("");

	}

	protected void updateKeysCB() {
		keysCB.removeAllItems();
		keysCB.addItem(null);
		if (editor.getKey() != null)
			keysCB.addItem(editor.getKey());
		if (editor.getLever() != null)
			keysCB.addItem(editor.getLever());
	}

}
