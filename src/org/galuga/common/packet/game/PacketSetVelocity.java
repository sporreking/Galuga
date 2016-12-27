package org.galuga.common.packet.game;

public class PacketSetVelocity {
	
	public final int ID;
	
	public final float START_X;
	public final float START_Y;
	public final float DX;
	public final float DY;
	
	public PacketSetVelocity(int id, float startX, float startY, float dx, float dy) {
		this.ID = id;
		this.START_X = startX;
		this.START_Y = startY;
		this.DX = dx;
		this.DY = dy;
	}
	
}