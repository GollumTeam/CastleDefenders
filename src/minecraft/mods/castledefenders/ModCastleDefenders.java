package mods.castledefenders;

import java.io.IOException;

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
import mods.castledefenders.common.config.ConfigCastleDefender;
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
import mods.gollum.core.creativetab.GollumCreativeTabs;
import mods.gollum.core.facory.Mobactory;
import mods.gollum.core.helper.items.HItem;
import mods.gollum.core.mod.GollumMod;
import mods.gollum.core.registry.SoundRegistry;
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

@Mod(modid = ModCastleDefenders.MODID, name = ModCastleDefenders.MODNAME, version = ModCastleDefenders.VERSION, acceptedMinecraftVersions = ModCastleDefenders.MINECRAFT_VERSION, dependencies = ModCastleDefenders.DEPENDENCIES)
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class ModCastleDefenders extends GollumMod {

	public final static String MODID = "CastleDefenders";
	public final static String MODNAME = "Castle Defenders";
	public final static String VERSION = "3.0.0 [Build Smeagol]";
	public final static String MINECRAFT_VERSION = "1.6.4";
	public final static String DEPENDENCIES = "required-after:GollumCoreLib";
	
	@Instance(ModCastleDefenders.MODID)
	public static ModCastleDefenders instance;
	
	@SidedProxy(clientSide = "mods.castledefenders.client.ClientProxyCastleDefenders", serverSide = "mods.castledefenders.common.CommonProxyCastleDefenders")
	public static CommonProxyCastleDefenders proxy;

	/**
	 * La configuration
	 */
	public static ConfigCastleDefender config;
	
	/**
	 * Tab du mode creative
	 */
	public static GollumCreativeTabs tabCastleDefenders = new GollumCreativeTabs("CastleDefender");;
	
	/////////////////////
	// Liste des blocs //
	/////////////////////
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
	
	/////////////////////
	// Liste des items //
	/////////////////////
	public static Item itemMedallion;
	
	/////////////////////////////
	// Liste des constructions //
	/////////////////////////////
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
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
		super.preInit (event);
		
		// Charge la configuration
		this.config = new ConfigCastleDefender();
		
		// Test la version du mod
		new VersionChecker();
		
		// Initialisation des sons
		this.initSounds ();
		
		// Initialisation des blocks
		this.initBlocks ();
		
		//Initialisation des items
		this.initItems();
	}
	
	/**
	 * 2 
	 **/
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		// Execution du renderer en fonction du serveur ou du client
		proxy.registerRenderers();
		
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
	 * Initialisation des sons
	 */
	public void initSounds () {
		SoundRegistry.register("monk");
	}
	
	/**
	 * Initialisation des blocks
	 */
	public void initBlocks () {
		
		// Création des blocks
		this.blockKnight     = new BlockKnight    (this.config.blockKnightID,     "BlockKnight"    ).setHardness(2.0F).setResistance(5.0F);
		this.blockKnight2    = new BlockKnight2   (this.config.blockKnight2ID,    "BlockKnight2"   ).setHardness(2.0F).setResistance(5.0F);
		this.blockArcher     = new BlockArcher    (this.config.blockArcherID,     "BlockArcher"    ).setHardness(2.0F).setResistance(5.0F);
		this.blockArcher2    = new BlockArcher2   (this.config.blockArcher2ID,    "BlockArcher2"   ).setHardness(2.0F).setResistance(5.0F);
		this.blockMerc       = new BlockMerc      (this.config.blockMercID,       "BlockMerc"      ).setHardness(2.0F).setResistance(5.0F);
		this.blockMercArcher = new BlockMercArcher(this.config.blockMercArcherID, "BlockMercArcher").setHardness(2.0F).setResistance(5.0F);
		this.blockMage       = new BlockMage      (this.config.blockMageID,       "BlockMage"      ).setHardness(2.0F).setResistance(5.0F);
		this.blockHealer     = new BlockHealer    (this.config.blockHealerID,     "BlockHealer"    ).setHardness(2.0F).setResistance(5.0F);
		this.blockEKnight    = new BlockEKnight   (this.config.blockEKnightID,    "BlockEKnight"   ).setHardness(2.0F).setResistance(5.0F);
		this.blockEArcher    = new BlockEArcher   (this.config.blockEArcherID,    "BlockEArcher"   ).setHardness(2.0F).setResistance(5.0F);
		this.blockEMage      = new BlockEMage     (this.config.blockEMageID,      "BlockEMage"     ).setHardness(2.0F).setResistance(5.0F);
		
	}
	
	/**
	 * Initialisation des items
	 */
	public void initItems () {
		this.itemMedallion = new HItem (this.config.medallionID, "Medallion").setCreativeTab(this.tabCastleDefenders);
	}
	
	/**
	 * // Nom des TileEntities
	 */
	private void initTileEntities () {
		
		GameRegistry.registerTileEntity(TileEntityBlockKnight.class    , "KnightBlock");
		GameRegistry.registerTileEntity(TileEntityBlockKnight2.class   , "Knight2Block");
		GameRegistry.registerTileEntity(TileEntityBlockArcher.class    , "ArcherBlock");
		GameRegistry.registerTileEntity(TileEntityBlockArcher2.class   , "Archer2Block");
		GameRegistry.registerTileEntity(TileEntityBlockMerc.class      , "MercBlock");
		GameRegistry.registerTileEntity(TileEntityBlockMercArcher.class, "MercArcherBlock");
		GameRegistry.registerTileEntity(TileEntityBlockMage.class      , "MageBlock");
		GameRegistry.registerTileEntity(TileEntityBlockHealer.class    , "HearlerBlock");
		GameRegistry.registerTileEntity(TileEntityBlockEKnight.class   , "EnemyKnightBlock");
		GameRegistry.registerTileEntity(TileEntityBlockEArcher.class   , "EnemyArcherBlock");
		GameRegistry.registerTileEntity(TileEntityBlockEMage.class     , "EnemyMageBlock");
		
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
		
		this.registerMob(EntityKnight.class    , "Knight"     , 0x000000);
		this.registerMob(EntityKnight2.class   , "Knight2"    , 0x00FFFC);
		this.registerMob(EntityArcher.class    , "Archer"     , 0x500000);
		this.registerMob(EntityArcher2.class   , "Archer2"    , 0x00FF88);
		this.registerMob(EntityMage.class      , "Mage"       , 0xE10000);
		this.registerMob(EntityEKnight.class   , "EnemyKnight", 0xFF00AA);
		this.registerMob(EntityEArcher.class   , "EnemyArcher", 0xE1AA00);
		this.registerMob(EntityEMage.class     , "EnemyMage"  , 0xE12AFF);
		this.registerMob(EntityMerc.class      , "Merc"       , 0x875600);
		this.registerMob(EntityMercArcher.class, "MercArcher" , 0xBF95FF);
		this.registerMob(EntityHealer.class    , "Healer"     , 0xFF84B4);
		
	}
	
	// TODO faire ca générique
	/**
	 * Enregistre les générateur de terrain
	 * @throws IOException 
	 */
	private void initBuildings () {
		
		BuildingParser parser = new BuildingParser ();
		
		this.buildingMercenary1 = parser.parse ("mercenary1", this.getModId());
		this.buildingMercenary2 = parser.parse ("mercenary2", this.getModId());
		this.buildingMercenary3 = parser.parse ("mercenary3", this.getModId());
		this.buildingMercenary4 = parser.parse ("mercenary4", this.getModId());
		this.buildingCastle1    = parser.parse ("castle1", this.getModId());
		this.buildingCastle2    = parser.parse ("castle2", this.getModId());
		this.buildingCastle3    = parser.parse ("castle3", this.getModId());
		this.buildingCastle4    = parser.parse ("castle4", this.getModId());
	}
	
	/**
	 * Enregistre les générateur de terrain
	 */
	private void initWorldGenerators () {
		
		// Céation du world generator
		WorldGeneratorByBuilding worldGeneratorByBuilding = new WorldGeneratorByBuilding();
		
		int idGroupMercenary = worldGeneratorByBuilding.addGroup (this.config.mercenarySpawnRate);
		int idGroupCastle    = worldGeneratorByBuilding.addGroup (this.config.castleSpawnRate);
		
		// Ajout des batiments
		worldGeneratorByBuilding.addBuilding (idGroupMercenary, this.buildingMercenary1, this.config.mercenaryBuilding1SpawnRate);
		worldGeneratorByBuilding.addBuilding (idGroupMercenary, this.buildingMercenary2, this.config.mercenaryBuilding2SpawnRate);
		worldGeneratorByBuilding.addBuilding (idGroupMercenary, this.buildingMercenary3, this.config.mercenaryBuilding3SpawnRate);
		worldGeneratorByBuilding.addBuilding (idGroupMercenary, this.buildingMercenary4, this.config.mercenaryBuilding4SpawnRate);
		
		worldGeneratorByBuilding.addBuilding (idGroupCastle, this.buildingCastle1, this.config.castleBuilding1SpawnRate);
		worldGeneratorByBuilding.addBuilding (idGroupCastle, this.buildingCastle2, this.config.castleBuilding2SpawnRate);
		worldGeneratorByBuilding.addBuilding (idGroupCastle, this.buildingCastle3, this.config.castleBuilding3SpawnRate);
		worldGeneratorByBuilding.addBuilding (idGroupCastle, this.buildingCastle4, this.config.castleBuilding4SpawnRate);
		
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
	public void registerMob (Class entityClass, String name, int color) {
		
		new Mobactory().register(this, entityClass, name, 0x000000, color);;
		
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
			BiomeGenBase.taiga, BiomeGenBase.taigaHills,
		});
		
	}
}
