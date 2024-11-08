package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.gamePanel;

public class objPoop extends Entity{
	public objPoop(gamePanel gp)
	{
		super(gp);
		name = "poop";
		
		idol1 = setup("/objects/poop");
		
		collision = true;
		
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 10;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		
	}
}