package mods.castledefenders.common;

import java.util.logging.Logger;

import mods.castledefenders.common.blocks.BlockArcher;
import mods.castledefenders.common.blocks.BlockArcher2;
import mods.castledefenders.common.blocks.BlockArcherM;
import mods.castledefenders.common.blocks.BlockEArcher;
import mods.castledefenders.common.blocks.BlockEKnight;
import mods.castledefenders.common.blocks.BlockEMage;
import mods.castledefenders.common.blocks.BlockKnight;
import mods.castledefenders.common.blocks.BlockKnight2;
import mods.castledefenders.common.blocks.BlockMage;
import mods.castledefenders.common.blocks.BlockMerc;
import mods.castledefenders.common.entities.EntityArcher;
import mods.castledefenders.common.entities.EntityKnight;
import mods.castledefenders.common.items.ItemMedallion;
import mods.castledefenders.common.tileentities.TileEntityBlockArcher;
import mods.castledefenders.common.tileentities.TileEntityBlockArcher2;
import mods.castledefenders.common.tileentities.TileEntityBlockArcherM;
import mods.castledefenders.common.tileentities.TileEntityBlockEArcher;
import mods.castledefenders.common.tileentities.TileEntityBlockEKnight;
import mods.castledefenders.common.tileentities.TileEntityBlockEMage;
import mods.castledefenders.common.tileentities.TileEntityBlockKnight;
import mods.castledefenders.common.tileentities.TileEntityBlockKnight2;
import mods.castledefenders.common.tileentities.TileEntityBlockMage;
import mods.castledefenders.common.tileentities.TileEntityBlockMerc;
import mods.castledefenders.utils.ConfigLoader;
import mods.castledefenders.utils.ConfigProp;
import mods.castledefenders.utils.VersionChecker;
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

