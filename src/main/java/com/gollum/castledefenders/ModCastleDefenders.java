package com.gollum.castledefenders;

import net.minecraftforge.common.MinecraftForge;

import com.gollum.castledefenders.common.CommonProxyCastleDefenders;
import com.gollum.castledefenders.common.config.ConfigCastleDefender;
import com.gollum.castledefenders.inits.ModBlocks;
import com.gollum.castledefenders.inits.ModCreativeTab;
import com.gollum.castledefenders.inits.ModItems;
import com.gollum.castledefenders.inits.ModMobs;
import com.gollum.castledefenders.inits.ModRecipes;
import com.gollum.castledefenders.inits.ModTileEntities;
import com.gollum.core.ModGollumCoreLib;
import com.gollum.core.common.i18n.I18n;
import com.gollum.core.common.log.Logger;
import com.gollum.core.common.mod.GollumMod;
import com.gollum.core.common.version.VersionChecker;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
	modid = ModCastleDefenders.MODID,
	name = ModCastleDefenders.MODNAME,
	version = ModCastleDefenders.VERSION,
	acceptedMinecraftVersions = ModCastleDefenders.MINECRAFT_VERSION,
	dependencies = ModCastleDefenders.DEPENDENCIES
)
public class ModCastleDefenders extends GollumMod {

	public final static String MODID = "CastleDefenders";
	public final static String MODNAME = "Castle Defenders";
	public final static String VERSION = "3.0.0 [Build Smeagol]";
	public final static String MINECRAFT_VERSION = "1.7.10";
	public final static String DEPENDENCIES = "required-after:"+ModGollumCoreLib.MODID;
	
	@Instance(ModCastleDefenders.MODID)
	public static ModCastleDefenders instance;
	
	@SidedProxy(clientSide = "com.gollum.castledefenders.client.ClientProxyCastleDefenders", serverSide = "com.gollum.castledefenders.common.CommonProxyCastleDefenders")
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
	}
	
	/** 2 **/
	@Override
	public void init(FMLInitializationEvent event) {
		
		// Execution du renderer en fonction du serveur ou du client
		proxy.registerRenderers();
		
		// Initialisation les TileEntities
		ModTileEntities.init ();
		
		// Ajout des recettes
		ModRecipes.init ();
		
		// Initialisation des Mobs
		ModMobs.init ();
		
		// Set de l'icon du tab creative
		ModCreativeTab.init();
		
	}
	
	/** 3 **/
	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}
}
