package org.galuga.client.net;

import sk.net.SKConnection;
import sk.net.SKPacketListener;

public class ClientListener implements SKPacketListener {

	@Override
	public void connected(SKConnection connection) {
		System.out.println("Connected to server! My ID: " + connection.getID());
	}
	
	@Override
	public void disconnected(SKConnection connection, boolean arg1, String arg2) {
		System.out.println("Disconnected from server!");
	}
	
	@Override
	public void received(SKConnection arg0, Object arg1) {
		
	}
}