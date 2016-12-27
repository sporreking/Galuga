package org.galuga.client.gamestate.game;

import org.galuga.client.game.Mover;
import org.galuga.client.game.OtherPlayer;
import org.galuga.client.game.Player;
import org.galuga.client.gamestate.Background;
import org.galuga.client.net.Client;
import org.galuga.client.net.GameQueue;
import org.galuga.client.net.PacketQueue;
import org.galuga.common.user.User;

import sk.entity.Container;
import sk.entity.Entity;
import sk.entity.Root;
import sk.gamestate.GameState;
import sk.gfx.Transform;

public class ArcadeGame implements GameState {
	
	private int gameID;
	
	private int myID;
	
	private int numOfPlayers;
	
	private Root root;
	
	private User[] players;
	
	private volatile boolean ready = false;
	private volatile boolean initialized = false;
	
	@Override
	public void init() {
		
		root = new Root();
		
		//Background
		root.add(-1, "background", new Entity().add(0, Background.INSTANCE));
		
		initialized = true;
	}
	
	public void setInfo(int gameID, int numOfPlayers) {
		this.gameID = gameID;
		this.numOfPlayers = numOfPlayers;
		this.players = new User[numOfPlayers];
		this.ready = false;
		this.myID = Client.getID();
		
		System.out.println("Entering arcade game...\nGame ID: " + gameID
				+ "\nPlayers: " + numOfPlayers);
	}
	
	public synchronized void addPlayer(int slot, int id, String name) {
		players[slot] = new User(id, name);
		System.out.println("Player " + slot + " added. ID: " + id + " Username: " + name);
		System.out.println("add");
		for(int i = 0; i < numOfPlayers; i++) {
			if(players[i] == null)
				return;
		}
		
		setup();
	} 
	
	/**
	 * 
	 * Called when all players have connected to set up game
	 * 
	 */
	public void setup() {
		
		while(!initialized);
		
		//Add ships
		for(int i = 0; i < numOfPlayers; i++) {
			
			if(players[i].getID() != myID)
				root.add(1, "ship" + players[i].getID(), new OtherPlayer(players[i]));
			else
				root.add(0, "ship" + players[i].getID(), new Player(players[i]));
		}
		
		//Start updating game
		ready = true;
	}
	
	public synchronized void setVelocity(int playerID, float startX, float startY, float dx, float dy) {
		if(playerID == myID)
			return;
		
		((OtherPlayer) root.get("ship" + playerID)).get(Mover.class).setVelocity(dx, dy);
		((OtherPlayer) root.get("ship" + playerID)).get(Transform.class).position.x = startX;
		((OtherPlayer) root.get("ship" + playerID)).get(Transform.class).position.y = startY;
	}
	
	@Override
	public void draw() {
		root.draw();
	}
	
	@Override
	public void update(double delta) {
		if(!ready)
			return;
		
		GameQueue.poll();
		
		root.update(delta);
	}
	
	@Override
	public void exit() {
		root.destroy();
	}
}