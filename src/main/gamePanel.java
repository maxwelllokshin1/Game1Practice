package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.superObject;
import tile.tileManager;

//import main.keyHandler;

public class gamePanel extends JPanel implements Runnable{
	
	final int originalTileSize = 16; // 16x16 tile   DEFAULT SIZE OF ANY TILE/CHARACTER
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12; // ratio is 4x3
	
	public final int screenWidth = tileSize * maxScreenCol;	// 768 pixels
	public final int screenHeight = tileSize * maxScreenRow;	// 576 pixels
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	int FPS = 60;
	
	tileManager tileM = new tileManager(this);
	
	keyHandler keyH = new keyHandler();
	
	public collisionChecker cChecker = new collisionChecker(this);
	public assetSetter aSet = new assetSetter(this);
	public UI ui = new UI(this);
	
	Thread gameThread;
	
	public Player player = new Player(this, keyH);
	public superObject obj[] = new superObject[10];
	
	
	public gamePanel()
	{
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}

	public void setUpGame()
	{
		aSet.setObject();
	}
	public void startGameThread()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null)
		{
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			
			timer += (currentTime-lastTime);
			lastTime = currentTime;
			
			if(delta >= 1)
			{
				update();
				repaint();
				delta--;
				drawCount++;
				//System.out.println("drawCount: " + drawCount);
			}
			if(timer >= 1000000000)
			{
				drawCount = 0;
				timer = 0;
			}
		}
		
	}
	
	public void update()
	{
		player.update();
		for(int i = 0; i<obj.length; i++)
		{
			if(obj[i] != null)
			{
				obj[i].update();
			}
		}
		
		
	}
	
	public void paintComponent(Graphics g) // standard method to draw panels
	{
		super.paintComponent(g); // calling from jPanel
		Graphics2D g2 = (Graphics2D)g; // changing graphics to graphics 2d class
		
		//DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime == true)
		{
			drawStart = System.nanoTime();
		}
		
		// Tile
		tileM.draw(g2); // layer 1 background
		
		// Object
		for(int i = 0; i<obj.length; i++)
		{
			if(obj[i] != null)
			{
				obj[i].draw(g2, this);
			}
		}
		
		// Player
		player.draw(g2); // layer 2 character
		ui.draw(g2);
		
		if(keyH.checkDrawTime == true)
		{
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, 10, 400);
			System.out.println("Draw Time: " + passed);
		}
		
		g2.dispose(); // good practice to save memory
	}
}
