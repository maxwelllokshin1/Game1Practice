package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import entity.Entity;
import entity.Player;
import tile.tileManager;

//import main.keyHandler;

public class gamePanel extends JPanel implements Runnable{
	
	final int originalTileSize = 16; // 16x16 tile   DEFAULT SIZE OF ANY TILE/CHARACTER
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 15; // ratio is 4x3
	
	public final int screenWidth = tileSize * maxScreenCol;	// 768 pixels
	public final int screenHeight = tileSize * maxScreenRow;	// 576 pixels
	
	// WORLD SETTINGS
	public final int maxWorldCol = 100;
	public final int maxWorldRow = 100;
	int FPS = 60;
	public int pickedUp = 0;
	
	
	public tileManager tileM = new tileManager(this);
	
	public keyHandler keyH = new keyHandler(this);
	
	public collisionChecker cChecker = new collisionChecker(this);
	public assetSetter aSet = new assetSetter(this);
	public UI ui = new UI(this);
	public eventHandler eHandler = new eventHandler(this);
	Thread gameThread;
	
	public Player player = new Player(this, keyH);
	public Entity obj[] = new Entity[50];
	public Entity npc[] = new Entity[50];
	ArrayList<Entity> entityList = new ArrayList<>();
	
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialougeState = 3;
	
	// Mouse Dragging state
    private boolean isDragging = false;
    private Entity draggingNpc = null;
    private int npcID = 0;
    
    public int secondsHeld = 0;
    public int countCowsTimer = 0;

	
	
	public gamePanel()
	{
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		// Add mouse listeners for drag functionality
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	gamePanel.this.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                gamePanel.this.mouseReleased();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
            	gamePanel.this.mouseDragged(e);
            }
        });
	}

	public void setUpGame()
	{
		aSet.setObject();
		aSet.setNPC();
		gameState = titleState;
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
		if(gameState == playState)
		{
			//PLAYER
			player.update();
			
			//NPC
			for(int i = 0; i<npc.length; i++)
			{
				if(npc[i] != null)
				{
					if(pickedUp == 0)
					{
						npc[i].update();
					}else
					{
						if(i != npcID)
						{
							npc[i].update();
						}else if(i == npcID && npc[i].stopDrawing == 1)
						{
							npc[i] = null;
						}
					}
				}
			}
			for(int i = 0; i<obj.length; i++)
			{
				if(obj[i] != null)
				{
					obj[i].update();
				}
			}
		}
		if(gameState == pauseState)
		{
			//Nothing for now
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
		
		// TITLE SCREEN
		if(gameState == titleState)
		{
			ui.draw(g2);
			
		}else {
			// Tile
			tileM.draw(g2); // layer 1 background
			
			// ADD ENTITIES TO LIST
			entityList.add(player);
			
			for(int i = 0; i<npc.length; i++)
			{
				if(npc[i] != null)
				{
					entityList.add(npc[i]);
				}
			}
			
			for(int i = 0; i<obj.length; i++)
			{
				if(obj[i] != null)
				{
					entityList.add(obj[i]);
				}
			}
			
			// SORT
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity o1, Entity o2) {
					int result = Integer.compare(o1.worldY, o2.worldY);
					return result;
				}
				
			});
			
			// DRAW ENTITIES
			
			for(int i = 0; i<entityList.size(); i++)
			{
				entityList.get(i).draw(g2);
			}
			
			// EMPTY ENTITY LIST
			for(int i = 0; i<entityList.size(); i++)
			{
				entityList.remove(i);
			}
			
			
			ui.draw(g2);
		}
		
		
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
	
	// Mouse Pressed - Initialize dragging
    private void mousePressed(MouseEvent e) {
        // Get the mouse coordinates
        int mouseX = ((e.getX()- player.screenX) + player.worldX)/tileSize;
        int mouseY = ((e.getY()- player.screenY) + player.worldY)/tileSize;
        //System.out.println(mouseX + " " + mouseY); 
        //System.out.println(player.worldX/tileSize + " " + player.worldY/tileSize);

        // Check if mouse is inside any NPC's bounds
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                // Convert mouse position to world coordinates (taking scaling into account)
                int npcX = npc[i].worldX/tileSize;
                int npcY = npc[i].worldY/tileSize;

                // Assuming NPC has a bounding area (e.g., a rectangle) to detect dragging
                //System.out.println(mouseX + " " + mouseY + " == " + npcX + " " + npcY);
                if ((mouseX == npcX && mouseY == npcY) 
                || (mouseX-1 == npcX && mouseY == npcY)
                || (mouseX+2 == npcX && mouseY == npcY)
                || (mouseX == npcX && mouseY-1 == npcY)
                || (mouseX == npcX && mouseY+2 == npcY)) {
                	
                	isDragging = true;
                    draggingNpc = npc[i];
                    npcID = i;
                    System.out.println("Dragging NPC: " + npcID);

                    // Calculate the mouse offset relative to NPC's position
                    pickedUp = 1;
                    break;
                }
            }
        }
    }

    // Mouse Dragged - Update the position of the dragged NPC
    private void mouseDragged(MouseEvent e) {
        if (isDragging && draggingNpc != null) {
        	secondsHeld++;
        	//System.out.println(secondsHeld);
        	if(secondsHeld == 60)
        	{
        		countCowsTimer++;
        		if(countCowsTimer == 3)
        		{
        			draggingNpc.stopDrawing = 1;
                    //System.out.println(draggingNpc.worldX/tileSize + " " + draggingNpc.worldY/tileSize);
        			countCowsTimer = 0;
        		}
        		//System.out.println(countCowsTimer);
                secondsHeld = 0;
        	}
        	
            // Calculate the new position of the NPC relative to the mouse movement
        	int mouseX = (e.getX()- player.screenX) + player.worldX;
            int mouseY = (e.getY()- player.screenY) + player.worldY;

            // Ensure NPC doesn't go out of bounds (screen boundaries or tile boundaries)
            int maxX = screenWidth - draggingNpc.solidArea.width;
            int maxY = screenHeight - draggingNpc.solidArea.height;

            if (maxX < 0) maxX = 0;
            if (maxY < 0) maxY = 0;

            // Update the position of the dragged NPC
            draggingNpc.worldX = mouseX;
            draggingNpc.worldY = mouseY;
            
            repaint(); // Redraw the screen to reflect the new NPC position
        }
    }

    // Mouse Released - Stop dragging
    private void mouseReleased() {
        isDragging = false; // Stop dragging when the mouse is released
        draggingNpc = null; // Clear the dragged NPC
        pickedUp = 0;
        countCowsTimer = 0;
        secondsHeld = 0;
    }
}
