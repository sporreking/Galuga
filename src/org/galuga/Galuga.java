package org.galuga;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public final class Galuga {
	public static final String VERSION = "1.0";
	
	private static String ip = "127.0.0.1";
	public static final int PORT = 6969;
	
	public static final Font getFont(float size) {
		return font.deriveFont(size);
	}
	
	public static final void setIP(String ip) {
		Galuga.ip = ip;
	}
	
	public static final String getIP() {
		return ip;
	}
	
	private static Font font;
	
	static {
		
		//Load font
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("res/pixie.otf"));
		} catch (FontFormatException e) {
			System.err.println("Invalid font format!");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Could not load font file!");
			e.printStackTrace();
		}
	}
}