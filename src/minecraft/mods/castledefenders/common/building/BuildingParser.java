package mods.castledefenders.common.building;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import mods.castledefenders.common.ModCastleDefenders;
import net.minecraft.client.Minecraft;
import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.jdom.JsonStringNode;
import argo.saj.InvalidSyntaxException;

public class BuildingParser {
	
	public static final String PATH_BUILDING_ASSETS = "/assets/castledefenders/buildings/";
	public static final String NAME_IMG             = "structure.png";
	public static final String NAME_JSON            = "infos.json";
	
	private static JdomParser parser = new JdomParser();
	
	/**
	 * Renvoie le flux de fichier depuis le jar (depuis le système de fichier en mode DEV)
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	public InputStream getResource (String path) throws FileNotFoundException {
		
		InputStream is = getClass().getResourceAsStream(path);
		
		if (is == null) {
			ModCastleDefenders.log.warning ("Failed to read resource '" + path + "' in jar. read by path file");
			is = new FileInputStream (Minecraft.getMinecraft().mcDataDir + path);
		}
		
		if (is != null) {
			ModCastleDefenders.log.info ("Read resource '" + path + "' in jar");
		}
		
		return is;
	}
	
	/**
	 * Parse un dossier de construction et renvoie al construction
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Building parse (String name) throws Exception {

		ModCastleDefenders.log.info ("Parse '"+name+"' building");
		Building building = new Building ();
		
		// Liste de la correspondance couleur block
		Hashtable<Integer, String> corlorBlockIndex = new Hashtable();
		
		// Objet pour générer des ramdoms
		Random random = new Random();
		
		try {
			InputStream is      = this.getResource(BuildingParser.PATH_BUILDING_ASSETS + name + "/" + NAME_IMG);
			BufferedImage image = ImageIO.read(is);
			
			InputStream isJson       = this.getResource(BuildingParser.PATH_BUILDING_ASSETS + name + "/" + NAME_JSON);
			JsonRootNode json        = this.parser.parse(new InputStreamReader(isJson));
			
			
			
			//////////////////////////////////////////////////////////////
			//                                                          //
			// Création des clefs correspondance couleur_pixel => block //
			//                                                          //
			//////////////////////////////////////////////////////////////
			
			JsonNode colorLink = json.getNode ("color");
			for (int y = 0; y < image.getHeight(); y++) {

				int color = image.getRGB(0, y) & 0xFFFFFF;
				
				if (color == 0x000000 || color == 0xFFFFFF) {
					break;
				}
				
				String type = colorLink.getStringValue(y);
				
				corlorBlockIndex.put(color, type);
			}
			
			ModCastleDefenders.log.info ("Color index  building '"+name+"' loaded");
			
			
			
			
			//////////////////////////////////////////
			//                                      //
			// Construction de la matrice de blocks //
			//                                     //
			//////////////////////////////////////////
			
			int slideNum = 0;
			
			int x = 0;
			int y = 0;
			int z = 0;
			
			JsonNode slidesInfos = json.getNode ("slides");

			int originXSlide = 1;
			
			while (originXSlide < image.getWidth()) {
				
				int xImage = originXSlide;
				for (int yImage = 0; yImage < image.getHeight(); yImage++) {
					for (xImage = originXSlide; xImage < image.getWidth(); xImage++) {
						
						int color = image.getRGB(0, y) & 0xFFFFFF;
						if (color == 0x000000 || color == 0xFFFFFF) {
							break;
						}
						
					}
				}
				//int height = slidesInfos.getStringValue("ee", "eee");
			}
			
			//ModCastleDefenders.log.info ("random : " + random.nextInt(8));
			//ModCastleDefenders.log.info ("random : " + random.nextInt(8));
			//ModCastleDefenders.log.info ("random : " + random.nextInt(8));
			//ModCastleDefenders.log.info ("random : " + random.nextInt(8));
			//ModCastleDefenders.log.info ("random : " + random.nextInt(8));
			
			
		} catch (IOException e) {
			ModCastleDefenders.log.severe ("Error to read resource in jar for building :'"+name+"'");
			throw e;
		} catch (InvalidSyntaxException e) {
			ModCastleDefenders.log.severe ("Invalid json in jar for building :'"+name+"'");
			throw e;
		}
		
		return building;
	}
	
	
}
