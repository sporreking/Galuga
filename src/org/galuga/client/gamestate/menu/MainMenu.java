package org.galuga.client.gamestate.menu;

import org.galuga.client.gamestate.Background;
import org.galuga.client.gamestate.GameStates;
import org.galuga.client.gui.Button;

import sk.entity.Entity;
import sk.entity.Root;
import sk.gamestate.GameState;
import sk.gamestate.GameStateManager;

public class MainMenu implements GameState {
	
	//Buttons
	private Entity playButton;
	private Entity settingsButton;
	private Entity exitButton;
	
	//Root
	private Root root;
	
	@Override
	public void init() {
		
		//Buttons
		playButton = new Entity().add(0, new Button("Play", 0, 128, 64, () -> {
			GameStateManager.enterState(GameStates.MODE_MENU);
		}));
		
		settingsButton = new Entity().add(0, new Button("Settings", 0, 0, 64, () -> {
			System.out.println("TODO: Settings");
		}));
		
		exitButton = new Entity().add(0, new Button("Exit", 0, -128, 64, () -> {
			GameStateManager.enterState(GameStates.CONNECT_MENU);
		}));
		
		//Root
		root = new Root().add(0, "b_play", playButton)
				.add(0, "b_settings", settingsButton).add(0, "b_exit", exitButton);
		
		root.add(-1, "background", new Entity().add(0, new Background()));
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