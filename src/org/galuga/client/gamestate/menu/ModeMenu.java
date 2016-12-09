package org.galuga.client.gamestate.menu;

import java.awt.Font;

import org.galuga.client.gamestate.Background;
import org.galuga.client.gamestate.GameStates;
import org.galuga.client.gui.Button;
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
	
	//Root
	private Root root;
	
	@Override
	public void init() {
		
		//Buttons
		arcadeButton = new Entity().add(0, new Button("Arcade", .25f, 0, 64, () -> {
			GameStates.LOBBY_PICK_MENU.selectedMode = GameMode.ARCADE;
			GameStateManager.enterState(GameStates.LOBBY_PICK_MENU);
		}));
		
		backButton = new Entity().add(0, new Button("Back", -.25f, 0, 64, () -> {
			GameStateManager.enterState(GameStates.MAIN_MENU);
		}));
		
		//Root
		root = new Root().add(0, "b_arcade", arcadeButton).add(0, "b_back", backButton);
		
		root.add(-1, "background", new Entity().add(0, Background.INSTANCE));
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
	}
}