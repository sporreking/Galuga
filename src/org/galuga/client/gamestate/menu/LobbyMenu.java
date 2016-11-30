package org.galuga.client.gamestate.menu;

import java.awt.Font;

import org.galuga.client.gamestate.Background;
import org.galuga.client.gamestate.GameStates;
import org.galuga.client.gui.Button;
import org.galuga.client.gui.LobbySlot;
import org.galuga.client.net.Client;
import org.galuga.client.net.PacketQueue;
import org.galuga.common.GameMode;
import org.galuga.common.packet.game.PacketLeaveLobby;
import org.galuga.common.user.User;

import sk.entity.Entity;
import sk.entity.Root;
import sk.gamestate.GameState;
import sk.gamestate.GameStateManager;
import sk.gfx.FontTexture;
import sk.gfx.Texture;
import sk.gfx.gui.GUIButton;
import sk.gfx.gui.GUIElement;
import sk.sst.SST;
import sk.util.vector.Vector3f;

public class LobbyMenu implements GameState {
	
	//Lobby info
	protected int lobbyID = -1;
	protected boolean host = false;
	
	//Buttons
	private Entity startButton;
	private Entity backButton;
	
	//Root
	private Root root;
	
	//Users
	private User[] players = new User[4];
	
	@Override
	public void init() {
		
		//Buttons
		startButton = new Entity().add(0, new Button("Start", -.5f, 24, 32, () -> {
			System.out.println("Start!");
		}, 36f));
		
		backButton = new Entity().add(0, new Button("Back", -.5f, -24, 32, () -> {
			Client.client.send(new PacketLeaveLobby(lobbyID));
			lobbyID = -1;
		}, 36f));
		
		//Root
		root = new Root().add(0, "b_start", startButton).add(0, "b_back", backButton)
				.add(0, "slots", new Root());
		
		root.add(-1, "background", new Entity().add(0, new Background()));
		
		for(int i = 0; i < 4; i++) {
			setSlot(i, false);
		}
	}
	
	public void setLobbyInfo(int id, boolean host) {
		this.lobbyID = id;
		this.host = host;
	}
	
	public void setPlayerSlot(int slot, User player, boolean host) {
		players[slot % 4] = player;
		setSlot(slot % 4, host);
	}
	
	private void setSlot(int i, boolean host) {
		//Root
		Root root = (Root) this.root.get("slots");
		
		//Remove old slot
		if(root.has(Integer.toString(i))) {
			root.get(Integer.toString(i)).destroy();
			root.remove(Integer.toString(i));
		}
		
		boolean you = false;
		
		if(players[i] != null)
			you = players[i].getID() == Client.client.getConnection().getID();
		
		//Create new slot
		Entity slot = new Entity().add(0, new LobbySlot(players[i], i, 0, host, you));
		/*GUIElement t_slot = new GUIElement(0, 0, -192 + 128 * i, 0, 128, 128);
		if(players[i] == null) {
			//Add content to "r_slot" if the slot is empty
			t_slot.setTexture(Texture.DEFAULT);
		} else {
			//Add content to "r_slot" if the slot is occupied
			t_slot.setTexture(new FontTexture(players[i].getUsername(), 128, 128, 0, 64,
					new Font("Fixedsys", Font.BOLD, 24), new Vector3f(1, 0, 0)));
			
			//You
			if(players[i].getID() == Client.client.getConnection().getID()) {
				Entity you = new Entity();
				GUIElement t_you = new GUIElement(0, 0, -192 + 128 * i, -32, 128, 64);
				t_you.setTexture(new FontTexture("(you)", 128, 128, 0, 64,
						new Font("Fixedsys", Font.BOLD, 24), new Vector3f(1, 0, 0)));
				
				you.add(0, t_you);
				r_slot.add(0, "you", you);
			}
			
			//Host
			if(host) {
				Entity e_host = new Entity();
				GUIElement t_host = new GUIElement(0, 0, -192 + 128 * i, -16, 128, 64);
				t_host.setTexture(new FontTexture("(host)", 128, 128, 0, 64,
						new Font("Fixedsys", Font.BOLD, 24), new Vector3f(1, 0, 0)));
				
				e_host.add(0, t_host);
				r_slot.add(0, "host", e_host);
			}
		}
		title.add(0, t_slot);
		r_slot.add(0, "title", title);*/
		root.add(0, Integer.toString(i), slot);
	}
	
	public void setEmptySlot(int playerID) {
		for(int i = 0; i < players.length; i++) {
			if(players[i] != null && players[i].getID() == playerID) {
				players[i] = null;
				setSlot(i, false);
				break;
			}
		}
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
		
		for(int i = 0; i < players.length; i++)
			players[i] = null;
	}
}