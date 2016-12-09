package org.galuga.server;

import java.util.HashMap;
import java.util.Scanner;

import org.galuga.server.cmd.Action;
import org.galuga.server.cmd.Command;
import org.galuga.server.cmd.Parameter;
import org.galuga.server.game.LobbyList;
import org.galuga.server.net.Server;

public final class InputManager {
	
	private static volatile boolean running = false;
	
	private static final Scanner scanner = new Scanner(System.in);
	
	private static final HashMap<String, Command> commands = new HashMap<>();
	
	public static final void start() {
		
		registerCommands();
		
		running = true;
		while(running) {
			String[] line = scanner.nextLine().split("[ ]+");
			
			String name = line[0];
			String[] args = new String[line.length - 1];
			
			for(int i = 1; i < line.length; i++)
				args[i - 1] = line[i];
			
			if(commands.get(name) == null)
				System.err.println("Unrecognized command: " + name);
			else
				commands.get(name).fire(args);
		}
	}
	
	private static final void registerCommands() {
		
		//Exit command
		registerCommand((args) -> {
			running = false;
		}, "stop");
		
		//Kick Command
		registerCommand((args) -> {
			
			Server.kick(Integer.parseInt(args[0]), args[1]);
			
		}, "kick", new Parameter<>("client", Integer.class),
				new Parameter<>("msg", String.class));
		
		//List Command
		registerCommand((args) -> {
			Server.list();
		}, "list");
		
		//Games Command
		registerCommand((args) -> {
			LobbyList.print();
		}, "lobbys");
	}
	
	private static final void registerCommand(Action action, String name, Parameter<?>... params) {
		commands.put(name, new Command(action, name, params));
	}
	
}