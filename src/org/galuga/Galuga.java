package org.galuga;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public final class Galuga {
	public static final String VERSION = "1.0";
	
	public static final String IP = "127.0.0.1";
	public static final int PORT = 6969;
	
	public static final Font getFont(float size) {
		return font.deriveFont(size);
	}
	
	private static Font font;
	
	static {
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("res/fixedsys.ttf"));
		} catch (FontFormatException e) {
			System.err.println("Invalid font format!");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Could not load font file!");
			e.printStackTrace();
		}
	}
}