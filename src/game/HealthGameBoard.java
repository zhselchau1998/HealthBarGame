package game;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

import javax.sound.sampled.*;
import javax.swing.*;

public class HealthGameBoard extends JPanel implements Board{

	//TODO:
	/*	Create Healthbar Mechanic
	 *  Add Obstacles
	 * 	Finish off music and SFX
	 * 	Add Sprites
	 * 	Create Sprinting Mechanic
	 * 	Create Entity Object
	 */
	
	/** 
	CONSTANTS 
	*/
	private final int __B_WIDTH = 500;
	private final int __B_HEIGHT = 500;
	private final int __DELAY = 20;			
	private final int __INVEN_SIZE = 9;		
	private final int __WORLD_SIZE = 4; 	// 1|2
											// 3|4
	/**
	VARIABLES
	*/
	//Game Structure
	private boolean inGame;
	private Timer gameTimer;	//Game clock
	
	//Game Mechanics
	private boolean upDir;		//Movement Direction of Player
	private boolean downDir;
	private boolean rightDir;
	private boolean leftDir;
	
	//Game Asthetic
	private File soundFile;
	private Clip loopClip;		//Music
	private Clip onePlayClip;	//Sound effects
	private Font bigFont;
	private FontMetrics bigMtr;
	
	//Player
	private int playerX;		//2d so player indexes
	private int playerY;
	private int playerSpeed;	//Speed for movement in px
	private int playerTile;		//Which world tile player is in
	private int playerHealth;
	private int playerMaxHealth;
	private int playerSize;		//Size of player, square.
	private Rectangle playerHitBox = new Rectangle();
	
	
	public HealthGameBoard(){
		
		addKeyListener(new KAdapter());
		setBackground(Color.LIGHT_GRAY);
		setFocusable(true);
		
		setPreferredSize(new Dimension(__B_WIDTH, __B_HEIGHT));
		initGame();
	}
	
	
	/**
	Game Proper Functions
	*/	
	public void initGame() {
		/*Variable Setting*/
		//Game Structure
		inGame = true;
		gameTimer = new Timer(__DELAY, this);
		
		//Game Mechanics
		upDir = downDir = rightDir = leftDir = false;
		
		//Game Asthetic
		bigFont = new Font("Helvetica", Font.BOLD, 52);
		bigMtr = getFontMetrics(bigFont);
		
		//Player
		playerX = __B_WIDTH/2;
		playerY = __B_HEIGHT/2;
		playerSpeed = 2;
		playerTile = 1;
		playerHealth = 100;
		playerMaxHealth = 100;
		playerSize = 20;
		playerHitBox.setBounds(
				playerX-(playerSize/2), //Width start
				playerY-(playerSize/2), //Height start
				playerSize, 			//Width length
				playerSize);			//Height length
		
		/*Starting the game*/
		gameTimer.start();
		
	}

	//Moving the player
	private void move(){
		
		String outOfBounds = outOfBoundsCheck();
		
		if(outOfBounds.compareTo("NONE") == 0){//Normal movement
			if(upDir)playerY -= playerSpeed;
			if(downDir)playerY += playerSpeed;
			if(rightDir)playerX += playerSpeed;
			if(leftDir)playerX -= playerSpeed;
		}else if(tileCheck(outOfBounds)){//Can move to another tile
			System.out.println("Moved to a new tile toward " + outOfBounds);
			if(outOfBounds.compareTo("UP")==0){
				playerY = __B_HEIGHT - playerSize/2 - 1;
				playerTile -= 2;
			}
			if(outOfBounds.compareTo("DOWN")==0){
				playerY = playerSize/2 + 1;
				playerTile +=2;
			}
			if(outOfBounds.compareTo("RIGHT")==0){
				playerX = playerSize/2 + 1;
				playerTile += 1;
			}
			if(outOfBounds.compareTo("LEFT")==0){
				playerX = __B_WIDTH - playerSize/2 - 1;
				playerTile -= 1;
			}
		}else{//World border collision
			//System.out.println("Colided with world border");
			if(outOfBounds.compareTo("UP")==0){
				playerY += playerSpeed;
				if(upDir)playerY -= playerSpeed;
			}
			if(outOfBounds.compareTo("DOWN")==0){
				playerY -= playerSpeed;
				if(downDir)playerY += playerSpeed;
			}
			if(outOfBounds.compareTo("RIGHT")==0){
				playerX -= playerSpeed;
				if(rightDir)playerX += playerSpeed;
			}
			if(outOfBounds.compareTo("LEFT")==0){
				playerX += playerSpeed;
				if(leftDir)playerX -= playerSpeed;
			}
		}
		
		//Need to reset hitbox after movement
		playerHitBox.setBounds(
				playerX-(playerSize/2), //Width start
				playerY-(playerSize/2), //Height start
				playerSize, 			//Width length
				playerSize);			//Height length
	}
	
