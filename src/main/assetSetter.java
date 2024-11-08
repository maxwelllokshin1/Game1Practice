package main;

import java.util.Random;

import entity.npcBull;
import entity.npcCow;
import object.objCookie;
import object.objPoop;

public class assetSetter {
	gamePanel gp;
	public assetSetter(gamePanel gp)
	{
		this.gp = gp;
	}
	
	public void setObject()
	{
		Random rand = new Random();
		int newX;
		int newY;
		
	
		for(int i =0; i<gp.obj.length; i++)
		{
			do
			{
				newX = rand.nextInt(100);
				newY = rand.nextInt(100);

			}while(gp.tileM.tile[gp.tileM.mapTileNum[newX][newY]].collision == true  
					|| (newX == gp.player.worldX && newY == gp.player.worldY));
			
			gp.obj[i] = new objPoop(gp);
			gp.obj[i].worldX = gp.tileSize*newX;
			gp.obj[i].worldY = gp.tileSize*newY;
		}
	}
	
	public void setNPC()
	{
		Random rand = new Random();
		int newX;
		int newY;
		
	
		for(int i =0; i<gp.npc.length; i++)
		{
			do
			{
				newX = rand.nextInt(100);
				newY = rand.nextInt(100);

			}while(gp.tileM.tile[gp.tileM.mapTileNum[newX][newY]].collision == true  
					|| (newX == gp.player.worldX && newY == gp.player.worldY));
			
			int npcChoice = rand.nextInt(2);
			if(npcChoice == 0)
			{
				gp.npc[i] = new npcBull(gp);
			}else
			{
				gp.npc[i] = new npcCow(gp);
			}
			gp.npc[i].worldX = gp.tileSize*newX;
			gp.npc[i].worldY = gp.tileSize*newY;
		}
	}
}
