package com.gollum.castledefenders.inits;

import com.gollum.castledefenders.ModCastleDefenders;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockArcher;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockArcher2;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockEArcher;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockEKnight;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockEMage;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockHealer;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockKnight;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockKnight2;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockMage;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockMerc;
import com.gollum.castledefenders.common.tileentities.TileEntityBlockMercArcher;

import net.minecraftforge.fml.common.registry.GameRegistry;


public class ModTileEntities {
	
	public static void init() {
		GameRegistry.registerTileEntity(TileEntityBlockKnight.class    , ModCastleDefenders.MODID+":knightblock");
		GameRegistry.registerTileEntity(TileEntityBlockKnight2.class   , ModCastleDefenders.MODID+":knight2block");
		GameRegistry.registerTileEntity(TileEntityBlockArcher.class    , ModCastleDefenders.MODID+":archerblock");
		GameRegistry.registerTileEntity(TileEntityBlockArcher2.class   , ModCastleDefenders.MODID+":archer2block");
		GameRegistry.registerTileEntity(TileEntityBlockMerc.class      , ModCastleDefenders.MODID+":mercblock");
		GameRegistry.registerTileEntity(TileEntityBlockMercArcher.class, ModCastleDefenders.MODID+":mercarcherblock");
		GameRegistry.registerTileEntity(TileEntityBlockMage.class      , ModCastleDefenders.MODID+":mageblock");
		GameRegistry.registerTileEntity(TileEntityBlockHealer.class    , ModCastleDefenders.MODID+":hearlerblock");
		GameRegistry.registerTileEntity(TileEntityBlockEKnight.class   , ModCastleDefenders.MODID+":enemyknightblock");
		GameRegistry.registerTileEntity(TileEntityBlockEArcher.class   , ModCastleDefenders.MODID+":enemyarcherblock");
		GameRegistry.registerTileEntity(TileEntityBlockEMage.class     , ModCastleDefenders.MODID+":enemymageblock");
	}
}
