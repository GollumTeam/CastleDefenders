package mods.castledefenders;

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
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

import com.gollum.core.common.creativetab.GollumCreativeTabs;
import com.gollum.core.common.facory.Mobactory;
import com.gollum.core.common.i18n.I18n;
import com.gollum.core.common.log.Logger;
import com.gollum.core.common.mod.GollumMod;
import com.gollum.core.common.version.VersionChecker;
import com.gollum.core.tools.helper.items.HItem;
import com.gollum.core.tools.registry.SoundRegistry;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ModCastleDefenders.MODID, name = ModCastleDefenders.MODNAME, version = ModCastleDefenders.VERSION, acceptedMinecraftVersions = ModCastleDefenders.MINECRAFT_VERSION, dependencies = ModCastleDefenders.DEPENDENCIES)
public class ModCastleDefenders extends GollumMod {

	public final static String MODID = "CastleDefenders";
	public final static String MODNAME = "Castle Defenders";
	public final static String VERSION = "3.0.0 [Build Smeagol]";
	public final static String MINECRAFT_VERSION = "1.7.2";
	public final static String DEPENDENCIES = "required-after:GollumCoreLib";
	
	@Instance(ModCastleDefenders.MODID)
	public static ModCastleDefenders instance;
	
	@SidedProxy(clientSide = "mods.castledefenders.client.ClientProxyCastleDefenders", serverSide = "mods.castledefenders.common.CommonProxyCastleDefenders")
	public static CommonProxyCastleDefenders proxy;

	/**
	 * Gestion des logs
	 */
	public static Logger log;
	
	/**
	 * Gestion de l'i18n
	 */
	public static I18n i18n;
	
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
	
	
	@EventHandler public void handler(FMLPreInitializationEvent event)  { super.handler (event); }
	@EventHandler public void handler(FMLInitializationEvent event)     { super.handler (event); }
	@EventHandler public void handler(FMLPostInitializationEvent event) { super.handler (event); }
	
	/** 1 **/
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
		// Test la version du mod
		new VersionChecker();
		
		// Initialisation des sons
		this.initSounds ();
		
		// Initialisation des blocks
		this.initBlocks ();
		
