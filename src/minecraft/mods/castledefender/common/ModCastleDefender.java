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
import mods.castledefender.common.items.ItemMedallion;
import mods.castledefender.common.tileentities.TileEntityBlockKnight;
import mods.castledefender.utils.ConfigLoader;
import mods.castledefender.utils.ConfigProp;
import mods.castledefender.utils.VersionChecker;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "CastleDef", name = "Castle Defender", version = "2.0.0 [Build Smeagol]", acceptedMinecraftVersions = "1.6.4")
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
		
		GameRegistry.registerTileEntity(TileEntityBlockKnight.class, "Knight Block");
		
//		GameRegistry.registerTileEntity(TileEntityBlockArcher.class,
//				"BlockArcher");
		
//		GameRegistry
//				.registerTileEntity(TileEntityBlockMerc.class, "Merc Block");
		
//		GameRegistry.registerTileEntity(TileEntityBlockEKnight.class,
//				"Enemy Knight Block");
		
//		GameRegistry.registerTileEntity(TileEntityBlockEArcher.class,
//				"Enemy Archer Block");
		
//		GameRegistry.registerTileEntity(TileEntityBlockEArcher.class,
//				"Mage Block");
		
//		GameRegistry.registerTileEntity(TileEntityBlockEArcher.class,
//				"Enemy Mage Block");
		
		
		
	}
	
}
