package org.galuga.server;

import sk.net.SKConnection;
import sk.net.SKPacketListener;

public class ServerListener implements SKPacketListener {
	
	@Override
	public void connected(SKConnection connection) {
		System.out.println("Client connected with ID: " + connection.getID());
	}
	
	@Override
	public void disconnected(SKConnection connection, boolean arg1, String msg) {
		System.out.println("Client " + connection.getID() + " disconnected: " + msg);
	}
	
	@Override
	public void received(SKConnection arg0, Object arg1) {
		
	}
}