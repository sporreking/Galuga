package org.galuga.client.gamestate;

import sk.gfx.Mesh;
import sk.gfx.Renderer;
import sk.gfx.Texture;
import sk.util.vector.Vector2f;

public class Background extends Renderer {
	
	public Background() {
		super(Mesh.QUAD);
		setTexture(TEXTURE_DEFAULT);
		camera.createOrtho(-1, 1, 1, -1);
		transform.scale = new Vector2f(2, 2);
	}
	
	public static final Texture TEXTURE_DEFAULT = new Texture()
			.generate("res/texture/temp_bg.png");
}