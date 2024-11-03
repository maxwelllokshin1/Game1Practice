package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.*;

public class Player extends Entity{
	gamePanel gp;
	
	keyHandler keyH;
	
	public final int screenX, screenY;
	public int hasCookie = 0;
	public int allCookies = 0;
	
	public Player(gamePanel gp, keyHandler keyH)
	{
		this.gp = gp;
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
		
		worldX = gp.tileSize * 24;
		worldY = gp.tileSize * 24;
		speed = 4;
		digSpeed = 3;
		direction = "idol";
	}
	
	public void getPlayerImage()
	{
//		try {
//			up1 = ImageIO.read(getClass().getResourceAsStream("/player/monsterUp1.png"));
//			up2 = ImageIO.read(getClass().getResourceAsStream("/player/monsterUp2.png"));
//			down1 = ImageIO.read(getClass().getResourceAsStream("/player/monsterDown1.png"));
//			down2 = ImageIO.read(getClass().getResourceAsStream("/player/monsterDown2.png"));
//			left1 = ImageIO.read(getClass().getResourceAsStream("/player/monsterLeft1.png"));
//			left2 = ImageIO.read(getClass().getResourceAsStream("/player/monsterLeft2.png"));
//			right1 = ImageIO.read(getClass().getResourceAsStream("/player/monsterRight1.png"));
//			right2 = ImageIO.read(getClass().getResourceAsStream("/player/monsterRight2.png"));
//			idol1 = ImageIO.read(getClass().getResourceAsStream("/player/idol1.png"));
//			idol2 = ImageIO.read(getClass().getResourceAsStream("/player/idol2.png"));
//		}catch(IOException e)
//		{
//			e.printStackTrace();
//		}
		
		up1 = setup("monsterUp1");
		up2 = setup("monsterUp2");
		down1 = setup("monsterDown1");
		down2 = setup("monsterDown2");
		left1 = setup("monsterLeft1");
		left2 = setup("monsterLeft2");
		right1 = setup("monsterRight1");
		right2 = setup("monsterRight2");
		idol1 = setup("idol1");
		idol2 = setup("idol2");
	}
	
	public BufferedImage setup(String imageName)
	{
		utilityTool uTool = new utilityTool();
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName+".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return image;
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
			String objectName = gp.obj[i].name;
			switch(objectName)
			{
			case "cookie":
				gp.obj[i] = null;
				speed += 4;
				digSpeed += 3;
				//hasCookie++;
				allCookies++;
				gp.ui.showMessage("COOKIE");
				break;
			}
		}
		
		if(allCookies == 6)
		{
			System.out.println("ALL TAKEN");
			gp.ui.gameFinished = true;
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
					image = idol1;
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
