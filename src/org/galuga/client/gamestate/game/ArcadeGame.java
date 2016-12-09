package org.galuga.client.gamestate.game;

import sk.entity.Root;
import sk.gamestate.GameState;

public class ArcadeGame implements GameState {
	
	private int gameID;
	private int numOfPlayers;
	
	private Root root;
	
	@Override
	public void init() {
		root = new Root();
	}
	
	public void setInfo(int gameID, int numOfPlayers) {
		this.gameID = gameID;
		this.numOfPlayers = numOfPlayers;
		
		System.out.println("Entering arcade game...\nGame ID: " + gameID
				+ "\nPlayers: " + numOfPlayers);
	}
	
	@Override
	public void draw() {
		root.draw();
	}
	
	@Override
	public void update(double delta) {
		root.update(delta);
	}
	
	@Override
	public void exit() {
		root.destroy();
	}
}