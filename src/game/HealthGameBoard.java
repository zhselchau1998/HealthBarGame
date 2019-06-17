package game;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

import javax.sound.sampled.*;
import javax.swing.*;

public class HealthGameBoard extends JPanel implements Board{

	//TODO:
	 /* Add Obstacles
	 * 	Add Sprites
	 * 	Enhance Sprinting Mechanic
	 */
	

	private static final long serialVersionUID = 1L;
	/** 
	CONSTANTS 
	*/
	private final int __B_WIDTH = 500;
	private final int __B_HEIGHT = 500;
	private final int __DELAY = 20;			
	//private final int __INVEN_SIZE = 9;		
	private final int __WORLD_SIZE = 4; 	// 1|2
											// 3|4
	/**
	VARIABLES
	*/
	//Game Structure
	private boolean inGame;
	private Timer gameTimer = new Timer(__DELAY, this);	//Game clock
	private int score;
	
	//Game Mechanics
	private boolean upDir;		//Movement Direction of Player
	private boolean downDir;
	private boolean rightDir;
	private boolean leftDir;
	private int dmgRate;		//How many tics between constant dot dmg
	private int dmgRateTicCount;
	
	//Game Asthetic
	private boolean bumpPlayed;	//Has bump sound been played yet
	private File soundFile;
	private Clip musicClip;		//Music
	private Clip onePlayClip;	//Sound effects
	private Font bigFont;
	private Font smlFont;
	private FontMetrics bigMtr;
	private FontMetrics smlMtr;
	
	//Player
	Player user;
	/*private int user.X;		//2d so player indexes
	private int user.Y;
	private int user.speed;	//Speed for movement in px
	private int user.tile;		//Which world tile player is in
	private int user.health;
	private int user.maxHealth;
	private int user.size;		//Size of player, square.
	private Rectangle user.hitBox = new Rectangle();*/
	
	//Food
	Food food;

	
	
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
		score = 0;
		
		//Game Mechanics
		upDir = downDir = rightDir = leftDir = false;
		dmgRate = 20;					//Determines how many tics b/w dot dmg
		dmgRateTicCount = 0;			//Initialize at 0
		
		//Game Asthetic
		bumpPlayed = false;
		bigFont = new Font("Helvetica", Font.BOLD, 52);
		smlFont = new Font("Helvetica", Font.BOLD, 24);
		bigMtr = getFontMetrics(bigFont);
		smlMtr = getFontMetrics(smlFont);
		musicClip = loopSound("HealthBarGameMusic.wav");
		
		//Player
		user = new Player(__B_WIDTH/2, __B_HEIGHT/2);
		
		//Food
		spawnFood();
		food.tile = 1;
		food.X = __B_WIDTH*2/3;
		food.Y = __B_HEIGHT*2/3;
		food.setRect();
		
