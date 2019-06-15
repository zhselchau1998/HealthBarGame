package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class Entity {
	int X;
	int Y;
	int size;
	Rectangle hitBox;
	Color shade;
	
	//Constructors
	public Entity(){
		this.X = 0;
		this.Y = 0;
		this.size = 20;
		this.hitBox = new Rectangle(X,Y,size,size);
		this.shade = Color.GREEN;
	}
	public Entity(int X, int Y){
		this.X = X;
		this.Y = Y;
		this.size = 20;
		this.hitBox = new Rectangle(this.X,this.Y,size,size);
	}
	
	public void drawEntity(Graphics g){
		//Body
		g.setColor(shade);
		g.fillRect(X,Y,size,size);
		
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
				size*4/3,
				5);
		
		//Borders
		g.setColor(Color.BLACK);
		g.drawRect(X,Y,size,size);
		g.drawRect(X+1, Y+1, size-2, size-1);
		
		g.drawRect(
				X-size*2/3, 
				Y-size*2/3-5,
				size*4/3,
				5);
	}
}
