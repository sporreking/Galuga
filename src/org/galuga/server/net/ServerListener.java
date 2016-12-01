package org.galuga.server.net;

import org.galuga.common.GameMode;
import org.galuga.common.packet.PacketLogin;
import org.galuga.common.packet.lobby.PacketCreateLobby;
import org.galuga.common.packet.lobby.PacketJoinLobby;
import org.galuga.common.packet.lobby.PacketLeaveLobby;
import org.galuga.common.packet.lobby.PacketRequestGameStart;
import org.galuga.common.packet.lobby.PacketRequestLobbys;
import org.galuga.server.game.Lobby;
import org.galuga.server.game.LobbyList;

import sk.net.SKConnection;
import sk.net.SKPacketListener;
import sk.net.SKServer;

public class ServerListener implements SKPacketListener {
	
	private SKServer server;
	
	public ServerListener(SKServer server) {
		this.server = server;
	}
	
	@Override
	public void connected(SKConnection connection) {
		System.out.println("Client connected with ID: " + connection.getID());
	}
	
	@Override
	public void disconnected(SKConnection connection, boolean local, String msg) {
		System.out.println("Client " + connection.getID()
		+ (local ? " was kicked: " : " disconnected: ") + msg);
		
		Server.removeUser(connection.getID());
	}
	
	@Override
	public void received(SKConnection connection, Object packet) {
		//Verifying registration
		if(!Server.isRegistered(connection.getID())) {
			if(packet instanceof PacketLogin) {
				PacketLogin p = (PacketLogin) packet;
				
				if(!p.USERNAME.matches("[A-Za-z0-9]+")) {
					server.disconnect(connection.getID(), "Invalid username");
					return;
				}
				
				Server.addUser(connection.getID(), p.USERNAME);
				
				System.out.println("Registered user " + p.USERNAME
						+ "(" + connection.getID() + ")");
			} else {
				server.disconnect(connection.getID(), "Illegal behaviour");
				return;
			}
		}
		
		/* Packet handling */
		
		//Request lobby list
		if(packet instanceof PacketRequestLobbys) {
			PacketRequestLobbys p = (PacketRequestLobbys) packet;
			LobbyList.fetch(connection.getID(), p.MODE);
		}
		
		//Create lobby
		else if(packet instanceof PacketCreateLobby) {
			PacketCreateLobby p = (PacketCreateLobby) packet;
			int lobbyID = LobbyList.add(p.MODE, p.NAME);
			LobbyList.join(lobbyID, connection.getID(), true);
		}
		
		//Leave lobby
		else if(packet instanceof PacketLeaveLobby) {
			PacketLeaveLobby p = (PacketLeaveLobby) packet;
			LobbyList.leave(p.ID, connection.getID());
		}
		
		//Join lobby
		else if(packet instanceof PacketJoinLobby) {
			PacketJoinLobby p = (PacketJoinLobby) packet;
			LobbyList.join(p.ID, connection.getID(), false);
		}
		
		//Request game start
		else if(packet instanceof PacketRequestGameStart) {
			PacketRequestGameStart p = (PacketRequestGameStart) packet;
			LobbyList.start(connection.getID(), p.LOBBY_ID);
		}
	}
}