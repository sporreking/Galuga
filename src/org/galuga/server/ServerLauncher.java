package org.galuga.server;

import org.galuga.Galuga;
import org.galuga.common.packet.Packets;
import org.galuga.server.net.Server;

import sk.net.SKNet;

public class ServerLauncher {
	
	public static void main(String[] args) {
		init();
		
		Server.start(Galuga.IP, Galuga.PORT);
		
		InputManager.start();
		
		Server.server.stop("Server closed");
	}
	
	public static void init() {
		System.out.println("Starting Galuga Server v" + Galuga.VERSION);
		SKNet.init();
		Packets.register();
	}
}