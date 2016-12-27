package org.galuga.client.game;

import org.galuga.common.user.User;

import sk.entity.Entity;
import sk.entity.component.AABB;
import sk.gfx.Mesh;
import sk.gfx.Renderer;
import sk.gfx.Transform;

public class OtherPlayer extends Entity {
	
	private User user;
	
	public OtherPlayer(User user) {
		this.user = user;
		
		Transform transform = new Transform();
		transform.scale.x = Player.WIDTH;
		transform.scale.y = Player.HEIGHT;
		
		add(0, transform);
		add(0, new Mover());
		add(1, new AABB(Player.WIDTH, Player.HEIGHT));
		add(2, new Renderer(Mesh.QUAD));
	}
}