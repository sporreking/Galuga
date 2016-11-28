package org.galuga.common.user;

public class User {
	
	private int id;
	private String username;
	
	public User(int id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public int getID() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
}