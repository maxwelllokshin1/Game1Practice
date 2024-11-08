package entity;

import java.util.Random;

import main.gamePanel;

public class npcBull extends Entity{
	public npcBull(gamePanel gp)
	{
		super(gp);
		direction = "idol";
		speed = 1;
		//digSpeed = 3;
		
		maxLife = 4;
		life = maxLife;
		
		getImage();
		setDialouge();
	}
	
	public void getImage()
	{
		
		up1 = setup("/npcBull/up1");
		up2 = setup("/npcBull/up2");
		down1 = setup("/npcBull/down1");
		down2 = setup("/npcBull/down2");
		left1 = setup("/npcBull/left1");
		left2 = setup("/npcBull/left2");
		right1 = setup("/npcBull/right1");
		right2 = setup("/npcBull/right2");
		idol1 = setup("/npcBull/idol1");
		idol2 = setup("/npcBull/idol2");
	}
	
	public void setDialouge()
	{
		dialouges[0] = "Moo moo\nMoo ...";
		dialouges[1] = "What do you want";
		dialouges[2] = "mooooooooooooo";
		dialouges[3] = "let me out, let me out my name is\njacob ive been trapped in this cow\ncostume for 7 years HELP ME, the sex\nwizard put me here";
	}
	
	public void setAction()
	{
		actionLockCounter++;
		if(actionLockCounter == 120)
		{
			//stepCounter += 1;
//			if(dialougeIndex == 0)
//			{
					//speed = 1;
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
//			System.out.println(stepCounter);	
//			if(stepCounter == 5)
//			{
////				for(int j = 0; j<gp.entityAmnt/2; j++)
////				{
////					gp.obj[j].worldX = gp.npc[j].worldX;
////					gp.obj[j].worldY = gp.npc[j].worldY;
////				}
//				
//				stepCounter = 0;
//			}
	 		//}//else
//			{
////	 			if(gp.player.worldX - worldX > 10)
////	 			{
////	 				worldX = gp.player.worldX - (gp.tileSize);
////	 			}
////	 			else if(gp.player.worldY - worldY > 10)
////	 			{
////	 				worldY = gp.player.worldY - (gp.tileSize);
////	 			}
////	 			else if(gp.player.worldX + worldX > 10)
////	 			{
////	 				worldX = gp.player.worldX + (gp.tileSize);
////	 			}
////	 			else if(gp.player.worldY + worldY > 10)
////	 			{
////	 				worldY = gp.player.worldY + (gp.tileSize);
////	 			}
//	 			speed = gp.player.speed;
//				if(gp.player.direction == "up")
//				{
//					direction = "up";
//				}
//				if(gp.player.direction == "down")
//				{
//					direction = "down";
//				}
//				if(gp.player.direction == "left")
//				{
//					direction = "left";
//				}
//				if(gp.player.direction == "right")
//				{
//					direction = "right";
//				}
//				if(gp.player.direction == "idol")
//				{
//					direction = "idol";
//				}
//			}
			actionLockCounter = 0;
		}
		
		
 	}
	
	public void speak()
	{
		super.speak();
	}
	
}
