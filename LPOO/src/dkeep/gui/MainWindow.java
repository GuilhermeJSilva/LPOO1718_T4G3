package dkeep.gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import java.util.ArrayList;

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

import dkeep.gameManipulator.Editor;
import dkeep.gameManipulator.saveFunction;
import dkeep.logic.DoorMechanism;
import dkeep.logic.Enemy;
import dkeep.logic.Game;
import dkeep.logic.Guard;

public class MainWindow {

	private JFrame frame;
	private JTextField nOgresTF;
	private Game game;
	private Editor editor = null;

	private JPanel options;
	private JPanel leftSide;
	private JLabel nOgres;
	private JLabel guardType;
	private JComboBox<String> guardTypeCB;
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
	private JPanel buttonPanel;
	private JButton btnLoadGame;
	private JButton btnSaveGame;

	final JFileChooser fc = new JFileChooser();
	private JComboBox<Guard> guard_CB;
	private JLabel lblGuardPath;
	private JTextField txtSizex;
	private JTextField txtSizey;

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

		initializeLeftSide();

		initializeRightSide();

	}

	protected void initializeEditingTab() {
		editing = new JPanel();
		tabbedPane.addTab("Editing", null, editing, null);
		GridBagLayout gbl_editing = new GridBagLayout();
		gbl_editing.columnWeights = new double[] { 1.0, 1.0 };
		gbl_editing.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		editing.setLayout(gbl_editing);

		initiazeSEButtton();

		initializeNLButton();

		initializeNewLButton();

		initializeRLButton();

		initializeALButton();

		initializeEditorSizeLabels();

		initializeKeyToOpen();

		initializeGuardPath();

		initializeSaveParameters();

		initializeTileChoser();

		initializeEditingInfo();
	}

	protected void initializeEditingInfo() {
		lblInfo = new JLabel("Info");
		GridBagConstraints gbc_lblInfo = new GridBagConstraints();
		gbc_lblInfo.gridwidth = 2;
		gbc_lblInfo.gridx = 0;
		gbc_lblInfo.gridy = 9;
		editing.add(lblInfo, gbc_lblInfo);
	}

	protected void initializeALButton() {
		btnAddLevel = new JButton("Add Level");
		btnAddLevel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnAddLevel.isEnabled()) {
					try {
						saveFile((index, fileName) -> editor.insertLvl(index, fileName));
					} catch (SecurityException e1) {
						System.err.println("Method not found");
						System.exit(-2);
					}
				}
			}
		});
		GridBagConstraints gbc_btnAddLevel = new GridBagConstraints();
		gbc_btnAddLevel.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddLevel.gridx = 0;
		gbc_btnAddLevel.gridy = 7;
		editing.add(btnAddLevel, gbc_btnAddLevel);
	}

	protected void initializeGuardPathModif() {
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
				Guard guard = (Guard) guard_CB.getSelectedItem();
				if (guard != null) {
					if (!guard.setPath(path.toCharArray())) {
						lblInfo.setText("Wrong guard path");
					}
				}
			}
		});
		GridBagConstraints gbc_textField_Path = new GridBagConstraints();
		gbc_textField_Path.insets = new Insets(0, 0, 5, 0);
		gbc_textField_Path.fill = GridBagConstraints.BOTH;
		gbc_textField_Path.gridx = 1;
		gbc_textField_Path.gridy = 6;
		editing.add(textField_Path, gbc_textField_Path);
		textField_Path.setColumns(10);
	}

	protected void initializeTileChoser() {
		tileChoser = new JLabel("Tile");
		GridBagConstraints gbc_tileChoser = new GridBagConstraints();
		gbc_tileChoser.insets = new Insets(0, 0, 5, 5);
		gbc_tileChoser.gridx = 0;
		gbc_tileChoser.gridy = 3;
		editing.add(tileChoser, gbc_tileChoser);

		tileChoser_CB = new JComboBox<String>();
		GridBagConstraints gbc_tileChoser_CB = new GridBagConstraints();
		gbc_tileChoser_CB.insets = new Insets(0, 0, 5, 0);
		gbc_tileChoser_CB.fill = GridBagConstraints.HORIZONTAL;
		gbc_tileChoser_CB.gridx = 1;
		gbc_tileChoser_CB.gridy = 3;
		editing.add(tileChoser_CB, gbc_tileChoser_CB);
		tileChoser_CB.addItem("Wall");
		tileChoser_CB.addItem("Floor");
		tileChoser_CB.addItem("Door");
		tileChoser_CB.addItem("Exit");
		tileChoser_CB.addItem("Key");
		tileChoser_CB.addItem("Lever");
		tileChoser_CB.addItem("Hero Armed");
		tileChoser_CB.addItem("Hero");
		tileChoser_CB.addItem("Ogre Spawn");
		tileChoser_CB.addItem("Guard");
	}

	protected void initializeRLButton() {
		btnReplaceLevel = new JButton("Replace Level");
		btnReplaceLevel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnReplaceLevel.isEnabled()) {
					try {
						saveFile((index, fileName) -> editor.replaceLvl(index, fileName));
					} catch (SecurityException e1) {
						System.err.println("Method not found");
						System.exit(-2);
					}
				}
			}
		});
		GridBagConstraints gbc_btnReplaceLevel = new GridBagConstraints();
		gbc_btnReplaceLevel.insets = new Insets(0, 0, 5, 5);
		gbc_btnReplaceLevel.gridx = 0;
		gbc_btnReplaceLevel.gridy = 8;
		editing.add(btnReplaceLevel, gbc_btnReplaceLevel);
	}

	protected void initializeSaveParameters() {
		txtLevelN = new JTextField();
		txtLevelN.setText("Level n\u00BA");
		GridBagConstraints gbc_txtLevelN = new GridBagConstraints();
		gbc_txtLevelN.insets = new Insets(0, 0, 5, 0);
		gbc_txtLevelN.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLevelN.gridx = 1;
		gbc_txtLevelN.gridy = 7;
		editing.add(txtLevelN, gbc_txtLevelN);
		txtLevelN.setColumns(10);

		txtFileName = new JTextField();
		txtFileName.setText("File Name");
		GridBagConstraints gbc_txtFileName = new GridBagConstraints();
		gbc_txtFileName.insets = new Insets(0, 0, 5, 0);
		gbc_txtFileName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFileName.gridx = 1;
		gbc_txtFileName.gridy = 8;
		editing.add(txtFileName, gbc_txtFileName);
		txtFileName.setColumns(10);
	}

	protected void initializeGuardPath() {
		lblGuardPath = new JLabel("Guard Path");
		GridBagConstraints gbc_lblGuardPath = new GridBagConstraints();
		gbc_lblGuardPath.gridwidth = 2;
		gbc_lblGuardPath.insets = new Insets(0, 0, 5, 0);
		gbc_lblGuardPath.gridx = 0;
		gbc_lblGuardPath.gridy = 5;
		editing.add(lblGuardPath, gbc_lblGuardPath);

		guard_CB = new JComboBox<Guard>();
		guard_CB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					updateGuardPathText();
				}
			}
		});
		GridBagConstraints gbc_guard_CB = new GridBagConstraints();
		gbc_guard_CB.insets = new Insets(0, 0, 5, 5);
		gbc_guard_CB.fill = GridBagConstraints.HORIZONTAL;
		gbc_guard_CB.gridx = 0;
		gbc_guard_CB.gridy = 6;
		editing.add(guard_CB, gbc_guard_CB);

		initializeGuardPathModif();
	}

	protected void initializeKeyToOpen() {
		lblKeyToOpen = new JLabel("Key To Open");
		lblKeyToOpen.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblKeyToOpen = new GridBagConstraints();
		gbc_lblKeyToOpen.insets = new Insets(0, 0, 5, 5);
		gbc_lblKeyToOpen.gridx = 0;
		gbc_lblKeyToOpen.gridy = 4;
		editing.add(lblKeyToOpen, gbc_lblKeyToOpen);

		keysCB = new JComboBox<DoorMechanism>();
		GridBagConstraints gbc_keysCB = new GridBagConstraints();
		gbc_keysCB.insets = new Insets(0, 0, 5, 0);
		gbc_keysCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_keysCB.gridx = 1;
		gbc_keysCB.gridy = 4;
		editing.add(keysCB, gbc_keysCB);
	}

	protected void initializeEditorSizeLabels() {
		txtSizex = new JTextField();
		txtSizex.setText("10");
		GridBagConstraints gbc_txtSizex = new GridBagConstraints();
		gbc_txtSizex.insets = new Insets(0, 0, 5, 0);
		gbc_txtSizex.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSizex.gridx = 1;
		gbc_txtSizex.gridy = 1;
		editing.add(txtSizex, gbc_txtSizex);
		txtSizex.setColumns(10);

		txtSizey = new JTextField();
		txtSizey.setText("10");
		GridBagConstraints gbc_txtSizey = new GridBagConstraints();
		gbc_txtSizey.insets = new Insets(0, 0, 5, 0);
		gbc_txtSizey.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSizey.gridx = 1;
		gbc_txtSizey.gridy = 2;
		editing.add(txtSizey, gbc_txtSizey);
		txtSizey.setColumns(10);
	}

	protected void initializeNewLButton() {
		btnNewLevel = new JButton("New Level");
		addListenerNewL();
		GridBagConstraints gbc_btnNewLevel = new GridBagConstraints();
		gbc_btnNewLevel.gridheight = 2;
		gbc_btnNewLevel.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewLevel.gridx = 0;
		gbc_btnNewLevel.gridy = 1;
		editing.add(btnNewLevel, gbc_btnNewLevel);
	}

	protected void addListenerNewL() {
		btnNewLevel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnNewLevel.isEnabled()) {
					ArrayList<Integer> size = new ArrayList<Integer>();
					boolean parse = getEditorSize(size);

					if (!parse)
						return;
					editor = new Editor(size.get(0), size.get(1));
					updateEditFields();
					updateKeysCB();
					txtLevelN.setText("Lvl order");
					txtFileName.setText("Filename");
					gameArea.repaint();
					updateGuardCB();
				}

				gameArea.requestFocusInWindow();
			}
		});
	}

	protected void initializeNLButton() {
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
						updateGuardCB();
					}
				}
				gameArea.requestFocusInWindow();
			}
		});
		gbc_btnNxtLevel.insets = new Insets(0, 0, 5, 0);
		gbc_btnNxtLevel.gridx = 1;
		gbc_btnNxtLevel.gridy = 0;
		editing.add(btnNxtLevel, gbc_btnNxtLevel);
	}

	protected void initiazeSEButtton() {
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
					updateGuardCB();
				}
				gameArea.requestFocusInWindow();
			}

		});

		GridBagConstraints gbc_btnStartEdit = new GridBagConstraints();
		gbc_btnStartEdit.insets = new Insets(0, 0, 5, 5);
		gbc_btnStartEdit.gridx = 0;
		gbc_btnStartEdit.gridy = 0;
		editing.add(btnStartEdit, gbc_btnStartEdit);
	}

	protected void initializeGuardType() {
		guardType = new JLabel("Guard Type");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		settings_panel.add(guardType, gbc_lblNewLabel_2);
		guardType.setHorizontalAlignment(SwingConstants.CENTER);

		guardTypeCB = new JComboBox<String>();
		guardTypeCB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!nOgresTF.isEnabled())
					gameArea.requestFocusInWindow();
				else
					super.mouseClicked(e);
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		settings_panel.add(guardTypeCB, gbc_comboBox);
		guardTypeCB.addItem("Drunken");
		guardTypeCB.addItem("Rookie");
		guardTypeCB.addItem("Suspicious");
	}

	protected void initializeNOgres() {
		nOgres = new JLabel("Ogres per Spawn");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		settings_panel.add(nOgres, gbc_lblNewLabel_1);
		nOgres.setHorizontalAlignment(SwingConstants.CENTER);

		nOgresTF = new JTextField();
		nOgresTF.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!nOgresTF.isEnabled() || !nOgresTF.isEditable())
					gameArea.requestFocusInWindow();
				else
					super.mouseClicked(e);
			}
		});
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		settings_panel.add(nOgresTF, gbc_textField);
		nOgresTF.setText("1");
		nOgresTF.setColumns(10);
	}

	protected void initializeSettingsPanel() {
		settings_panel = new JPanel();
		tabbedPane.addTab("Settings", null, settings_panel, null);
		GridBagLayout gbl_settings_panel = new GridBagLayout();
		gbl_settings_panel.columnWeights = new double[] { 0.0, 0.0 };
		gbl_settings_panel.rowWeights = new double[] { 0.0, 0.0 };
		settings_panel.setLayout(gbl_settings_panel);
		initializeNOgres();
		initializeGuardType();
	}

	protected void initializeDownButton() {
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

				updateGame();
			}
		});
		GridBagConstraints gbc_downBt = new GridBagConstraints();
		gbc_downBt.insets = new Insets(0, 0, 0, 5);
		gbc_downBt.anchor = GridBagConstraints.NORTHWEST;
		gbc_downBt.gridx = 1;
		gbc_downBt.gridy = 2;
		controls.add(downBt, gbc_downBt);
	}

	protected void initializeRightButton() {
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

				updateGame();
			}
		});
		GridBagConstraints gbc_rightBt = new GridBagConstraints();
		gbc_rightBt.anchor = GridBagConstraints.NORTHWEST;
		gbc_rightBt.insets = new Insets(0, 0, 5, 5);
		gbc_rightBt.gridx = 2;
		gbc_rightBt.gridy = 1;
		controls.add(rightBt, gbc_rightBt);
	}

	protected void intializeLeftButton() {
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

				updateGame();
			}
		});
		GridBagConstraints gbc_leftBt = new GridBagConstraints();
		gbc_leftBt.anchor = GridBagConstraints.NORTHWEST;
		gbc_leftBt.insets = new Insets(0, 0, 5, 5);
		gbc_leftBt.gridx = 0;
		gbc_leftBt.gridy = 1;
		controls.add(leftBt, gbc_leftBt);
	}

	protected void initializeUpButton() {
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

				updateGame();

			}
		});
		GridBagConstraints gbc_upBt = new GridBagConstraints();
		gbc_upBt.fill = GridBagConstraints.BOTH;
		gbc_upBt.anchor = GridBagConstraints.NORTHWEST;
		gbc_upBt.insets = new Insets(0, 0, 5, 5);
		gbc_upBt.gridx = 1;
		gbc_upBt.gridy = 0;
		controls.add(upBt, gbc_upBt);
	}

	protected void initializeControlsTab() {
		controls = new JPanel();
		tabbedPane.addTab("Controls", null, controls, null);
		GridBagLayout gbl_controls = new GridBagLayout();
		gbl_controls.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_controls.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		controls.setLayout(gbl_controls);
		initializeUpButton();
		intializeLeftButton();
		initializeRightButton();
		initializeDownButton();
	}

	protected void initializeTabbedPanel() {
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
		initializeControlsTab();

		initializeSettingsPanel();

		initializeEditingTab();
	}

	protected void initializeQuitButton() {
		quitBt = new JButton("Exit");
		quitBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});

		GridBagConstraints gbc_quitBt = new GridBagConstraints();
		gbc_quitBt.gridx = 0;
		gbc_quitBt.gridy = 2;
		rightSide.add(quitBt, gbc_quitBt);
		enableChanges(true);
	}

	protected void initializeNGButton() {
		newGameBt = new JButton("New Game");
		GridBagConstraints gbc_newGameBt = new GridBagConstraints();
		gbc_newGameBt.gridwidth = 2;
		gbc_newGameBt.gridx = 0;
		gbc_newGameBt.gridy = 1;
		buttonPanel.add(newGameBt, gbc_newGameBt);
		nGButtonAddListeners();
	}

	protected void nGButtonAddListeners() {
		newGameBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (newGameBt.isEnabled()) {

					int nOgres = getNOgres();
					if (nOgres == -1)
						return;

					game = new Game();
					editor = null;

					gameArea.setGuardType((String) guardTypeCB.getSelectedItem());
					game.nextLevel(nOgres, (String) guardTypeCB.getSelectedItem());
					updateGame();
					enableChanges(false);

				}
				gameArea.requestFocusInWindow();

			}
		});
	}

	protected void initializeSGButton() {
		btnSaveGame = new JButton("Save Game");
		addListenerSGButtom();
		GridBagConstraints gbc_btnSaveGame = new GridBagConstraints();
		gbc_btnSaveGame.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveGame.gridx = 1;
		gbc_btnSaveGame.gridy = 0;
		buttonPanel.add(btnSaveGame, gbc_btnSaveGame);
	}

	protected void addListenerSGButtom() {
		btnSaveGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showSaveDialog(btnSaveGame);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						FileOutputStream fout = new FileOutputStream(file.getAbsolutePath(), true);
						ObjectOutputStream oos = new ObjectOutputStream(fout);
						oos.writeObject(game);
						oos.close();
					} catch (IOException ex) {
						gameInfo.setText("Save failed");
					} finally {
						gameInfo.setText("Game state saved");
					}

				} else {
					System.err.println("Open command cancelled by user.\n");
				}
				gameArea.requestFocusInWindow();

			}
		});
	}

	protected void initializeLGButton() {
		btnLoadGame = new JButton("Load Game");
		addLGButtonListener();
		GridBagConstraints gbc_btnLoadGame = new GridBagConstraints();
		gbc_btnLoadGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoadGame.gridx = 0;
		gbc_btnLoadGame.gridy = 0;
		buttonPanel.add(btnLoadGame, gbc_btnLoadGame);
	}

	protected void addLGButtonListener() {
		btnLoadGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					File file = getLoadFile();
					FileInputStream fout = new FileInputStream(file);
					ObjectInputStream oos = new ObjectInputStream(fout);
					game = (Game) oos.readObject();
					oos.close();
					gameArea.setGuardType(game.getGuardType());
					updateScreen();
					enableChanges(false);
				} catch (IOException ex) {
					gameInfo.setText("Load failed (IO)");
					ex.printStackTrace();
				} catch (ClassNotFoundException e1) {
					gameInfo.setText("Load failed (Class)");
				}
				gameArea.requestFocusInWindow();

			}
		});
	}

	protected void initializeButtonPanel() {
		buttonPanel = new JPanel();
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.fill = GridBagConstraints.BOTH;
		gbc_buttonPanel.insets = new Insets(0, 0, 5, 0);
		gbc_buttonPanel.gridx = 0;
		gbc_buttonPanel.gridy = 0;
		rightSide.add(buttonPanel, gbc_buttonPanel);
		GridBagLayout gbl_buttonPanel = new GridBagLayout();
		gbl_buttonPanel.columnWidths = new int[] { 124, 0 };
		gbl_buttonPanel.columnWeights = new double[] { 0.0, 0.0 };
		gbl_buttonPanel.rowWeights = new double[] { 0.0, 0.0 };
		buttonPanel.setLayout(gbl_buttonPanel);
		initializeLGButton();
		initializeSGButton();
		initializeNGButton();
	}

	protected void initializeLeftSide() throws IOException {
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

		initializeInfo();
		initializeGameArea();
	}

	protected void initializeInfo() {
		gameInfo = new JLabel("Press New Game to start the game!");
		GridBagConstraints gbc_gameInfo = new GridBagConstraints();
		gbc_gameInfo.weighty = 1.0;
		gbc_gameInfo.weightx = 1.0;
		gbc_gameInfo.insets = new Insets(0, 0, 5, 0);
		gbc_gameInfo.gridx = 0;
		gbc_gameInfo.gridy = 16;
		leftSide.add(gameInfo, gbc_gameInfo);
	}

	protected void initializeOptions() {
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
		initializeTabbedPanel();

	}

	protected void initializeRightSide() {
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

		initializeButtonPanel();

		initializeOptions();

		initializeQuitButton();
	}

	protected void initializeGameArea() throws IOException {
		gameArea = new GameBoard();
		gameAreaAddListeners();
		GridBagConstraints gbc_gameArea = new GridBagConstraints();
		gbc_gameArea.weighty = 40.0;
		gbc_gameArea.weightx = 1.0;
		gbc_gameArea.gridheight = 15;
		gbc_gameArea.insets = new Insets(0, 0, 5, 0);
		gbc_gameArea.fill = GridBagConstraints.BOTH;
		gbc_gameArea.gridx = 0;
		gbc_gameArea.gridy = 1;
		leftSide.add(gameArea, gbc_gameArea);
	}

	protected void gameAreaAddListeners() {
		gameAreaAddMouseListener();
		gameAreaAddKeyListener();
	}

	protected void gameAreaAddKeyListener() {
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

				updateGame();
			}
		});
	}

	protected void gameAreaAddMouseListener() {
		gameArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (game != null) {
					gameArea.requestFocusInWindow();
					return;
				}
				try {
					int coords[] = gameArea.getMapCoords(e.getX(), e.getY());
					String tile = (String) tileChoser_CB.getSelectedItem();
					editor.editCoords(coords, tile);
					updateDoorMechanism(coords, tile);
					gameArea.setMap(editor.getMapWCharacter());
					gameArea.repaint();
				} catch (InvalidClick ex) {
					System.err.println("Invalid click");
				}
				gameArea.requestFocusInWindow();
			}
		});
	}

	public void updateScreen() throws NumberFormatException, IOException {
		char[][] gameText = game.getMapWCharacter();
		gameArea.setMap(gameText);
		gameArea.repaint();

		if (game != null)
			switch (game.endLevel()) {
			case 0:
				if (!game.nextLevel(Integer.parseInt(nOgresTF.getText()), (String) guardTypeCB.getSelectedItem())) {
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
		// newGameBt.setEnabled(value);
		nOgresTF.setEditable(value);
		guardTypeCB.setEnabled(value);

		tileChoser.setEnabled(value);
		tileChoser_CB.setEnabled(value);
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
		updateKeysCB();
		updateGuardCB();
		updateGuardPathText();

	}

	protected void updateGuardPathText() {
		Guard guard = (Guard) guard_CB.getSelectedItem();
		if (guard != null) {
			String path = new String();
			for (char c : guard.getPath()) {
				path += c;
			}
			textField_Path.setText(path);
		} else
			textField_Path.setText("");
	}

	protected void updateKeysCB() {
		keysCB.removeAllItems();
		if (editor == null)
			return;
		keysCB.addItem(null);
		for (DoorMechanism dMecha : editor.getdMechanism()) {
			keysCB.addItem(dMecha);
		}
	}

	protected void updateGuardCB() {
		guard_CB.removeAllItems();
		if (editor == null)
			return;
		for (Enemy enemy : editor.getEnemies()) {
			if (enemy instanceof Guard) {
				guard_CB.addItem((Guard) enemy);
			}
		}
	}

	protected int checkSave(int index) {
		if (editor == null) {
			lblInfo.setText("Invalid editor");
			return -1;
		}

		boolean exit = editor.checkLevel();
		if (!exit) {
			lblInfo.setText("No way out");
		}

		try {
			index = Integer.parseInt(txtLevelN.getText());
		} catch (NumberFormatException e) {
			lblInfo.setText("Invalid level number");
			return -1;
		}

		if (txtFileName.getText().equals("Filename")) {
			lblInfo.setText("Invalid filename");
			return -1;
		}
		return index;
	}

	public void saveFile(saveFunction saveMethod) {
		try {
			String fileName;
			Integer index = 0;
			index = checkSave(index);
			if (index == -1)
				return;

			fileName = txtFileName.getText();
			editor.saveLevel(fileName);
			saveMethod.save(index, fileName);
			editor.saveLevelFiles();
		} catch (FileNotFoundException e) {
			lblInfo.setText("Invalid filename");
			return;
		} catch (UnsupportedEncodingException e) {
			lblInfo.setText("Invalid ecoding");
			return;
		} catch (IllegalArgumentException e) {
			lblInfo.setText("Wrong save method");
			return;
		}

		lblInfo.setText("File Saved");
	}

	protected void updateGame() {
		try {
			updateScreen();
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		gameArea.requestFocusInWindow();
	}

	protected int getNOgres() {
		int nOgres;
		try {
			nOgres = Integer.parseInt(nOgresTF.getText());
			if (nOgres <= 0 || nOgres > 5) {
				gameInfo.setText("Invalid Number of ogres");
				return -1;
			}

		} catch (NumberFormatException e) {
			gameInfo.setText("Invalid Number of ogres");
			return -1;
		}
		return nOgres;
	}

	protected void updateDoorMechanism(int[] coords, String tile) {
		DoorMechanism dm;
		if (editor != null) {
			switch (tile) {
			case "Door":
				dm = (DoorMechanism) keysCB.getSelectedItem();
				if (dm != null) {
					dm.addDoor(coords.clone(), ' ');
				}
				break;
			case "Exit":
				dm = (DoorMechanism) keysCB.getSelectedItem();
				if (dm != null) {
					dm.addDoor(coords.clone(), 'S');
				}
				break;
			case "Key":
			case "Lever":
				updateKeysCB();
				break;
			default:
				break;
			}
			updateEditFields();
		}
	}

	protected boolean getEditorSize(ArrayList<Integer> editorSize) {
		try {
			editorSize.add(Integer.parseInt(txtSizex.getText()));
			editorSize.add(Integer.parseInt(txtSizey.getText()));
		} catch (NumberFormatException e) {
			lblInfo.setText("Invalid level number");
			return false;
		}
		return true;
	}

	protected File getLoadFile() {
		File file = null;
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = fc.showOpenDialog(btnSaveGame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		} else {
			System.err.println("Open command cancelled by user.\n");
		}
		return file;
	}
}
