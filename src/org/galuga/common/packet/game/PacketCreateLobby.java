package org.galuga.common.packet.game;

import org.galuga.common.GameMode;

public class PacketCreateLobby {
	
	public final String NAME;
	public final int MODE;
	
	public PacketCreateLobby(String name, int mode) {
		this.NAME = name;
		this.MODE = mode;
	}
	
}