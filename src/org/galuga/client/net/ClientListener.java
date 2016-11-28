package org.galuga.client.net;

import org.galuga.client.gamestate.GameStates;
import org.galuga.client.gamestate.menu.GamePickMenu;
import org.galuga.client.gamestate.menu.GamePickMenu.LobbyListItem;
import org.galuga.common.packet.PacketLogin;
import org.galuga.common.packet.game.PacketFetchLobby;
import org.galuga.common.packet.game.PacketJoinLobby;
import org.galuga.common.packet.game.PacketLeaveLobby;

import sk.gamestate.GameStateManager;
import sk.net.SKClient;
import sk.net.SKConnection;
import sk.net.SKPacketListener;

public class ClientListener implements SKPacketListener {
	
	private SKClient client;
	
	public ClientListener(SKClient client) {
		this.client = client;
	}
	
	@Override
	public void connected(SKConnection connection) {
		System.out.println("Connected to server! My ID: " + connection.getID());
		client.send(new PacketLogin(Client.getUsername()));
	}
	
	@Override
	public void disconnected(SKConnection connection, boolean local, String msg) {
		System.out.println((local ? "Disconnected" : "Kicked") + " from server: " + msg);
		
		GameStateManager.enterState(GameStates.CONNECT_MENU);
	}
	
	@Override
	public void received(SKConnection connection, Object packet) {
		
		//Send to lobby
		if(packet instanceof PacketJoinLobby) {
			PacketJoinLobby p = (PacketJoinLobby) packet;
			GameStates.LOBBY_MENU.setLobbyInfo(p.ID, p.HOST);
			GameStateManager.enterState(GameStates.LOBBY_MENU);
		}
		
		//Leave lobby
		else if(packet instanceof PacketLeaveLobby) {
			GameStateManager.enterState(GameStates.GAME_PICK_MENU);
		}
		
		//Packets that have to be handled at the main thread
		else {
			PacketQueue.add(packet);
		}
	}
}