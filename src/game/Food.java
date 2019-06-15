package game;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Food extends Entity{
	
	int healthRecovered;
	Image icon;
	
	public Food(){
		this.X = 0;
		this.Y = 0;
		this.size = 10;
		this.tile = 1;
		this.hitBox = new Rectangle(X-size/2,Y-size/2,size,size);
		this.shade = Color.CYAN;
		this.healthRecovered = 20;
		this.icon = null;
	}
	
	public Food(int X, int Y, int tile){
		this.X = X;
		this.Y = Y;
		this.size = 10;
		this.tile = tile;
		this.hitBox = new Rectangle(X-size/2,Y-size/2,size,size);
		this.shade = Color.CYAN;
		this.healthRecovered = 20;
		this.icon = null;
	}
	
	public void draw (Graphics g){
		//Body
		g.setColor(shade);
		g.fillRect(X-size/2,Y-size/2,size,size);
		
		//Border
		g.setColor(Color.BLACK);
		g.drawRect(X-size/2,Y-size/2,size,size);
		g.drawRect(X+1-size/2, Y+1-size/2, size-2, size-2);
		
	}
}
