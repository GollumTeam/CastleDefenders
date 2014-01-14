package mods.castledefenders.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Building {
	
	public static final String PATH_IMG = "assets/castledefenders/buildings/";
	
	String name;
	
	public Building(String name) {
		this.name = name;
	}
	
	
	public void getBlocksList() {
		InputStream is = getClass().getResourceAsStream(Building.PATH_IMG + this.name + ".png");
		try {
			BufferedImage image = ImageIO.read(is);
			

			return;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	
}
