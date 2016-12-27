package org.galuga.client.game;

import org.galuga.common.user.User;

import sk.entity.Entity;
import sk.entity.component.AABB;
import sk.gfx.Mesh;
import sk.gfx.Renderer;
import sk.gfx.Transform;

public class Player extends Entity {
	
	public static final float WIDTH = .2f;
	public static final float HEIGHT = .4f;
	
	private User user;
	
	public Player(User user) {
		this.user = user;
		
		Transform transform = new Transform();
		transform.scale.x = WIDTH;
		transform.scale.y = HEIGHT;
		
		add(0, transform);
		add(0, new PlayerController(this));
		add(1, new AABB(WIDTH, HEIGHT));
		add(2, new Renderer(Mesh.QUAD));
	}
}