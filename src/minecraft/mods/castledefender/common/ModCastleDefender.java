package mods.castledefender.common;

import java.util.logging.Logger;

import mods.castledefender.common.blocks.BlockArcher;
import mods.castledefender.common.blocks.BlockArcherM;
import mods.castledefender.common.blocks.BlockEArcher;
import mods.castledefender.common.blocks.BlockEKnight;
import mods.castledefender.common.blocks.BlockEMage;
import mods.castledefender.common.blocks.BlockKnight;
import mods.castledefender.common.blocks.BlockMage;
import mods.castledefender.common.blocks.BlockMerc;
import mods.castledefender.common.entities.EntityArcher;
import mods.castledefender.common.entities.EntityKnight;
import mods.castledefender.common.items.ItemMedallion;
import mods.castledefender.common.tileentities.TileEntityBlockArcher;
import mods.castledefender.common.tileentities.TileEntityBlockArcherM;
import mods.castledefender.common.tileentities.TileEntityBlockEArcher;
import mods.castledefender.common.tileentities.TileEntityBlockEKnight;
import mods.castledefender.common.tileentities.TileEntityBlockEMage;
import mods.castledefender.common.tileentities.TileEntityBlockKnight;
import mods.castledefender.common.tileentities.TileEntityBlockMage;
import mods.castledefender.common.tileentities.TileEntityBlockMerc;
import mods.castledefender.utils.ConfigLoader;
import mods.castledefender.utils.ConfigProp;
import mods.castledefender.utils.VersionChecker;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "CastleDefender", name = "Castle Defender", version = "2.0.0 [Build Smeagol]", acceptedMinecraftVersions = "1.6.4")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class ModCastleDefender {
	
	@Instance("ModCastleDefender")
	public static ModCastleDefender instance;
	
	@SidedProxy(clientSide = "mods.castledefender.common.ClientProxyCastleDefender", serverSide = "mods.castledefender.common.CommonProxyCastleDefender")
	public static CommonProxyCastleDefender proxy;
	
	@ConfigProp (info = "Display version checker message")
	public static boolean versionChecker = true;
	
	// Gestion des logs
	public static Logger log;
	
	// Tab du mode creative
	public static CastleDefenderTabs tabsCastleDefender;
	
	// Liste des blocks
	public static Block BlockKnight;
	public static Block BlockArcher;
	public static Block BlockMerc;
	public static Block BlockEKnight;
	public static Block BlockEArcher;
	public static Block BlockMage;
	public static Block BlockEMage;
	public static Block BlockArcherM;
	
	// Liste des items
	public static Item ItemMedallion;
	
	@ConfigProp(group = "Mobs Entities") public static int startEntityId = 300;
	
	// Liste des IDs
	@ConfigProp(group = "Blocks Ids") public static int BlockKnightID  = 238;
	@ConfigProp(group = "Blocks Ids") public static int BlockArcherID  = 239;
	@ConfigProp(group = "Blocks Ids") public static int BlockMercID    = 234;
	@ConfigProp(group = "Blocks Ids") public static int BlockEKnightID = 237;
	@ConfigProp(group = "Blocks Ids") public static int BlockEArcherID = 236;
	@ConfigProp(group = "Blocks Ids") public static int BlockMageID    = 235;
	@ConfigProp(group = "Blocks Ids") public static int BlockEMageID   = 233;
	@ConfigProp(group = "Blocks Ids") public static int BlockArcherMID = 232;
	
	@ConfigProp(group = "Items Ids") public static int MedallionID    = 3001;

//	@ConfigProp(group = "Mobs Ids") public static int defenderID = -32;
	@ConfigProp(group = "Mobs Ids") public static int knightID   = -31;
	@ConfigProp(group = "Mobs Ids") public static int archerID   = -30;
	@ConfigProp(group = "Mobs Ids") public static int mercID     = -29;
	@ConfigProp(group = "Mobs Ids") public static int EknightID  = -28;
	@ConfigProp(group = "Mobs Ids") public static int EarcherID  = -27;
	@ConfigProp(group = "Mobs Ids") public static int EmageID    = -26;
	@ConfigProp(group = "Mobs Ids") public static int archerMID  = -25;
	@ConfigProp(group = "Mobs Ids") public static int mageID     = -13;
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		
		// Creation du logger
		log = event.getModLog();
		
		ConfigLoader configLoader = new ConfigLoader(this.getClass(), event);
		configLoader.loadConfig();
		
	}
	
	/** 2 **/
	@EventHandler
	public void load(FMLInitializationEvent var1) {
		
		// Execution du renderer en fonction du serveur ou du client
		proxy.registerRenderers();
		
		// Creation du checker de version
		VersionChecker.getInstance(this.versionChecker).check(this);
		
		tabsCastleDefender = new CastleDefenderTabs("CastleDefender", BlockKnightID);
		LanguageRegistry.instance().addStringLocalization("itemGroup.CastleDefender", "en_US", "Castle Defender");
		
		// Création des blocks
		BlockKnight  = (new BlockKnight(BlockKnightID))  .setUnlocalizedName("BlockKnight") .setHardness(2.0F).setResistance(5.0F);
		BlockArcher  = (new BlockArcher(BlockArcherID))  .setUnlocalizedName("BlockArcher") .setHardness(2.0F).setResistance(5.0F);
		BlockMerc    = (new BlockMerc(BlockMercID))      .setUnlocalizedName("BlockMerc")   .setHardness(2.0F).setResistance(5.0F);
		BlockEKnight = (new BlockEKnight(BlockEKnightID)).setUnlocalizedName("BlockEKnight").setHardness(2.0F).setResistance(5.0F);
		BlockEArcher = (new BlockEArcher(BlockEArcherID)).setUnlocalizedName("BlockEArcher").setHardness(2.0F).setResistance(5.0F);
		BlockMage    = (new BlockMage(BlockMageID))      .setUnlocalizedName("BlockMage")   .setHardness(2.0F).setResistance(5.0F);
		BlockEMage   = (new BlockEMage(BlockEMageID))    .setUnlocalizedName("BlockEMage")  .setHardness(2.0F).setResistance(5.0F);
		BlockArcherM = (new BlockArcherM(BlockArcherMID)).setUnlocalizedName("BlockArcherM").setHardness(2.0F).setResistance(5.0F);
		
		ItemMedallion = (new ItemMedallion(MedallionID)).setUnlocalizedName("Medallion");
		
		// Enregistrement des blocks
		GameRegistry.registerBlock(BlockKnight , "Knight Spawner");
		GameRegistry.registerBlock(BlockArcher , "Archer Spawner");
		GameRegistry.registerBlock(BlockMerc   , "Merc Spawner");
		GameRegistry.registerBlock(BlockEKnight, "Enemy Knight Spawner");
		GameRegistry.registerBlock(BlockEArcher, "Enemy Archer Spawner");
		GameRegistry.registerBlock(BlockMage   , "Mage Spawner");
		GameRegistry.registerBlock(BlockEMage  , "Enemy Mage Spawner");
		GameRegistry.registerBlock(BlockArcherM, "Merc Archer Spawner");
		
		// Nom des blocks
		LanguageRegistry.addName(BlockKnight , "Knight Spawner");
		LanguageRegistry.addName(BlockArcher , "Archer Spawner");
		LanguageRegistry.addName(BlockMerc   , "Merc Spawner");
		LanguageRegistry.addName(BlockEKnight, "Enemy Knight Spawner");
		LanguageRegistry.addName(BlockEArcher, "Enemy Archer Spawner");
		LanguageRegistry.addName(BlockMage   , "Mage Spawner");
		LanguageRegistry.addName(BlockEMage  , "Enemy Mage Spawner");
		LanguageRegistry.addName(BlockArcherM, "Merc Archer Spawner");
		
		LanguageRegistry.addName(ItemMedallion, "Medallion");
		

		// Nom des Tile Entities
		GameRegistry.registerTileEntity(TileEntityBlockKnight.class, "Knight Block");
		GameRegistry.registerTileEntity(TileEntityBlockArcher.class, "BlockArcher");
		GameRegistry.registerTileEntity(TileEntityBlockMerc.class, "Merc Block");
		GameRegistry.registerTileEntity(TileEntityBlockEKnight.class, "Enemy Knight Block");
		GameRegistry.registerTileEntity(TileEntityBlockEArcher.class, "Enemy Archer Block");
		GameRegistry.registerTileEntity(TileEntityBlockMage.class, "Mage Block");
		GameRegistry.registerTileEntity(TileEntityBlockEMage.class, "Enemy Mage Block");
		GameRegistry.registerTileEntity(TileEntityBlockArcherM.class, "Merc Archer Block");
		
		// Ajout des recettes
		GameRegistry.addRecipe(new ItemStack(BlockKnight, 1), new Object[] {" X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.swordIron});
		GameRegistry.addRecipe(new ItemStack(BlockArcher, 1), new Object[] {" X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.bow});
		GameRegistry.addRecipe(new ItemStack(BlockMage, 1), new Object[] {"   ", " X ", " Y ", 'X', ItemMedallion, 'Y', BlockEMage});

		GameRegistry.addRecipe(new ItemStack(BlockMerc, 1), new Object[] {" Z ", "XYX", " Z ", 'X', Item.swordWood   , 'Y', ItemMedallion, 'Z', Item.ingotIron});
		GameRegistry.addRecipe(new ItemStack(BlockMerc, 1), new Object[] {" Z ", "XYX", " Z ", 'X', Item.swordStone  , 'Y', ItemMedallion, 'Z', Item.ingotIron});
		GameRegistry.addRecipe(new ItemStack(BlockMerc, 1), new Object[] {" Z ", "XYX", " Z ", 'X', Item.swordIron   , 'Y', ItemMedallion, 'Z', Item.ingotIron});
		GameRegistry.addRecipe(new ItemStack(BlockMerc, 1), new Object[] {" Z ", "XYX", " Z ", 'X', Item.swordGold   , 'Y', ItemMedallion, 'Z', Item.ingotIron});
		GameRegistry.addRecipe(new ItemStack(BlockMerc, 1), new Object[] {" Z ", "XYX", " Z ", 'X', Item.swordDiamond, 'Y', ItemMedallion, 'Z', Item.ingotIron});

		GameRegistry.addRecipe(new ItemStack(BlockArcherM, 1), new Object[] {" Z ", "XYX", " Z ", 'X', Item.bow   , 'Y', ItemMedallion, 'Z', Item.ingotIron});
		
		// Enregistrement des Mobs
		this.registerMob(EntityKnight.class, "Knight", knightID, 0x000000);
		this.registerMob(EntityArcher.class, "Archer", archerID, 0x500000);
//		this.registerMob(EntityDefender.class, "Defender", defenderID);
		
	}
	
	/**
	 * Enregistre un mob
	 * @param entityClass
	 * @param name
	 * @param id
	 */
	public void registerMob (Class entityClass, String name, int id) {
		this.registerMob(entityClass, name, id, -1);
	}
	
	/**
	 * Enregistre un mob
	 * @param entityClass
	 * @param name
	 * @param id
	 * @param spawn
	 */
	public void registerMob (Class entityClass, String name, int id, int color) {
		
		EntityRegistry.registerGlobalEntityID(entityClass, name, id);
		
		if (color != -1) {
			
			// Pop dans les biomes
			EntityRegistry.addSpawn(entityClass, 10, 0, 0, EnumCreatureType.creature, new BiomeGenBase[] {
				BiomeGenBase.desert, BiomeGenBase.desertHills,
				BiomeGenBase.extremeHills,
				BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest,
				BiomeGenBase.forestHills, BiomeGenBase.frozenOcean,
				BiomeGenBase.frozenRiver, BiomeGenBase.hell,
				BiomeGenBase.iceMountains, BiomeGenBase.icePlains,
				BiomeGenBase.jungle, BiomeGenBase.jungleHills,
				BiomeGenBase.mushroomIsland,
				BiomeGenBase.mushroomIslandShore, BiomeGenBase.ocean,
				BiomeGenBase.plains, BiomeGenBase.river,
				BiomeGenBase.sky, BiomeGenBase.swampland,
				BiomeGenBase.taiga, BiomeGenBase.taigaHills
			});
			LanguageRegistry.instance().addStringLocalization("entity."+name+".name", "Knight");
			
			// Cherche une entité vide
			do { ++startEntityId; } while (EntityList.getStringFromID(startEntityId) != null);
			
			// Ajout de l'oeuf
			int entityId = startEntityId;
			EntityList.IDtoClassMapping.put(Integer.valueOf(entityId), entityClass);
			EntityList.entityEggs.put(Integer.valueOf(entityId), new EntityEggInfo(entityId, 0xFFFFFF, color));
			
		}
		
	}
	
}
