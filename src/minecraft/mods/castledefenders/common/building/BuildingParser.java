package mods.castledefenders.common.building;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import mods.castledefenders.common.ModCastleDefenders;
import mods.castledefenders.common.building.Building.Unity;
import net.minecraft.block.Block;
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
		Hashtable<Integer, Unity> corlorBlockIndex = new Hashtable ();
		
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
				
				JsonNode type = colorLink.getNode(y);
				
				///////////////////////////////////
				// Récupération de l'objet block //
				///////////////////////////////////
				
				Unity unity = this.parseBlockDescription (type);
				
				corlorBlockIndex.put(color, unity);
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
			
			int originXSlide = 1;
			
			// Parcours l'image pour créer la matrice de block
			while (originXSlide < image.getWidth()) {
				
				
				int xImage = originXSlide;
				for (int zImage = 0; zImage < image.getHeight(); zImage++) {
					
					for (xImage = originXSlide; xImage < image.getWidth(); xImage++) {
						
						int color = image.getRGB(xImage, zImage) & 0xFFFFFF;
						if (color == 0x000000) {
							break;
						}
						
						Unity unityPtr = null; try { unityPtr = (Unity)corlorBlockIndex.get(color); } catch (Exception e) {};
						Unity unity =  (unityPtr != null) ? (Unity)unityPtr.clone () : new Unity ();
						
						building.set(x, y, z, unity);
						
						x++;
					}
					z++;
					x = 0;
				}
				originXSlide = xImage + 1;
				y++;
				z = 0;
			}
			
			Map<JsonStringNode, JsonNode> map = json.getNode ("sets").getFields();
			for (JsonStringNode key : map.keySet()) {
				String position3D[] = key.getText().split("x");
				x = Integer.parseInt(position3D[0]);
				y = Integer.parseInt(position3D[1]);
				z = Integer.parseInt(position3D[2]);
				
				Unity unity = this.parseBlockDescription(map.get(key));
				building.set(x, y, z, unity);
				
			}
			
			ModCastleDefenders.log.info ("Matrice building '"+name+"' loaded");
			
			
		} catch (IOException e) {
			ModCastleDefenders.log.severe ("Error to read resource in jar for building :'"+name+"'");
			throw e;
		} catch (InvalidSyntaxException e) {
			ModCastleDefenders.log.severe ("Invalid json in jar for building :'"+name+"'");
			throw e;
		}
		
		return building;
	}
	
	/**
	 * Parse une description de block
	 * @param type
	 * @return
	 */
	private Unity parseBlockDescription(JsonNode type) {
		
		Unity unity = new Unity();
		try {
			// Découpe le type par ClassName|ObjetBlock ou ClassName|ObjetBlock:intMetadataOptional
			String[] explode   = type.getStringValue ("block").split(Pattern.quote("|"));
			String metadata    = "0"; try { metadata = type.getNumberValue ("metadata"); } catch (Exception e) { }
			String orientation = "none"; try { orientation = type.getStringValue ("orientation"); } catch (Exception e) { }
			
			
			// Récupère l'attribut
			Class classBlock;
			classBlock = Class.forName(explode[0]);
			Field f = classBlock.getDeclaredField(explode[1]);
			Block block = (Block) f.get(null);
			
			
			unity.block       = block;
			unity.metadata    = Integer.parseInt(metadata);

			if (orientation.equals("none"))   { unity.orientation = Unity.ORIENTATION_NONE;     } else 
			if (orientation.equals("up"))     { unity.orientation = Unity.ORIENTATION_UP;     } else 
			if (orientation.equals("down"))   { unity.orientation = Unity.ORIENTATION_DOWN;   } else 
			if (orientation.equals("left"))   { unity.orientation = Unity.ORIENTATION_LEFT;   } else 
			if (orientation.equals("right"))  { unity.orientation = Unity.ORIENTATION_RIGTH;  } else 
			if (orientation.equals("top"))    { unity.orientation = Unity.ORIENTATION_TOP;    } else 
			if (orientation.equals("bottom")) { unity.orientation = Unity.ORIENTATION_BOTTOM; }
			
			if (unity.orientation != 0) {
				ModCastleDefenders.log.info("Generation orientation : "+unity.orientation);
				ModCastleDefenders.log.info("Generation orientation : "+orientation);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unity;
	}
	
	
}
