package org.galuga.client.gamestate.menu;

import java.awt.Font;

import org.galuga.client.gamestate.GameStates;
import org.galuga.common.GameMode;

import sk.entity.Entity;
import sk.entity.Root;
import sk.gamestate.GameState;
import sk.gamestate.GameStateManager;
import sk.gfx.FontTexture;
import sk.gfx.gui.GUIButton;
import sk.util.vector.Vector3f;

public class ModeMenu implements GameState {
	
	//Buttons
	private Entity arcadeButton;
	private Entity backButton;
	
	//Texture
	private FontTexture ft_arcade;
	private FontTexture ft_back;
	
	//Root
	private Root root;
	
	@Override
	public void init() {
		
		//Textures
		ft_arcade = new FontTexture("Arcade", 128, 128, 0, 64,
				new Font("Fixedsys", Font.BOLD, 24), new Vector3f(1, 0, 0));
		
		ft_back = new FontTexture("Back", 128, 128, 0, 64,
				new Font("Fixedsys", Font.BOLD, 24), new Vector3f(1, 0, 0));
		
		//Buttons
		GUIButton b_arcade = new GUIButton(0, .5f, 0, 0, 256, 64);
		b_arcade.setTexture(ft_arcade);
		b_arcade.setOnClick(() -> {
			GameStates.GAME_PICK_MENU.selectedMode = GameMode.ARCADE;
			GameStateManager.enterState(GameStates.GAME_PICK_MENU);
		});
		arcadeButton = new Entity().add(0, b_arcade);
		
		GUIButton b_back = new GUIButton(0, -.5f, 0, 0, 256, 64);
		b_back.setTexture(ft_back);
		b_back.setOnClick(() -> {
			GameStateManager.enterState(GameStates.MAIN_MENU);
		});
		backButton = new Entity().add(0, b_back);
		
		//Root
		root = new Root().add(0, "b_arcade", arcadeButton).add(0, "b_back", backButton);
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
		ft_arcade.destroy();
		ft_back.destroy();
	}
}