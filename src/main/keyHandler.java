package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyHandler implements KeyListener{
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	//DEBUG
	boolean checkDrawTime = false;
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
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
		}
		
		//DEBUG
		if(code == KeyEvent.VK_P)
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
