package org.galuga.common.packet.lobby;

public class PacketFetchLobby {
	
	public final int ID;
	public final String NAME;
	public final int PLAYERS;
	
	public PacketFetchLobby(int id, String name, int players) {
		this.ID = id;
		this.NAME = name;
		this.PLAYERS = players;
	}
	
}