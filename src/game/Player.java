package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class Player extends Entity{

	int speed;
	int health;
	int maxHealth;
	int sprint;
	int sprintRate;
	int sprintCheck;
	boolean isSprinting;
	
	public Player(){
		this.X = 0;
		this.Y = 0;
		this.size = 20;
		this.hitBox = new Rectangle(X-size/2,Y-size/2,size,size);
		this.shade = Color.GREEN;
		this.speed = 2;
		this.tile = 1;
		this.health = 100;
		this.maxHealth = 100;
		this.sprint = 100;
		this.sprintRate = 3;
		this.sprintCheck = 0;
		this.isSprinting = false;
	}
	
	public Player(int X, int Y){
		this.X = X;
		this.Y = Y;
		this.size = 20;
		this.hitBox = new Rectangle(this.X-size/2,this.Y-size/2,size,size);
		this.shade = Color.GREEN;
		this.speed = 2;
		this.tile = 1;
		this.health = 100;
		this.maxHealth = 100;
		this.sprint = 100;
		this.sprintRate = 3;
		this.sprintCheck = 0;
		this.isSprinting = false;
	}
	
	public void draw(Graphics g){
		
		//Body
		g.setColor(shade);
		g.fillRect(X-size/2,Y-size/2,size,size);
		
		//Border
		g.setColor(Color.BLACK);
		g.drawRect(X-size/2,Y-size/2,size,size);
		g.drawRect(X+1-size/2, Y+1-size/2, size-2, size-2);
		
		//Health Bar
		g.setColor(Color.RED);
		g.fillRect(
				X-size*2/3, 
				Y-size*2/3-5,
				size*4/3,
				5);
		g.setColor(Color.GREEN);
		g.fillRect(
				X-size*2/3, 
				Y-size*2/3-5,
				size*4/3*health/maxHealth,
				5);
		
		//Border
		g.setColor(Color.BLACK);
		g.drawRect(
				X-size*2/3, 
				Y-size*2/3-5,
				size*4/3,
				5);
				
	}

	//Heals the player by the int. If health > maxHealth then reduce health till at maxhealth
	public boolean healBy(int healthRecovered){
		this.health += healthRecovered;			//Recover health
		if(this.health > this.maxHealth) {
			this.health = this.maxHealth;		//Cap health
			//System.out.println("Player healed up to " + this.health + " health");
			return true;
		}
		return false;
	}

	//Determine if sprinting and if so, increase speed to 5 and periodically recude sprint
	public void sprint(){
		if(this.isSprinting && this.sprint > 0){					//While sprinting
			this.sprintCheck++;										//Incrememnt sprint check
			if(this.sprintRate <= this.sprintCheck){
				this.sprint--;										//Decrement sprint
				this.sprintCheck = 0;								//Reset sprint counter
			}
			this.speed = 4;											//Sprint speed
		}else{																	//While not sprinting
			this.sprintCheck++;													//Increment sprint check
			if(this.sprintRate*5 <= this.sprintCheck && this.sprint < 100){	//if sprint is less than 100
				this.sprint++;													//Increment sprint
				this.sprintCheck = 0;											//Reset sptint counter
			}
			this.speed = 2;											//Walking speed
		}
		
		if(this.sprint <= 0) this.isSprinting = false;
	}

	//Toggles the isSprinting variable
	public boolean toggleSprinting(){
		if(this.isSprinting) this.isSprinting = false;
		else this.isSprinting = true;
		return this.isSprinting;
	}
}
