package org.galuga.client.gamestate.menu;

import java.awt.Font;

import org.galuga.client.gamestate.GameStates;
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
	
	//Texture
	private FontTexture ft_connect;
	
	@Override
	public void init() {
		
		//Close connection if connected
		if(Client.client.getConnection() != null &&
				!Client.client.getConnection().isSocketClosed()) {
			Client.client.disconnect("Logged out");
		}
		
		//Texture
		ft_connect = new FontTexture("Connect", 128, 128, 0, 64,
				new Font("Fixedsys", Font.BOLD, 24), new Vector3f(1, 0, 0));
		
		//Connect button
		GUIButton b_connect = new GUIButton(0, 0, 0, 0, 100, 100);
		b_connect.setTexture(ft_connect);
		b_connect.setOnClick(() -> {
			if(Client.connect(Galuga.IP, Galuga.PORT))
				GameStateManager.enterState(GameStates.MAIN_MENU);
		});
		connectButton = new Entity().add(0, b_connect);
		
		
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
		
		//Texture
		ft_connect.destroy();
	}
}