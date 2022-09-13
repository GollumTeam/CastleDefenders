package com.gollum.castledefenders;

import com.gollum.castledefenders.common.CommonProxyCastleDefenders;
import com.gollum.castledefenders.common.config.ConfigCastleDefender;
import com.gollum.castledefenders.common.handlers.BuildingHandler;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.castledefenders.inits.ModCreativeTab;
import com.gollum.castledefenders.inits.ModEntities;
import com.gollum.castledefenders.inits.ModItems;
import com.gollum.castledefenders.inits.ModMobs;
import com.gollum.castledefenders.inits.ModSounds;
import com.gollum.castledefenders.inits.ModTileEntities;
import com.gollum.core.ModGollumCoreLib;
import com.gollum.core.common.i18n.I18n;
import com.gollum.core.common.log.Logger;
import com.gollum.core.common.mod.GollumMod;
import com.gollum.core.common.version.VersionChecker;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
	modid = ModCastleDefenders.MODID,
	name = ModCastleDefenders.MODNAME,
	version = ModCastleDefenders.VERSION,
	acceptedMinecraftVersions = ModCastleDefenders.MINECRAFT_VERSION,
	dependencies = ModCastleDefenders.DEPENDENCIES
)
public class ModCastleDefenders extends GollumMod {

	public final static String MODID = "castledefenders";
	public final static String MODNAME = "Castle Defenders";
	public final static String VERSION = "4.0.0DEV [Build Smeagol]";
	public final static String MINECRAFT_VERSION = "1.12.2";
	public final static String DEPENDENCIES = "required-after:"+ModGollumCoreLib.MODID;
	
	@Instance(ModCastleDefenders.MODID)
	public static ModCastleDefenders instance;
	
	@SidedProxy(clientSide = "com.gollum.castledefenders.client.ClientProxyCastleDefenders", serverSide = "com.gollum.castledefenders.common.CommonProxyCastleDefenders")
	public static CommonProxyCastleDefenders proxy;

	/**
	 * Gestion des logs
	 */
	public static Logger logger;
	
	/**
	 * Gestion de l'i18n
	 */
	public static I18n i18n;
	
	/**
	 * La configuration
	 */
	public static ConfigCastleDefender config;
	
	@EventHandler public void handler(FMLPreInitializationEvent event)  { super.handler (event); }
	@EventHandler public void handler(FMLInitializationEvent event)     { super.handler (event); }
	@EventHandler public void handler(FMLPostInitializationEvent event) { super.handler (event); }
	
	/** 1 **/
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
		// Test la version du mod
		new VersionChecker();
		
		// Initialisation des blocks
		ModBlocks.init ();
		
		//Initialisation des items
		ModItems.init ();
		
		//Initialisation des entities
		ModEntities.init ();
		
		//Initialisation des sounds
		ModSounds.init ();
	}
	
	/** 2 **/
	@Override
	public void init(FMLInitializationEvent event) {
		
		// Initialisation les TileEntities
		ModTileEntities.init ();
		
//		// Initialisation des Mobs
		ModMobs.init ();
		
		// Set de l'icon du tab creative
		ModCreativeTab.init();
		
//		// Init des achievements
//		ModAchievements.init();
		
		// Execution du renderer en fonction du serveur ou du client
		proxy.registerRenderers();
		
		MinecraftForge.EVENT_BUS.register(new BuildingHandler());
		
	}
	
	/** 3 **/
	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}
	
}
