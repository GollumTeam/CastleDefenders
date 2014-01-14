package mods.castledefenders.common;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;

public class Building {
	
	public static final String PATH_IMG = "assets/castledefenders/buildings/";
	
	String name;
	
	public Building(String name) {
		this.name = name;
	}
	
	private static BufferedReader bufferedReaderFromClassPathResource(String resourcePath)
    {
        URL url = Resources.getResource(resourcePath);
        InputSupplier<InputStreamReader> readerSupplier = Resources.newReaderSupplier(url, Charsets.UTF_8);
        BufferedReader in;
        try
        {
            in = new BufferedReader(readerSupplier.getInput());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return in;
    }
	
	public InputStream getResouce (String path) throws FileNotFoundException {
		InputStream is = getClass().getResourceAsStream(path);
		InputStream is2 = Minecraft.getMinecraft().getClass().getResourceAsStream(path);
		
		if (is == null) {
			is = new FileInputStream (Minecraft.getMinecraft().mcDataDir + "/" + path);
		}
		
		return is;
	}
	
	public void getBlocksList() {
		
		try {
			InputStream is = this.getResouce(Building.PATH_IMG + this.name + ".png");
			BufferedImage image = ImageIO.read(is);
			

			return;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	
}