	//Checking if player is out of bounds
	private String outOfBoundsCheck(){
		
		if(playerY - playerSize/2 < 0) return "UP";
		if(playerY + playerSize/2 > __B_HEIGHT) return "DOWN";
		if(playerX + playerSize/2 > __B_WIDTH) return "RIGHT";
		if(playerX - playerSize/2 < 0) return "LEFT";
		
		return "NONE";
	}

	//Checking if player can move to a new tile, directly tied to outOfBoundsCheck
	private boolean tileCheck(String dir){
		
		if(dir.compareTo("UP") == 0 && (playerTile == 3 || playerTile == 4))
			return true;
		if(dir.compareTo("DOWN") == 0 && (playerTile == 1 || playerTile == 2))
			return true;
		if(dir.compareTo("RIGHT") == 0 && (playerTile == 1 || playerTile == 3))
			return true;
		if(dir.compareTo("LEFT") == 0 && (playerTile == 2 || playerTile == 4))
			return true;
		
		return false;
	}
	
	
	/**
 	Graphics
	*/
	private void drawPlayer(Graphics g){
		//Player body
		g.setColor(Color.GREEN);
		g.fillRect(
				playerX - playerSize/2, 
				playerY - playerSize/2, 
				playerSize, 
				playerSize);
		
		//Player health bar
		g.setColor(Color.RED);
		g.fillRect(
				playerX - playerSize*2/3, 	//Offset by 1/3 player size
				playerY - playerSize*2/3-5, //Offest by 1/3 player size and 5 px for drawing bar
				playerSize*4/3, 
				5);
		g.setColor(Color.GREEN);
		g.fillRect(
				playerX - playerSize*2/3, 	//Offset by 1/3 player size
				playerY - playerSize*2/3-5, //Offest by 1/3 player size and 5 px for drawing bar
				playerSize*4/3 * playerHealth/playerMaxHealth, 
				5);
		
		//Player body boarder
		g.setColor(Color.BLACK);
		g.drawRect(
				playerX - playerSize/2, 
				playerY - playerSize/2, 
				playerSize, 
				playerSize);
		g.drawRect(
				playerX - playerSize/2+1, 
				playerY - playerSize/2+1, 
				playerSize-2, 
				playerSize-2);
		
		//Player health bar boarder
		g.drawRect(
				playerX - playerSize*2/3, 	//Offset by 1/3 player size
				playerY - playerSize*2/3-5, //Offest by 1/3 player size and 5 px for drawing bar
				playerSize*4/3, 
				5);
	}
	
	private void drawBackground(Graphics g){
		g.setFont(bigFont);
		g.setColor(Color.WHITE);
		g.drawString(""+playerTile, (__B_WIDTH - bigMtr.stringWidth(""+playerTile))/2, __B_HEIGHT/2);
	}
	
	
	/**
	Game Engine Functions
	*///Every tic of the gameTimer
	public void actionPerformed(ActionEvent e) {
		
		move();
		
		repaint();
	}
	
	//This is where all the graphics should occur.
	private void doDrawing(Graphics g){
		if(inGame){
			drawBackground(g);
			drawPlayer(g);
		}
	}
	
	//Connection to JPanel
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		doDrawing(g);
	}

	//SFX
	public void playSound(String filename) {
		
		
	}

	//Music/Loop sounds
	public Clip loopSound(String filename) {
		
		return null;
	}

	//Pause a sound
	public void pauseSound(Clip loopedSound) {
		
		
	}

	/**
	Controls
	*/
	private class KAdapter extends KeyAdapter{
		
		public void keyPressed(KeyEvent e){
			int key = e.getKeyCode();
			
			//Movement
			if(key == KeyEvent.VK_UP) upDir = true;
			if(key == KeyEvent.VK_DOWN) downDir = true;
			if(key == KeyEvent.VK_RIGHT) rightDir = true;
			if(key == KeyEvent.VK_LEFT) leftDir = true;
			
			//Testing
			if(key == KeyEvent.VK_S) playerSpeed = 5;//Sprinting Mechanic?
			if(key == KeyEvent.VK_H) playerHealth = playerMaxHealth/2;
		}
		
		
		public void keyReleased(KeyEvent e){
			int key = e.getKeyCode();
			
			//Movement
			if(key == KeyEvent.VK_UP) upDir = false;
			if(key == KeyEvent.VK_DOWN) downDir = false;
			if(key == KeyEvent.VK_RIGHT) rightDir = false;
			if(key == KeyEvent.VK_LEFT) leftDir = false;
			
			//Testing
			if(key == KeyEvent.VK_S) playerSpeed = 2;
			if(key == KeyEvent.VK_H) playerHealth += playerHealth;
		}
	}
}
