package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.gamePanel;
import main.utilityTool;

public class tileManager {
	gamePanel gp;
	public tile[] tile;
//	int tilesCounter= 0;
//	int tilesNum = 1;
	
	public int mapTileNum[][];
	
	public tileManager(gamePanel gp)
	{
		this.gp = gp;
		
		tile = new tile[50];
		
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/worldMap02.txt");
	}
	
//	public void getTileImage()
//	{
////		try {
//			
////			tile[0] = new tile();
////			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png")).getSubimage(16, 16, 16, 16);
////			
//////			BufferedImage scaledImage = new BufferedImage(gp.tileSize, gp.tileSize, tile[0].image.getType());
//////			Graphics2D g2 = scaledImage.createGraphics();
//////			g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
//////			tile[0].image = scaledImage;
////			
////			tile[1] = new tile();
////			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png")).getSubimage(16, 16, 16, 16);
////			tile[1].collision = true;
////			
////			tile[2] = new tile();
////			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png")).getSubimage(16, 16, 16, 16);
////			
////			tile[3] = new tile();
////			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rocky.png")).getSubimage(16, 16, 16, 16);
////			tile[3].collision = true;
////			
////			tile[4] = new tile();
////			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass1.png")).getSubimage(16, 16, 16, 16);
////			
////			tile[5] = new tile();
////			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree1.png")).getSubimage(16, 16, 16, 16);
////			tile[5].collision = true;
//			
////			tile[6] = new tile();
////			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree2.png")).getSubimage(16, 16, 16, 16);
////			tile[6].collision = true;
//			
//			setup(0, "dirt", false);
//			setup(1, "water", true);
//			setup(2, "stone", true);
//			setup(3, "rocky", true);
//			setup(4, "grass1", false);
//			setup(5, "tree1", true);
////
////		}catch(IOException e)
////		{
////			e.printStackTrace();
////		}
//	}
	
	public void getTileImage()
	{
		
		setup(0, "grass", false);
		setup(1, "grassTint", false);
		setup(2, "rock1", true);
		setup(3, "rock2", true);
		setup(4, "tree", true);
		setup(5, "water00", true);
		setup(6, "water01", true);
		setup(7, "water02", true);
		setup(8, "water10", true);
		setup(9, "water11", true);
		
		
		setup(10, "grass", false);
		setup(11, "grassTint", false);
		setup(12, "rock1", true);
		setup(13, "rock2", true);
		setup(14, "tree", true);
		setup(15, "water00", true);
		setup(16, "water01", true);
		setup(17, "water02", true);
		setup(18, "water10", true);
		setup(19, "water11", true);
		setup(20, "water12", true);
		setup(21, "water20", true);
		setup(22, "water21", true);
		setup(23, "water22", true);
	}
	
	public void setup(int index, String imageName, boolean collision)
	{
		utilityTool uTool = new utilityTool();
		try {
			tile[index] = new tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
//	public void update()
//	{
//		tilesCounter++;
//		if(tilesCounter > 12)
//		{
//			if(tilesNum == 1)
//			{
//				tilesNum = 2;
//			}else if(tilesNum == 2)
//			{
//				tilesNum = 1;
//			}
//			tilesCounter = 0;
//		}
//	}
	
	public void draw(Graphics2D g2)
	{
//		g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
//		g2.drawImage(tile[1].image, 48, 0, gp.tileSize, gp.tileSize, null);
//		g2.drawImage(tile[2].image, 96, 0, gp.tileSize, gp.tileSize, null);
		
		int worldCol = 0, worldRow= 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow)
		{
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) { // draws tiles that are around the player
				
//				if(tilesNum == 1)
//				{
//					tileNum = 5;
//				}
//				if(tilesNum == 2)
//				{
//					tileNum = 6;
//				}
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			worldCol++;
			
			if(worldCol == gp.maxWorldCol)
			{
				worldCol = 0;
				worldRow++;
			}
		}
	}
	
	public void loadMap(String filePath)
	{
		try
		{
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0, row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow)
			{
				String line = br.readLine();
				while(col < gp.maxWorldCol)
				{
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol)
				{
					col = 0;
					row++;
				}
			}
			br.close();
		}catch(Exception e)
		{
			//e.printStackTrace();
		}
	}
	
	
}
