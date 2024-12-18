package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.gamePanel;
import main.utilityTool;

public class superObject {
	public BufferedImage image, imageUp, imageDown;
	public String name;
	public boolean collision = false;
	
	public int objCounter = 0;
	public int objNum = 1;
	
	public int worldX, worldY;
	
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	
	utilityTool uTool = new utilityTool();
	
	public void update()
	{
		objCounter++;
		if(objCounter > 12)
		{
			if(objNum == 1)
			{
				objNum = 2;
			}else if(objNum == 2)
			{
				objNum = 1;
			}
			objCounter = 0;
		}
	}
	
	public void draw(Graphics2D g2, gamePanel gp)
	{
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
		   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
		   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
		   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
		{
			if(objNum == 1)
			{
				image = imageUp;
			}
			if(objNum == 2)
			{
				image = imageDown;
			}
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}
}
