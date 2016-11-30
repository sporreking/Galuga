package org.galuga.client.gui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.galuga.Galuga;
import org.galuga.common.user.User;

import sk.gfx.Texture;
import sk.gfx.gui.GUIElement;

public class LobbySlot extends GUIElement {
	
	public static final int SIZE = 128;
	
	private User player;
	
	private boolean host, you;
	
	//Textures
	private Texture t_slot;
	
	public LobbySlot(User player, int slot, float anchorY, boolean host, boolean you) {
		super(0, anchorY, (int) ((SIZE + 16) * slot - (SIZE + 16) * 1.5f), 0, SIZE, SIZE);
		
		this.player = player;
		this.host = host;
		this.you = you;
		
		
		setTexture(TEXTURE_NEUTRAL);
	}
	
	@Override
	public void init() {
		super.init();
		
		if(player == null)
			return;
		
		createSlotTextures();
	}
	
	private void createSlotTextures() {
		
		BufferedImage img = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
		
		Graphics gfx = img.getGraphics();
		
		Font font;
		
		//Caption
		font = Galuga.getFont(48f);
		
		gfx.setFont(font);
		
		FontMetrics fm = gfx.getFontMetrics();
		
		gfx.drawString(player.getUsername(), (SIZE - fm.stringWidth(player.getUsername())) / 2,
				(SIZE + fm.getHeight() / 2) / 2);
		
		
		//Create texture
		int[] pixels = new int[img.getWidth() * img.getHeight()];
		
		img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());
		
		t_slot = new Texture().generate(img.getWidth(), img.getHeight(), pixels);
	}
	
	@Override
	public void draw() {
		super.draw();
		
		if(player == null)
			return;
		
		t_slot.bind();
		
		getMesh().draw();
	}
	
	@Override
	public void exit() {
		super.exit();
		
		if(player == null)
			return;
		
		t_slot.destroy();
	}
	
	public static final Texture TEXTURE_NEUTRAL = new Texture()
			.generate(1, 1, new int[] { 0x80000000 });
}