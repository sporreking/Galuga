package org.galuga.client.gamestate;

import java.util.Random;

import sk.game.Window;
import sk.gfx.Mesh;
import sk.gfx.Renderer;
import sk.gfx.Texture;
import sk.gfx.Vertex;
import sk.gfx.Vertex2D;
import sk.util.vector.Vector2f;

public class Background extends Renderer {
	
	private float speed;
	
	private float[] offsets;
	
	private float ar;
	
	public Background() {
		super(new Mesh(new Vertex2D[] {
				new Vertex2D(-.5f, .5f, 0, 0),
				new Vertex2D(.5f, .5f, 3, 0),
				new Vertex2D(.5f, -.5f, 3, 3),
				new Vertex2D(-.5f, -.5f, 0, 3)
		}, 0, 1, 3, 3, 1, 2));
		
		setTexture(TEXTURE);
		
		ar = Window.getAspectRatio();
		transform.scale = new Vector2f(2 * ar, 2 * ar);
		
		speed = .1f;
		offsets = new float[3];
	}
	
	@Override
	public void update(double delta) {
		for(int i = 0; i < offsets.length; i++) {
			offsets[i] -= delta * speed / (i + 1);
			
			if(offsets[i] <= -2 * ar)
				offsets[i] += 2 * ar;
		}
	}
	
	@Override
	public void draw() {
		for(int i = 0; i < offsets.length; i++) {
			transform.position.x = -.3f + i * .3f;
			transform.position.y = offsets[i];
			
			super.draw();
			
			transform.position.y += 2 * ar;
			
			super.draw();
		}
	}
	
	
	
	public static final Texture TEXTURE = new Texture("res/texture/stars/1.png");
	
	public static final Background INSTANCE = new Background();
}