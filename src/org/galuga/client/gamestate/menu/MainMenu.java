package org.galuga.client.gamestate.menu;

import java.awt.Font;

import org.galuga.client.gamestate.GameStates;

import sk.entity.Entity;
import sk.entity.Root;
import sk.gamestate.GameState;
import sk.gamestate.GameStateManager;
import sk.gfx.FontTexture;
import sk.gfx.gui.GUIButton;
import sk.util.vector.Vector3f;;

public class MainMenu implements GameState {
	
	//Buttons
	private Entity playButton;
	private Entity exitButton;
	
	//Texture
	private FontTexture ft_play;
	private FontTexture ft_exit;
	
	//Root
	private Root root;
	
	@Override
	public void init() {
		
		//Textures
		ft_play = new FontTexture("Play", 128, 128, 0, 64,
				new Font("Fixedsys", Font.BOLD, 24), new Vector3f(1, 0, 0));
		
		ft_exit = new FontTexture("Exit", 128, 128, 0, 64,
				new Font("Fixedsys", Font.BOLD, 24), new Vector3f(1, 0, 0));
		
		//Buttons
		GUIButton b_play = new GUIButton(0, .5f, 0, 0, 256, 64);
		b_play.setTexture(ft_play);
		b_play.setOnClick(() -> {
			GameStateManager.enterState(GameStates.MODE_MENU);
		});
		playButton = new Entity().add(0, b_play);
		
		GUIButton b_exit = new GUIButton(0, -.5f, 0, 0, 256, 64);
		b_exit.setTexture(ft_exit);
		b_exit.setOnClick(() -> {
			GameStateManager.enterState(GameStates.CONNECT_MENU);
		});
		exitButton = new Entity().add(0, b_exit);
		
		//Root
		root = new Root().add(0, "b_play", playButton).add(0, "b_exit", exitButton);
	}
	
	@Override
	public void draw() {
		root.draw();
	}
	
	@Override
	public void update(double delta) {
		root.update(delta);
	}
	
	@Override
	public void exit() {
		
		//Root
		root.destroy();
		
		//Texture
		ft_play.destroy();
		ft_exit.destroy();
	}
}