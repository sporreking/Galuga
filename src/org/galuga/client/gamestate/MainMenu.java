package org.galuga.client.gamestate;

import java.awt.Font;

import org.galuga.client.net.Client;

import sk.entity.Entity;
import sk.gamestate.GameState;
import sk.gfx.FontTexture;
import sk.gfx.gui.GUIButton;
import sk.util.vector.Vector3f;

public class MainMenu implements GameState {
	
	//Buttons
	private Entity connectButton;
	
	//Texture
	private FontTexture ft_connect;
	
	@Override
	public void init() {
		
		//Texture
		ft_connect = new FontTexture("Connect", 128, 128, 0, 64,
				new Font("Fixedsys", Font.BOLD, 11), new Vector3f(1, 0, 0));
		
		//Connect button
		GUIButton b_connect = new GUIButton(0, 0, 0, 0, 100, 100);
		b_connect.setTexture(ft_connect);
		b_connect.setOnClick(() -> Client.connect("192.168.1.8", 6969));
		connectButton = new Entity().add(0, b_connect);
		
		
	}
	
	@Override
	public void draw() {
		connectButton.draw();
	}
	
	@Override
	public void update(double delta) {
		connectButton.update(delta);
	}
	
	@Override
	public void exit() {
		
		//Connect button
		connectButton.destroy();
		
		//Texture
		ft_connect.destroy();
	}
}