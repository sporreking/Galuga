package org.galuga.server.net;

import java.io.IOException;
import java.util.HashMap;

import org.galuga.common.user.User;

import sk.net.SKServer;

public class Server {
	
	private static final HashMap<Integer, User> users = new HashMap<>();
	
	public static final void start(String ip, int port) {
		
		server.setTimeout(1000);
		
		try {
			server.start();
			server.bind(ip, port);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static final synchronized void addUser(int id, String name) {
		if(users.get(id) != null) {
			System.err.println("User with ID " + id + " is already registered");
			System.err.println("Removing user " + id + " from server due to illegal behaviour");
			
			if(server.getConnection(id) != null) {
				server.disconnect(id, "Illegal behaviour");
			}
			
			users.remove(id);
		} else {
			users.put(id, new User(id, name));
		}
	}
	
	public static final User getUser(int id) {
		return users.get(id);
	}
	
	public static final boolean isRegistered(int id) {
		return users.get(id) != null;
	}
	
	public static final void kick(int id, String msg) {
		server.disconnect(id, msg);
	}
	
	public static final void removeUser(int id) {
		users.remove(id);
	}
	
	public static final void list() {
		HashMap<Integer, User> users = (HashMap<Integer, User>) Server.users.clone();
		
		for(User u : users.values())
			System.out.print(u.getUsername() + "(" + u.getID() + ")");
		
		System.out.println();
	}
	
	public static final SKServer server = new SKServer();
	
	static {
		server.addPacketListener(new ServerListener(server));
	}
}