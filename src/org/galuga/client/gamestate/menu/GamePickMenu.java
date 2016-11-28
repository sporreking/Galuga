package org.galuga.client.gamestate.menu;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import org.galuga.client.gamestate.GameStates;
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

public class GamePickMenu implements GameState {
	
	private ArrayList<LobbyListItem> lobbyList;
	
	//GameMode
	protected int selectedMode;
	
	//Buttons
	private Entity backButton;
	private Entity refreshButton;
	private Entity createButton;
	
	//Texture
	private FontTexture ft_create;
	private FontTexture ft_refresh;
	private FontTexture ft_back;
	
	private ArrayList<Texture> gameTextures;
	
	//Root
	private Root root;
	
	@Override
	public void init() {
		
		//Game list
		lobbyList = new ArrayList<>();
		gameTextures = new ArrayList<>();
		
		//Textures
		ft_create = new FontTexture("Create", 128, 16, 0, 16,
				new Font("Fixedsys", Font.BOLD, 24), new Vector3f(1, 0, 0));
		
		ft_refresh = new FontTexture("Refresh", 128, 16, 0, 16,
				new Font("Fixedsys", Font.BOLD, 24), new Vector3f(1, 0, 0));
		
		ft_back = new FontTexture("Back", 128, 16, 0, 16,
				new Font("Fixedsys", Font.BOLD, 24), new Vector3f(1, 0, 0));
		
		//Buttons
		GUIButton b_create = new GUIButton(-.5f, -.5f, 64 * 2, 0, 64, 16);
		b_create.setTexture(ft_create);
		b_create.setOnClick(() -> {
			//Tell server to create game lobby
			Client.client.send(new PacketCreateLobby("Lobby", selectedMode));
		});
		createButton = new Entity().add(0, b_create);
		
		GUIButton b_refresh = new GUIButton(-.5f, -.5f, 64 * 1, 0, 64, 16);
		b_refresh.setTexture(ft_refresh);
		b_refresh.setOnClick(() -> {
			refresh();
		});
		refreshButton = new Entity().add(0, b_refresh);
		
		GUIButton b_back = new GUIButton(-.5f, -.5f, 64 * 0, 0, 64, 16);
		b_back.setTexture(ft_back);
		b_back.setOnClick(() -> {
			GameStateManager.enterState(GameStates.MODE_MENU);
		});
		backButton = new Entity().add(0, b_back);
		
		//Root
		root = new Root().add(0, "b_create", createButton).add(0, "b_back", backButton)
				.add(0, "b_refresh", refreshButton).add(0, "lobby_list", new Container());
		
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
		for(Texture t : gameTextures)
			t.destroy();
		gameTextures.clear();
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
		lobbyList.add(new LobbyListItem(id, name, players));
		
		Root root = new Root();
		
		Entity button = new Entity();
		FontTexture texture = new FontTexture(lobbyList.get(lobbyList.size() - 1).toString(),
				128, 16, 0, 8, new Font("Fixedsys", Font.BOLD, 11), new Vector3f(1, 0, 0));
		GUIButton b_button = new GUIButton(0, .75f,
				0, -(lobbyList.size() - 1) * 16, 128, 16);
		gameTextures.add(texture);
		b_button.setTexture(texture);
		b_button.setOnClick(() -> {
			Client.client.send(new PacketJoinLobby(id, false));
		});
		button.add(0, b_button);
		
		root.add(0, "button", button);
		
		((Container) this.root.get("lobby_list")).add(root);
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
		
		//Texture
		ft_create.destroy();
		ft_back.destroy();
		
		//Game list
		lobbyList.clear();
		for(Texture t : gameTextures)
			t.destroy();
		gameTextures.clear();
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