package game;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	static final int SCREEN_WIDTH = 800;
	static final int SCREEN_HEIGHT = 600;
	static final int OBJECTS_SIZE = 25; // size of each object on the screen
	static final int GAME_OBJECTS = (SCREEN_WIDTH * SCREEN_HEIGHT) / OBJECTS_SIZE; // how many objects can be on screen
	static final int DELAY = 100; // delay for the movement of the game
	final int x[] = new int[OBJECTS_SIZE]; // x coordinates of the snake
	final int y[] = new int[OBJECTS_SIZE]; // y coordinates of the snake
	int bodyParts = 6; // start number of snake parts
	int applesEaten = 0;
	int appleX, appleY;
	char direction = 'R'; // R = right, L = left, U = up, D = down
	boolean running = false;
	Timer timer;
	Random random;

	public GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new keyTracker());
		startGame();

	}

	public void startGame() {
		newApple(); // create new apple on screen
		running = true; // set that the game is running
		timer = new Timer(DELAY, this); // set the running delay of the game
		timer.start();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		if (running) {
			//			for (int i = 0; i < SCREEN_HEIGHT / OBJECTS_SIZE; i++) {
			//				g.drawLine(i * OBJECTS_SIZE, 0, i * OBJECTS_SIZE, SCREEN_HEIGHT);
			//				g.drawLine(0, i * OBJECTS_SIZE, SCREEN_WIDTH, i * OBJECTS_SIZE);
			//			}
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, OBJECTS_SIZE, OBJECTS_SIZE);

			for (int i = 0; i < bodyParts; i++) {
				if (i == 0) { // if its the head of the snake
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], OBJECTS_SIZE, OBJECTS_SIZE);

				} else {
					g.setColor(new Color(45, 180 + random.nextInt(75), 0)); // paint the body in an different shade of green

					g.fillRect(x[i], y[i], OBJECTS_SIZE, OBJECTS_SIZE);
				}
				g.setColor(Color.red);
				g.setFont(new Font("Ink free", Font.BOLD, 40));
				FontMetrics metrics = getFontMetrics(g.getFont());
				g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
						g.getFont().getSize());

			}
		} else {
			gameOver(g);
		}
	}

	public void newApple() {
		appleX = random.nextInt((int) SCREEN_WIDTH / OBJECTS_SIZE) * OBJECTS_SIZE; // set the places it can spawn in, and multiply for placing it evenly in each tile
		appleY = random.nextInt((int) SCREEN_HEIGHT / OBJECTS_SIZE) * OBJECTS_SIZE;
	}

	public void move() {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		switch (direction) {
		case 'U':
			y[0] = y[0] - OBJECTS_SIZE;
			break;
		case 'D':
			y[0] = y[0] + OBJECTS_SIZE;
			break;
		case 'L':
			x[0] = x[0] - OBJECTS_SIZE;
			break;
		case 'R':
			x[0] = x[0] + OBJECTS_SIZE;
			break;

		}

	}

	public void checkApple() {
		if ((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}

	public void checkCollisions() {
		// checks for head collision with the body
		for (int i = bodyParts; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		// check for head collision with left border
		if (x[0] < 0) {
			running = false;
		}
		// check for head collision with right border
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
		// check for head collision with top border
		if (y[0] < 0) {
			running = false;
		}
		// check for head collision with bottom border
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		if (!running) {
			timer.stop();
		}
	}

	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink free", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2,
				g.getFont().getSize());
		g.setColor(Color.red);
		g.setFont(new Font("Ink free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over!", (SCREEN_WIDTH - metrics2.stringWidth("Game Over!")) / 2, SCREEN_HEIGHT / 2);
		g.setColor(Color.white);
		g.setFont(new Font("Ink free", Font.BOLD, 40));
		FontMetrics metrics3 = getFontMetrics(g.getFont());
		g.drawString("Restart in: not yet an option :(", (SCREEN_WIDTH - metrics3.stringWidth("Restart in: not yet an option :(")) / 2, SCREEN_HEIGHT / 2 + 100);

	}

	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}

	public class keyTracker extends KeyAdapter {

		public void keyTyped(KeyEvent e) {

		}

		public void keyReleased(KeyEvent e) {

		}

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';

				}
				break;
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';

				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';

				}
				break;
			}
		}
	}

}
