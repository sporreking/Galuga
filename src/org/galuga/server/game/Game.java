package org.galuga.server.game;

import org.galuga.common.packet.lobby.PacketStartGame;
import org.galuga.server.net.Server;

public abstract class Game {
	
	protected final int ID;
	
	public Game(int id, Lobby lobby) {
		this.ID = id;
		start(lobby);
	}
	
	private void start(Lobby lobby) {
		for(int i = 0; i < lobby.getNumOfPlayers(); i++)
			Server.server.send(lobby.getPlayers().get(i).getID(),
					new PacketStartGame(ID, lobby.MODE, lobby.getNumOfPlayers()));
	}
	
}