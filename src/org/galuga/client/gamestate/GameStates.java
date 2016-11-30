package org.galuga.client.gamestate;

import org.galuga.client.gamestate.menu.ConnectMenu;
import org.galuga.client.gamestate.menu.LobbyPickMenu;
import org.galuga.client.gamestate.menu.LobbyMenu;
import org.galuga.client.gamestate.menu.MainMenu;
import org.galuga.client.gamestate.menu.ModeMenu;

import sk.gamestate.GameState;

public class GameStates {
	
	public static final ConnectMenu CONNECT_MENU = new ConnectMenu();
	public static final MainMenu MAIN_MENU = new MainMenu();
	public static final ModeMenu MODE_MENU = new ModeMenu();
	public static final LobbyPickMenu LOBBY_PICK_MENU = new LobbyPickMenu();
	public static final LobbyMenu LOBBY_MENU = new LobbyMenu();
}