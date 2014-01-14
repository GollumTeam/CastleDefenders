package mods.castledefenders.common;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import com.google.common.io.Resources;

import net.minecraft.client.Minecraft;

public class Building {
	
	public static final String PATH_IMG = "assets/castledefenders/buildings/";
	
	String name;
	
	public Building(String name) {
		this.name = name;
	}
	
	public InputStream getResource (String path) throws FileNotFoundException {
		
		InputStream is = getClass().getResourceAsStream(path);
		
		if (is == null) {
			ModCastleDefenders.log.warning ("Failed to read resource '" + path + "' in jar. read by path file");
			is = new FileInputStream (Minecraft.getMinecraft().mcDataDir + "/" + path);
		}
		
		if (is != null) {
			ModCastleDefenders.log.info ("Read resource '" + path + "' in jar");
		}
		
		return is;
	}
	
	public void getBlocksList() throws IOException {
		
		try {
			InputStream is = this.getResource(Building.PATH_IMG + this.name + ".png");
			BufferedImage image = ImageIO.read(is);
			

			return;
			
		} catch (IOException e) {
			ModCastleDefenders.log.severe ("Error to read resource in jar");
			throw e;
		}
	}
	
	
}
