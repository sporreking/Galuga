package org.galuga.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.galuga.Galuga;
import org.galuga.common.user.User;

import sk.game.Window;
import sk.gfx.Texture;
import sk.gfx.gui.GUIElement;

public class LobbySlot extends GUIElement {
	
	public static final int SIZE = (int) (256 * Window.getWidth() / 1280f);
	
	private User player;
	
	private int slot;
	
	private boolean host, you;
	
	//Textures
	private Texture t_slot;
	
	public LobbySlot(User player, int slot, float anchorY, boolean host, boolean you) {
		super(0, anchorY, (int) ((SIZE + 16) * slot - (SIZE + 16) * 1.5f), 0, SIZE, SIZE);
		
		this.player = player;
		this.slot = slot;
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
		
		font = Galuga.getFont(Math.min(((float) SIZE - 32f) / gfx.getFontMetrics(font)
				.stringWidth(player.getUsername()) * 48f, 48f));
		
		gfx.setFont(font);
		
		FontMetrics fm = gfx.getFontMetrics(font);
		
		gfx.drawString(player.getUsername(), (SIZE - fm.stringWidth(player.getUsername())) / 2,
				(SIZE + fm.getHeight() / 2) / 2);
		
		/* --- */
		
		font = Galuga.getFont(24f);
		
		gfx.setFont(font);
		
		fm = gfx.getFontMetrics(font);
		
		gfx.setColor(Color.RED);
		
		//Host
		if(host) {
			gfx.drawString("HOST", 16, SIZE - 16);
		}
		
		//You
		if(you) {
			gfx.drawString("YOU", SIZE - fm.stringWidth("YOU") - 16, SIZE - 16);
		}
		
		//Name caption
		String nameCaption = "PLAYER" + (slot + 1);
		
		gfx.drawString(nameCaption, (SIZE - fm.stringWidth(nameCaption)) / 2,
				SIZE / 3 * 2 + fm.getHeight() / 2);
		
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