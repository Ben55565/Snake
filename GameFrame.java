package game;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public GameFrame() {
		this.add(new GamePanel()); // create frame
		this.setTitle("Snake"); // give the window a name
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close program on exit
		this.setResizable(false); // set so it wont be possible to enlarge frame
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		requestFocus();
	}

}
