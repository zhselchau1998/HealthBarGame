package game;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class HealthGame extends JFrame{

	public HealthGame(){
		add(new HealthGameBoard());
		
		setResizable(false);
		pack();
		
		setTitle("Healthbar Game");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String [] args){
		
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				JFrame ex = new HealthGame();
				ex.setVisible(true);
			}
		});
	}
}