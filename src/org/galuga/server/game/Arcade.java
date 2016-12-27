package org.galuga.server.game;

import org.galuga.common.GameMode;

public class Arcade extends Game {
	
	public Arcade(int id, Lobby lobby) {
		super(id, lobby);
		
		
	}
	
	
	@Override
	public int mode() {
		return GameMode.ARCADE;
	}
}