package dkeep.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dkeep.logic.Game;

public class MainWindow {
	
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
	private JTabbedPane tabbedPane;
	private JPanel settings_panel;
	private JPanel editing;
	private JLabel tileChoser;
	private JComboBox<String> tileChoser_CB;
	private JLabel guardPath;
	private JTextField textField_Path;
	private JRadioButton chckbxEdit;
	private JRadioButton rdbtnSelect;
	private JButton btnStartEdit;
	private JButton btnNxtLevel;
	private JButton btnNewLevel;
	private JTextField txtLevelN;
	private JButton btnSaveLevel;
	private JTextField txtFileName;

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
	 * @throws IOException 
	 */
	public MainWindow() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(10, 10, 1*(1024-(256 + 32)), 1*(512));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] { 256 };
		gridBagLayout.columnWidths = new int[] { 192, 64 };
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
		gameArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (KeyEvent.VK_W == arg0.getKeyCode()) {
					if (game == null)
						return;
					game.movement('w');

				}
				if (KeyEvent.VK_S == arg0.getKeyCode()) {
					if (game == null)
						return;
					game.movement('s');

				}
				if (KeyEvent.VK_A == arg0.getKeyCode()) {
					if (game == null)
						return;
					game.movement('a');

				}
				if (KeyEvent.VK_D == arg0.getKeyCode()) {
					if (game == null)
						return;
					game.movement('d');

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
		gbl_rightSide.columnWeights = new double[] { 1.0 };
		gbl_rightSide.rowWeights = new double[] { 0.0, 1.0, 0.0 };
		rightSide.setLayout(gbl_rightSide);

		newGameBt = new JButton("New Game");
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

					} catch (Exception e) {
						gameInfo.setText("Invalid Number of ogres");
						return;
					}

					gameInfo.setText("Playing");
				
					game = new Game();
					gameArea.setGuardType((String) comboBox.getSelectedItem());
					try {
						game.nextLevel(nOgres, (String) comboBox.getSelectedItem());
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IOException e) {
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
		GridBagConstraints gbc_newGameBt = new GridBagConstraints();
		gbc_newGameBt.insets = new Insets(0, 0, 5, 0);
		gbc_newGameBt.gridx = 0;
		gbc_newGameBt.gridy = 0;
		rightSide.add(newGameBt, gbc_newGameBt);

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
				if(!textField.isEnabled() || !textField.isEditable())
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
				if(!textField.isEnabled())
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
		gbl_editing.columnWeights = new double[]{0.0, 1.0};
		gbl_editing.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		editing.setLayout(gbl_editing);
		
		btnStartEdit = new JButton("Start Edit");
		GridBagConstraints gbc_btnStartEdit = new GridBagConstraints();
		gbc_btnStartEdit.gridwidth = 2;
		gbc_btnStartEdit.insets = new Insets(0, 0, 5, 0);
		gbc_btnStartEdit.gridx = 0;
		gbc_btnStartEdit.gridy = 0;
		editing.add(btnStartEdit, gbc_btnStartEdit);
		
		btnNxtLevel = new JButton("Next Level");
		GridBagConstraints gbc_btnNxtLevel = new GridBagConstraints();
		gbc_btnNxtLevel.gridwidth = 2;
		gbc_btnNxtLevel.insets = new Insets(0, 0, 5, 0);
		gbc_btnNxtLevel.gridx = 0;
		gbc_btnNxtLevel.gridy = 1;
		editing.add(btnNxtLevel, gbc_btnNxtLevel);
		
		btnNewLevel = new JButton("New Level");
		GridBagConstraints gbc_btnNewLevel = new GridBagConstraints();
		gbc_btnNewLevel.gridheight = 2;
		gbc_btnNewLevel.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewLevel.gridx = 0;
		gbc_btnNewLevel.gridy = 2;
		editing.add(btnNewLevel, gbc_btnNewLevel);
		
		txtLevelN = new JTextField();
		txtLevelN.setText("Level n\u00BA");
		GridBagConstraints gbc_txtLevelN = new GridBagConstraints();
		gbc_txtLevelN.insets = new Insets(0, 0, 5, 0);
		gbc_txtLevelN.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLevelN.gridx = 1;
		gbc_txtLevelN.gridy = 2;
		editing.add(txtLevelN, gbc_txtLevelN);
		txtLevelN.setColumns(10);
		
		txtFileName = new JTextField();
		txtFileName.setText("File Name");
		GridBagConstraints gbc_txtFileName = new GridBagConstraints();
		gbc_txtFileName.insets = new Insets(0, 0, 5, 0);
		gbc_txtFileName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFileName.gridx = 1;
		gbc_txtFileName.gridy = 3;
		editing.add(txtFileName, gbc_txtFileName);
		txtFileName.setColumns(10);
		
		chckbxEdit = new JRadioButton("Edit");
		GridBagConstraints gbc_chckbxEdit = new GridBagConstraints();
		gbc_chckbxEdit.gridwidth = 2;
		gbc_chckbxEdit.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxEdit.gridx = 0;
		gbc_chckbxEdit.gridy = 4;
		editing.add(chckbxEdit, gbc_chckbxEdit);
		
		tileChoser = new JLabel("Tile");
		GridBagConstraints gbc_tileChoser = new GridBagConstraints();
		gbc_tileChoser.insets = new Insets(0, 0, 5, 5);
		gbc_tileChoser.gridx = 0;
		gbc_tileChoser.gridy = 5;
		editing.add(tileChoser, gbc_tileChoser);
		
		tileChoser_CB = new JComboBox<String>();
		GridBagConstraints gbc_tileChoser_CB = new GridBagConstraints();
		gbc_tileChoser_CB.insets = new Insets(0, 0, 5, 0);
		gbc_tileChoser_CB.fill = GridBagConstraints.HORIZONTAL;
		gbc_tileChoser_CB.gridx = 1;
		gbc_tileChoser_CB.gridy = 5;
		editing.add(tileChoser_CB, gbc_tileChoser_CB);
		
		rdbtnSelect = new JRadioButton("Select");
		GridBagConstraints gbc_rdbtnSelect = new GridBagConstraints();
		gbc_rdbtnSelect.gridwidth = 2;
		gbc_rdbtnSelect.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnSelect.gridx = 0;
		gbc_rdbtnSelect.gridy = 6;
		editing.add(rdbtnSelect, gbc_rdbtnSelect);
		
		guardPath = new JLabel("Path");
		guardPath.setToolTipText("");
		GridBagConstraints gbc_guardPath = new GridBagConstraints();
		gbc_guardPath.insets = new Insets(0, 0, 5, 5);
		gbc_guardPath.gridx = 0;
		gbc_guardPath.gridy = 7;
		editing.add(guardPath, gbc_guardPath);
		
		textField_Path = new JTextField();
		GridBagConstraints gbc_textField_Path = new GridBagConstraints();
		gbc_textField_Path.insets = new Insets(0, 0, 5, 0);
		gbc_textField_Path.fill = GridBagConstraints.BOTH;
		gbc_textField_Path.gridx = 1;
		gbc_textField_Path.gridy = 7;
		editing.add(textField_Path, gbc_textField_Path);
		textField_Path.setColumns(10);
		
		btnSaveLevel = new JButton("Save Level");
		GridBagConstraints gbc_btnSaveLevel = new GridBagConstraints();
		gbc_btnSaveLevel.gridwidth = 2;
		gbc_btnSaveLevel.gridx = 0;
		gbc_btnSaveLevel.gridy = 8;
		editing.add(btnSaveLevel, gbc_btnSaveLevel);
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
		chckbxEdit.setEnabled(value);
		rdbtnSelect.setEnabled(value);
		btnStartEdit.setEnabled(value);
		btnNxtLevel.setEnabled(value);
		
		btnNewLevel.setEnabled(value);
		txtLevelN.setEnabled(value);
		txtFileName.setEnabled(value);
		
		upBt.setEnabled(!value);
		downBt.setEnabled(!value);
		leftBt.setEnabled(!value);
		rightBt.setEnabled(!value);
	}
	
}
