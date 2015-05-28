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

import cpw.mods.fml.common.registry.GameRegistry;


public class ModTileEntities {
	
	public static void init() {
		
		GameRegistry.registerTileEntity(TileEntityBlockKnight.class    , ModCastleDefenders.MODID+":KnightBlock");
		GameRegistry.registerTileEntity(TileEntityBlockKnight2.class   , ModCastleDefenders.MODID+":Knight2Block");
		GameRegistry.registerTileEntity(TileEntityBlockArcher.class    , ModCastleDefenders.MODID+":ArcherBlock");
		GameRegistry.registerTileEntity(TileEntityBlockArcher2.class   , ModCastleDefenders.MODID+":Archer2Block");
		GameRegistry.registerTileEntity(TileEntityBlockMerc.class      , ModCastleDefenders.MODID+":MercBlock");
		GameRegistry.registerTileEntity(TileEntityBlockMercArcher.class, ModCastleDefenders.MODID+":MercArcherBlock");
		GameRegistry.registerTileEntity(TileEntityBlockMage.class      , ModCastleDefenders.MODID+":MageBlock");
		GameRegistry.registerTileEntity(TileEntityBlockHealer.class    , ModCastleDefenders.MODID+":HearlerBlock");
		GameRegistry.registerTileEntity(TileEntityBlockEKnight.class   , ModCastleDefenders.MODID+":EnemyKnightBlock");
		GameRegistry.registerTileEntity(TileEntityBlockEArcher.class   , ModCastleDefenders.MODID+":EnemyArcherBlock");
		GameRegistry.registerTileEntity(TileEntityBlockEMage.class     , ModCastleDefenders.MODID+":EnemyMageBlock");
		
	}
}
