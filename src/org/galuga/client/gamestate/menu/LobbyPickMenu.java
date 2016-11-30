package org.galuga.client.gamestate.menu;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import org.galuga.client.gamestate.Background;
import org.galuga.client.gamestate.GameStates;
import org.galuga.client.gui.Button;
import org.galuga.client.net.Client;
import org.galuga.client.net.PacketQueue;
import org.galuga.common.GameMode;
import org.galuga.common.packet.game.PacketCreateLobby;
import org.galuga.common.packet.game.PacketJoinLobby;
import org.galuga.common.packet.game.PacketRequestLobbys;

import sk.entity.Container;
import sk.entity.Entity;
import sk.entity.Root;
import sk.gamestate.GameState;
import sk.gamestate.GameStateManager;
import sk.gfx.FontTexture;
import sk.gfx.Texture;
import sk.gfx.gui.GUIButton;
import sk.util.vector.Vector3f;

public class LobbyPickMenu implements GameState {
	
	private ArrayList<LobbyListItem> lobbyList;
	
	//GameMode
	protected int selectedMode;
	
	//Buttons
	private Entity backButton;
	private Entity refreshButton;
	private Entity createButton;
	
	//Root
	private Root root;
	
	@Override
	public void init() {
		
		//Game list
		lobbyList = new ArrayList<>();
		
		//Buttons
		createButton = new Entity().add(0, new Button("Create", -.5f, 48, 32, () -> {
			//Tell server to create game lobby
			Client.client.send(new PacketCreateLobby("Lobby", selectedMode));
		}, 36f));
		
		refreshButton = new Entity().add(0, new Button("Refresh", -.5f, 0, 32, () -> {
			refresh();
		}, 36f));
		
		backButton = new Entity().add(0, new Button("Back", -.5f, -48, 32, () -> {
			GameStateManager.enterState(GameStates.MODE_MENU);
		}, 36f));
		
		//Root
		root = new Root().add(0, "b_create", createButton).add(0, "b_back", backButton)
				.add(0, "b_refresh", refreshButton).add(0, "lobby_list", new Container());
		
		root.add(-1, "background", new Entity().add(0, new Background()));
		
		//Request games
		refresh();
	}
	
	/**
	 * 
	 * Requests list of games from server. Clears previous list and removes old textures.
	 * 
	 */
	public void refresh() {
		lobbyList.clear();
		((Container) root.get("lobby_list")).destroy();
		Client.client.send(new PacketRequestLobbys(GameMode.ARCADE));
	}
	
	/**
	 * 
	 * Adds a new game list item.
	 * 
	 * @param id the game id.
	 * @param name the name of the game.
	 * @param players the amount of players in this lobby.
	 */
	public synchronized void add(int id, String name, int players) {
		LobbyListItem lobbyListItem = new LobbyListItem(id, name, players);
		
		lobbyList.add(lobbyListItem);
		
		Entity button = new Entity().add(0, new Button(lobbyListItem.toString(), .75f,
				-(lobbyList.size() - 1) * 32, 32, () -> {
			Client.client.send(new PacketJoinLobby(id, false));
		}, 36f));
		
		((Container) this.root.get("lobby_list")).add(button);
	}
	
	@Override
	public void draw() {
		root.draw();
	}
	
	@Override
	public void update(double delta) {
		PacketQueue.poll();
		root.update(delta);
	}
	
	@Override
	public void exit() {
		
		//Root
		root.destroy();
		
		//Lobby list
		lobbyList.clear();
	}
	
	public static class LobbyListItem {
		
		public final int ID;
		public final String NAME;
		public final int PLAYERS;
		
		public LobbyListItem(int id, String name, int players) {
			this.ID = id;
			this.NAME = name;
			this.PLAYERS = players;
		}
		
		@Override
		public String toString() {
			return NAME + ": " + PLAYERS + "/4";
		}
	}
}