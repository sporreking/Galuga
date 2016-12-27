package org.galuga.common;

public class GameMode {
	public static final int ARCADE = 0;
	
	//Current only used by client
	private static int current = -1;
	
	public static final int current() {
		return current;
	}
	
	public static final void setCurrent(int mode) {
		current = mode;
	}
}