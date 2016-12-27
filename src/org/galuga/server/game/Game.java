package org.galuga.server.game;

import java.util.List;

import org.galuga.common.packet.game.PacketJoinGame;
import org.galuga.common.packet.game.PacketSetVelocity;
import org.galuga.common.packet.lobby.PacketStartGame;
import org.galuga.common.user.User;
import org.galuga.server.net.Server;

public abstract class Game {
	
	protected final int ID;
	
	protected int hostID;
	
	protected List<User> players;
	
	public Game(int id, Lobby lobby) {
		this.ID = id;
		players = lobby.getPlayers();
		hostID = lobby.getHostID();
		start();
	}
	
	private void start() {
		for(int i = 0; i < players.size(); i++)
			Server.server.send(players.get(i).getID(),
					new PacketStartGame(ID, mode(), players.size()));
		
		for(int i = 0; i < players.size(); i++) {
			for(int j = 0; j < players.size(); j++) {
				
				Server.server.send(players.get(i).getID(),
						new PacketJoinGame(players.get(j).getID(), j, mode(), players.get(j).getUsername()));
			}
		}
	}
	
	public void setVelocity(int playerID, float startX, float startY, float dx, float dy) {
		for(int i = 0; i < players.size(); i++) {
			if(i == playerID)
				continue;
			
			Server.server.send(i, new PacketSetVelocity(playerID, startX, startY, dx, dy));
		}
	}
	
	public abstract int mode();
	
}