package org.galuga.server.game;

import java.util.HashMap;

import org.galuga.common.GameMode;
import org.galuga.common.user.User;

public class GameManager {
	
	private static final HashMap<Integer, Game> games = new HashMap<>();
	
	public static final void create(Lobby lobby) {
		int i = 0;
		
		boolean search = true;
		while(search) {
			search = false;
			for(int j : games.keySet())
				if(j == i)
					search = true;
			
			if(search)
				i++;
		}
		
		switch(lobby.MODE) {
		case GameMode.ARCADE:
			games.put(i, new Arcade(i, lobby));
			break;
		}
	}
	
	public static final void setVelocity(int playerID, float startX, float startY, float dx, float dy) {
		for(Game g : games.values()) {
			for(User p : g.players) {
				if(p.getID() == playerID) {
					
					g.setVelocity(playerID, startX, startY, dx, dy);
					
					return;
				}
			}
		}
	}
	
}