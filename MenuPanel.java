import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class MenuPanel extends JPanel implements ActionListener {
	private JLabel title;
	private JButton btnGame;
	private JButton btnInstructions;
	private JButton btnQuit;
	private BufferedImage menuGraphic = null;
	private Timer t;
	private Animation menuAnimation;

	public MenuPanel() {
		setLayout(null);
		menuAnimation = new Animation("Images/Player/Idle/Idle_", 32);
		t = new Timer(120, this);

		menuAnimation.load();

		t.start();

		// Title
		title = new JLabel("SMASH");
		title.setBounds(655, 200, 300, 70);
		title.setFont(new Font("Arial", Font.TRUETYPE_FONT, 80));

		// Play Button
		btnGame = new JButton("Play");
		btnGame.addActionListener(this);
		btnGame.setBounds(650, 300, 300, 70);

		// Instructions Button
		btnInstructions = new JButton("Instructions");
		btnInstructions.addActionListener(this);
		btnInstructions.setBounds(650, 400, 300, 70);

		// Quit Button
		btnQuit = new JButton("Quit");
		btnQuit.addActionListener(this);
		btnQuit.setBounds(650, 500, 300, 70);

		add(title);
		add(btnGame);
		add(btnInstructions);
		add(btnQuit);
		setBackground(new Color(255, 255, 255));
		// Set Background
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnGame) {
			Frame.flipToCard("Game");
		} else if (e.getSource() == btnInstructions) {
			Frame.flipToCard("Instructions");
		} else if (e.getSource() == t) {
			menuGraphic = menuAnimation.getNextFrame();
			repaint();
		} else {
			System.exit(0);
		}
	}

	public void paintComponent(Graphics g) {
		// Improve Resolution
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 700, 1200, 100);
		g.drawImage(menuGraphic, -250, -200, 1196, 937, null);
	}
}
