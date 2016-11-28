package org.galuga.client.net;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import org.galuga.client.gamestate.GameStates;
import org.galuga.common.packet.game.PacketFetchLobby;
import org.galuga.common.packet.game.PacketFetchUser;
import org.galuga.common.packet.game.PacketUserLeft;
import org.galuga.common.user.User;

public class PacketQueue {
	
	private static final PriorityBlockingQueue<Object> packets =
			new PriorityBlockingQueue<>(1, new Comparator<Object>() {
				@Override
		        public int compare(Object o1, Object o2) {
		            return 0;
		        }
			});
	
	public static final synchronized void add(Object packet) {
		packets.put(packet);
	}
	
	public static final synchronized void poll() {
		while(!packets.isEmpty()) {
			Object packet = packets.poll();
			
			//Fetch lobby
			if(packet instanceof PacketFetchLobby) {
				PacketFetchLobby p = (PacketFetchLobby) packet;
				GameStates.GAME_PICK_MENU.add(p.ID, p.NAME, p.PLAYERS);
			}
			
			//Fetch user
			else if(packet instanceof PacketFetchUser) {
				PacketFetchUser p = (PacketFetchUser) packet;
				GameStates.LOBBY_MENU.setPlayerSlot(p.SLOT, new User(p.ID, p.NAME), p.HOST);
			}
			
			//User left
			else if(packet instanceof PacketUserLeft) {
				PacketUserLeft p = (PacketUserLeft) packet;
				GameStates.LOBBY_MENU.setEmptySlot(p.ID);
			}
		}
	}
	
}