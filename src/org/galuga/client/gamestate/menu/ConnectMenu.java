package org.galuga.client.gamestate.menu;

import java.awt.Font;

import org.galuga.client.gamestate.GameStates;
import org.galuga.client.gui.Button;
import org.galuga.client.net.Client;

import sk.entity.Entity;
import sk.game.Game;
import sk.gamestate.GameState;
import sk.gamestate.GameStateManager;
import sk.gfx.FontTexture;
import sk.gfx.gui.GUIButton;
import sk.util.vector.Vector3f;

import org.galuga.Galuga;

public class ConnectMenu implements GameState {
	
	//Buttons
	private Entity connectButton;
	
	@Override
	public void init() {
		
		//Close connection if connected
		if(Client.client.getConnection() != null &&
				!Client.client.getConnection().isSocketClosed()) {
			Client.client.disconnect("Logged out");
		}
		
		//Connect button
		connectButton = new Entity().add(0, new Button("Connect", 0, 0, 64, () -> {
			if(Client.connect(Galuga.getIP(), Galuga.PORT))
				GameStateManager.enterState(GameStates.MAIN_MENU);
		}));
	}
	
	@Override
	public void draw() {
		connectButton.draw();
	}
	
	@Override
	public void update(double delta) {
		
		connectButton.update(delta);
		
		if(Client.client.getConnection() != null
				&& !Client.client.getConnection().isSocketClosed()) {
			
		}
	}
	
	@Override
	public void exit() {
		
		//Close connection
		if(Client.client.getConnection() != null &&
				!Client.client.getConnection().isSocketClosed() && !Game.isRunning()) {
			Client.client.disconnect("Logged out");
		}
		
		//Connect button
		connectButton.destroy();
	}
}