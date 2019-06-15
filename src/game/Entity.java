package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

/**************	Entity Object ***************
 * An Entity is anything in the game. Player, food, enemy, wall, etc.
 * An Entity is made up of a hitbox, an X and Y position, a size, and a color.
 */

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
		this.hitBox = new Rectangle(X-size/2,Y-size/2,size,size);
		this.shade = Color.GREEN;
	}
	public Entity(int X, int Y){
		this.X = X;
		this.Y = Y;
		this.size = 20;
		this.hitBox = new Rectangle(this.X-size/2,this.Y-size/2,size,size);
	}
	
	public void drawEntity(Graphics g){
		//Body
		g.setColor(shade);
		g.fillRect(X-size/2,Y-size/2,size,size);
		
		//Border
		g.setColor(Color.BLACK);
		g.drawRect(X-size/2,Y-size/2,size,size);
		g.drawRect(X+1-size/2, Y+1-size/2, size-2, size-2);
	}
}
