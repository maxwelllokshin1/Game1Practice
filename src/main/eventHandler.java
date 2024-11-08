package main;

import java.awt.Rectangle;
import java.util.Random;

public class eventHandler {
	gamePanel gp;
	eventRect eventRect[][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	public eventHandler(gamePanel gp)
	{
		this.gp = gp;
		eventRect = new eventRect[gp.maxWorldCol][gp.maxWorldCol];
		
		int col = 0;
		int row = 0;
		while(col < gp.maxWorldCol && row < gp.maxWorldRow)
		{
			eventRect[col][row] = new eventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
			col++;
			if(col == gp.maxWorldCol)
			{
				col = 0;
				row++;
			}
		}
		
	}
	
	public void checkEvent()
	{
		//CHECK IF PLAYER IS MORE THAN LAST TILE AWAY FROM EVENT
		int distanceX = Math.abs(gp.player.worldX - previousEventX);
		int distanceY = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(distanceX, distanceY);
		if(distance>gp.tileSize)
		{
			canTouchEvent = true;
		}
		
//		if(canTouchEvent == true)
//		{
//			Random rand = new Random();
//			int newX;
//			int newY;
//			for(int i =0; i<100; i++)
//			{
//				do
//				{
//					newX = rand.nextInt(100);
//					newY = rand.nextInt(100);
//
//				}while(gp.tileM.tile[gp.tileM.mapTileNum[newX][newY]].collision == true);
//				if(hit(newX,newY,"any") == true){ 
//					mineField(newX,newY,gp.dialougeState);
//				}
//			}
//		}
		
//		for(int i =0; i<gp.entityAmnt; i++)
//		{
//			if(gp.cChecker.checkEntity(gp.player, gp.npc) == i)
//			{
//				System.out.println("here");
//				healingCow(gp.dialougeState);
//			}
//		}
	}
	
	public boolean hit(int col, int row, String reqDirection)
	{
		boolean hit = false;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;
		
		if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false)
		{
			if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any"))
			{
				hit = true;
				
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
				
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		
		return hit;
	}
	
	public void mineField(int col, int row,int gameState)
	{
		gp.gameState = gameState;
		gp.ui.currentDialouge = "HIT";
		gp.player.life -= 1;
		eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}
	
	public void healingCow(int col, int row,int gameState)
	{
//		if(gp.keyH.enterPressed == true)
//		{
			gp.gameState = gameState;
			gp.ui.currentDialouge = "Sucked life out of Mr cow";
			gp.player.life = gp.player.maxLife;
		//}
	}
	
}
