package org.galuga.common.packet.lobby;

public class PacketFetchUser {
	
	public final String NAME;
	public final int ID;
	public final int SLOT;
	public final boolean HOST;
	
	public PacketFetchUser(int playerID, String playerName, int slot, boolean host) {
		this.ID = playerID;
		this.NAME = playerName;
		this.SLOT = slot;
		this.HOST = host;
	}
}