@Mod(modid = "CastleDefenders", name = "Castle Defenders", version = "3.0.0 [Build Smeagol]", acceptedMinecraftVersions = "1.6.4")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class ModCastleDefenders {
	
	@Instance("ModCastleDefenders")
	public static ModCastleDefenders instance;
	
	@SidedProxy(clientSide = "mods.castledefender.common.ClientProxyCastleDefenders", serverSide = "mods.castledefender.common.CommonProxyCastleDefenders")
	public static CommonProxyCastleDefenders proxy;
	
	@ConfigProp (info = "Display version checker message")
	public static boolean versionChecker = true;
	
	// Gestion des logs
	public static Logger log;
	
	// Tab du mode creative
	public static CastleDefendersTabs tabsCastleDefenders;
	
	// Liste des blocks
	public static Block BlockKnight;
	public static Block BlockKnight2;
	public static Block BlockArcher;
	public static Block BlockArcher2;
	public static Block BlockMage;
	public static Block BlockMerc;
	public static Block BlockArcherM;
	public static Block BlockEKnight;
	public static Block BlockEArcher;
	public static Block BlockEMage;
	
	// Liste des items
	public static Item ItemMedallion;
	
	@ConfigProp(group = "Mobs Entities") public static int startEntityId = 300;
	
	// Liste des IDs
	@ConfigProp(group = "Blocks Ids") public static int BlockKnightID  = 1238;
	@ConfigProp(group = "Blocks Ids") public static int BlockKnight2ID = 1240;
	@ConfigProp(group = "Blocks Ids") public static int BlockArcherID  = 1239;
	@ConfigProp(group = "Blocks Ids") public static int BlockArcher2ID = 1241;
	@ConfigProp(group = "Blocks Ids") public static int BlockMercID    = 1234;
	@ConfigProp(group = "Blocks Ids") public static int BlockArcherMID = 1232;
	@ConfigProp(group = "Blocks Ids") public static int BlockMageID    = 1235;
	@ConfigProp(group = "Blocks Ids") public static int BlockEKnightID = 1237;
	@ConfigProp(group = "Blocks Ids") public static int BlockEArcherID = 1236;
	@ConfigProp(group = "Blocks Ids") public static int BlockEMageID   = 1233;
	
	@ConfigProp(group = "Items Ids") public static int MedallionID    = 13001;
	
	@ConfigProp(group = "Mobs Ids") public static int knightID   = -31;
	@ConfigProp(group = "Mobs Ids") public static int knigh2tID  = -32;
	@ConfigProp(group = "Mobs Ids") public static int archerID   = -30;
	@ConfigProp(group = "Mobs Ids") public static int archer2ID  = -33;
	@ConfigProp(group = "Mobs Ids") public static int mercID     = -29;
	@ConfigProp(group = "Mobs Ids") public static int archerMID  = -25;
	@ConfigProp(group = "Mobs Ids") public static int mageID     = -13;
	@ConfigProp(group = "Mobs Ids") public static int EknightID  = -28;
	@ConfigProp(group = "Mobs Ids") public static int EarcherID  = -27;
	@ConfigProp(group = "Mobs Ids") public static int EmageID    = -26;
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		
		// Creation du logger
		log = event.getModLog();
		
		ConfigLoader configLoader = new ConfigLoader(this.getClass(), event);
		configLoader.loadConfig();
		
	}
	
	/** 2 **/
	@EventHandler
	public void load(FMLInitializationEvent event) {
		
		// Execution du renderer en fonction du serveur ou du client
		proxy.registerRenderers();
		
		// Creation du checker de version
		VersionChecker.getInstance(this.versionChecker).check(this);

		tabsCastleDefenders = new CastleDefendersTabs("CastleDefender", BlockKnightID);
		LanguageRegistry.instance().addStringLocalization("itemGroup.CastleDefender", "en_US", "Castle Defender");

		//Initialisation des items
		this.initItems();
		
		//Initialisation des blocks
		this.initBlocks ();

		// Nom des TileEntities
		this.initTileEntities ();
		
		// Ajout des recettes
		this.initRecipes ();
		
		// Enregistrement des Mobs
//		this.initMobs ();
		
	}

	/**
	 * Initialisation des items
	 */
	public void initItems () {

		ItemMedallion = (new ItemMedallion(MedallionID)).setUnlocalizedName("Medallion");
		
		LanguageRegistry.addName(ItemMedallion, "Medallion");
	}
	
	/**
	 * Initialisation des blocks
	 */
	public void initBlocks () {

		// Création des blocks
		BlockKnight  = (new BlockKnight(BlockKnightID))  .setUnlocalizedName("BlockKnight") .setHardness(2.0F).setResistance(5.0F);
		BlockKnight2 = (new BlockKnight2(BlockKnight2ID)).setUnlocalizedName("BlockKnight2").setHardness(2.0F).setResistance(5.0F);
		BlockArcher  = (new BlockArcher(BlockArcherID))  .setUnlocalizedName("BlockArcher") .setHardness(2.0F).setResistance(5.0F);
		BlockArcher2 = (new BlockArcher2(BlockArcher2ID)).setUnlocalizedName("BlockArcher2").setHardness(2.0F).setResistance(5.0F);
		BlockMerc    = (new BlockMerc(BlockMercID))      .setUnlocalizedName("BlockMerc")   .setHardness(2.0F).setResistance(5.0F);
		BlockEKnight = (new BlockEKnight(BlockEKnightID)).setUnlocalizedName("BlockEKnight").setHardness(2.0F).setResistance(5.0F);
		BlockEArcher = (new BlockEArcher(BlockEArcherID)).setUnlocalizedName("BlockEArcher").setHardness(2.0F).setResistance(5.0F);
		BlockMage    = (new BlockMage(BlockMageID))      .setUnlocalizedName("BlockMage")   .setHardness(2.0F).setResistance(5.0F);
		BlockEMage   = (new BlockEMage(BlockEMageID))    .setUnlocalizedName("BlockEMage")  .setHardness(2.0F).setResistance(5.0F);
		BlockArcherM = (new BlockArcherM(BlockArcherMID)).setUnlocalizedName("BlockArcherM").setHardness(2.0F).setResistance(5.0F);
		
		
		// Enregistrement des blocks
		GameRegistry.registerBlock(BlockKnight , "Knight Spawner");
		GameRegistry.registerBlock(BlockKnight2, "Knight Spawner - Level 2");
		GameRegistry.registerBlock(BlockArcher , "Archer Spawner");
		GameRegistry.registerBlock(BlockArcher2, "Archer Spawner - Level 2");
		GameRegistry.registerBlock(BlockMerc   , "Merc Spawner");
		GameRegistry.registerBlock(BlockMage   , "Mage Spawner");
		GameRegistry.registerBlock(BlockArcherM, "Merc Archer Spawner");
		GameRegistry.registerBlock(BlockEKnight, "Enemy Knight Spawner");
		GameRegistry.registerBlock(BlockEArcher, "Enemy Archer Spawner");
		GameRegistry.registerBlock(BlockEMage  , "Enemy Mage Spawner");
		
		// Nom des blocks
		LanguageRegistry.addName(BlockKnight , "Knight Spawner");
		LanguageRegistry.addName(BlockKnight2, "Knight Spawner - Level 2");
		LanguageRegistry.addName(BlockArcher , "Archer Spawner");
		LanguageRegistry.addName(BlockArcher2, "Archer Spawner - Level 2");
		LanguageRegistry.addName(BlockMage   , "Mage Spawner");
		LanguageRegistry.addName(BlockMerc   , "Merc Spawner");
		LanguageRegistry.addName(BlockArcherM, "Merc Archer Spawner");
		LanguageRegistry.addName(BlockEKnight, "Enemy Knight Spawner");
		LanguageRegistry.addName(BlockEArcher, "Enemy Archer Spawner");
		LanguageRegistry.addName(BlockEMage  , "Enemy Mage Spawner");
	}
	
	/**
	 * // Nom des TileEntities
	 */
	private void initTileEntities () {
		GameRegistry.registerTileEntity(TileEntityBlockKnight.class , "Knight Block");
		GameRegistry.registerTileEntity(TileEntityBlockKnight2.class, "Knight Block2");
		GameRegistry.registerTileEntity(TileEntityBlockArcher.class , "BlockArcher");
		GameRegistry.registerTileEntity(TileEntityBlockArcher2.class, "BlockArcher2");
		GameRegistry.registerTileEntity(TileEntityBlockMerc.class   , "Merc Block");
		GameRegistry.registerTileEntity(TileEntityBlockArcherM.class, "Merc Archer Block");
		GameRegistry.registerTileEntity(TileEntityBlockMage.class   , "Mage Block");
		GameRegistry.registerTileEntity(TileEntityBlockEKnight.class, "Enemy Knight Block");
		GameRegistry.registerTileEntity(TileEntityBlockEArcher.class, "Enemy Archer Block");
		GameRegistry.registerTileEntity(TileEntityBlockEMage.class  , "Enemy Mage Block");
	}
	
	/**
	 * Ajout des recettes
	 */
	private void initRecipes () {
		GameRegistry.addRecipe(new ItemStack(BlockKnight, 1), new Object[] {" X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.swordIron});
		GameRegistry.addRecipe(new ItemStack(BlockArcher, 1), new Object[] {" X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.bow});
		GameRegistry.addRecipe(new ItemStack(BlockMage, 1), new Object[] {"   ", " X ", " Y ", 'X', ItemMedallion, 'Y', BlockEMage});

		GameRegistry.addRecipe(new ItemStack(BlockMerc, 1), new Object[] {" Z ", "XYX", " Z ", 'X', Item.swordWood   , 'Y', ItemMedallion, 'Z', Item.ingotIron});
		GameRegistry.addRecipe(new ItemStack(BlockMerc, 1), new Object[] {" Z ", "XYX", " Z ", 'X', Item.swordStone  , 'Y', ItemMedallion, 'Z', Item.ingotIron});
		GameRegistry.addRecipe(new ItemStack(BlockMerc, 1), new Object[] {" Z ", "XYX", " Z ", 'X', Item.swordIron   , 'Y', ItemMedallion, 'Z', Item.ingotIron});
		GameRegistry.addRecipe(new ItemStack(BlockMerc, 1), new Object[] {" Z ", "XYX", " Z ", 'X', Item.swordGold   , 'Y', ItemMedallion, 'Z', Item.ingotIron});
		GameRegistry.addRecipe(new ItemStack(BlockMerc, 1), new Object[] {" Z ", "XYX", " Z ", 'X', Item.swordDiamond, 'Y', ItemMedallion, 'Z', Item.ingotIron});

		GameRegistry.addRecipe(new ItemStack(BlockArcherM, 1), new Object[] {" Z ", "XYX", " Z ", 'X', Item.bow   , 'Y', ItemMedallion, 'Z', Item.ingotIron});
	}
	
	/**
	 * Enregistrement des Mobs
	 */
	private void initMobs () {
		this.registerMob(EntityKnight.class, "Knight", knightID, 0x000000);
		this.registerMob(EntityArcher.class, "Archer", archerID, 0x500000);
	}
	
	/**
	 * Enregistre un mob
	 * @param entityClass
	 * @param name
	 * @param id
	 */
	private void registerMob (Class entityClass, String name, int id) {
		this.registerMob(entityClass, name, id, -1);
	}
	
	/**
	 * Enregistre un mob
	 * @param entityClass
	 * @param name
	 * @param id
	 * @param spawn
	 */
	private void registerMob (Class entityClass, String name, int id, int color) {
		
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
