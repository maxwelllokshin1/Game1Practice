package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyHandler implements KeyListener{
	gamePanel gp;
	
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
	//DEBUG
	boolean checkDrawTime = false;
	
	public keyHandler(gamePanel gp)
	{
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		// TITLE STATE
		if(gp.gameState == gp.titleState)
		{
			if(gp.ui.titleScreenState == 0)
			{
				switch(code)
				{
				case KeyEvent.VK_UP:
					gp.ui.commandNum--;
					if(gp.ui.commandNum < 0)
					{
						gp.ui.commandNum = 2;
					}
					break;
				case KeyEvent.VK_DOWN:
					gp.ui.commandNum++;
					if(gp.ui.commandNum > 2)
					{
						gp.ui.commandNum = 0;
					}
					break;
				case KeyEvent.VK_ENTER:
//					if(gp.ui.commandNum == 0)
//					{
//						gp.ui.titleScreenState = 1;
//					}
					switch(gp.ui.commandNum)
					{
					case 0:
						gp.ui.titleScreenState = 1;
						break;
					case 1:
						//gp.gameState = gp.playState;
						break;
					case 2:
						System.exit(0);;
						break;
					}
					break;
				}
			}
			
			else if(gp.ui.titleScreenState == 1)
			{
				switch(code)
				{
				case KeyEvent.VK_UP:
					gp.ui.commandNum--;
					if(gp.ui.commandNum < 0)
					{
						gp.ui.commandNum = 3;
					}
					break;
				case KeyEvent.VK_DOWN:
					gp.ui.commandNum++;
					if(gp.ui.commandNum > 3)
					{
						gp.ui.commandNum = 0;
					}
					break;
				case KeyEvent.VK_ENTER:
//					if(gp.ui.commandNum == 0)
//					{
//						gp.gameState = gp.playState;
//					}
					switch(gp.ui.commandNum)
					{
					case 0:
						System.out.println("Fighter");
						gp.gameState = gp.playState;
						break;
					case 1:
						System.out.println("Gatherer");
						gp.gameState = gp.playState;
						
						break;
					case 2:
						System.out.println("Pooper");
						gp.gameState = gp.playState;
						break;
					case 3:
						gp.ui.titleScreenState = 0;
						break;
					}
					break;
				}
			}
		}
		
		//PLAY STATE
		if(gp.gameState == gp.playState)
		{
			switch(code)
			{
			case KeyEvent.VK_W:
				upPressed = true;
				break;
			case KeyEvent.VK_S:
				downPressed = true;
				break;
			case KeyEvent.VK_A:
				leftPressed = true;
				break;
			case KeyEvent.VK_D:
				rightPressed = true;
				break;
			case KeyEvent.VK_P:
				gp.gameState = gp.pauseState;
				break;
//			case KeyEvent.VK_ENTER:
//				enterPressed = true;
//				break;
			}
		}
		
		// PUASE STATE
		else if(gp.gameState == gp.pauseState)
		{
			if(code == KeyEvent.VK_P)
			{
				gp.gameState = gp.playState;
			}
		}
		
		// DIALOUGE STATE
		else if(gp.gameState == gp.dialougeState)
		{
			if(code == KeyEvent.VK_ENTER)
			{
				gp.gameState = gp.playState;
			}
		}
		
		
		//DEBUG
		if(code == KeyEvent.VK_T)
		{
			if(checkDrawTime == false)
			{
				checkDrawTime = true;
			}else {
				checkDrawTime = false;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		switch(code)
		{
		case KeyEvent.VK_W:
			upPressed = false;
			break;
		case KeyEvent.VK_S:
			downPressed = false;
			break;
		case KeyEvent.VK_A:
			leftPressed = false;
			break;
		case KeyEvent.VK_D:
			rightPressed = false;
			break;
		}
	}
	
}
