package game;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

import javax.sound.sampled.*;
import javax.swing.*;

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
