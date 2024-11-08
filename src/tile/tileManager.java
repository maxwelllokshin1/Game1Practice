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
		loadMap("/maps/worldMap05.txt");
	}
	

	
	public void getTileImage()
	{
		
		setup(0, "00", false);
		setup(1, "01", false);
		setup(2, "02", true);
		setup(3, "03", true);
		setup(4, "04", true);
		setup(5, "05", true);
		setup(6, "06", true);
		setup(7, "07", true);
		setup(8, "08", true);
		setup(9, "09", true);
		
		
		setup(10, "grass", false);
		setup(11, "grassTint", false);
		setup(12, "rock1", true);
		setup(13, "rock2", true);
		setup(14, "tree", true);
		setup(15, "water00", true);
		setup(16, "water01", true);
		setup(17, "water02", true);
		setup(18, "water03", true);
		setup(19, "water04", true);
		setup(20, "water10", true);
		setup(21, "water11", true);
		setup(22, "water12", true);
		setup(23, "water13", true);
		setup(24, "water14", true);
		setup(25, "water20", true);
		setup(26, "water21", true);
		setup(27, "water22", true);
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
	
	public void draw(Graphics2D g2)
	{
		
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
