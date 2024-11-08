package entity;

import java.util.Random;

import main.gamePanel;

public class npcCow extends Entity{
	public npcCow(gamePanel gp)
	{
		super(gp);
		direction = "idol";
		speed = 1;
		digSpeed = 3;
		
		maxLife = 2;
		life = maxLife;
		
		getImage();
		setDialouge();
	}
	
	public void getImage()
	{
		
		up1 = setup("/npcCow/up1");
		up2 = setup("/npcCow/up2");
		down1 = setup("/npcCow/down1");
		down2 = setup("/npcCow/down2");
		left1 = setup("/npcCow/left1");
		left2 = setup("/npcCow/left2");
		right1 = setup("/npcCow/right1");
		right2 = setup("/npcCow/right2");
		idol1 = setup("/npcCow/idol1");
		idol2 = setup("/npcCow/idol2");
	}
	
	public void setDialouge()
	{
		dialouges[0] = "raaaa";
		dialouges[1] = "I took a\nmassive poop\nin the bushes";
		dialouges[2] = "moo";
		dialouges[3] = "moo???";
		dialouges[4] = "Milk Me ";
	}
	
	public void setAction()
	{
		actionLockCounter++;
		if(actionLockCounter == 120)
		{
					Random random = new Random();
					int i = random.nextInt(125) + 1; // pick random number 1 to 100
					
					if(i <= 25)
					{
						direction = "up";
					}
					if(i > 25 && i<=50)
					{
						direction = "down";
					}
					if(i > 50 && i<=75)
					{
						direction = "left";
					}
					if(i > 75 && i<=100)
					{
						direction = "right";
					}
					if(i > 100 && i<=125)
					{
						direction = "idol";
					}
			actionLockCounter = 0;
		}
		
 	}
	
	public void speak()
	{
		super.speak();
	}
	
}
