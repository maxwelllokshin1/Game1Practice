package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.*;

public class Player extends Entity{
	
	keyHandler keyH;
	
	public final int screenX, screenY;
	
	public Player(gamePanel gp, keyHandler keyH)
	{
		super(gp);
		this.keyH = keyH;
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		

		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 20;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 20;
		solidArea.height = 20;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues()
	{
		Random rand = new Random();
		int newX;
		int newY;
		do
		{
			newX = rand.nextInt(100);
			newY = rand.nextInt(100);

		}while(gp.tileM.tile[gp.tileM.mapTileNum[newX][newY]].collision == true);
		worldX = gp.tileSize * newX;
		worldY = gp.tileSize * newY;
		speed = 4;
		digSpeed = 3;
		direction = "idol";
		
		// PLAYER STATUS
		maxLife = 6;
		life = maxLife;
	}
	
	public void getPlayerImage()
	{	
		up1 = setup("/player/up1");
		up2 = setup("/player/up2");
		down1 = setup("/player/down1");
		down2 = setup("/player/down2");
		left1 = setup("/player/left1");
		left2 = setup("/player/left2");
		right1 = setup("/player/right1");
		right2 = setup("/player/right2");
		idol1 = setup("/player/idol1");
		idol2 = setup("/player/idol2");
	}
	
	
	public void update()
	{
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true)
		{
			if(keyH.upPressed == true)
			{
				direction = "up";
			} if(keyH.downPressed == true)
			{
				direction = "down";
			} if(keyH.leftPressed == true)
			{
				direction = "left";
			} if(keyH.rightPressed == true)
			{
				direction = "right";
			}
			
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			//CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// CHECK EVENT
			gp.eHandler.checkEvent();
			
			if(life == 0)
			{
				System.exit(0);
			}
			
			if(collisionOn == false)
			{
				if(keyH.upPressed == true && keyH.leftPressed == true)
				{
					worldX -= digSpeed;
					worldY -= digSpeed;
				}else if(keyH.upPressed == true && keyH.rightPressed == true)
				{
					worldX += digSpeed;
					worldY -= digSpeed;
				}else if(keyH.downPressed == true && keyH.leftPressed == true)
				{
					worldX -= digSpeed;
					worldY += digSpeed;
				}else if(keyH.downPressed == true && keyH.rightPressed == true)
				{
					worldX += digSpeed;
					worldY += digSpeed;
				}else 
				{
					switch(direction)
					{
					case "up": worldY -= speed; break;
					case "down": worldY += speed; break;
					case "left": worldX -= speed; break;
					case "right": worldX += speed; break;
					}
				}
			}
				
			spriteCounter++;
			if(spriteCounter > 12)
			{
				if(spriteNum == 1)
				{
					spriteNum = 2;
				}else if(spriteNum == 2)
				{
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}else 
		{
			direction = "idol";
			spriteCounter++;
			if(spriteCounter > 12)
			{
				if(spriteNum == 1)
				{
					spriteNum = 2;
				}else if(spriteNum == 2)
				{
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
	}
	
	public void pickUpObject(int i)
	{
		if(i != 999)
		{
		}
		
	}
	
	public void interactNPC(int i)
	{
		if(i != 999)
		{
//			if(gp.keyH.enterPressed == true)
//			{
				gp.gameState = gp.dialougeState;
				gp.npc[i].speak();
//			}
//			gp.keyH.enterPressed = false;
		}
	}
	
	public void draw(Graphics2D g2)
	{
		BufferedImage image = null;
//		g2.setColor(Color.white);
//		g2.fillOval(screenX ,screenY, gp.tileSize, gp.tileSize);
//		g2.dispose();
		
		switch(direction)
		{
			case "up":
				if(spriteNum == 1)
				{
					image = up1;
				}
				if(spriteNum == 2)
				{
					image = up2;
				}
				break;
			case "down":
				if(spriteNum == 1)
				{
					image = down1;
				}
				if(spriteNum == 2)
				{
					image = down2;
				}
				break;
			case "left":
				if(spriteNum == 1)
				{
					image = left1;
				}
				if(spriteNum == 2)
				{
					image = left2;
				}
				break;
			case "right":
				if(spriteNum == 1)
				{
					image = right1;
				}
				if(spriteNum == 2)
				{
					image = right2;
				}
				break;
			case "idol":
				if(spriteNum == 1)
				{
					image = idol2;
				}
				if(spriteNum == 2)
				{
					image = idol2;
				}
				break;
		}
		
		g2.drawImage(image,screenX, screenY, null);
		
		
	}
}
