package org.galuga.common.packet.lobby;

public class PacketRequestGameStart {
	
	public final int LOBBY_ID;
	
	public PacketRequestGameStart(int lobbyID) {
		this.LOBBY_ID = lobbyID;
	}
}