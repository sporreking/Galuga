package org.galuga.client;

import org.galuga.client.gamestate.GameStates;
import org.galuga.common.packet.Packets;

import sk.audio.AudioManager;
import sk.game.Game;
import sk.game.GameProperties;
import sk.net.SKNet;

public class ClientLauncher {
	
	public static void main(String[] args) {
		
		GameProperties properties = new GameProperties();
		
		properties.title = "Galuga - v1.0";
		properties.startState = GameStates.CONNECT_MENU;
		
		AudioManager.start();
		
		SKNet.init();
		Packets.register();
		
		Game.start(properties);
		
	}
}