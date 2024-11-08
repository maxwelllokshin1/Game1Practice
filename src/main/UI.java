package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import object.objCookie;
import object.objHeart;
import entity.Entity;

public class UI {
	gamePanel gp;
	Graphics2D g2;
	Font midieval, pop;
	BufferedImage heartFull, heartHalf, heartEmpty;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialouge = "";
	public int commandNum = 0;
	public int titleScreenState = 0; // 0: the first screen, 1: second screen
	
//	double playTime;
//	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(gamePanel gp)
	{
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/big.ttf");
			pop = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Entity heart = new objHeart(gp);
		heartFull = heart.image;
		heartHalf = heart.image2;
		heartEmpty = heart.image3;
		
		
	}
	
	public void showMessage(String text)
	{
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2)
	{
		this.g2 = g2;
		g2.setFont(pop);
		g2.setColor(Color.white);
		
		// DIALOUGE STATE
		if(gp.gameState == gp.titleState)
		{
			drawTitleScreen();
		}
				
		// PLAY STATE
		if(gp.gameState == gp.playState)
		{
			drawPlayerLife();
		}
		// PUASE STATE
		if(gp.gameState == gp.pauseState)
		{
			drawPlayerLife();
			drawPauseScreen();
		}
		// DIALOUGE STATE
		if(gp.gameState == gp.dialougeState)
		{
			drawPlayerLife();
			drawDialougeScreen();
		}
		
		
	}
	
	public void drawPlayerLife()
	{
		//gp.player.life = 1;
		int x = gp.screenWidth/2 - (gp.tileSize) ;
		int y = gp.screenHeight/2 - (gp.tileSize/2)- gp.tileSize;
		int i = 0;
		
		// DRAW MAX HEART
		while(i<gp.player.maxLife/2)
		{
			g2.drawImage(heartEmpty, x, y, null);
			i++;
			x += gp.tileSize/2;
		}
		
		// RESET
		x = gp.screenWidth/2 - (gp.tileSize) ;
		y = gp.screenHeight/2 - (gp.tileSize/2)- gp.tileSize;
		i = 0;
		
		// DRAW CURRENT LIFE
		while(i<gp.player.life)
		{
			g2.drawImage(heartHalf, x, y, null);
			i++;
			if(i<gp.player.life)
			{
				g2.drawImage(heartFull,x,y,null);
			}
			i++;
			x += gp.tileSize/2;
		}
		
	}
	
	public void drawTitleScreen()
	{
		if(titleScreenState == 0)
		{
			g2.setColor(new Color(187, 153, 105));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			//TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
			String text = "Round em up";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			
			//SHADOW
			g2.setColor(Color.black);
			g2.drawString(text,x+5,y+5);
			
			// MAIN COLOR
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			// BEAR
			x = gp.screenWidth/2 - (gp.tileSize*2)/2;
			y += gp.tileSize*2;
			g2.drawImage(gp.player.idol2, x, y, gp.tileSize*3, gp.tileSize*3, null);
			
			// BULL
			g2.drawImage(gp.npc[0].idol1, x+(gp.tileSize*3), y, gp.tileSize*2, gp.tileSize*2, null);
			// COW
			g2.drawImage(gp.npc[1].idol2, x-(gp.tileSize*2), y, gp.tileSize*2, gp.tileSize*2, null);
			
			// MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
			
			text = "NEW GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize*7;
			g2.drawString(text, x, y);
			if(commandNum == 0)
			{
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "LOAD GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1)
			{
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "QUIT";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2)
			{
				g2.drawString(">", x-gp.tileSize, y);
			}
		}
		else if(titleScreenState == 1)
		{
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			String text = "SELECT:";
			int x = getXforCenteredText(text);
			int y = gp.tileSize *3;
			g2.drawString(text, x, y);
			
			text = "Fighter";
			x = getXforCenteredText(text);
			y += gp.tileSize *3;
			g2.drawString(text, x, y); 
			
			if(commandNum == 0)
			{
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			
			text = "Gatherer";
			x = getXforCenteredText(text);
			y += gp.tileSize *2;
			g2.drawString(text, x, y); 
			
			if(commandNum == 1)
			{
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Pooper";
			x = getXforCenteredText(text);
			y += gp.tileSize *2;
			g2.drawString(text, x, y); 
			
			if(commandNum == 2)
			{
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Back";
			x = getXforCenteredText(text);
			y += gp.tileSize *3;
			g2.drawString(text, x, y); 
			
			if(commandNum == 3)
			{
				g2.drawString(">", x-gp.tileSize, y);
			}
		}
	}
	
	public void drawPauseScreen()
	{
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "Paused";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawDialougeScreen()
	{
		// WINDOW
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		
		drawSubWindow(x,y,width,height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
		x += gp.tileSize;
		y += gp.tileSize;
		for(String line : currentDialouge.split("\n"))
		{
			g2.drawString(line, x, y);
			y+=40;
		}
	}
	
	public void drawSubWindow(int x, int y, int width, int height)
	{
		Color c = new Color(0,0,0, 175);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
	
	public int getXforCenteredText(String text)
	{
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return gp.screenWidth/2 - length/2;
	}

//	public void currentDialouge() {
//		// TODO Auto-generated method stub
//		
//	}
}
