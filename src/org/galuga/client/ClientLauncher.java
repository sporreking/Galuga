package org.galuga.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.galuga.Galuga;
import org.galuga.client.gamestate.GameStates;
import org.galuga.client.net.Client;
import org.galuga.common.packet.Packets;

import sk.audio.Audio;
import sk.audio.AudioManager;
import sk.game.Game;
import sk.game.GameProperties;
import sk.net.SKNet;
import sk.util.vector.Vector4f;

public class ClientLauncher {
	
	public static void main(String[] args) {
		
		GameProperties properties = new GameProperties();
		
		properties.title = "Galuga - v1.0";
		properties.startState = GameStates.CONNECT_MENU;
		properties.width = 1280;
		properties.height = 720;
		properties.clearColor = new Vector4f(35f / 255f, 39f / 255f, 68f / 255f, 1f);
		
		AudioManager.start();
		
		Audio audio = new Audio("res/audio/elevator.wav");
		
//		AudioManager.playSource(0, 1, 1, audio, true);
		
		SKNet.init();
		Packets.register();
		
		load();
		
		Game.start(properties);
		
		audio.destroy();
		
	}
	
	private static final void load() {
		try {
			Scanner scanner = new Scanner(new File("galuga.txt"));
			
			Galuga.setIP(scanner.nextLine());
			
			Client.setUsername(scanner.nextLine());
			
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}