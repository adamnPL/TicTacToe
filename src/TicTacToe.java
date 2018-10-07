import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;

public class TicTacToe implements ActionListener {

	private int resultPlayerX = 0;
	private int resultPlayerO = 0;
	private int startingPlayer = 0;
	private String currentUserMove;
	private final int maxGameField = 9;
	private JButton gameButtons[] = new JButton[maxGameField];
	private JLabel lblScoreX;
	private JLabel lblScoreO;
	private Font defaultLabelFont = new Font("Tahoma", Font.BOLD, 20);

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToe window = new TicTacToe();
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
	public TicTacToe() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 640, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		frame.setTitle("Kó³ko i krzy¿yk");

		/* create JPanel for gameSection object */
		JPanel gameSection = new JPanel();
		frame.getContentPane().add(gameSection);
		gameSection.setLayout(new GridLayout(0, 3, 0, 0));

		for (int i = 0; i < gameButtons.length; i++) {
			gameButtons[i] = new JButton("");
			gameButtons[i].addActionListener(this);
			gameButtons[i].setEnabled(false);
			gameButtons[i].setFont(new Font("Serif", Font.BOLD, 28));
			gameSection.add(gameButtons[i]);
		}

		/* create JPanel for infoSection object */
		JPanel infoSection = new JPanel();

		frame.getContentPane().add(infoSection);
		infoSection.setLayout(new GridLayout(0, 1, 0, 0));

		/* create JPanel for scoreSection object */
		JPanel scoreSection = new JPanel();
		infoSection.add(scoreSection);
		scoreSection.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblPlayer = new JLabel("Gracz");
		lblPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer.setFont(defaultLabelFont);
		scoreSection.add(lblPlayer);

		JLabel lblScore = new JLabel("Wynik");
		lblScore.setFont(defaultLabelFont);
		scoreSection.add(lblScore);

		JLabel lblPlayerX = new JLabel("X:");
		lblPlayerX.setFont(defaultLabelFont);
		lblPlayerX.setHorizontalAlignment(SwingConstants.CENTER);
		scoreSection.add(lblPlayerX);

		lblScoreX = new JLabel("0");
		lblScoreX.setFont(defaultLabelFont);
		lblScoreX.setHorizontalAlignment(SwingConstants.LEFT);
		scoreSection.add(lblScoreX);

		JLabel lblPlayerO = new JLabel("O:");
		lblPlayerO.setFont(defaultLabelFont);
		lblPlayerO.setHorizontalAlignment(SwingConstants.CENTER);
		scoreSection.add(lblPlayerO);

		lblScoreO = new JLabel("0");
		lblScoreO.setFont(defaultLabelFont);
		lblScoreO.setHorizontalAlignment(SwingConstants.LEFT);
		scoreSection.add(lblScoreO);

		/* create JPanel for gameOption object */
		JPanel gameOption = new JPanel();
		infoSection.add(gameOption);
		gameOption.setLayout(new GridLayout(3, 1, 0, 0));

		JButton btnNewGame = new JButton("Nowa gra");
		gameOption.add(btnNewGame);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetGameField(1);
				startingPlayer = 0;
			}
		});

		JButton btnEndRound = new JButton("Zakoñcz rundê");
		gameOption.add(btnEndRound);
		btnEndRound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetGameField(0);
				if (startingPlayer % 2 == 0) {
					resultPlayerX++;
				} else {
					resultPlayerO++;
				}
				setScore();
				checkGameWinner();

			}
		});

		JButton btnEndGame = new JButton("Zakoñcz grê");
		gameOption.add(btnEndGame);
		btnEndGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null, "Gracz" + currentUserMove + " wygra³ grê!", "Koniec gry",
						JOptionPane.PLAIN_MESSAGE);
				resetGameField(1);

			}
		});

	}

	/* default actionListener method for game field buttons */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		if (!actionCommand.equals("Used")) {
			JButton pressedButton = ((JButton) e.getSource());
			if (startingPlayer % 2 == 0) {
				pressedButton.setText("X");
				currentUserMove = "X";

				pressedButton.setBackground(new Color(255, 99, 71));
			} else {
				pressedButton.setText("O");
				currentUserMove = "O";
				pressedButton.setBackground(new Color(60, 179, 113));
			}
			pressedButton.setActionCommand("Used");
			checkRoundWinner();
			startingPlayer++;
		}

	}

	/* checking method for verify end of round */
	public void checkRoundWinner() {
		String fieldValue[] = new String[maxGameField];
		int freeField = 0;
		for (int i = 0; i < gameButtons.length; i++) {
			fieldValue[i] = gameButtons[i].getText();
			if (fieldValue[i].equals("")) {
				freeField++;
			}
		}

		String checkingCondition;
		if (currentUserMove.equals("X")) {
			checkingCondition = "XXX";
		} else {
			checkingCondition = "OOO";
		}

		if ( /* checking horizontal field */
		(fieldValue[0] + fieldValue[1] + fieldValue[2]).equals(checkingCondition)
				|| (fieldValue[3] + fieldValue[4] + fieldValue[5]).equals(checkingCondition)
				|| (fieldValue[6] + fieldValue[7] + fieldValue[8]).equals(checkingCondition)
				/* checking vertical field */
				|| (fieldValue[0] + fieldValue[3] + fieldValue[6]).equals(checkingCondition)
				|| (fieldValue[1] + fieldValue[4] + fieldValue[7]).equals(checkingCondition)
				|| (fieldValue[2] + fieldValue[5] + fieldValue[8]).equals(checkingCondition)
				/* checking cross field */
				|| (fieldValue[0] + fieldValue[4] + fieldValue[8]).equals(checkingCondition)
				|| (fieldValue[2] + fieldValue[4] + fieldValue[6]).equals(checkingCondition)) {

			if (currentUserMove.equals("X")) {
				resultPlayerX++;
			} else
				resultPlayerO++;
			resetGameField(0);
			setScore();
			JOptionPane.showMessageDialog(null, "Wygrana gracza " + currentUserMove, "Wygrana",
					JOptionPane.PLAIN_MESSAGE);
		}

		if (freeField == 0) {
			JOptionPane.showMessageDialog(null, "Nikt nie wygra³", "Remis", JOptionPane.PLAIN_MESSAGE);
			resetGameField(0);
		}

	}

	/* checking method for verify end of game */
	public void checkGameWinner() {
		if (resultPlayerX >= 5) {
			JOptionPane.showMessageDialog(null, "Gracz X wygra³ grê!", "Koniec gry", JOptionPane.PLAIN_MESSAGE);

			resetGameField(1);

		} else if (resultPlayerO >= 5) {
			JOptionPane.showMessageDialog(null, "Gracz O wygra³ grê!", "Koniec gry", JOptionPane.PLAIN_MESSAGE);
			resetGameField(1);

		}
	}

	/* method to set actual score */
	public void setScore() {
		lblScoreX.setText(Integer.toString(resultPlayerX));
		lblScoreO.setText(Integer.toString(resultPlayerO));

	}

	// reset type with @param - use: 0 - on the end of round - 1 to reset all game
	public void resetGameField(int resetType) {
		for (int i = 0; i < gameButtons.length; i++) {
			gameButtons[i].setActionCommand("");
			gameButtons[i].setText("");
			gameButtons[i].setBackground(null);
			gameButtons[i].setEnabled(true);
		}
		if (resetType == 1) {
			resultPlayerX = 0;
			resultPlayerO = 0;
			startingPlayer = 0;
			currentUserMove = "X";
			resultPlayerX = 0;
			resultPlayerO = 0;
			setScore();
		}
	}

}