		/*Starting the game*/
		gameTimer.start();
		
	}

	//Moving the player
	private void move(){
		
		String outOfBounds = outOfBoundsCheck();
		
		if(outOfBounds.compareTo("NONE") == 0){		//Normal movement
			if(upDir)user.Y -= user.speed;
			if(downDir)user.Y += user.speed;
			if(rightDir)user.X += user.speed;
			if(leftDir)user.X -= user.speed;
			bumpPlayed = false;						//Allow bump sound again
		}else if(tileCheck(outOfBounds)){			//Can move to another tile
			System.out.println("Moved to a new tile toward " + outOfBounds);
			if(outOfBounds.compareTo("UP")==0){
				user.Y = __B_HEIGHT - user.size/2 - 1;
				user.tile -= 2;
			}
			if(outOfBounds.compareTo("DOWN")==0){
				user.Y = user.size/2 + 1;
				user.tile +=2;
			}
			if(outOfBounds.compareTo("RIGHT")==0){
				user.X = user.size/2 + 1;
				user.tile += 1;
			}
			if(outOfBounds.compareTo("LEFT")==0){
				user.X = __B_WIDTH - user.size/2 - 1;
				user.tile -= 1;
			}
		}else{//World border collision
			//System.out.println("Colided with world border");
			if(outOfBounds.compareTo("UP")==0){
				user.Y += user.speed;
				if(upDir)user.Y -= user.speed;
			}
			if(outOfBounds.compareTo("DOWN")==0){
				user.Y -= user.speed;
				if(downDir)user.Y += user.speed;
			}
			if(outOfBounds.compareTo("RIGHT")==0){
				user.X -= user.speed;
				if(rightDir)user.X += user.speed;
			}
			if(outOfBounds.compareTo("LEFT")==0){
				user.X += user.speed;
				if(leftDir)user.X -= user.speed;
			}
			if(!bumpPlayed){						//Play bump sound once
				playSound("CollisionSound.wav");
				bumpPlayed = true;
			}
		}
		
		//Need to reset hitbox after movement
		user.hitBox.setBounds(
				user.X-(user.size/2), //Width start
				user.Y-(user.size/2), //Height start
				user.size, 			//Width length
				user.size);			//Height length
	}
	
	//Checking if player is out of bounds
	private String outOfBoundsCheck(){
		
		if(user.Y - user.size/2 < 0) return "UP";
		if(user.Y + user.size/2 > __B_HEIGHT) return "DOWN";
		if(user.X + user.size/2 > __B_WIDTH) return "RIGHT";
		if(user.X - user.size/2 < 0) return "LEFT";
		
		return "NONE";
	}

	//Checking if player can move to a new tile, directly tied to outOfBoundsCheck
	private boolean tileCheck(String dir){
		
		if(dir.compareTo("UP") == 0 && (user.tile == 3 || user.tile == 4))
			return true;
		if(dir.compareTo("DOWN") == 0 && (user.tile == 1 || user.tile == 2))
			return true;
		if(dir.compareTo("RIGHT") == 0 && (user.tile == 1 || user.tile == 3))
			return true;
		if(dir.compareTo("LEFT") == 0 && (user.tile == 2 || user.tile == 4))
			return true;
		
		return false;
	}
	
	//This is where damage calculation for everything happens every tic
	private void dmgCalc(){
		//Survival DOT dmg
		if(dmgRate/user.speed <= dmgRateTicCount++) {
			user.health--; 
			dmgRateTicCount=0;
			}

	}
	
	//Checks to see if player is out of health
	private boolean healthCheck(){
		if(user.health == 0) return true;
		else return false;
	}
	
	//Spawns a new Food entity at a random location in the world
	private void spawnFood(){
		food = new Food(
				(int) Math.abs(new Random().nextInt(__B_WIDTH-40)) + 20,
				(int) Math.abs(new Random().nextInt(__B_HEIGHT-40)) + 20,
				(int) Math.abs(new Random().nextInt(__WORLD_SIZE))+1
				);
		System.out.println("Food spawned at X: " + food.X + ", Y: " + food.Y + ", and Tile: " + food.tile);
	}
	
	//Checks if the player is in contact with a food entity
	private boolean playerFoodCollisionCheck(){
		
		if(user.hitBox.intersects(food.hitBox) && user.tile == food.tile){
			score += food.healthRecovered;						//Base score
			if(user.healBy(food.healthRecovered))score += 100;	//Heal the player and extra score
			playSound("MunchingSound.wav");						//Eating SFX
			spawnFood();										//Reset food
		}
		return false;
	}
	
	/**
 	Graphics
	*/
	private void drawEntities(Graphics g){
		if(user.tile == food.tile){
			food.draw(g);
		}
		user.draw(g);
	}
	
	private void drawBackground(Graphics g){
		g.setFont(bigFont);
		g.setColor(Color.WHITE);
		g.drawString(""+user.tile, (__B_WIDTH - bigMtr.stringWidth(""+user.tile))/2, __B_HEIGHT/2);
	}
	
	private void gameOver(Graphics g){
		g.setFont(bigFont);
		g.setColor(Color.WHITE);
		g.drawString(
				"GAME OVER", 
				(__B_WIDTH-bigMtr.stringWidth("GAME OVER"))/2, 
				__B_HEIGHT/2);
		
		musicClip.close();		//This is to prevent overlapping music
	}
	
	private void drawScore(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(smlFont);
		g.drawString("Score: " + score, 5, 5+24);
	}
	
	
	/**
	Game Engine Functions
	*///Every tic of the gameTimer
	public void actionPerformed(ActionEvent e) {
		
		if(inGame){
			move();
			playerFoodCollisionCheck();
			dmgCalc();
			inGame = !healthCheck();
		}
		
		repaint();
	}
	
	//This is where all the graphics logic should occur.
	private void doDrawing(Graphics g){
		if(inGame){
			drawBackground(g);
			drawEntities(g);
			drawScore(g);
		} else gameOver(g);
	}
	
	//Connection to JPanel
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		doDrawing(g);
	}

	//SFX
	public void playSound(String filename) {
		
		soundFile = new File(filename);
		
		try{
			onePlayClip = AudioSystem.getClip();
			onePlayClip.open(AudioSystem.getAudioInputStream(soundFile));
			onePlayClip.start();
		}catch(Exception e){}
	}

	//Music/Loop sounds
	public Clip loopSound(String filename) {
		
		soundFile = new File(filename);
		Clip tmpClip = null;
		
		try{
			tmpClip = AudioSystem.getClip();
			tmpClip.open(AudioSystem.getAudioInputStream(soundFile));	//Setting up sound file
			tmpClip.start();											//Play clip
			tmpClip.loop(Clip.LOOP_CONTINUOUSLY);						//Loop clip
		}catch(Exception e){}
		return tmpClip;
	}

	//Pause a sound
	public void pauseSound(Clip loopedSound) {
		if(loopedSound.isActive()) loopedSound.stop();	//If music is playing then pause
		else{											//If it isn't playing then unpause
			loopedSound.start(); 
			loopedSound.loop(Clip.LOOP_CONTINUOUSLY);
		}
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
			if(key == KeyEvent.VK_S) user.speed = 4;//Sprinting Mechanic?
			//if(key == KeyEvent.VK_H) user.health = user.maxHealth/2;
		}
		
		
		public void keyReleased(KeyEvent e){
			int key = e.getKeyCode();
			
			//Movement
			if(key == KeyEvent.VK_UP) upDir = false;
			if(key == KeyEvent.VK_DOWN) downDir = false;
			if(key == KeyEvent.VK_RIGHT) rightDir = false;
			if(key == KeyEvent.VK_LEFT) leftDir = false;
			
			//Testing
			if(key == KeyEvent.VK_S) user.speed = 2;
			//if(key == KeyEvent.VK_H) user.health += user.health;
			if(key == KeyEvent.VK_M) pauseSound(musicClip);
			
			//Restart Game
			if(!inGame && key == KeyEvent.VK_ENTER) initGame();
		}
	}
}
