package org.galuga.client.net;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import org.galuga.client.gamestate.GameStates;
import org.galuga.client.gamestate.game.ArcadeGame;
import org.galuga.common.GameMode;
import org.galuga.common.packet.game.PacketSetVelocity;
import org.galuga.common.packet.lobby.PacketFetchLobby;
import org.galuga.common.packet.lobby.PacketFetchUser;
import org.galuga.common.packet.lobby.PacketUserLeft;
import org.galuga.common.user.User;

public class GameQueue {
	
	private static final PriorityBlockingQueue<Object> packets =
			new PriorityBlockingQueue<>(1, new Comparator<Object>() {
				@Override
		        public int compare(Object o1, Object o2) {
		            return 0;
		        }
			});
	
	public static final void add(Object packet) {
		packets.put(packet);
	}
	
	public static final synchronized void poll() {
		while(!packets.isEmpty()) {
			Object packet = packets.poll();
			
			//Set velocity
			if(packet instanceof PacketSetVelocity) {
				PacketSetVelocity p = (PacketSetVelocity) packet;
				
				new Thread(() -> {
						switch(GameMode.current()) {
						case GameMode.ARCADE:
							GameStates.ARCADE_GAME.setVelocity(p.ID, p.START_X, p.START_Y, p.DX, p.DY);
							break;
						}
					}
				).start();
			}
		}
	}
}