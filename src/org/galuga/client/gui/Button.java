package org.galuga.client.gui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.galuga.Galuga;

import sk.game.Window;
import sk.gfx.Texture;
import sk.gfx.gui.Event;
import sk.gfx.gui.GUIButton;

public class Button extends GUIButton {
	
	private Texture t_caption;
	private String caption;
	private float fontSize;
	
	public Button(String caption, float anchorY, int offsetY, int height, Event event) {
		this(caption, anchorY, offsetY, height, event, 48f);
	}
	
	public Button(String caption, float anchorY, int offsetY, int height, Event event, float fontSize) {
		super(0, anchorY, 0, offsetY, Window.getWidth(), height);
		
		setTexture(TEXTURE_NEUTRAL);
		
		this.caption = caption;
		this.fontSize = fontSize;
		
		setOnClick(event);
		
		setOnHover(() -> {
			setTexture(TEXTURE_SELECTED);
		});
		
		setOnUnhover(() -> {
			setTexture(TEXTURE_NEUTRAL);
		});
	}
	
	@Override
	public void init() {
		super.init();
		
		createFontTexture();
	}
	
	private void createFontTexture() {
		
		Font font = Galuga.getFont(fontSize);
		
		BufferedImage img = new BufferedImage(Window.getWidth(), height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics gfx = img.getGraphics();
		
		gfx.setFont(font);
		FontMetrics fm = gfx.getFontMetrics();
		
		gfx.drawString(caption, (img.getWidth() - fm.stringWidth(caption)) / 2,
				(img.getHeight() + fm.getHeight() / 2) / 2);
		
		int[] pixels = new int[img.getWidth() * img.getHeight()];
		
		img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());
		
		t_caption = new Texture().generate(img.getWidth(), img.getHeight(), pixels);
	}
	
	@Override
	public void draw() {
		super.draw();
		
		t_caption.bind();
		getMesh().draw();
	}
	
	@Override
	public void exit() {
		super.exit();
		
		t_caption.destroy();
	}
	
	//ARGB
	public static final Texture TEXTURE_NEUTRAL = new Texture()
			.generate(1, 1, new int[] { 0x00000000 });
	
	public static final Texture TEXTURE_SELECTED = new Texture()
			.generate(1, 1, new int[] { 0x80ff0000 });
}