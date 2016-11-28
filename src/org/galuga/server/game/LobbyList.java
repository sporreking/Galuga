package org.galuga.server.game;

import java.util.HashMap;

import org.galuga.common.GameMode;
import org.galuga.common.packet.game.PacketFetchLobby;
import org.galuga.common.packet.game.PacketFetchUser;
import org.galuga.common.packet.game.PacketJoinLobby;
import org.galuga.common.packet.game.PacketLeaveLobby;
import org.galuga.common.packet.game.PacketUserLeft;
import org.galuga.common.user.User;
import org.galuga.server.net.Server;

public class LobbyList {
	
	protected static final HashMap<Integer, Lobby> lobbys = new HashMap<>();
	
	public static final int add(int mode, String name) {		
		
		int i = 0;
		boolean search = true;
		while(search) {
			search = false;
			for(int j : lobbys.keySet())
				if(j == i)
					search = true;
			
			if(search)
				i++;
		}
		
		lobbys.put(i, new Lobby(mode, i, name));
		
		return i;
	}
	
	public static final void join(int lobbyID, int playerID, boolean host) {
		boolean success = lobbys.get(lobbyID).join(playerID, host);
		
		//Success is false if the lobby was full
		if(success) {
			Server.server.send(playerID, new PacketJoinLobby(lobbyID, host));
			sendUsers(lobbyID);
		}
	}
	
	public static final void leave(int lobbyID, int playerID) {
		boolean disband = lobbys.get(lobbyID).leave(playerID);
		
		Server.server.send(playerID, new PacketLeaveLobby(lobbyID));
		//Disband is true if the host left
		if(disband) {
			lobbys.get(lobbyID).disband();
		} else {
			for(int i = 0; i < lobbys.get(lobbyID).getPlayers().size(); i++) {
				User player = lobbys.get(lobbyID).getPlayers().get(i);
				Server.server.send(player.getID(), new PacketUserLeft(playerID));
			}
		}
	}
	
	private static final void sendUsers(int lobbyID) {
		for(int i = 0; i < lobbys.get(lobbyID).getPlayers().size(); i++) {
			User player = lobbys.get(lobbyID).getPlayers().get(i);
			for(int j = 0; j < lobbys.get(lobbyID).getPlayers().size(); j++) {
				Server.server.send(lobbys.get(lobbyID).getPlayers().get(j).getID(),
						new PacketFetchUser(player.getID(), player.getUsername(),
								i, player.getID() == lobbys.get(lobbyID).getHostID()));
			}
		}
	}
	
	/**
	 * 
	 * Sends lobby packets to a client.
	 * 
	 */
	public static final void fetch(int connectionID, int mode) {
		for(Lobby lobby : lobbys.values())
			if(lobby.MODE == mode)
				Server.server.send(connectionID,
						new PacketFetchLobby(lobby.ID, lobby.NAME, lobby.getNumOfPlayers()));
	}
	
	public static final void print() {
		for(Lobby lobby : lobbys.values())
			System.out.print(lobby.NAME + "(" + lobby.ID + "): " + lobby.getNumOfPlayers() + "/4, ");
		
		System.out.println();
	}
}