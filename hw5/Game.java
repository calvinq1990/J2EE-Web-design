/**
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * Course: 08600
 * 
 * Description:
 * 		There are 3 helper inner class: timeCount, TimeThread and ButtonThread.
 * The first one is used to handle the time, the second one is to control the 
 * time,calculate the time left ; The second one is used to control the 
 * buttons when to pop up and pop down.
 * 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Game implements ActionListener {
	private final static int buttonNum = 50;
	private JButton[] buttons;
	private JTextArea scoreArea, timeLeftArea;
	private JButton startButton;
	private static int score = 0;
	private timeCount time = new timeCount();

	//built the GUI in the constructor
	public Game() {
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 14);

		JFrame frame = new JFrame("Whack-a-Mole");
		frame.setSize(650, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel buttonPane = new JPanel();
		JPanel controlPane = new JPanel();

		startButton = new JButton("start");
		controlPane.add(startButton);
		startButton.addActionListener(this);

		JLabel timeLeftLabel = new JLabel("Time Left");
		controlPane.add(timeLeftLabel);
		timeLeftArea = new JTextArea(1, 6);
		timeLeftArea.setEditable(true);
		controlPane.add(timeLeftArea);

		JLabel scoreLabel = new JLabel("Score");
		controlPane.add(scoreLabel);
		scoreArea = new JTextArea(1, 6);
		scoreArea.setEditable(true);
		controlPane.add(scoreArea);

		buttons = new JButton[buttonNum];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton("   ");
			buttons[i].setFont(font);
			buttons[i].setBackground(Color.LIGHT_GRAY);
			buttons[i].setOpaque(true);
			buttonPane.add(buttons[i]);
			buttons[i].addActionListener(this);
		}

		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.add(controlPane, BorderLayout.NORTH);
		pane.add(buttonPane, BorderLayout.CENTER);
		frame.setContentPane(pane);
		frame.setVisible(true);

	}
	/**
	 * In this class, time will be wrapped here,
	 * Also time will decrease after each second.
	 * The method getTime can be used when time is 
	 * needed to shown in the board.
	 * 
	 * Also the key to overcome the condition race is
	 * to synchronized the timeCount class when it  is 
	 * read or write. 
	 * 
	 */
	
	private class timeCount {
		private int time;

		private void start() {
			time = 20;
		}

		private int reCount() {
			time--;
			return time;
		}

		private int getTime() {
			return time;
		}
	}

	public static void main(String[] args) {
		new Game();
	}
	/**
	 * 
	 * each thread control each button to pop up or pop 
	 * down between different time interval
	 */
	private static class ButtonThread extends Thread {
		private JButton button;
		private long mySleepTime;
		private Color myColor = Color.ORANGE;
		private String myText = "up";
		private Random random = new Random();
		private timeCount time;
		private int timeLeft;

		public ButtonThread(JButton buttons, timeCount timeCount) {
			this.button = buttons;
			this.time = timeCount;

		}

		public void run() {
			mySleepTime = (long) (1000 * (15 * random.nextFloat()));
			try {
				Thread.sleep(mySleepTime);
			} catch (InterruptedException e) {
				throw new AssertionError(e);
			}
			synchronized (time) {
				timeLeft = time.getTime();
			}

			while (timeLeft > 0) {
				button.setText(myText);
				button.setBackground(myColor);

				// pop up for 1 to 4 seconds
				mySleepTime = (long) (1000 * (Math.random() * (4 - 0.5) + 0.5));
				try {
					Thread.sleep(mySleepTime);
				} catch (InterruptedException e) {
					throw new AssertionError(e);
				}

				// pop down and sleep for 4s
				button.setText(" ");
				button.setBackground(Color.LIGHT_GRAY);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					throw new AssertionError(e);
				}
				synchronized (time) {
					timeLeft = time.getTime();
				}
			}
		}
	}
	/**
	 * 
	 * 1.time thread is used to start the mole threads, each mole threads
	 * is specifically to each button.
	 * 2. start button should be set to be unavailable before game is over
	 *
	 */
	private static class TimeThread extends Thread {
		private JTextArea timeLeftArea;
		private JButton startButton;
		private JButton[] allButtons;
		private ButtonThread[] threads = new ButtonThread[buttonNum];
		private timeCount time;
		private int timeLeft;

		public TimeThread(JTextArea area, JButton button, JButton[] buttons,
				timeCount time) {
			this.timeLeftArea = area;
			this.startButton = button;
			this.allButtons = buttons;
			this.time = time;
		}

		public void run() {

			for (int i = 0; i < allButtons.length; i++) {
				threads[i] = new ButtonThread(allButtons[i], time);
				threads[i].start();
			}

			startButton.setEnabled(false);
			time.start();
			timeLeft = time.getTime();

			while (0 <= timeLeft) {
				timeLeftArea.setText(Integer.toString(timeLeft));
				try {
					Thread.sleep(1000);

				} catch (InterruptedException e) {
					// Should not happen
					throw new AssertionError(e);
				}
				synchronized (time) {
					timeLeft = time.reCount();
				}
			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			startButton.setEnabled(true);
		}
	}

	/**
	 * This function mainly implement 2 button actions: 
	 * 1. when start button has been press,update the score
	 *  and start a time thread 
	 * 2. for each mole button, hit when the mole is up, score
	 *  will +1 and backgroud color will change to gray from 
	 *   orange.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == startButton) {
			score = 0;
			scoreArea.setText(Integer.toString(score));
			Thread timeThread = new TimeThread(timeLeftArea, startButton,
					buttons, time);
			timeThread.start();
		}
		for (int i = 0; i < buttonNum; i++) {
			synchronized (time) {
				if (e.getSource() == buttons[i]
						&& buttons[i].getBackground() == Color.ORANGE
						&& time.getTime() > 0) {
					buttons[i].setText(":-)");
					score++;
					scoreArea.setText(Integer.toString(score));
					buttons[i].setBackground(Color.gray);
				}
			}
		}

	}
}
