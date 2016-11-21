package org.galuga.client.net;

import java.io.IOException;
import java.net.UnknownHostException;

import sk.net.SKClient;
import sk.net.SKNet;

public class Client {
	
	public static final SKClient client = new SKClient();
	
	public static final void connect(String ip, int port) {
		
		SKNet.init();
		
		client.addPacketListener(new ClientListener());
		
		try {
			client.connect(ip, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}