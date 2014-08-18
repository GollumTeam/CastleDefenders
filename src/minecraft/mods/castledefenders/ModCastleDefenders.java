package mods.castledefenders;

import java.io.IOException;
import java.lang.annotation.Annotation;

import mods.castledefenders.common.CommonProxyCastleDefenders;
import mods.castledefenders.common.blocks.BlockArcher;
import mods.castledefenders.common.blocks.BlockArcher2;
import mods.castledefenders.common.blocks.BlockEArcher;
import mods.castledefenders.common.blocks.BlockEKnight;
import mods.castledefenders.common.blocks.BlockEMage;
import mods.castledefenders.common.blocks.BlockHealer;
import mods.castledefenders.common.blocks.BlockKnight;
import mods.castledefenders.common.blocks.BlockKnight2;
import mods.castledefenders.common.blocks.BlockMage;
import mods.castledefenders.common.blocks.BlockMerc;
import mods.castledefenders.common.blocks.BlockMercArcher;
import mods.castledefenders.common.entities.EntityArcher;
import mods.castledefenders.common.entities.EntityArcher2;
import mods.castledefenders.common.entities.EntityEArcher;
import mods.castledefenders.common.entities.EntityEKnight;
import mods.castledefenders.common.entities.EntityEMage;
import mods.castledefenders.common.entities.EntityHealer;
import mods.castledefenders.common.entities.EntityKnight;
import mods.castledefenders.common.entities.EntityKnight2;
import mods.castledefenders.common.entities.EntityMage;
import mods.castledefenders.common.entities.EntityMerc;
import mods.castledefenders.common.entities.EntityMercArcher;
import mods.castledefenders.common.items.ItemMedallion;
import mods.castledefenders.common.tileentities.TileEntityBlockArcher;
import mods.castledefenders.common.tileentities.TileEntityBlockArcher2;
import mods.castledefenders.common.tileentities.TileEntityBlockEArcher;
import mods.castledefenders.common.tileentities.TileEntityBlockEKnight;
import mods.castledefenders.common.tileentities.TileEntityBlockEMage;
import mods.castledefenders.common.tileentities.TileEntityBlockHealer;
import mods.castledefenders.common.tileentities.TileEntityBlockKnight;
import mods.castledefenders.common.tileentities.TileEntityBlockKnight2;
import mods.castledefenders.common.tileentities.TileEntityBlockMage;
import mods.castledefenders.common.tileentities.TileEntityBlockMerc;
import mods.castledefenders.common.tileentities.TileEntityBlockMercArcher;
import mods.gollum.core.building.Building;
import mods.gollum.core.building.BuildingParser;
import mods.gollum.core.config.ConfigLoader;
import mods.gollum.core.config.ConfigProp;
import mods.gollum.core.config.ItemStackConfig;
import mods.gollum.core.creativetab.GollumCreativeTabs;
import mods.gollum.core.facory.BlockFactory;
import mods.gollum.core.facory.ItemFactory;
import mods.gollum.core.i18n.I18n;
import mods.gollum.core.log.Logger;
import mods.gollum.core.version.VersionChecker;
import mods.gollum.core.worldgenerator.WorldGeneratorByBuilding;
import net.minecraft.block.Block;
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

