package org.galuga.common.packet;

import org.galuga.common.packet.game.*;
import org.galuga.common.packet.lobby.*;

import sk.serializer.SKSerializer;

public class Packets {
	
	public static final void register() {
		SKSerializer.register(PacketLogin.class);
		SKSerializer.register(PacketCreateLobby.class);
		SKSerializer.register(PacketFetchLobby.class);
		SKSerializer.register(PacketRequestLobbys.class);
		SKSerializer.register(PacketJoinLobby.class);
		SKSerializer.register(PacketLeaveLobby.class);
		SKSerializer.register(PacketFetchUser.class);
		SKSerializer.register(PacketUserLeft.class);
		SKSerializer.register(PacketRequestGameStart.class);
		SKSerializer.register(PacketStartGame.class);
		SKSerializer.register(PacketJoinGame.class);
		SKSerializer.register(PacketSetVelocity.class);
	}
}