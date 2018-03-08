package dkeep.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;

public class MainWindow {

	private JFrame frame;
	private JTextField textField;

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
		frame.setBounds(100, 100, 677, 641);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblOgre = new JLabel("Number of Ogres");
		lblOgre.setBounds(26, 36, 234, 33);
		frame.getContentPane().add(lblOgre);
		
		textField = new JTextField();
		textField.setBounds(260, 33, 122, 39);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblGuardPersonality = new JLabel("Guard Personality");
		lblGuardPersonality.setBounds(26, 100, 228, 33);
		frame.getContentPane().add(lblGuardPersonality);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(270, 97, 210, 39);
		frame.getContentPane().add(comboBox);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(448, 175, 171, 41);
		frame.getContentPane().add(btnNewGame);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(448, 463, 171, 41);
		frame.getContentPane().add(btnExit);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Courier New", Font.PLAIN, 13));
		textArea.setBounds(26, 175, 368, 331);
		frame.getContentPane().add(textArea);
		
		JButton btnUp = new JButton("Up");
		btnUp.setBounds(448, 260, 171, 41);
		frame.getContentPane().add(btnUp);
		
		JButton btnLeft = new JButton("Left");
		btnLeft.setBounds(420, 329, 77, 41);
		frame.getContentPane().add(btnLeft);
		
		JButton btnRight = new JButton("Right");
		btnRight.setBounds(550, 329, 95, 41);
		frame.getContentPane().add(btnRight);
		
		JButton btnDown = new JButton("Down");
		btnDown.setBounds(448, 394, 171, 41);
		frame.getContentPane().add(btnDown);
		
		JLabel label = new JLabel("");
		label.setBounds(26, 520, 115, 33);
		frame.getContentPane().add(label);
	}
}
