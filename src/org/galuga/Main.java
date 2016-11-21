package org.galuga;

import org.galuga.gamestate.GameStates;

import sk.audio.AudioManager;
import sk.game.Game;
import sk.game.GameProperties;

public class Main {
	
	public static void main(String[] args) {
		
		GameProperties properties = new GameProperties();
		
		properties.title = "Galuga - v1.0";
		properties.startState = GameStates.MAIN_MENU;
		
		AudioManager.start();
		
		Game.start(properties);
		
	}
}