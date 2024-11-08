package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.gamePanel;

public class objCookie extends Entity{
	public objCookie(gamePanel gp)
	{
		super(gp);
		name = "cookie";
		
		idol1 = setup("/objects/cookie1");
		idol2 = setup("/objects/cookie2");
		
		collision = true;
	}
}
