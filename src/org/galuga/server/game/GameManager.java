package org.galuga.server.game;

import java.util.HashMap;

import org.galuga.common.GameMode;

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
	
}