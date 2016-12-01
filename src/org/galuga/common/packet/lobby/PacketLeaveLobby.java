package org.galuga.common.packet.lobby;

public class PacketLeaveLobby {

	public final int ID;
	
	public PacketLeaveLobby(int lobbyID) {
		this.ID = lobbyID;
	}
}