@Mod(modid = ModCastleDefenders.MODID, name = ModCastleDefenders.MODNAME, version = ModCastleDefenders.VERSION, acceptedMinecraftVersions = ModCastleDefenders.MINECRAFT_VERSION, dependencies = ModCastleDefenders.DEPENDENCIES)
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class ModCastleDefenders {

	public final static String MODID = "CastleDefenders";
	public final static String MODNAME = "Castle Defenders";
	public final static String VERSION = "3.0.0DEV3 [Build Smeagol]";
	public final static String MINECRAFT_VERSION = "1.6.4";
	public final static String DEPENDENCIES = "required-after:GollumCoreLib";
	
	@Instance("ModCastleDefenders")
	public static ModCastleDefenders instance;
	
	@SidedProxy(clientSide = "mods.castledefenders.client.ClientProxyCastleDefenders", serverSide = "mods.castledefenders.common.CommonProxyCastleDefenders")
	public static CommonProxyCastleDefenders proxy;

	// Gestion des logs
	public static Logger log;
	
	// Gestion de l'i18n
	public static I18n i18n;
	
	// Tab du mode creative
	public static GollumCreativeTabs tabCastleDefenders;
	
	// Liste des blocks
	public static Block blockKnight;
	public static Block blockKnight2;
	public static Block blockArcher;
	public static Block blockArcher2;
	public static Block blockMerc;
	public static Block blockMercArcher;
	public static Block blockMage;
	public static Block blockHealer;
	public static Block blockEKnight;
	public static Block blockEArcher;
	public static Block blockEMage;
	
	// Liste des items
	public static Item itemMedallion;

	// Config d'affichage
	@ConfigProp(group = "Display") public static boolean displayMercenaryMessage = true;
	@ConfigProp(group = "Display") public static boolean displayMercenaryLife = true;
	@ConfigProp(group = "Display") public static double mercenaryLifeTop = 80.D;
	@ConfigProp(group = "Display") public static double mercenaryLifeHeight = 3.D;
	@ConfigProp(group = "Display") public static double mercenaryLifeWidth = 40.D;
	
	// Liste des IDs
	@ConfigProp(group = "Blocks Ids") public static int blockKnightID  = 1238;
	@ConfigProp(group = "Blocks Ids") public static int blockKnight2ID = 1240;
	@ConfigProp(group = "Blocks Ids") public static int blockArcherID  = 1239;
	@ConfigProp(group = "Blocks Ids") public static int blockArcher2ID = 1241;
	@ConfigProp(group = "Blocks Ids") public static int blockMercID    = 1234;
	@ConfigProp(group = "Blocks Ids") public static int blockMercArcherID = 1232;
	@ConfigProp(group = "Blocks Ids") public static int blockMageID    = 1235;
	@ConfigProp(group = "Blocks Ids") public static int blockHealerID  = 1242;
	@ConfigProp(group = "Blocks Ids") public static int blockEKnightID = 1237;
	@ConfigProp(group = "Blocks Ids") public static int blockEArcherID = 1236;
	@ConfigProp(group = "Blocks Ids") public static int blockEMageID   = 1233;
	
	@ConfigProp(group = "Items Ids") public static int medallionID    = 13001;
	
	@ConfigProp(group = "Mobs Ids") public static int knightID      = -31;
	@ConfigProp(group = "Mobs Ids") public static int knight2ID     = -32;
	@ConfigProp(group = "Mobs Ids") public static int archerID      = -30;
	@ConfigProp(group = "Mobs Ids") public static int archer2ID     = -33;
	@ConfigProp(group = "Mobs Ids") public static int mercID        = -29;
	@ConfigProp(group = "Mobs Ids") public static int MercArcherID  = -25;
	@ConfigProp(group = "Mobs Ids") public static int mageID        = -13;
	@ConfigProp(group = "Mobs Ids") public static int healerID      = -34;
	@ConfigProp(group = "Mobs Ids") public static int eKnightID     = -28;
	@ConfigProp(group = "Mobs Ids") public static int eArcherID     = -27;
	@ConfigProp(group = "Mobs Ids") public static int eMageID       = -26;

	// Config des mercenaire
	@ConfigProp(group = "Mercenary", info="ItemID:metadata:number,...") 
	public static ItemStackConfig[] mercenaryCost  = {new ItemStackConfig(Item.ingotGold.itemID, 1), new ItemStackConfig(Item.ingotIron.itemID, 10)};
	@ConfigProp(group = "Mercenary")
	public static ItemStackConfig[] mercArcherCost = {new ItemStackConfig(Item.ingotGold.itemID, 1), new ItemStackConfig(Item.ingotIron.itemID, 10)};
	@ConfigProp(group = "Mercenary")
	public static ItemStackConfig[] healerCost     = {new ItemStackConfig(Item.ingotGold.itemID, 1), new ItemStackConfig(Item.ingotIron.itemID, 10)};
	
	// Ratio de building de chaque type
	@ConfigProp(group = "Spawn rate group [0-10]") public static int castleSpawnRate    = 5;
	@ConfigProp(group = "Spawn rate group [0-10]") public static int mercenarySpawnRate = 5;
	
	// Ratio de building entre les batiments d'un meme type
	@ConfigProp(group = "Spawn rate between mercenary building")
	public static int mercenaryBuilding1SpawnRate = 7;
	@ConfigProp(group = "Spawn rate between mercenary building")
	public static int mercenaryBuilding2SpawnRate = 6;
	@ConfigProp(group = "Spawn rate between mercenary building")
	public static int mercenaryBuilding3SpawnRate = 1;
	@ConfigProp(group = "Spawn rate between mercenary building")
	public static int mercenaryBuilding4SpawnRate = 1;
	
	@ConfigProp(group = "Spawn rate between castle building")
	public static int castleBuilding1SpawnRate = 6;
	@ConfigProp(group = "Spawn rate between castle building")
	public static int castleBuilding2SpawnRate = 6;
	@ConfigProp(group = "Spawn rate between castle building")
	public static int castleBuilding3SpawnRate = 3;
	@ConfigProp(group = "Spawn rate between castle building")
	public static int castleBuilding4SpawnRate = 2;

	@ConfigProp(group = "Entity Knight capacities")
	public static double knightFollowRange = 20.D;
	@ConfigProp(group = "Entity Knight capacities")
	public static double knightMoveSpeed = 0.5D;
	@ConfigProp(group = "Entity Knight capacities")
	public static double knightHealt = 20.D;
	@ConfigProp(group = "Entity Knight capacities")
	public static int knightAttackStrength = 4;

	@ConfigProp(group = "Entity Knight 2 capacities")
	public static double knight2FollowRange = 25.D;
	@ConfigProp(group = "Entity Knight 2 capacities")
	public static double knight2MoveSpeed = 0.6D;
	@ConfigProp(group = "Entity Knight 2 capacities")
	public static double knight2Healt = 30.D;
	@ConfigProp(group = "Entity Knight 2 capacities")
	public static int knight2AttackStrength = 8;

	@ConfigProp(group = "Entity Enemy Knight capacities")
	public static double eKnightFollowRange = 16.D;
	@ConfigProp(group = "Entity Enemy Knight capacities")
	public static double eKnightMoveSpeed = 0.55D;
	@ConfigProp(group = "Entity Enemy Knight capacities")
	public static double eKnightHealt = 25.D;
	@ConfigProp(group = "Entity Enemy Knight capacities")
	public static int eKnightAttackStrength = 6;
	
	@ConfigProp(group = "Entity Archer capacities")
	public static double archerTimeRange = 30.D;
	@ConfigProp(group = "Entity Archer capacities")
	public static double archerFollowRange = 30.D;
	@ConfigProp(group = "Entity Archer capacities")
	public static double archerMoveSpeed = 0.1D;
	@ConfigProp(group = "Entity Archer capacities")
	public static double archerHealt = 15.D;
	@ConfigProp(group = "Entity Archer capacities")
	public static int archerAttackStrength = 4;
	
	@ConfigProp(group = "Entity Archer 2 capacities")
	public static double archer2TimeRange = 15.D;
	@ConfigProp(group = "Entity Archer 2 capacities")
	public static double archer2FollowRange = 25.D;
	@ConfigProp(group = "Entity Archer 2 capacities")
	public static double archer2MoveSpeed = 0.1D;
	@ConfigProp(group = "Entity Archer 2 capacities")
	public static double archer2Healt = 30.D;
	@ConfigProp(group = "Entity Archer 2 capacities")
	public static int archer2AttackStrength = 7;
	
	@ConfigProp(group = "Entity Enemy Archer capacities")
	public static double eArcherTimeRange = 20.D;
	@ConfigProp(group = "Entity Enemy Archer capacities")
	public static double eArcherFollowRange = 17.D;
	@ConfigProp(group = "Entity Enemy Archer capacities")
	public static double eArcherMoveSpeed = 0.1D;
	@ConfigProp(group = "Entity Enemy Archer capacities")
	public static double eArcherHealt = 20.D;
	@ConfigProp(group = "Entity Enemy Archer capacities")
	public static int eArcherAttackStrength = 6;
	
	@ConfigProp(group = "Entity Mage capacities")
	public static double mageTimeRange = 40.D;
	@ConfigProp(group = "Entity Mage capacities")
	public static double mageFollowRange = 10.D;
	@ConfigProp(group = "Entity Mage capacities")
	public static double mageMoveSpeed = 0.1D;
	@ConfigProp(group = "Entity Mage capacities")
	public static double mageHealt = 25.D;
	@ConfigProp(group = "Entity Mage capacities")
	public static int mageAttackStrength = 3;
	
	@ConfigProp(group = "Entity Enemy Mage capacities")
	public static double eMageTimeRange = 40.D;
	@ConfigProp(group = "Entity Enemy Mage capacities")
	public static double eMageFollowRange = 10.D;
	@ConfigProp(group = "Entity Enemy Mage capacities")
	public static double eMageMoveSpeed = 0.1D;
	@ConfigProp(group = "Entity Enemy Mage capacities")
	public static double eMageHealt = 30.D;
	@ConfigProp(group = "Entity Enemy Mage capacities")
	public static int eMageAttackStrength = 5;
	
	@ConfigProp(group = "Entity Mercenary capacities")
	public static double mercFollowRange = 10.D;
	@ConfigProp(group = "Entity Mercenary capacities")
	public static double mercMoveSpeed = 0.1D;
	@ConfigProp(group = "Entity Mercenary capacities")
	public static double mercHealt = 25.D;
	@ConfigProp(group = "Entity Mercenary capacities")
	public static int mercAttackStrength = 3;
	
	
	// Liste des constructions
	private Building buildingMercenary1;
	private Building buildingMercenary2;
	private Building buildingMercenary3;
	private Building buildingMercenary4;
	private Building buildingCastle1;
	private Building buildingCastle2;
	private Building buildingCastle3;
	private Building buildingCastle4;
	
	/**
	 * 1
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		

		// Creation du logger
		log = new Logger(event);
		
		// Creation du logger
		i18n = new I18n(this);
		
		// Charge la configuration
		ConfigLoader configLoader = new ConfigLoader(this.getClass(), event);
		configLoader.loadConfig();
		
		//Test la version du mod
		new VersionChecker(this);
		
	}
	
	/**
	 * 2 
	 * @throws IOException
	 * **/
	@EventHandler
	public void init(FMLInitializationEvent event) throws Exception {
		
		// Execution du renderer en fonction du serveur ou du client
		proxy.registerRenderers();
		
		// Creation du tab creative
		tabCastleDefenders = new GollumCreativeTabs("CastleDefender");
//		LanguageRegistry.instance().addStringLocalization("itemGroup.CastleDefender", "en_US", "Castle Defender");

		//Initialisation des items
		this.initItems();
		
		// Initialisation des blocks
		this.initBlocks ();

		// Initialisation les TileEntities
		this.initTileEntities ();
		
		// Ajout des recettes
		this.initRecipes ();
		
		// Initialisation des Mobs
		this.initMobs ();

		// Initialisation des buildings
		this.initBuildings();
		
		// Initialisation des générateur de terrain
		this.initWorldGenerators();
		
		// Set de l'icon du tab creative
		this.tabCastleDefenders.setIcon(this.blockMercArcher);
		
	}

	/**
	 * Initialisation des items
	 */
	public void initItems () {
		
		ItemFactory factory = new ItemFactory();
		
		this.itemMedallion = factory.create(new ItemMedallion(this.medallionID), "Medallion", this.getModid());
	}
	
	/**
	 * Initialisation des blocks
	 */
	public void initBlocks () {
		
		BlockFactory factory = new BlockFactory();
		
		// Création des blocks
		this.blockKnight     = factory.create (new BlockKnight(this.blockKnightID), "BlockKnight"            ).setHardness(2.0F).setResistance(5.0F);
		this.blockKnight2    = factory.create (new BlockKnight2(this.blockKnight2ID), "BlockKnight2"         ).setHardness(2.0F).setResistance(5.0F);
		this.blockArcher     = factory.create (new BlockArcher(this.blockArcherID), "BlockArcher"            ).setHardness(2.0F).setResistance(5.0F);
		this.blockArcher2    = factory.create (new BlockArcher2(this.blockArcher2ID), "BlockArcher2"         ).setHardness(2.0F).setResistance(5.0F);
		this.blockMerc       = factory.create (new BlockMerc(this.blockMercID), "BlockMerc"                  ).setHardness(2.0F).setResistance(5.0F);
		this.blockMercArcher = factory.create (new BlockMercArcher(this.blockMercArcherID), "BlockMercArcher").setHardness(2.0F).setResistance(5.0F);
		this.blockMage       = factory.create (new BlockMage(this.blockMageID), "BlockMage"                  ).setHardness(2.0F).setResistance(5.0F);
		this.blockHealer     = factory.create (new BlockHealer(this.blockHealerID), "BlockHealer"            ).setHardness(2.0F).setResistance(5.0F);
		this.blockEKnight    = factory.create (new BlockEKnight(this.blockEKnightID), "BlockEKnight"         ).setHardness(2.0F).setResistance(5.0F);
		this.blockEArcher    = factory.create (new BlockEArcher(this.blockEArcherID), "BlockEArcher"         ).setHardness(2.0F).setResistance(5.0F);
		this.blockEMage      = factory.create (new BlockEMage(this.blockEMageID), "BlockEMage"               ).setHardness(2.0F).setResistance(5.0F);
		
	}
	
	/**
	 * // Nom des TileEntities
	 */
	private void initTileEntities () {
		GameRegistry.registerTileEntity(TileEntityBlockKnight.class    , "Knight Block");
		GameRegistry.registerTileEntity(TileEntityBlockKnight2.class   , "Knight Block2");
		GameRegistry.registerTileEntity(TileEntityBlockArcher.class    , "BlockArcher");
		GameRegistry.registerTileEntity(TileEntityBlockArcher2.class   , "BlockArcher2");
		GameRegistry.registerTileEntity(TileEntityBlockMerc.class      , "Merc Block");
		GameRegistry.registerTileEntity(TileEntityBlockMercArcher.class, "BlockMercArcher"); // Id du TileEntity pour la retrocompatibilité
		GameRegistry.registerTileEntity(TileEntityBlockMage.class      , "Mage Block");
		GameRegistry.registerTileEntity(TileEntityBlockHealer.class    , "Hearler Block");
		GameRegistry.registerTileEntity(TileEntityBlockEKnight.class   , "Enemy Knight Block");
		GameRegistry.registerTileEntity(TileEntityBlockEArcher.class   , "Enemy Archer Block");
		GameRegistry.registerTileEntity(TileEntityBlockEMage.class     , "Enemy Mage Block");
	}
	
	/**
	 * Ajout des recettes
	 */
	private void initRecipes () {
		
		
		GameRegistry.addRecipe(new ItemStack(this.blockKnight ,    1), new Object[] { " X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.swordIron });
		GameRegistry.addRecipe(new ItemStack(this.blockKnight2,    1), new Object[] { " X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.swordDiamond });
		GameRegistry.addRecipe(new ItemStack(this.blockArcher ,    1), new Object[] { " X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.bow });
		GameRegistry.addRecipe(new ItemStack(this.blockArcher2,    1), new Object[] { "ZXZ", "XYX", "ZXZ", 'X', Item.ingotIron, 'Y', Item.bow,          'Z', Item.diamond });
		GameRegistry.addRecipe(new ItemStack(this.blockMerc,       1), new Object[] { "KXK", "XYX", "KXK", 'X', Block.planks,   'Y', Item.swordDiamond, 'K', Item.ingotGold });
		GameRegistry.addRecipe(new ItemStack(this.blockMercArcher, 1), new Object[] { "KXK", "XYX", "KXK", 'X', Block.planks,   'Y', Item.bow,          'K', Item.ingotGold });
		GameRegistry.addRecipe(new ItemStack(this.blockMage   ,    1), new Object[] { "YYY", "XXX", "XXX", 'X', Block.obsidian, 'Y', this.itemMedallion });
		GameRegistry.addRecipe(new ItemStack(this.blockHealer ,    1), new Object[] { "XYX", "XYX", "XYX", 'X', Block.planks,   'Y', this.itemMedallion });
	}
	
	/**
	 * Enregistrement des Mobs
	 */
	private void initMobs () {
		this.registerMob(EntityKnight.class    , "Knight"      , "Knight"          , this.knightID    , 0x000000);
		this.registerMob(EntityKnight2.class   , "Knight2"     , "Knight - Level 2", this.knight2ID   , 0x00FFFC);
		this.registerMob(EntityArcher.class    , "Archer"      , "Archer"          , this.archerID    , 0x500000);
		this.registerMob(EntityArcher2.class   , "Archer2"     , "Archer - Level 2", this.archer2ID   , 0x00FF88);
		this.registerMob(EntityMage.class      , "Mage"        , "Mage"            , this.mageID      , 0xE10000);
		this.registerMob(EntityEKnight.class   , "Enemy Knight", "Enemy Knight"    , this.eKnightID   , 0xFF00AA);
		this.registerMob(EntityEArcher.class   , "Enemy Archer", "Enemy Archer"    , this.eArcherID   , 0xE1AA00);
		this.registerMob(EntityEMage.class     , "Enemy Mage"  , "Enemy Mage"      , this.eMageID     , 0xE12AFF);
		this.registerMob(EntityMerc.class      , "Merc"        , "Mercenary"       , this.mercID      , 0x875600);
		this.registerMob(EntityMercArcher.class, "MercArcher"  , "Mercenary Archer", this.MercArcherID, 0xBF95FF);
		this.registerMob(EntityHealer.class    , "Healer"      , "Healer"          , this.healerID    , 0xFF84B4);
	}
	
	/**
	 * Enregistre les générateur de terrain
	 * @throws IOException 
	 */
	private void initBuildings () throws Exception {
		BuildingParser parser = new BuildingParser ();
		this.buildingMercenary1 = parser.parse ("mercenary1", this.getModid());
		this.buildingMercenary2 = parser.parse ("mercenary2", this.getModid());
		this.buildingMercenary3 = parser.parse ("mercenary3", this.getModid());
		this.buildingMercenary4 = parser.parse ("mercenary4", this.getModid());
		this.buildingCastle1    = parser.parse ("castle1", this.getModid());
		this.buildingCastle2    = parser.parse ("castle2", this.getModid());
		this.buildingCastle3    = parser.parse ("castle3", this.getModid());
		this.buildingCastle4    = parser.parse ("castle4", this.getModid());
	}
	
	/**
	 * Enregistre les générateur de terrain
	 */
	private void initWorldGenerators () {
		
		// Céation du world generator
		WorldGeneratorByBuilding worldGeneratorByBuilding = new WorldGeneratorByBuilding();
		
		int idGroupMercenary = worldGeneratorByBuilding.addGroup (this.mercenarySpawnRate);
		int idGroupCastle    = worldGeneratorByBuilding.addGroup (this.castleSpawnRate);
		
		// Ajout des batiments
		worldGeneratorByBuilding.addBuilding (idGroupMercenary, this.buildingMercenary1, this.mercenaryBuilding1SpawnRate);
		worldGeneratorByBuilding.addBuilding (idGroupMercenary, this.buildingMercenary2, this.mercenaryBuilding2SpawnRate);
		worldGeneratorByBuilding.addBuilding (idGroupMercenary, this.buildingMercenary3, this.mercenaryBuilding3SpawnRate);
		worldGeneratorByBuilding.addBuilding (idGroupMercenary, this.buildingMercenary4, this.mercenaryBuilding4SpawnRate);
		
		worldGeneratorByBuilding.addBuilding (idGroupCastle, this.buildingCastle1, this.castleBuilding1SpawnRate);
		worldGeneratorByBuilding.addBuilding (idGroupCastle, this.buildingCastle2, this.castleBuilding2SpawnRate);
		worldGeneratorByBuilding.addBuilding (idGroupCastle, this.buildingCastle3, this.castleBuilding3SpawnRate);
		worldGeneratorByBuilding.addBuilding (idGroupCastle, this.buildingCastle4, this.castleBuilding4SpawnRate);
		
		// Enregistrement du worldgenerator mercenary
		GameRegistry.registerWorldGenerator (worldGeneratorByBuilding);
	}
	
	/**
	 * Enregistre un mob
	 * @param entityClass
	 * @param name
	 * @param id
	 * @param spawn
	 */
	private void registerMob (Class entityClass, String name, String languageName, int id, int color) {
		
		EntityRegistry.registerGlobalEntityID(entityClass, name, EntityRegistry.findGlobalUniqueEntityId(), 0xFFFFFF, color);
		
		// 30 est traking range : trackingRange The range at which MC will send tracking updates
		// 1 est la frequence : The frequency of tracking updates
		// true est l'envoie de la msie a jour de la velocité : sendsVelocityUpdates Whether to send velocity information packets as well
		EntityRegistry.registerModEntity(entityClass, name, id, this, 30, 1, true);
		
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
		
		// Ajout de la langue
		LanguageRegistry.instance().addStringLocalization("entity."+name+".name", languageName);
		
	}
	
	/**
	 * Renvoie le modID du MOD
	 * @return String
	 */
	public String getModid () {
		String modid = "Error";
		
		for (Annotation annotation : this.getClass().getAnnotations()) {
			if (annotation instanceof Mod) {
				modid = ((Mod)annotation).modid();
			}
		}
		
		return modid;
	}
	
}