		//Initialisation des items
		this.initItems();
	}
	
	/** 2 **/
	@Override
	public void init(FMLInitializationEvent event) {
		
		// Execution du renderer en fonction du serveur ou du client
		proxy.registerRenderers();
		
		// Initialisation les TileEntities
		this.initTileEntities ();
		
		// Ajout des recettes
		this.initRecipes ();
		
		// Initialisation des Mobs
		this.initMobs ();
		
		// Set de l'icon du tab creative
		this.tabCastleDefenders.setIcon(this.blockMercArcher);
		
	}
	
	/** 3 **/
	@Override
	public void postInit(FMLPostInitializationEvent event) {
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
		this.blockKnight     = new BlockKnight    ("BlockKnight"    ).setHardness(2.0F).setResistance(5.0F);
		this.blockKnight2    = new BlockKnight2   ("BlockKnight2"   ).setHardness(2.0F).setResistance(5.0F);
		this.blockArcher     = new BlockArcher    ("BlockArcher"    ).setHardness(2.0F).setResistance(5.0F);
		this.blockArcher2    = new BlockArcher2   ("BlockArcher2"   ).setHardness(2.0F).setResistance(5.0F);
		this.blockMerc       = new BlockMerc      ("BlockMerc"      ).setHardness(2.0F).setResistance(5.0F);
		this.blockMercArcher = new BlockMercArcher("BlockMercArcher").setHardness(2.0F).setResistance(5.0F);
		this.blockMage       = new BlockMage      ("BlockMage"      ).setHardness(2.0F).setResistance(5.0F);
		this.blockHealer     = new BlockHealer    ("BlockHealer"    ).setHardness(2.0F).setResistance(5.0F);
		this.blockEKnight    = new BlockEKnight   ("BlockEKnight"   ).setHardness(2.0F).setResistance(5.0F);
		this.blockEArcher    = new BlockEArcher   ("BlockEArcher"   ).setHardness(2.0F).setResistance(5.0F);
		this.blockEMage      = new BlockEMage     ("BlockEMage"     ).setHardness(2.0F).setResistance(5.0F);
		
	}
	
	/**
	 * Initialisation des items
	 */
	public void initItems () {
		this.itemMedallion = new HItem ("Medallion").setCreativeTab(this.tabCastleDefenders);
	}
	
	/**
	 * // Nom des TileEntities
	 */
	private void initTileEntities () {
		
		GameRegistry.registerTileEntity(TileEntityBlockKnight.class    , this.MODID+"KnightBlock");
		GameRegistry.registerTileEntity(TileEntityBlockKnight2.class   , this.MODID+"Knight2Block");
		GameRegistry.registerTileEntity(TileEntityBlockArcher.class    , this.MODID+"ArcherBlock");
		GameRegistry.registerTileEntity(TileEntityBlockArcher2.class   , this.MODID+"Archer2Block");
		GameRegistry.registerTileEntity(TileEntityBlockMerc.class      , this.MODID+"MercBlock");
		GameRegistry.registerTileEntity(TileEntityBlockMercArcher.class, this.MODID+"MercArcherBlock");
		GameRegistry.registerTileEntity(TileEntityBlockMage.class      , this.MODID+"MageBlock");
		GameRegistry.registerTileEntity(TileEntityBlockHealer.class    , this.MODID+"HearlerBlock");
		GameRegistry.registerTileEntity(TileEntityBlockEKnight.class   , this.MODID+"EnemyKnightBlock");
		GameRegistry.registerTileEntity(TileEntityBlockEArcher.class   , this.MODID+"EnemyArcherBlock");
		GameRegistry.registerTileEntity(TileEntityBlockEMage.class     , this.MODID+"EnemyMageBlock");
		
	}
	
	/**
	 * Ajout des recettes
	 */
	private void initRecipes () {
		
		GameRegistry.addRecipe(new ItemStack(this.blockKnight ,    1), new Object[] { " X ", "XYX", " X ", 'X', Items.iron_ingot, 'Y', Items.iron_sword });
		GameRegistry.addRecipe(new ItemStack(this.blockKnight2,    1), new Object[] { " X ", "XYX", " X ", 'X', Items.iron_ingot, 'Y', Items.diamond_sword });
		GameRegistry.addRecipe(new ItemStack(this.blockArcher ,    1), new Object[] { " X ", "XYX", " X ", 'X', Items.iron_ingot, 'Y', Items.bow });
		GameRegistry.addRecipe(new ItemStack(this.blockArcher2,    1), new Object[] { "ZXZ", "XYX", "ZXZ", 'X', Items.iron_ingot, 'Y', Items.bow,          'Z', Items.diamond });
		GameRegistry.addRecipe(new ItemStack(this.blockMerc,       1), new Object[] { "KXK", "XYX", "KXK", 'X', Blocks.planks,   'Y', Items.diamond_sword, 'K', Items.gold_ingot });
		GameRegistry.addRecipe(new ItemStack(this.blockMercArcher, 1), new Object[] { "KXK", "XYX", "KXK", 'X', Blocks.planks,   'Y', Items.bow,           'K', Items.gold_ingot });
		GameRegistry.addRecipe(new ItemStack(this.blockMage   ,    1), new Object[] { "YYY", "XXX", "XXX", 'X', Blocks.obsidian, 'Y', this.itemMedallion });
		GameRegistry.addRecipe(new ItemStack(this.blockHealer ,    1), new Object[] { "XYX", "XYX", "XYX", 'X', Blocks.planks,   'Y', this.itemMedallion });
		
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

	/**
	 * Enregistre un mob
	 * @param entityClass
	 * @param name
	 * @param id
	 * @param spawn
	 */
	public void registerMob (Class entityClass, String name, int color) {
		
		new Mobactory().register(entityClass, name, 0xFFFFFF, color);;
		
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
