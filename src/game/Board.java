package game;

import java.awt.event.*;
import javax.sound.sampled.*;

public interface Board extends ActionListener{

	//Starting the game
	public void initGame();
	
	//
	
	//Anytime any pyhsics occurs
	public void actionPerformed(ActionEvent e);
	
	//Playing a sound once i.e. sound effects
	public void playSound(String filename);
	
	//Looping a sound indefenitely i.e. music
	public Clip loopSound(String filename);
	
	//Pausing a sound, i.e. music
	public void pauseSound(Clip loopedSound);
	
}
