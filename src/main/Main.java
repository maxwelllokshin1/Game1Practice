package main;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args)
	{
		JFrame window = new JFrame();
		window.setResizable(false);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Darn Kitchen Thief");
		
		gamePanel gp = new gamePanel();
		window.add(gp);
		
		window.pack();
		window.setLocationRelativeTo(null);
		
		window.setVisible(true);
		
		gp.setUpGame();
		gp.startGameThread();
		
	}
}
