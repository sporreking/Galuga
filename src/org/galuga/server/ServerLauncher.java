package org.galuga.server;

import java.io.IOException;

import sk.net.SKNet;
import sk.net.SKServer;

public class ServerLauncher {
	
	public static void main(String[] args) {
		
		SKNet.init();
		
		SKServer server = new SKServer();
		
		server.addPacketListener(new ServerListener());
		
		try {
			server.start();
			server.bind("192.168.1.8", 6969);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true);
	}
	
}