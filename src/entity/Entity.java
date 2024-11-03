package entity;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Entity {
	public int worldX, worldY;
	public int speed;
	public int digSpeed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, idol1, idol2;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
}
