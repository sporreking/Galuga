package org.galuga.common.packet.game;

public class PacketJoinLobby {
	
	public final int ID;
	
	public final boolean HOST;
	
	public PacketJoinLobby(int lobbyID, boolean host) {
		this.ID = lobbyID;
		this.HOST = host;
	}
}