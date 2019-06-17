package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class Player extends Entity{

	int speed;
	int health;
	int maxHealth;
	
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
		this.health += healthRecovered;
		if(this.health > this.maxHealth) {
			this.health = this.maxHealth;
			System.out.println("Player healed up to " + this.health + " health");
			return true;
		}
		return false;
	}
}
