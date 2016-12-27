package org.galuga.client.game;

import sk.entity.Component;
import sk.gfx.Transform;
import sk.util.vector.Vector2f;

public class Mover extends Component {
	
	private Vector2f velocity = new Vector2f();
	
	private Transform transform;
	
	@Override
	public void init() {
		transform = getParent().get(Transform.class);
	}
	
	@Override
	public void update(double delta) {
		transform.position.x += velocity.x * delta;
		transform.position.y += velocity.y * delta;
	}
	
	public void setVelocity(float x, float y) {
		this.velocity.x = x;
		this.velocity.y = y;
	}
}