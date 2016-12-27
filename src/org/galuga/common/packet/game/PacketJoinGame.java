package org.galuga.common.packet.game;

public class PacketJoinGame {
	
	/**
	 * The player ID
	 */
	public final int ID;
	
	/**
	 * The player slot
	 */
	public final int SLOT;
	
	/**
	 * The game mode
	 */
	public final int MODE;
	
	/**
	 * The player name
	 */
	public final String NAME;
	
	public PacketJoinGame(int id, int slot, int mode, String name) {
		this.ID = id;
		this.SLOT = slot;
		this.MODE = mode;
		this.NAME = name;
	}
}