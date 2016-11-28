package org.galuga.server.game;

import java.util.ArrayList;
import java.util.List;

import org.galuga.common.packet.game.PacketLeaveLobby;
import org.galuga.common.user.User;
import org.galuga.server.net.Server;

public class Lobby {
	
	public final int MODE;
	public final int ID;
	public final String NAME;
	
	private User[] players;
	private int hostID;
	
	
	public Lobby(int mode, int id, String name) {
		this.MODE = mode;
		this.ID = id;
		this.NAME = name;
		players = new User[] { null, null, null, null };
	}
	
	public boolean join(int playerID, boolean host) {
		for(int i = 0; i < players.length; i++) {
			if(players[i] == null) {
				players[i] = Server.getUser(playerID);
				if(host)
					hostID = playerID;
				return true;
			}
		}
		
		//Returns false if the lobby was full
		return false;
	}
	
	public boolean leave(int playerID) {
		for(int i = 0; i < players.length; i++)
			if(players[i] != null && players[i].getID() == playerID)
				players[i] = null;
		
		//Returns true if the host left
		return playerID == hostID;
	}
	
	public void disband() {
		for(int i = 0; i < players.length; i++) {
			if(players[i] != null) {
				Server.server.send(players[i].getID(), new PacketLeaveLobby(ID));
				leave(players[i].getID());
			}
		}
		
		LobbyList.lobbys.remove(ID);
	}
	
	public int getNumOfPlayers() {
		int numOfPlayers = 0;
		for(int i = 0; i < players.length; i++)
			if(players[i] != null)
				numOfPlayers++;
		
		return numOfPlayers;
	}
	
	public List<User> getPlayers() {
		List<User> users = new ArrayList<>();
		
		for(int i = 0; i < players.length; i++)
			if(players[i] != null)
				users.add(players[i]);
		
		return users;
	}
	
	public int getHostID() {
		return hostID;
	}
}