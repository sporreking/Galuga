package org.galuga.client.game;

import org.galuga.client.net.Client;
import org.galuga.common.packet.game.PacketSetVelocity;
import org.lwjgl.glfw.GLFW;

import sk.entity.Component;
import sk.gfx.Transform;
import sk.util.io.Keyboard;
import sk.util.vector.Vector2f;

public class PlayerController extends Component {
	
	private Player player;
	
	private Vector2f velocity;
	
	private float speed = 1.0f;
	
	private Transform transform;
	
	public PlayerController(Player player) {
		this.player = player;
		
		velocity = new Vector2f();
	}
	
	@Override
	public void init() {
		transform = player.get(Transform.class);
	}
	
	@Override
	public void update(double delta) {
		
		boolean velocityChanged = false;
		
		//Vertical input pressed
		if(Keyboard.pressed(GLFW.GLFW_KEY_A)) {
			//Left
			
			velocity.x = (float) -(speed);
			
			velocityChanged = true;
			
		} else if(Keyboard.pressed(GLFW.GLFW_KEY_D)) {
			//Right
			
			velocity.x = (float) (speed);
			
			velocityChanged = true;
		}
		
		//Horizontal input pressed
		if(Keyboard.pressed(GLFW.GLFW_KEY_W)) {
			//Up
			
			velocity.y = (float) (speed);
			
			velocityChanged = true;
			
		} else if(Keyboard.pressed(GLFW.GLFW_KEY_S)) {
			//Down
			
			velocity.y = (float) -(speed);
			
			velocityChanged = true;
		}
		
		//Vertical input released
		if(Keyboard.released(GLFW.GLFW_KEY_A) || Keyboard.released(GLFW.GLFW_KEY_D)) {
			
			velocity.x = 0;
			
			velocityChanged = true;
			
		}
		
		//Horizontal input released
		if(Keyboard.released(GLFW.GLFW_KEY_W) || Keyboard.released(GLFW.GLFW_KEY_S)) {
			
			velocity.y = 0;
			
			velocityChanged = true;
			
		}
		
		//Movement
		transform.position.x += velocity.x * delta;
		transform.position.y += velocity.y * delta;
		
		if(velocityChanged) {
			Client.client.send(new PacketSetVelocity(Client.getID(), transform.position.x, transform.position.y,
					velocity.x, velocity.y));
		}
	}
}