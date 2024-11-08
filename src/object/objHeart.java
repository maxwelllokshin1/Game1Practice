package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.gamePanel;

public class objHeart extends Entity{
	public objHeart(gamePanel gp)
	{
		super(gp);
		name = "heart";
		image = setup("/objects/heartFull");
		image2 = setup("/objects/heartHalf");
		image3 = setup("/objects/heartEmpty");
		
		//collision = true;
	}
}
