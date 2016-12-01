package org.galuga.common.packet.lobby;

public class PacketStartGame {
	
	public final int ID;
	public final int MODE;
	public final int NUM_OF_PLAYERS;
	
	public PacketStartGame(int id, int mode, int numOfPlayers) {
		this.ID = id;
		this.MODE = mode;
		this.NUM_OF_PLAYERS = numOfPlayers;
	}
}