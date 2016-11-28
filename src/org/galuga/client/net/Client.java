package org.galuga.client.net;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import sk.net.SKClient;
import sk.net.SKNet;

public class Client {
	
	private static String username;
	
	public static final boolean connect(String ip, int port) {
		
		//TODO: Remove this username setter
		System.out.print("Enter username: ");
		username = new Scanner(System.in).nextLine();
		
		try {
			client.connect(ip, port);
		} catch (UnknownHostException e) {
			System.err.println("Unknown host");
			return false;
		} catch (IOException e) {
			System.err.println("I/O error");
			return false;
		}
		
		return true;
	}
	
	public static final String getUsername() {
		return username;
	}
	
	public static final SKClient client = new SKClient();
	
	static {
		client.addPacketListener(new ClientListener(client));
	}
}